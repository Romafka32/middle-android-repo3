package ru.yandex.architectureproject.domain

interface CompleteTaskUseCase {
    suspend operator fun invoke(taskId: Int)
}