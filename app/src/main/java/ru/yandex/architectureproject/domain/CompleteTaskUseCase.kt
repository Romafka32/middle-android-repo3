package ru.yandex.architectureproject.domain

import kotlinx.coroutines.delay
import ru.yandex.architectureproject.data.repository.TaskRepository

class CompleteTaskUseCase(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(taskId: Int) {
        repository.completeTask(taskId)
        delay(DELAY_BEFORE_DELETION)
        repository.deleteTask(taskId)
    }

    companion object {
        private const val DELAY_BEFORE_DELETION = 10_000L
    }
}
