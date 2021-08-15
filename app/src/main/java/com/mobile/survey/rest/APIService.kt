package com.mobile.survey.rest

import com.mobile.survey.model.Post
import retrofit2.http.GET

interface APIService {

    @GET("v3/d628facc-ec18-431d-a8fc-9c096e00709a")    //End Url
   suspend fun getPost() : Post
}
