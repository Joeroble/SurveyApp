package com.bignerdranch.android.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

class SurveyResultActivity : AppCompatActivity() {


    private lateinit var resetCountersButton: Button
    private lateinit var continueSurveyButton: Button
    private lateinit var noCounterText: TextView
    private lateinit var yesCounterText: TextView


    private val surveyViewModel: SurveyViewModel by lazy {
        ViewModelProvider(this).get(SurveyViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_result)

        resetCountersButton = findViewById(R.id.reset_button)
        continueSurveyButton = findViewById(R.id.continue_survey_button)
        noCounterText = findViewById(R.id.no_counter)
        yesCounterText = findViewById(R.id.yes_counter)

        surveyViewModel.yesCount = intent.getIntExtra(EXTRA_YES_COUNT, 0)
        surveyViewModel.noCount = intent.getIntExtra(EXTRA_NO_COUNT, 0)

        resetCountersButton.setOnClickListener{
            resetCounters()
        }

        continueSurveyButton.setOnClickListener{
            finish()
        }
        updateCounters()
    }


    //Creates the resetCounters function, which will call upon the resetCounters() in
    // the SurveyViewModel to reset the counters, it will call upon updateCounters() to update the counts.
    private fun resetCounters(){
        Intent().apply{
            putExtra(EXTRA_YES_COUNT, 0)
            putExtra(EXTRA_NO_COUNT, 0)
            setResult(RESULT_OK, this)
            finish()
        }

    }

    //Updates the counters with the current counts stored in SurveyViewModel, stores them in
    // yesCount/noCount values and passes them to the yesCounterText/noCounterText with toString()
    // conversion to display for the user.
    private fun updateCounters(){
        val yesCount = surveyViewModel.yesCount
        val noCount = surveyViewModel.noCount
        yesCounterText.text = yesCount.toString()
        noCounterText.text = noCount.toString()

    }
}