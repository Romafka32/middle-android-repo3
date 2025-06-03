package ru.yandex.architectureproject.domain

interface DeleteTaskUseCase {
    suspend operator fun invoke(taskId: Int)
}