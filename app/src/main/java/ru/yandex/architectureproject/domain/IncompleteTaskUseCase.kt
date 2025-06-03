package ru.yandex.architectureproject.domain

interface IncompleteTaskUseCase {
    suspend operator fun invoke(taskId: Int)
}