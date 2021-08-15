package com.mobile.survey.model
import com.mobile.survey.model.Question
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Post(
                val id: String,
                var start_question_id:String,
                val questions: List<Question>,
                val strings: Map<String,Map<String,String>>,
                ){

    class Deserializer : ResponseDeserializable<Array<Post>> {
        override fun deserialize(content: String): Array<Post>
                = Gson().fromJson(content, Array<Post>::class.java)
    }
}