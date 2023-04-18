package com.toy.adapters.output.persistence.board

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.util.StringUtils
import com.querydsl.jpa.impl.JPAQueryFactory
import com.toy.adapters.output.persistence.board.data.QSearchBoardDto
import com.toy.domain.board.model.Board
import com.toy.domain.board.model.SearchBoard
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BoardRepositoryCustomImpl (
    private val boardMapper: BoardMapper,
    private val jpaQueryFactory: JPAQueryFactory,
    private val boardEntity: QBoardEntity = QBoardEntity.boardEntity,
    ) : BoardRepositoryCustom {

    override fun searchBoardPageList(searchBoard: SearchBoard): Page<Board> {

        val queryCount = jpaQueryFactory
            .selectFrom(boardEntity)
            .where(
                condPredicate(searchBoard)
            )
            .fetch().size

        val queryResult = jpaQueryFactory
            .select(
                QSearchBoardDto(
                    boardEntity.boardId,
                    boardEntity.title,
                    boardEntity.body,
                    boardEntity.useYn,
                    boardEntity.createdBy,
                    boardEntity.createdTime,
                    boardEntity.modifiedBy,
                    boardEntity.modifiedTime
                )
            )
            .from(boardEntity)
            .where(
                condPredicate(searchBoard)
            )
            //.orderBy(searchBoard.pageInfo. ..desc())
            .limit(searchBoard.pageInfo!!.pageSize.toLong())
            .fetch()

        val pageable = PageRequest.of(searchBoard.pageInfo!!.pageNumber, searchBoard.pageInfo!!.pageSize)

        return PageImpl(queryResult.map(boardMapper::searchMapToDomain), pageable, queryCount.toLong())
    }

    private fun condPredicate(searchBoard: SearchBoard): BooleanBuilder {
        var booleanBuilder = BooleanBuilder()

        if ( !StringUtils.isNullOrEmpty(searchBoard.boardId))
            booleanBuilder.and(boardEntity.boardId.eq(searchBoard.boardId))

        if ( !StringUtils.isNullOrEmpty(searchBoard.title))
            booleanBuilder.and(boardEntity.title.contains(searchBoard.title))

        if ( !StringUtils.isNullOrEmpty(searchBoard.body))
            booleanBuilder.and(boardEntity.body.contains(searchBoard.body))

        if ( !StringUtils.isNullOrEmpty(searchBoard.toCreatedTime))
            if ( !StringUtils.isNullOrEmpty(searchBoard.endCreatedTime))
                boardEntity.createdTime.between(
                    LocalDateTime.parse(searchBoard.toCreatedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDateTime.parse(searchBoard.endCreatedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )

        if ( !StringUtils.isNullOrEmpty(searchBoard.toModifiedTime))
            if ( !StringUtils.isNullOrEmpty(searchBoard.endModifiedTime))
                boardEntity.modifiedTime.between(
                    LocalDateTime.parse(searchBoard.toModifiedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDateTime.parse(searchBoard.endModifiedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                )

        return booleanBuilder
    }

    /*

    override fun searchAPIPageList(searchPageAPIInventoryRequestDto: SearchPageAPIInventoryRequestDto, pageable: Pageable): Page<SearchPageAPIInventory> {

        // Total Page
        val queryCount = jpaQueryFactory
            .selectFrom(apiInventoryEntity)
            .where(
                condCallMethod(searchPageAPIInventoryRequestDto.callMethod),
                condAPIUrl(searchPageAPIInventoryRequestDto.apiUrl),
                condAPINm(searchPageAPIInventoryRequestDto.apiUrl),
                condJobDivsCd(searchPageAPIInventoryRequestDto.jobDivsCd),
                condSvcDivsCd(searchPageAPIInventoryRequestDto.svcDivsCd),
                condUseYn(searchPageAPIInventoryRequestDto.useYn)
            )
            .fetch().size

        val queryResult = jpaQueryFactory
            .select(QSearchPageAPIInventory(
                apiInventoryEntity.apiId,
                apiInventoryEntity.callMethod,
                apiInventoryEntity.apiUrl,
                apiInventoryEntity.apiNm
                )
            )
            .from(apiInventoryEntity)
            .where(
                condCallMethod(searchPageAPIInventoryRequestDto.callMethod),
                condAPIUrl(searchPageAPIInventoryRequestDto.apiUrl),
                condAPINm(searchPageAPIInventoryRequestDto.apiUrl),
                condJobDivsCd(searchPageAPIInventoryRequestDto.jobDivsCd),
                condSvcDivsCd(searchPageAPIInventoryRequestDto.svcDivsCd),
                condUseYn(searchPageAPIInventoryRequestDto.useYn)
                )
            .orderBy(apiInventoryEntity.modifiedTime.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return PageImpl(queryResult, pageable, queryCount.toLong())

    }
    private fun condCallMethod(callMethod: String) =
        if (StringUtils.isNullOrEmpty(callMethod)) null
        else apiInventoryEntity.callMethod.eq(callMethod)

    private fun condAPIUrl(apiUrl: String) =
        if (StringUtils.isNullOrEmpty(apiUrl)) null
        else apiInventoryEntity.apiUrl.contains(apiUrl)

    private fun condAPINm(apiNm: String) =
        if (StringUtils.isNullOrEmpty(apiNm)) null
        else apiInventoryEntity.apiNm.contains(apiNm)
    private fun condJobDivsCd(jobDivsCd: String) =
        if (StringUtils.isNullOrEmpty(jobDivsCd)) null
        else apiInventoryEntity.jobDivsCd.eq(jobDivsCd)
    private fun condSvcDivsCd(svcDivsCd: String) =
        if (StringUtils.isNullOrEmpty(svcDivsCd)) null
        else apiInventoryEntity.svcDivsCd.eq(svcDivsCd)
    private fun condUseYn(useYn: String) =
        if ( StringUtils.isNullOrEmpty(useYn) ) null
        else apiInventoryEntity.useYn.eq(useYn)
*/



}


