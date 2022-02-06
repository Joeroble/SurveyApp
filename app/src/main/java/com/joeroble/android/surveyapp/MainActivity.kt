package com.joeroble.android.surveyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.surveyapp.R

class MainActivity : AppCompatActivity() {

    // Establishes the components of the UI, uses lateinit var as they will be initialized in onCreate
    private lateinit var yesCountButton: Button
    private lateinit var noCountButton: Button
    private lateinit var noCounterText: TextView
    private lateinit var yesCounterText: TextView
    private lateinit var resetCountersButton: Button

    // Establishes the view model that will be used to store the counts
    private val surveyViewModel: SurveyViewModel by lazy {
        ViewModelProvider(this).get(SurveyViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializes the buttons and text views
        yesCountButton = findViewById(R.id.yes_button)
        noCountButton = findViewById(R.id.no_button)
        noCounterText = findViewById(R.id.no_counter)
        yesCounterText = findViewById(R.id.yes_counter)
        resetCountersButton = findViewById(R.id.reset_button)


        // Sets up the on click listeners for the buttons, which call the respective functions
        // for each button(increase the yes or no counts, and reset the count).  It will
        // update the counters at the end
        yesCountButton.setOnClickListener{
            increaseYesCount()
        }

        noCountButton.setOnClickListener{
            increaseNoCount()
        }

        resetCountersButton.setOnClickListener{
            resetCounters()
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

    //Creates the resetCounters function, which will call upon the resetCounters() in
    // the SurveyViewModel to reset the counters, it will call upon updateCounters() to update the counts.
    private fun resetCounters(){
        surveyViewModel.resetCounters()
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
}


