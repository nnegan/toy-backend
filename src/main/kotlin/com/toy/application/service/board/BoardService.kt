package com.toy.application.service.board

import com.toy.framework.cache.RedisSequence
import com.toy.application.port.input.board.BoardUseCase
import com.toy.application.port.output.repository.board.BoardPort
import com.toy.domain.board.BOARD_ID_PREFIX
import com.toy.domain.board.BOARD_KEY
import com.toy.domain.board.model.Board
import com.toy.domain.board.model.BoardComment
import com.toy.domain.board.model.SearchBoard
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.util.concurrent.TimeUnit

@Service
@Transactional
class BoardService(
    private val boardPort: BoardPort,
    private val redisSequence: RedisSequence,
    private val redissonClient: RedissonClient,
    private val transactionManager: PlatformTransactionManager,
) : BoardUseCase {

    private val log = LoggerFactory.getLogger(javaClass)


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun findOneBoard(boardId: String): Board {
        return boardPort.findOneBoard(boardId)
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun searchPageBoardList(searchBoard: SearchBoard): Page<Board> {
        return boardPort.searchPageBoardList(searchBoard)
    }

    //@Transactional
    override fun createBoard(board: Board): String {
        val boardCopy = board.copy(boardId = redisSequence.getSequenceLeftPad8(BOARD_ID_PREFIX, BOARD_KEY))

        val lock: RLock = redissonClient.getLock("$boardCopy.boardId")

        log.debug("lockId = $boardCopy.boardId")

        // 5초간 Lock 획득을 시도한다. 그 Lock을 획득하면 6초간 Lock을 유지하게 된다.
        val isLocked = lock.tryLock(5, 6, TimeUnit.SECONDS)

        log.debug("isLocked = $isLocked")

        // @Transactional 대신 코드내에서 직접 트랜잭션 관리
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())

        log.debug("status = $status")

        val result: String

        try {
            // Lock 획득에 실패한 경우이다. 5초 이상 기다렸는데 앞사람들의 작업이 끝나지 않았다면 이 로직이 수행될 것이다.
            if (!isLocked) {
                throw java.lang.RuntimeException("failed to get RLock")
            }
            try {


                log.debug("boardCopy = {}", boardCopy.toString())

                result =  boardPort.createBoard(boardCopy)
                // 성공적으로 비즈니스 로직이 수행됐다면 트랜잭션을 커밋해준다.
                transactionManager.commit(status)
                return result
               } catch (e: RuntimeException) {
                    // 비즈니스 로직 수행중 예외가 발생한 경우 트랜잭션을 롤백해준다.
                    transactionManager.rollback(status)
                    throw Exception(e.message.toString())
               }
           } catch (e: InterruptedException) {
                // 쓰레드가 작동전 interrupt 될 경우에 대한 예외처리
                throw Exception("Thread Interrupted")
           } finally {
                // 로직 수행 후 Lock을 반환한다. 다만 Lock의 주체가 이 로직을 호출한 쓰레드일 경우에만 반환할 수 있다.
                if (lock.isLocked && lock.isHeldByCurrentThread) {
                lock.unlock();
           }
        }
    }

    override fun modifyBoard(boardId: String, board: Board): String {
        val lock: RLock = redissonClient.getLock("$boardId")

        log.debug("lockId = $boardId")

        // 5초간 Lock 획득을 시도한다. 그 Lock을 획득하면 6초간 Lock을 유지하게 된다.
        val isLocked = lock.tryLock(5, 6, TimeUnit.SECONDS)

        log.debug("isLocked = $isLocked")

        // @Transactional 대신 코드내에서 직접 트랜잭션 관리
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())

        log.debug("status = $status")

        val result: String

        try {
            // Lock 획득에 실패한 경우이다. 5초 이상 기다렸는데 앞사람들의 작업이 끝나지 않았다면 이 로직이 수행될 것이다.
            if (!isLocked) {
                throw java.lang.RuntimeException("failed to get RLock")
            }
            try {
                result =  boardPort.modifyBoard(boardId, board)
                // 성공적으로 비즈니스 로직이 수행됐다면 트랜잭션을 커밋해준다.
                transactionManager.commit(status)
                return result
            } catch (e: RuntimeException) {
                // 비즈니스 로직 수행중 예외가 발생한 경우 트랜잭션을 롤백해준다.
                transactionManager.rollback(status)
                throw Exception(e.message.toString())
            }
        } catch (e: InterruptedException) {
            // 쓰레드가 작동전 interrupt 될 경우에 대한 예외처리
            throw Exception("Thread Interrupted")
        } finally {
            // 로직 수행 후 Lock을 반환한다. 다만 Lock의 주체가 이 로직을 호출한 쓰레드일 경우에만 반환할 수 있다.
            if (lock.isLocked && lock.isHeldByCurrentThread) {
                lock.unlock();
            }
        }

    //return boardPort.modifyBoard(apiId, board)
    }

    override fun deleteBoard(boardId: String) {
    return boardPort.deleteBoard(boardId)
    }

    /**
     * 강제 Lock 생성 테스트 코드
     */
    override fun forceLock(boardId: String){
        val lock: RLock = redissonClient.getLock("$boardId")
        log.debug("lock is  ${lock.isLocked}")

        // lock 상태이면
        if ( lock.isLocked ) {
            log.debug("unlock $boardId")
            lock.forceUnlock()

        }else {
            log.debug("lock $boardId")
            lock.lock()


        }
    }

    override fun createComment(boardComment: BoardComment) {
        boardPort.createComment(boardComment)
    }
}
