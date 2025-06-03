package ru.yandex.architectureproject.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.yandex.architectureproject.data.model.Task
import ru.yandex.architectureproject.data.repository.TaskRepository
import ru.yandex.architectureproject.domain.GetAllTasksUseCase

class GetAllTasksUseCaseImpl(
    private val repository: TaskRepository,
): GetAllTasksUseCase {
    override suspend operator fun invoke(): Flow<List<Task>> {
        return repository.getAllTasks()
    }
}
