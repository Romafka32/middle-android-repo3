package ru.yandex.architectureproject.domain

interface AddTaskUserCase {
    suspend operator fun invoke(task: String)
}