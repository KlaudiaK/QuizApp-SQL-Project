package pl.poznan.put.quizzy.answers

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.answers.model.Answer

@RestController
@RequiredArgsConstructor
class AnswerController(
    private val answerService: AnswerService
) {

    @GetMapping("/api/answers/{id}")
    fun getAnswersForQuiz(@PathVariable("id") questionId: Long): List<Answer> {
        return answerService.getAllAnswersForQuiz(questionId)
    }

    @PutMapping("/api/answers")
    fun editAnswer(
        @RequestBody answer: Answer
    ): Answer {
        return answerService.editAnswerForQuiz(answer)
    }

    @PostMapping("/api/answers")
    fun createAnswer(
        @RequestBody answer: Answer
    ): Answer {
        return answerService.createAnswer(answer)
    }

    @DeleteMapping("/api/answers/{id}")
    fun deleteAnswer(@PathVariable("id") id: Long) {
        return answerService.deleteAnswer(id)
    }

    @GetMapping("/api/answers_max_id")
    fun getMaxId() = answerService.getMaxId()
}