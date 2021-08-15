package com.mobile.survey.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Survey::class], version = 1, exportSchema = false)
abstract class SurveyDatabase : RoomDatabase() {

    abstract fun surveyDao(): SurveyDao

    companion object {
        @Volatile
        private var INSTANCE: SurveyDatabase? = null

        fun getDatabase(context: Context): SurveyDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SurveyDatabase::class.java,
                    "survey_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}