@file:OptIn(ExperimentalMaterial3Api::class)

package me.alexnaupay.todox.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.alexnaupay.todox.R
import me.alexnaupay.todox.presentation.screens.home.components.SectionTitle
import me.alexnaupay.todox.presentation.screens.home.components.SummaryInfo
import me.alexnaupay.todox.presentation.screens.home.components.TaskItem
import me.alexnaupay.todox.presentation.screens.home.providers.HomeScreenPreviewProvider
import me.alexnaupay.todox.ui.theme.TodoXAppTheme

@Composable
fun HomeScreenRoot(){
    val viewModel:HomeScreenViewModel = viewModel<HomeScreenViewModel>()
    val state = viewModel.state
    val event = viewModel.events

    val context = LocalContext.current

    LaunchedEffect(true) {
        event.collect{ event->
            when(event){
                HomeScreenEvent.DeletedTask -> {
                    Toast.makeText(context, context.getString(R.string.task_deleted), Toast.LENGTH_SHORT).show()
                }
                HomeScreenEvent.AllTaskDeleted->{
                    Toast.makeText(context, context.getString(R.string.all_task_deleted), Toast.LENGTH_SHORT).show()
                }
                HomeScreenEvent.UpdatedTask -> {
                    Toast.makeText(context, context.getString(R.string.task_updated), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeDataState,
    onAction: (HomeScreenAction) -> Unit
) {

    var isMenuExtended by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Box (
                        modifier= Modifier.padding(8.dp).clickable {
                            isMenuExtended = true
                        }
                    ){
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Add Task",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                        DropdownMenu(
                            expanded = isMenuExtended,
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.surfaceContainerHighest
                            ),
                            onDismissRequest = { isMenuExtended = false }
                        ) {
                            DropdownMenuItem(
                                text ={
                                    Text(
                                        text =stringResource(R.string.delete_all),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },

                                onClick = {
                                    onAction(HomeScreenAction.OnDeleteAllTasks)
                                    isMenuExtended = false
                                }
                            )
                        }
                    }
                }

            )
        },
        content = { paddingValues ->

            LazyColumn (
                modifier = Modifier.padding( paddingValues = paddingValues )
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                item {
                    SummaryInfo(
                        date = state.date,
                        tasksSummary = state.summary,
                        completedTasks = state.completedTask.size,
                        totalTasks = state.completedTask.size + state.pendingTask.size
                    )
                }

                stickyHeader{
                    SectionTitle(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.surface
                        ).fillParentMaxWidth(),
                        title = stringResource(R.string.pending_tasks)
                    )
                }

                items(items = state.pendingTask, key = { task -> task.id }){ task ->
                    TaskItem(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .animateItem(),
                        task = task,
                        onClickItem = { },
                        onDeleteItem = { onAction(HomeScreenAction.OnDeleteTask(task)) },
                        onToggleCompletion = { onAction(HomeScreenAction.OnToggleTask(task)) }
                    )
                }

                stickyHeader{
                    SectionTitle(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .background(
                            color = MaterialTheme.colorScheme.surface
                        ),
                        title = stringResource(R.string.completed_tasks)
                    )
                }

                items(
                    items = state.completedTask,
                    key = { task -> task.id }
                ){ task ->
                    TaskItem(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .animateItem(),
                        task = task,
                        onClickItem = { },
                        onDeleteItem = { onAction(HomeScreenAction.OnDeleteTask(task)) },
                        onToggleCompletion = { onAction(HomeScreenAction.OnToggleTask(task)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    )
}

@Preview
@Composable
fun HomeScreenPreviewLight(
    @PreviewParameter(HomeScreenPreviewProvider::class) state: HomeDataState
) {
    TodoXAppTheme {
        HomeScreen(
            state = HomeDataState(
                date = state.date,
                summary = state.summary,
                completedTask = state.completedTask,
                pendingTask =  state.pendingTask,
            ),
            onAction = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeScreenPreviewDark(
    @PreviewParameter(HomeScreenPreviewProvider::class) state: HomeDataState
) {
    TodoXAppTheme {
        HomeScreen(
            state = HomeDataState(
                date = state.date,
                summary = state.summary,
                completedTask = state.completedTask,
                pendingTask =  state.pendingTask
            ),
            onAction = {}
        )
    }
}
