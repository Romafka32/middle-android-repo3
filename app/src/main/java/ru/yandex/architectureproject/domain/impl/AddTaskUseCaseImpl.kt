package ru.yandex.architectureproject.domain.impl

import ru.yandex.architectureproject.data.repository.TaskRepository
import ru.yandex.architectureproject.domain.AddTaskUserCase

class AddTaskUseCaseImpl(
    private val repository: TaskRepository,
): AddTaskUserCase {
    override suspend operator fun invoke(task: String) {
        repository.addTask(task)
    }
}