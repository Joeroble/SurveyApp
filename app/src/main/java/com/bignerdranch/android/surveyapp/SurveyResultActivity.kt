package com.bignerdranch.android.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

class SurveyResultActivity : AppCompatActivity() {

    // Pre-initialization for the buttons and textviews.
    private lateinit var resetCountersButton: Button
    private lateinit var continueSurveyButton: Button
    private lateinit var noCounterText: TextView
    private lateinit var yesCounterText: TextView

    // Establishes the surveyViewModel to interact with it, and pull data.
    private val surveyViewModel: SurveyViewModel by lazy {
        ViewModelProvider(this).get(SurveyViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_result)
        // Links up the buttons and textviews with variables.
        resetCountersButton = findViewById(R.id.reset_button)
        continueSurveyButton = findViewById(R.id.continue_survey_button)
        noCounterText = findViewById(R.id.no_counter)
        yesCounterText = findViewById(R.id.yes_counter)

        // sets the counts in this activity by calling on intent.getIntExtra to pull the associated
        // values to EXTRA_YES_COUNT, and EXTRA_NO_COUNT
        surveyViewModel.yesCount = intent.getIntExtra(EXTRA_YES_COUNT, 0)
        surveyViewModel.noCount = intent.getIntExtra(EXTRA_NO_COUNT, 0)

        // Calls on resetCounters to reset the counter totals.
        resetCountersButton.setOnClickListener{
            resetCounters()
        }
        // Calls on finish and moves back to main activity while changing nothing.
        continueSurveyButton.setOnClickListener{
            finish()
        }
        updateCounters()
    }


    //Creates the resetCounters function, modified from the main activity version - this will
    // set the values for EXTRA_YES_COUNT, and EXTRA_NO_COUNT to 0, and package them with RESULT_OK,
    // and then finish the activity moving back to the main screen where surveyResultActivity
    // will pass the package to the handler.
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