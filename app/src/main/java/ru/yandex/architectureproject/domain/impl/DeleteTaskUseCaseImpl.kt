package ru.yandex.architectureproject.domain.impl

import ru.yandex.architectureproject.data.repository.TaskRepository
import ru.yandex.architectureproject.domain.DeleteTaskUseCase

class DeleteTaskUseCaseImpl(
    private val repository: TaskRepository,
): DeleteTaskUseCase {
    override suspend operator fun invoke(taskId: Int) {
        repository.deleteTask(taskId)
    }
}
