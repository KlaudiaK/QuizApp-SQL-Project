package pl.poznan.put.quizzy.questions

import lombok.RequiredArgsConstructor
import org.springframework.data.jdbc.repository.query.Query

import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.questions.model.api.QuestionResponse
import pl.poznan.put.quizzy.questions.model.db.Question
import pl.poznan.put.quizzy.questions.model.mapper.mapToApiModel

@RestController
@RequiredArgsConstructor
class QuestionsController(
    private val questionsService: QuestionsService
) {

    @GetMapping("/api/questions")
    fun getQuestions(@RequestParam("quizId") quizId: Long): List<QuestionResponse> {
        return questionsService.getAllQuestionsForQuiz(quizId).map { it.mapToApiModel() }
    }

    @GetMapping("/api/questions/{id}")
    fun getQuestionById(@PathVariable("id") quizId: Long): QuestionResponse? {
        return questionsService.getQuestionById(quizId)?.mapToApiModel()
    }

    @PutMapping("/api/questions")
    fun updateQuestion(@RequestBody question: QuestionResponse): Question {
        return questionsService.updateQuestion(question)
    }
    @PostMapping("/api/questions")
    fun createQuestion(@RequestBody question: QuestionResponse): Question {
        return questionsService.addQuestion(question)
    }

    @DeleteMapping("/api/questions/{id}")
    fun deleteQuestion(@PathVariable("id") id: Long) {
        return questionsService.deleteQuestion(id)
    }
}