package ru.yandex.architectureproject.domain

import kotlinx.coroutines.flow.Flow
import ru.yandex.architectureproject.data.model.Task

interface GetAllTasksUseCase {
    suspend operator fun invoke(): Flow<List<Task>>
}