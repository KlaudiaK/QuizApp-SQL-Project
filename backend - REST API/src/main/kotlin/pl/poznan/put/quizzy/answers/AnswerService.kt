package pl.poznan.put.quizzy.answers

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.answers.model.Answer

@Service
class AnswerService(
    private val answerRepository: AnswerRepository
) {
    fun getAllAnswersForQuiz(questionId: Long): List<Answer> {
        val result = answerRepository.findAnswersByQuestionReference(questionId)
        return result
    }

    fun editAnswerForQuiz(answer: Answer): Answer {
        return answerRepository.save(answer)
    }

    fun createAnswer(answer: Answer): Answer {
        return answerRepository.save(answer)
    }

    fun deleteAnswer(id: Long) {
        return answerRepository.deleteById(id)
    }
}