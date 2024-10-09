package com.taposek322.multiplicationtable.presentation.testscreen.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class TestViewModel: ViewModel() {
    private var isInitialized = false
    private lateinit var testQuestions: MutableList<Pair<Int,Int>>
    val questions: List<Pair<Int,Int>>
        get() = testQuestions.toList()

    private var _answersState = MutableStateFlow(List<Boolean?>(20){ null })
    val answersState: StateFlow<List<Boolean?>> = _answersState.asStateFlow()

    fun initTest(testValue: Int) {
        if (!isInitialized) {
            testQuestions = MutableList(20) {
                Pair(
                    if (testValue != -1) testValue else Random.nextInt(1, 10),
                    Random.nextInt(1, 10)
                )
            }
            isInitialized = true
        }
    }

    fun checkAnswer(questionNumber: Int, userAnswer: Int) {
        val testQuestion = testQuestions[questionNumber]
        _answersState.update {
            _answersState.value.toMutableList().apply {
                this[questionNumber] = (userAnswer == testQuestion.first * testQuestion.second)
            }.toList()
        }
    }
}