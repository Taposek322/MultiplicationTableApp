package com.taposek322.multiplicationtable.presentation.mainscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.taposek322.multiplicationtable.R
import com.taposek322.multiplicationtable.presentation.navigation.NavigationRoutes

@Composable
fun MainScreenRoot(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    MainScreen(
        navigate = { testValue ->
            navController.navigate("${NavigationRoutes.TEST_SCREEN}/$testValue")
        },
        modifier
    )
}

@Composable
fun MainScreen(
    navigate: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var inputValue by rememberSaveable {
        mutableStateOf("")
    }

    var isInputError by rememberSaveable{
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            Image(
                painter = painterResource(
                id = R.drawable.multiplication_table),
                contentDescription = stringResource(id = R.string.multiplication_table_text),
                modifier = modifier
                    .fillMaxSize()
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                       navigate(-1)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.start_test_random_value_text),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                TextField(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    isError = isInputError,
                )
                Button(
                    onClick = {
                        if(inputValue.isNotEmpty()) {
                            isInputError = false
                            navigate(inputValue.toInt())
                        } else {
                            isInputError = true
                        }
                    },
                    modifier = Modifier
                ) {
                    Text(
                        text = stringResource(id = R.string.start_test_inputed_value_text),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MainScreenPreview() {
    MainScreen(navigate = {})
}
