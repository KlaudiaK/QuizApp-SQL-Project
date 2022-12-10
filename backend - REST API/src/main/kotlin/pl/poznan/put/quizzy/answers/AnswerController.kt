package pl.poznan.put.quizzy.answers

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.quizzy.answers.model.Answer

@RestController
@RequiredArgsConstructor
class AnswerController(
    private val answerService: AnswerService
) {

    @GetMapping("/api/answers")
    fun getAnswersForQuiz(@RequestParam questionId: Long): List<Answer> {
        val result = answerService.getAllAnswersForQuiz(questionId)
        return result
    }

    @PutMapping("api/answers")
    fun editAnswer(
        @RequestBody answer: Answer
    ): Answer {
        return answerService.editAnswerForQuiz(answer)
    }
    @PostMapping("api/answers")
    fun createAnswer(
        @RequestBody answer: Answer
    ): Answer {
        return answerService.createAnswer(answer)
    }
}