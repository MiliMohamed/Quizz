package com.example.quizz.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizz.Login
import com.example.quizz.R
import com.example.quizz.databinding.QuizzBinding
import com.example.quizz.di.injectModuleDependencies
import com.example.quizz.model.Question
import com.example.quizz.model.User
import com.example.quizz.viewmodel.QuizzViewModel



import org.koin.androidx.viewmodel.ext.android.viewModel

class Quizz : AppCompatActivity(),HeaderActions {

    private lateinit var binding: QuizzBinding
    private val quizzViewModel: QuizzViewModel by viewModel()
    private var selectedAnswer: String? = null
    private var currentUser:User? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = QuizzBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectModuleDependencies(this@Quizz)

        val userId = intent.getIntExtra("USER_ID", -1)
        if (userId != -1) {
            quizzViewModel.fetchUserDetails(userId)
        }
        quizzViewModel.user.observe(this) { user ->
            updateUserInfoDisplay(user)
        }

        quizzViewModel.fetchQuestions("72g3zW6NalNMDb5JyPeAzty49ZiiXi6Gv8jyk2Ea", 10)

        quizzViewModel.currentQuestion.observe(this) { question ->
            displayQuestion(question)
        }
        binding.btnSubmit.setOnClickListener {
            selectedAnswer?.let { answer ->
                quizzViewModel.submitAnswer(answer)
                selectedAnswer = null
            } ?: run {

                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            }
        }

        quizzViewModel.quizCompleted.observe(this) { isCompleted ->
            if (isCompleted) {

                val message = "Quiz Completed! Your score: ${quizzViewModel.scoreLiveData.value}/10"
                val builder = AlertDialog.Builder(this)

                builder.setTitle("Quiz Completed")
                builder.setMessage(message)

                builder.setPositiveButton("Replay") { dialog, which ->
                    quizzViewModel.resetQuiz()
                    quizzViewModel.fetchQuestions("72g3zW6NalNMDb5JyPeAzty49ZiiXi6Gv8jyk2Ea", 10)
                }

                builder.setNegativeButton("Exit") { dialog, which ->
                    finish()
                }

                val dialog = builder.create()

                dialog.show()
            }
        }
        quizzViewModel.scoreLiveData.observe(this) { score ->
            binding.tvScore.text = "Score: $score"
        }
        binding.header.btnLogOut.setOnClickListener{
            logOut()
        }
        binding.header.leaderbord.setOnClickListener(){
            leaderboard()
        }
    }

    private fun displayQuestion(question: Question?) {
        binding.tvQuestion.text = question?.question
        binding.llAnswers.removeAllViews()

        question?.answers?.let { answers ->
            val answersList = listOfNotNull(answers.answer_a, answers.answer_b, answers.answer_c, answers.answer_d, answers.answer_e, answers.answer_f)
            answersList.forEach { answerText ->
                val button = Button(this).apply {
                    text = answerText
                    setOnClickListener {
                        selectedAnswer = this.text.toString()
                        highlightSelectedButton(this)
                    }
                }
                binding.llAnswers.addView(button)
            }
        }
    }

    private fun highlightSelectedButton(selectedButton: Button) {
        for (i in 0 until binding.llAnswers.childCount) {
            val button = binding.llAnswers.getChildAt(i) as Button
            if (button == selectedButton) {

                button.setBackgroundColor(ContextCompat.getColor(this, R.color.selected_answer))
                button.setTextColor(ContextCompat.getColor(this, R.color.white))
            } else {

                button.setBackgroundColor(ContextCompat.getColor(this, R.color.default_answer))
                button.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }
    }

    override fun updateUserInfoDisplay(user: User?) {
        this.currentUser = user;
        user?.let {
            binding.header.tvUsername.text = user.username
            binding.header.progressBarUser.progress = user.barProgress
            binding.header.tvLevel.text = "Lvl: ${user.level}"
        }
    }

    override fun logOut() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    override fun leaderboard() {
        val intent = Intent(this, Leaderboard::class.java).apply {
            putExtra("USER_ID", currentUser?.id)
            putExtra("USERNAME", currentUser?.username)
            putExtra("PROGRESS_BAR", currentUser?.barProgress)
            putExtra("LEVEL", currentUser?.level)
            Log.e("Test",currentUser?.barProgress.toString())
        }
        startActivity(intent)
        finish()
    }

    override fun quizz() {
        TODO("Not yet implemented")
    }

}


