package com.example.quizz.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizz.R
import com.example.quizz.model.Answers
import com.example.quizz.model.Question

class QuizzAdapter(private var questions: List<Question>) : RecyclerView.Adapter<QuizzAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.questionTextView)
        val answersLayout: LinearLayout = itemView.findViewById(R.id.answersLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.questionText.text = question.question
        val answersList = question.answers.toList()

        answersList.forEach { answer ->
            val button = Button(holder.itemView.context).apply {
                text = answer
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    topMargin = 8
                }
            }
            holder.answersLayout.addView(button)
        }
    }

    private fun Answers.toList(): List<String> {
        return listOfNotNull(answer_a, answer_b, answer_c, answer_d, answer_e, answer_f)
    }
    override fun getItemCount() = questions.size
    fun updateQuestions(newQuestions: List<Question>) {
        questions = newQuestions
        notifyDataSetChanged()
    }
}
