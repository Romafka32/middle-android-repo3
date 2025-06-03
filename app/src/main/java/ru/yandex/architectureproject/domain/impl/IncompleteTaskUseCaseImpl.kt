package ru.yandex.architectureproject.domain.impl

import ru.yandex.architectureproject.data.repository.TaskRepository
import ru.yandex.architectureproject.domain.IncompleteTaskUseCase

class IncompleteTaskUseCaseImpl(
    private val repository: TaskRepository,
): IncompleteTaskUseCase {
    override suspend operator fun invoke(taskId: Int) {
        repository.incompleteTask(taskId)
    }
}