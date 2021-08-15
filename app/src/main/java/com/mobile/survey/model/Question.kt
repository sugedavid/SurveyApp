package com.mobile.survey.model


//This class maps the json keys to the object
class Question {

    val id: String = ""
    val question_type: String  = ""
    val question_text: String  = ""
    val options: Array<Options> = arrayOf(Options())
}
