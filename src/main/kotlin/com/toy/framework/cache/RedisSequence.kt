package com.toy.framework.cache

interface RedisSequence {

    /**
     * 해당 sequence(채번)값을 리턴한다.
     * (해당 key가 없으면 1을 리턴함)
     *
     * @param key     : 채번에 사용되는 key값
     * @return       : 리턴값(Long) = 현재값(Long) + 1. (Max : 2147483647)
     * @throws Exception 에러 throw함
     */
    fun getSequence(key: String): Long

    /**
     * 해당 sequence(채번)값을 리턴한다.
     * (해당 key가 없으면 1을 리턴함)
     *
     * @param key     : 채번에 사용되는 key값
     * @return       : 리턴값(Long) = 현재값(Long) + 1. (Max : 2147483647)
     * @throws Exception 에러 throw함
     */
    fun getSequenceLeftPad8(prifix:String, key: String): String

    /**
     * 해당 sequence 현재값을 리턴한다.
     *
     * @param key     : 채번에 사용되는 key값
     * @return       : 리턴값(Long) = 현재값(Long) Max : 2147483647)
     */
    fun getCurrentValue(key: String?): Long


    /**
     * 해당 sequence(채번)값을 리턴 and 값이 입력Max값보타 크면 1을 리턴한다. (cycling)
     * (해당 key가 없으면 1을 리턴함)
     *
     * @param key      : 채번에 사용되는 key값, 입력 Max값
     * @param maxValue: 해당 key가 가질 수 있는 최대값. 넘으면 1을 리턴한다.
     * @return        : 리턴값(Long) = 현재값(Long) + 1. (Max : 2147483647)
     * @throws Exception 에러 throw함
     */
    fun getSequenceMax(key: String, maxValue: Long): Long


    /**
     * sequence를 만들어 준다. --> 추후 BO의 관리자 기능으로 이전 필요.
     * (해당 key가 없으면 1을 리턴함)
     *
     * @param key     : 채번에 사용되는 key값
     * @return       : 리턴값(Long) = 현재값(Long) + 1. (Max : 2147483647)
     * @throws Exception 에러 throw함
     */
    fun addSequence(key: String): Long

    /**
     * sequence값을 강제 업데이트. --> 추후 BO의 관리자 기능으로 이전 필요.
     *
     * @param key     : 채번에 사용되는 key값, 수정할 시퀀스값
     * @return       :
     * @throws Exception 에러 throw함
     */
    fun forceUpdateSequence(key: String, value: Long): Long
}