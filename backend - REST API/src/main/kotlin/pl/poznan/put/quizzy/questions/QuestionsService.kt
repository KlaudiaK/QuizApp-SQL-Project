package pl.poznan.put.quizzy.questions

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.questions.model.Question

@Service
class QuestionsService(
    private val questionsRepository: QuestionsRepository
) {
    fun getAllQuestionsForQuiz(quizId: Long): List<Question> {
        return questionsRepository.findQuestionByQuizReferenceId(quizId)
    }

    fun addQuestion(question: Question): Question {
        return questionsRepository.save(question)
    }

    fun updateQuestion(question: Question): Question {
        return questionsRepository.save(question)
    }

    fun deleteQuestion(id: Long) {
        return questionsRepository.deleteById(id)
    }
}