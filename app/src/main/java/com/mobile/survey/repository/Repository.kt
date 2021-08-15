package com.mobile.survey.repository

import com.mobile.survey.model.Post
import com.mobile.survey.rest.RetrofitInstance

class Repository {

    suspend fun getPost(): Post{
        return RetrofitInstance.api.getPost()
    }
}