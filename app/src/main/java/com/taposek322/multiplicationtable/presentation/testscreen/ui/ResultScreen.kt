package com.taposek322.multiplicationtable.presentation.testscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taposek322.multiplicationtable.R
import kotlin.math.min

@Composable
fun ResultScreenRoot(
    testResult: Int,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp.value
    val height = configuration.screenHeightDp.dp.value
    val promtFontSize = (min(width,height)/10).sp
    val resultFontSize = (min(width,height)/8).sp

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.test_result_promt_string),
            fontSize = promtFontSize,
            modifier = Modifier
                .padding(6.dp)
        )
        Text(
            text = stringResource(id = R.string.test_result_string, testResult),
            fontSize = resultFontSize,
            modifier = Modifier
                .padding(6.dp)
        )
    }
}