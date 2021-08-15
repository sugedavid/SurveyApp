package com.mobile.survey.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SurveyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSurvey(survey: Survey)

    @Query("SELECT * FROM survey_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Survey>>

}