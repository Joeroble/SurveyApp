package com.joeroble.android.surveyapp

import androidx.lifecycle.ViewModel

// SurveyViewModel is used to store the counts across rotations, and contains the functions
// used to increase the two counts, and resetting the counters.
class SurveyViewModel: ViewModel() {

     var yesCount = 0
     var noCount = 0

    fun increaseYesCount(){
    yesCount += 1
    }

    fun increaseNoCount(){
    noCount += 1
    }

    fun resetCounters(){
        yesCount = 0
        noCount = 0
    }
}