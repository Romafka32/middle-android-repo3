package ru.yandex.architectureproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import ru.yandex.architectureproject.domain.impl.AddTaskUseCaseImpl
import ru.yandex.architectureproject.domain.impl.CompleteTaskUseCaseImpl
import ru.yandex.architectureproject.domain.impl.DeleteTaskUseCaseImpl
import ru.yandex.architectureproject.domain.impl.GetAllTasksUseCaseImpl
import ru.yandex.architectureproject.domain.impl.IncompleteTaskUseCaseImpl
import ru.yandex.architectureproject.presentation.state.TaskAction
import ru.yandex.architectureproject.presentation.state.TaskState
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class TaskViewModel(
    private val addTaskUseCase: AddTaskUseCaseImpl,
    private val deleteTaskUseCase: DeleteTaskUseCaseImpl,
    private val getAllTasksUseCase: GetAllTasksUseCaseImpl,
    private val completeTaskUseCase: CompleteTaskUseCaseImpl,
    private val incompleteTaskUseCase: IncompleteTaskUseCaseImpl,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _state = MutableStateFlow<TaskState>(TaskState.Loading)
    val state: StateFlow<TaskState> = _state.asStateFlow()

    private val taskForDeletionJobMap: ConcurrentMap<Int, Job> = ConcurrentHashMap()


    init {
        reduce(TaskAction.LoadTasks)
    }

    fun reduce(action: TaskAction) {
        viewModelScope.launch {
            when (action) {
                is TaskAction.LoadTasks -> loadTasks()
                is TaskAction.AddTask -> addTaskUseCase(action.task)
                is TaskAction.DeleteTask -> deleteTaskUseCase(action.taskId)
                is TaskAction.UpdateTaskStatus -> {
                    if (action.isDone) {
                        taskForDeletionJobMap[action.taskId] = this.coroutineContext.job
                        completeTaskUseCase(action.taskId)
                    } else {
                        taskForDeletionJobMap[action.taskId]?.cancel()
                        incompleteTaskUseCase(action.taskId)
                    }
                }
            }
        }
    }

    private suspend fun loadTasks() {
        getAllTasksUseCase()
            .distinctUntilChanged()
            .onStart { _state.value = TaskState.Loading }
            .catch { e -> _state.value = TaskState.Error(e.message ?: "Ошибка загрузки") }
            .collect { tasks -> _state.value = TaskState.Loaded(tasks) }
    }
}
