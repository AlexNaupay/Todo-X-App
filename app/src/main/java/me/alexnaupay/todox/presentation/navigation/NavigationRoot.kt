package me.alexnaupay.todox.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import me.alexnaupay.todox.presentation.screens.detail.TaskScreenRoot
import me.alexnaupay.todox.presentation.screens.home.HomeScreenRoot

@Composable
fun NavigationRoot (navController: NavHostController){
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = HomeScreenDes) {

            composable<HomeScreenDes> {
                HomeScreenRoot(
                    navigateToTaskScreen = {
                        navController.navigate(TaskScreenDes)
                    }
                )
            }

            composable<TaskScreenDes> {
                TaskScreenRoot(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Serializable
object HomeScreenDes

@Serializable
object TaskScreenDes