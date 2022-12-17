package pl.poznan.put.quizzy.questions

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.questions.model.Question

@RestController
@RequiredArgsConstructor
class QuestionsController(
    private val questionsService: QuestionsService
) {

    @GetMapping("/api/questions/{id}")
    fun getQuestions(@PathVariable("id") quizId: Long): List<Question> {
        return questionsService.getAllQuestionsForQuiz(quizId)
    }

    @PutMapping("/api/questions")
    fun updateQuestion(@RequestBody question: Question): Question {
        return questionsService.updateQuestion(question)
    }
    @PostMapping("/api/questions")
    fun createQuestion(@RequestBody question: Question): Question {
        return questionsService.addQuestion(question)
    }

    @DeleteMapping("/api/questions/{id}")
    fun deleteQuestion(@PathVariable("id") id: Long) {
        return questionsService.deleteQuestion(id)
    }
}