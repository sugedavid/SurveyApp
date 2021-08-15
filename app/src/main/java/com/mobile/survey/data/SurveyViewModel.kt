package com.mobile.survey.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SurveyViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Survey>>
    private val repository: SurveyRepository

    init {
        val userDao = SurveyDatabase.getDatabase(application).surveyDao()
        repository = SurveyRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addSurvey(survey: Survey){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSurvey(survey)
        }
    }

}