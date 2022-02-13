package com.bignerdranch.android.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

const val EXTRA_YES_COUNT = "com.bignerdranch.android.surveyapp.yes_count"
const val EXTRA_NO_COUNT = "com.bignerdranch.android.surveyapp.no_count"

class MainActivity : AppCompatActivity() {

    // Establishes the components of the UI, uses lateinit var as they will be initialized in onCreate
    private lateinit var yesCountButton: Button
    private lateinit var noCountButton: Button
    private lateinit var noCounterText: TextView
    private lateinit var yesCounterText: TextView
    private lateinit var surveyResultsButton: Button

    // Establishes the view model that will be used to store the counts
    private val surveyViewModel: SurveyViewModel by lazy {
        ViewModelProvider(this).get(SurveyViewModel::class.java)
    }

    // Establishes the surveyResultLauncher that will take teh results from SurveyResultActivity and store it in results, and pass it to
    // handleSurveyResult.
    private val surveyResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result -> handleSurveyResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializes the buttons and text views
        yesCountButton = findViewById(R.id.yes_button)
        noCountButton = findViewById(R.id.no_button)
        noCounterText = findViewById(R.id.no_counter)
        yesCounterText = findViewById(R.id.yes_counter)
        surveyResultsButton = findViewById(R.id.result_button)



        // Sets up the on click listeners for the buttons, which call the respective functions
        // for each button(increase the yes or no counts, and reset the count).  It will
        // update the counters at the end

        // Replaces the reset button with a Results button, this will call on the SurveyResultActivity,
        //it will pass the current counts and associate them with their respective EXTRA consts, and
        // call on the surveyResultLauncher.
        yesCountButton.setOnClickListener{
            increaseYesCount()
        }

        noCountButton.setOnClickListener{
            increaseNoCount()
        }

        surveyResultsButton.setOnClickListener{
            Intent(this, SurveyResultActivity::class.java).apply{
                putExtra(EXTRA_YES_COUNT, surveyViewModel.yesCount)
                putExtra(EXTRA_NO_COUNT, surveyViewModel.noCount)
                surveyResultLauncher.launch(this)
            }
        }

        updateCounters()
    }

    // Creates the increaseYesCount() function, which will call upon the increaseYesCount() in
    // the SurveyViewModel to increase the count, it will call upon updateCounters() to update the counts.
    private fun increaseYesCount(){
        surveyViewModel.increaseYesCount()
        updateCounters()
    }

    //Creates the increaseNoCount() function, which will call upon the increaseNoCount() in
    // the SurveyViewModel to increase the count, it will call upon updateCounters() to update the counts.
    private fun increaseNoCount(){
        surveyViewModel.increaseNoCount()
        updateCounters()
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

    // sets up the handleSurveyResult function, this will take the result returned in
    // the surveyResultLauncher, check if it has a RESULT_OK, if it does, it stores the data
    // in intent, and then extracts the values associated with the EXTRA_NO_COUNT, and EXTRA_YES_COUNT
    // then passes them to the surveyViewModel to set the current values(if they were kept or reset in
    // SurveyResultActivity, and then updates the counters.
    private fun handleSurveyResult(result: ActivityResult){
        if(result.resultCode == RESULT_OK){
            val intent = result.data
            val noCountResult = intent?.getIntExtra(EXTRA_NO_COUNT, 0) ?: 0
            val yesCountResult = intent?.getIntExtra(EXTRA_YES_COUNT, 0) ?: 0
            surveyViewModel.yesCount = yesCountResult
            surveyViewModel.noCount = noCountResult
            updateCounters()
        }
    }
}


