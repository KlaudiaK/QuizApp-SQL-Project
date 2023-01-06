package pl.poznan.put.quizzy.questions

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.poznan.put.quizzy.answers.AnswerRepository
import pl.poznan.put.quizzy.questions.model.api.QuestionResponse
import pl.poznan.put.quizzy.questions.model.api.QuestionWithAnswers
import pl.poznan.put.quizzy.questions.model.db.Question
import pl.poznan.put.quizzy.questions.model.mapper.mapToApiModel
import pl.poznan.put.quizzy.questions.model.mapper.mapToDBModel

@Service
class QuestionsService(
    private val questionsRepository: QuestionsRepository,
    private val answerRepository: AnswerRepository
) {
    fun getAllQuestionsForQuiz(quizId: Long): List<Question> {
        return questionsRepository.findQuestionByQuizReferenceId(quizId)
    }

    fun getQuestionById(id: Long): Question? {
        return questionsRepository.findById(id).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Question not found with id: $id"
            )
        }
    }

    fun addQuestion(question: QuestionResponse): Question {
        return questionsRepository.save(question.mapToDBModel())
    }

    fun updateQuestion(question: QuestionResponse): Question {
        return questionsRepository.save(question.mapToDBModel())
    }

    fun deleteQuestion(id: Long) {
        return questionsRepository.deleteById(id)
    }

    fun getAllQuestionsWithAnswers(quizId: Long): QuestionWithAnswers {
        val result = mutableListOf<QuestionWithAnswers.QuestionsList>()
        val listOfQuestions = questionsRepository.findQuestionByQuizReferenceId(quizId)
        for(question in listOfQuestions){
            val answers = answerRepository.findAnswersByQuestionReference(question.id)
            result.add(
                QuestionWithAnswers.QuestionsList(
                question.mapToApiModel(),
                answers
            ))
        }
        return QuestionWithAnswers(result)
    }


    fun getMaxId(): Int = questionsRepository.getMaxQuestionId()
}