package pl.poznan.put.quizzy.solvedQuizzes

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.solvedQuizzes.model.SolvedQuizz

@RestController
@RequiredArgsConstructor
class SolvedQuizzesController(
    private val solvedQuizzesService: SolvedQuizzesService
) {

    @GetMapping("/api/solved_quizzes")
    fun getAllSolvedQuizzes(): List<SolvedQuizz> {
        return solvedQuizzesService.getAllSolvedQuizzes()
    }
    @GetMapping("/api/solved_quizzes/user={userId}")
    fun getSolvedQuizzForUser(@PathVariable("userId") userId: Long): List<SolvedQuizz> {
        return solvedQuizzesService.getAllSolvedQuizzesByUserId(userId)
    }
    @GetMapping("/api/solved_quizzes/quizz={quizzId}")
    fun getSolvedQuizz(@PathVariable("quizzId") quizzId: Long): List<SolvedQuizz> {
        return solvedQuizzesService.getAllSolvedQuizzesByQuizId(quizzId)
    }

    @PostMapping("/api/solved_quizzes")
    fun addSolvedQuizz(@RequestBody solvedQuizz: SolvedQuizz): SolvedQuizz {
        return solvedQuizzesService.addSolvedQuizz(solvedQuizz)
    }

    @DeleteMapping("/api/solved_quizzes")
    fun deleteSolvedQuizz(@RequestBody solvedQuizz: SolvedQuizz) {
        return solvedQuizzesService.deleteSolvedQuizz(solvedQuizz)
    }
}