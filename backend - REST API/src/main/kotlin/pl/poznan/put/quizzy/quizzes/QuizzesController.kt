package pl.poznan.put.quizzy.quizzes

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.quizzes.model.Quizz

@RestController
@RequiredArgsConstructor
class QuizzesController(
    private val quizzesService: QuizzesService
) {

    @GetMapping("/api/quizzes")
    fun getAllQuizzes(): List<Quizz> {
        return quizzesService.getAllQuizes()
    }

    @GetMapping("/api/quizzes/{id}")
    fun getQuizById(@PathVariable("id") id: Long): Quizz? {
        return quizzesService.getQuizById(id)
    }

    @GetMapping("/api/quizzes/filtered")
    fun getQuizzesFiltered(
        @RequestParam("userId") userId: Long?,
        @RequestParam("privacy") privacy: String?,
        @RequestParam("difficulty") difficultyLevel: Int?,
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
    fun updateQuizz(@RequestBody quizz: Quizz): Quizz {
        return quizzesService.updateQuiz(quizz)
    }
    @PostMapping("/api/quizzes")
    fun addQuizz(@RequestBody quizz: Quizz): Quizz {
        return quizzesService.createQuizz(quizz)
    }

    @DeleteMapping("/api/quizzes/{id}")
    fun deleteQuizz(@PathVariable("id") id: Long) {
        return quizzesService.deleteQuiz(id)
    }
}