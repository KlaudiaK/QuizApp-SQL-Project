package pl.poznan.put.quizzy.solvedQuizzes

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.solvedQuizzes.model.SolvedQuizResponse
import pl.poznan.put.quizzy.solvedQuizzes.model.SolvedQuizz

@RestController
@RequiredArgsConstructor
class SolvedQuizzesController(
    private val solvedQuizzesService: SolvedQuizzesService
) {

    @GetMapping("/api/solved_quizzes")
    fun getAllSolvedQuizzes(): List<SolvedQuizResponse> {
        return solvedQuizzesService.getAllSolvedQuizzes()
    }

    @GetMapping("/api/solved_quizzes/user={userId}")
    fun getSolvedQuizzForUser(@PathVariable("userId") userId: Long): List<SolvedQuizResponse> {
        return solvedQuizzesService.getAllSolvedQuizzesByUserId(userId)
    }
    @GetMapping("/api/solved_quizzes/quizz={quizzId}")
    fun getSolvedQuizz(@PathVariable("quizzId") quizzId: Long): List<SolvedQuizResponse> {
        return solvedQuizzesService.getAllSolvedQuizzesByQuizId(quizzId)
    }

    @PostMapping("/api/solved_quizzes")
    fun addSolvedQuizz(@RequestBody solvedQuizz: SolvedQuizResponse): SolvedQuizz {
        return solvedQuizzesService.addSolvedQuizz(solvedQuizz)
    }

    @DeleteMapping("/api/solved_quizzes")
    fun deleteSolvedQuizz(@RequestBody solvedQuizz: SolvedQuizResponse) {
        return solvedQuizzesService.deleteSolvedQuizz(solvedQuizz)
    }
}