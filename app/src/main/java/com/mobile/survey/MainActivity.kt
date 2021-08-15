package com.mobile.survey

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mobile.survey.rest.APIService
import com.google.android.material.snackbar.Snackbar
import com.mobile.survey.data.Survey
import com.mobile.survey.data.SurveyViewModel
import com.mobile.survey.model.Question
import com.mobile.survey.repository.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel
    private lateinit var mSurveyViewModel: SurveyViewModel

    private var indexMain: Int = 0
    private var questionList: List<Question> = listOf(Question())
    private var strings: Map<String,Map<String,String>> = mapOf()
    private var start_question_id: String = ""
    private var surveyList: List<Survey> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)

        // local db
        mSurveyViewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)
        mSurveyViewModel.readAllData.observe(this, Observer { survey ->
            surveyList = survey
        })

       // get request
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        insertDataToDatabase()
        viewModel.myResponse.observe(this, Observer { response ->
            updateAnswerForm()
            questionList = response.questions
            strings = response.strings
            start_question_id = response.start_question_id
            val x: Map<String, String>? = strings["en"]
            if (x != null) {
                if( x.containsKey(questionList[indexMain].question_text)){
                    txtQuestion.setText((indexMain + 1).toString() + ". " + (x?.get(questionList[indexMain].question_text)))
                }
            }

            //Spinner
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, response.questions[1].options)
            spnGender.adapter = adapter
            spnGender.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    spnGender.setSelection(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        })



        // Next Button
        btnNext.setOnClickListener{
            if(indexMain < questionList.size -1) {
                indexMain++
                val x: Map<String, String>? = strings["en"]
                if (x != null) {
                    if( x.containsKey(questionList[indexMain].question_text)){
                        txtQuestion.setText((indexMain + 1).toString() + ". " + (x?.get(questionList[indexMain].question_text)))
                    }
                }
                updateButtons()
                updateAnswerForm()
            }
        }
        // Previous Button
        btnPrevious.setOnClickListener{
            if(indexMain > 0 ) {
                indexMain--
                val x: Map<String, String>? = strings["en"]
                if (x != null) {
                    if( x.containsKey(questionList[indexMain].question_text)){
                        txtQuestion.setText((indexMain + 1).toString() + ". " + (x?.get(questionList[indexMain].question_text)))
                    }
                }
            }
            updateButtons()
            updateAnswerForm()
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onStart() {
        super.onStart()

        updateButtons()
        updateAnswerForm()
    }

    fun onSNACK(view: View, message:String){
        val snackbar = Snackbar.make(view, message,
            Snackbar.LENGTH_LONG).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.BLUE)
        snackbar.show()
    }

    fun updateButtons(){
        if (indexMain == 0){
            btnPrevious.isVisible = false
            btnNext.isVisible = true
            btnNext.setText("Next")
        }
       else if (indexMain >0 && indexMain < questionList.size -1){
            btnPrevious.isVisible = true
            btnNext.isVisible = true
            btnNext.setText("Next")
        }
        else {
            btnPrevious.isVisible = true
            btnNext.isVisible = true
            btnNext.setText("Finish")
        }
    }

    fun updateAnswerForm(){
        val x: String = questionList[indexMain].question_type

        if (x == "FREE_TEXT"){
            edtFreeText.isVisible = true
            spnGender.isVisible = false
            edtTypeVal.isVisible = false
        }
        else if (x == "SELECT_ONE"){
            edtFreeText.isVisible = false
            spnGender.isVisible = true
            edtTypeVal.isVisible = false
        }
        else if (x == "TYPE_VALUE"){
            edtFreeText.isVisible = false
            spnGender.isVisible = false
            edtTypeVal.isVisible = true
        }
        else {
            edtFreeText.isVisible = true
        }
    }

    //insert data to db
    private fun insertDataToDatabase() {
        if(!surveyList.isEmpty()){
            Toast.makeText(this, "Data Successfully Saved", Toast.LENGTH_LONG).show()
        }else{
            viewModel.getPost()
            //Create Survey Object
            val survey = Survey("0", start_question_id)
            // Add Data to Database
            mSurveyViewModel.addSurvey(survey)
        }
    }

}