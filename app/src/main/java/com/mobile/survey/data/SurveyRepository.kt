package com.mobile.survey.data

import androidx.lifecycle.LiveData

class SurveyRepository(private val surveyDao: SurveyDao) {

    val readAllData: LiveData<List<Survey>> = surveyDao.readAllData()

    suspend fun addSurvey(survey: Survey){
        surveyDao.addSurvey(survey)
    }

}