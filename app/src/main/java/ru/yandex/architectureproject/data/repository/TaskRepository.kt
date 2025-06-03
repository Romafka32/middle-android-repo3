package ru.yandex.architectureproject.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.yandex.architectureproject.data.db.TaskDao
import ru.yandex.architectureproject.data.model.Task

class TaskRepository(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getAllTasks(): Flow<List<Task>> = withContext(ioDispatcher) {
        taskDao.getAllTasks()
    }

    suspend fun addTask(task: String) = withContext(ioDispatcher) {
        taskDao.addTask(Task(text = task))
    }

    suspend fun completeTask(taskId: Int) = withContext(ioDispatcher) {
        taskDao.updateTaskStatus(taskId, true)
    }

    suspend fun incompleteTask(taskId: Int) = withContext(ioDispatcher) {
        taskDao.updateTaskStatus(taskId, false)
    }

    suspend fun deleteTask(taskId: Int) = withContext(ioDispatcher) {
        taskDao.deleteTask(taskId)
    }
}
