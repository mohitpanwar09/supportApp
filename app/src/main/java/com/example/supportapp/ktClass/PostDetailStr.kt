package com.example.supportapp.ktClass

class PostDetailStr (val uid:String,val namePost:String, val postDes:String, val postImageUrl:String, val likeCount:Int) {
    constructor():this("","","","",0,)
}