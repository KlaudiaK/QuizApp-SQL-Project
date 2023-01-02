package pl.poznan.put.quizzy.quizzes

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.quizzes.model.api.QuizzResponse
import pl.poznan.put.quizzy.quizzes.model.db.Quizz
import pl.poznan.put.quizzy.quizzes.model.mapper.mapToApiModel

@RestController
@RequiredArgsConstructor
class QuizzesController(
    private val quizzesService: QuizzesService
) {

    @GetMapping("/api/quizzes")
    fun getAllQuizzes(): List<QuizzResponse> {
        return quizzesService.getAllQuizes().map { it.mapToApiModel() }
    }

    @GetMapping("/api/quizzes/{id}")
    fun getQuizById(@PathVariable("id") id: Long): QuizzResponse? {
        return quizzesService.getQuizById(id)?.mapToApiModel()
    }

    @GetMapping("/api/quizzes/filtered")
    fun getQuizzesFiltered(
        @RequestParam("userId") userId: Long?,
        @RequestParam("privacy") privacy: String?,
        @RequestParam("difficulty") difficultyLevel: Long?,
        @RequestParam("category") category: String?
    ): List<Quizz> {
        userId?.let {
            privacy?.let {
                return quizzesService.getQuizzesForUsersAndPrivacySettings(userId, privacy)
            }
            return quizzesService.getQuizzesForUser(userId)
        }

        difficultyLevel?.let {
            return quizzesService.getQuizzesByDifficultyLevel(difficultyLevel)
        }

        category?.let {
            return quizzesService.getQuizzesByCategoryName(category)
        }

        privacy?.let {
            return quizzesService.getQuizzesByPrivacySettings(privacy)
        }

        return listOf()
    }

    @PutMapping("/api/quizzes")
    fun updateQuizz(@RequestBody quizz: QuizzResponse): Quizz {
        return quizzesService.updateQuiz(quizz)
    }
    @PostMapping("/api/quizzes")
    fun addQuizz(@RequestBody quizz: QuizzResponse): Quizz {
        return quizzesService.createQuizz(quizz)
    }

    @DeleteMapping("/api/quizzes/{id}")
    fun deleteQuizz(@PathVariable("id") id: Long) {
        return quizzesService.deleteQuiz(id)
    }
}