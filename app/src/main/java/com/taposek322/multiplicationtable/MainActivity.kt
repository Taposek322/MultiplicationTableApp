package com.taposek322.multiplicationtable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.taposek322.multiplicationtable.presentation.mainscreen.ui.MainScreenRoot
import com.taposek322.multiplicationtable.presentation.navigation.NavigationRoutes
import com.taposek322.multiplicationtable.presentation.testscreen.ui.ResultScreenRoot
import com.taposek322.multiplicationtable.presentation.testscreen.ui.TestScreenRoot
import com.taposek322.multiplicationtable.presentation.theme.MultiplicationTableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiplicationTableTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NavigationRoutes.MAIN_SCREEN
                    ) {
                        composable(route = NavigationRoutes.MAIN_SCREEN) {
                            MainScreenRoot(
                                navController = navController,
                                modifier = Modifier
                                    .padding(innerPadding)
                            )
                        }
                        composable(
                            route = "${NavigationRoutes.TEST_SCREEN}/{${NavigationRoutes.TEST_SCREEN_PARAM}}",
                            arguments = listOf(navArgument(NavigationRoutes.TEST_SCREEN_PARAM) { type = NavType.IntType })
                        ) { backstackEntry ->
                            val testValue = backstackEntry.arguments?.getInt(NavigationRoutes.TEST_SCREEN_PARAM)?: -1
                            TestScreenRoot(
                                testValue = testValue,
                                navController = navController,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            )
                        }
                        composable(
                            route = "${NavigationRoutes.RESULT_SCREEN}/{${NavigationRoutes.RESULT_SCREEN_PARAM}}",
                            arguments = listOf(navArgument(NavigationRoutes.RESULT_SCREEN_PARAM) { type = NavType.IntType })
                        ) { backstackEntry ->
                            val testResult = backstackEntry.arguments?.getInt(NavigationRoutes.RESULT_SCREEN_PARAM)?: -1
                            ResultScreenRoot(
                                testResult = testResult,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultiplicationTableTheme {
        Greeting("Android")
    }
}