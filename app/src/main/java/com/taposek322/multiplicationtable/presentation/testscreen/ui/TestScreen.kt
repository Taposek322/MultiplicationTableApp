package com.taposek322.multiplicationtable.presentation.testscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.taposek322.multiplicationtable.R
import com.taposek322.multiplicationtable.presentation.navigation.NavigationRoutes
import com.taposek322.multiplicationtable.presentation.testscreen.viewmodel.TestViewModel
import kotlin.math.min

@Composable
fun TestScreenRoot(
    testValue: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: TestViewModel = viewModel()
) {
    viewModel.initTest(testValue)

    val answerState = viewModel.answersState.collectAsState()

    TestScreen(
        questionList = viewModel.questions,
        answerList = answerState.value,
        navigate = { testResult ->
            navController.navigate("${NavigationRoutes.RESULT_SCREEN}/$testResult") {
                popUpTo(NavigationRoutes.MAIN_SCREEN)
            }
        },
        onCheckSolution = { index, value -> viewModel.checkAnswer(index, value)},
        modifier = modifier
    )
}

@Composable
fun TestScreen(
    questionList: List<Pair<Int, Int>>,
    answerList: List<Boolean?>,
    navigate: (Int) -> Unit,
    onCheckSolution: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp.value
    val height = configuration.screenHeightDp.dp.value
    val fontSize = (min(width,height)/10).sp

    var currentQuestion by rememberSaveable {
        mutableIntStateOf(0)
    }

    var answer by rememberSaveable {
        mutableStateOf("")
    }

    var isInputError by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = answerList) {
        if (answerList.all { it != null }) {
            navigate(answerList.count { it == true })
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                LazyRow(
                   horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(20) { index ->
                        val color: Color = when(answerList[index]) {
                            true -> Color.Green
                            false -> Color.Red
                            else -> Color.Gray
                        }
                        QuestionCircle(
                            color = color,
                            isCurrentQuestion = index == currentQuestion,
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    currentQuestion = index
                                    answer = ""
                                }
                        )
                    }
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
            .weight(1f)
        ) {
            Text(
                text = stringResource(
                    id = R.string.equation_text,
                    questionList[currentQuestion].first,
                    questionList[currentQuestion].second
                ),
                fontSize = fontSize,
                textAlign = TextAlign.Center,
            )
        }
        when(answerList[currentQuestion]) {
            true -> {
                Text(
                    text = stringResource(id = R.string.right_answer_result_text),
                    color = Color.Green
                )
            }
            false -> {
                Text(
                    text = stringResource(id = R.string.wrong_answer_result_text),
                    color = Color.Red
                )
            }
            else -> {
                TextField(
                    value = answer,
                    onValueChange = { answer = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = isInputError
                )
                Button(
                    onClick = {
                        if(answer.isNotEmpty()) {
                            isInputError = false
                            onCheckSolution(currentQuestion, answer.toInt())
                        } else {
                            isInputError = true
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.answer_text))
                }
            }
        }
    }
}

@Composable
fun QuestionCircle(
    color: Color,
    isCurrentQuestion: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .background(color = color, shape = CircleShape)
            .border(
                width = if (isCurrentQuestion) 5.dp else 0.dp,
                color = Color.Black,
                shape = CircleShape
            )
    )
}
