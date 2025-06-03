package ru.yandex.architectureproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import ru.yandex.architectureproject.App
import ru.yandex.architectureproject.data.db.TaskDatabase
import ru.yandex.architectureproject.data.repository.TaskRepository
import ru.yandex.architectureproject.domain.impl.AddTaskUseCaseImpl
import ru.yandex.architectureproject.domain.impl.DeleteTaskUseCaseImpl
import ru.yandex.architectureproject.domain.impl.GetAllTasksUseCaseImpl
import ru.yandex.architectureproject.domain.impl.CompleteTaskUseCaseImpl
import ru.yandex.architectureproject.domain.impl.IncompleteTaskUseCaseImpl

class TaskViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val taskDao = TaskDatabase.getInstance(App.context).taskDao()
        val repository = TaskRepository(taskDao)
        val addTaskUseCase = AddTaskUseCaseImpl(repository)
        val deleteTaskUseCase = DeleteTaskUseCaseImpl(repository)
        val completeTaskUseCase = CompleteTaskUseCaseImpl(repository)
        val incompleteTaskUseCase = IncompleteTaskUseCaseImpl(repository)
        val getAllTasksUseCase = GetAllTasksUseCaseImpl(repository)
        val ioDispatcher = Dispatchers.IO
        return TaskViewModel(
            addTaskUseCase,
            deleteTaskUseCase,
            getAllTasksUseCase,
            completeTaskUseCase,
            incompleteTaskUseCase,
            ioDispatcher,
        ) as T
    }
}
