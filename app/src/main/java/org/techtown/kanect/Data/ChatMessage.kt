package org.techtown.kanect.Data

data class ChatMessage(

    var userId : Long = 0,
    var userImg : String = "",
    var userName : String = "",
    var text : String = "",
    var timestamp : String = "",//시간
    var dayStamp : String = "",
    var dayMessage : Boolean = true //day 메세지인지 확인
)