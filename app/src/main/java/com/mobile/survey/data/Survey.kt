package com.mobile.survey.data

import androidx.room.*

@Entity(tableName = "survey_table")
data class Survey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var start_question_id:String,
//    val questions: List<SurveyQuestions>,
//    val strings: Map<String,Map<String,String>>,
)

@Entity(tableName = "questions_table")
data class SurveyQuestions(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var question_type:String,
    var question_text:String,
//    val strings: Map<String,Map<String,String>>,
)

@Entity()
data class SurveyWithQuestions(
    @Embedded val survey: Survey,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
//        associateBy = Junction(PlaylistSongCrossRef::class)
    )
    val questions: List<SurveyQuestions>
)