package com.example.quizz.model

data class Question(
    val id: Int,
    val question: String,
    val description: String?,
    val answers: Answers,
    val multiple_correct_answers: String,
    val correct_answers: CorrectAnswers,
    val explanation: String?,
    val tip: String?,
    val tags: List<Tag>?,
    val category: String,
    val difficulty: String
)

data class Answers(
    val answer_a: String?,
    val answer_b: String?,
    val answer_c: String?,
    val answer_d: String?,
    val answer_e: String?,
    val answer_f: String?
)

data class CorrectAnswers(
    val answer_a_correct: String,
    val answer_b_correct: String,
    val answer_c_correct: String,
    val answer_d_correct: String,
    val answer_e_correct: String,
    val answer_f_correct: String
)

data class Tag(
    val name: String
)

fun Answers.toList(): List<String> {
    return listOfNotNull(answer_a, answer_b, answer_c, answer_d, answer_e, answer_f)
}
