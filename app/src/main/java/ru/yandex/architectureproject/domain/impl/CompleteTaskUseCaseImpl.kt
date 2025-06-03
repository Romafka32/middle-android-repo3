package ru.yandex.architectureproject.domain.impl

import kotlinx.coroutines.delay
import ru.yandex.architectureproject.data.repository.TaskRepository
import ru.yandex.architectureproject.domain.CompleteTaskUseCase

class CompleteTaskUseCaseImpl(
    private val repository: TaskRepository,
): CompleteTaskUseCase {
    override suspend operator fun invoke(taskId: Int) {
        repository.completeTask(taskId)
        delay(DELAY_BEFORE_DELETION)
        repository.deleteTask(taskId)
    }

    companion object {
        private const val DELAY_BEFORE_DELETION = 10_000L
    }
}
