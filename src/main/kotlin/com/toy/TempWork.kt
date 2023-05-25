package com.toy

class TempWork {
}

// 생성자 대신 정적 팩토리 메소드 사용
fun main(){
    Member.createMember("Kim", 30)
    Member.createNotAge("Hong")

}

class Member constructor(val name: String, val age: Int) {
    companion object{
        fun createMember(name: String, age: Int) = Member(name, age)
        fun createNotAge(name: String) = Member(name, 0)
    }

    fun of(age: Int){
        if ( age > 10 ){
           // return Adult
        }
    }


}

