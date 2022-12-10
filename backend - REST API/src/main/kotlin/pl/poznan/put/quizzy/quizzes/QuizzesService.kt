package pl.poznan.put.quizzy.quizzes

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.difficultyLevels.model.DifficultyLevel
import pl.poznan.put.quizzy.quizzes.model.PrivacySettings
import pl.poznan.put.quizzy.quizzes.model.Quizz
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class QuizzesService(
    private val quizzesRepository: QuizzesRepository
) {

    fun getAllQuizes(): List<Quizz> {
        return quizzesRepository.findAll()
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun getQuizzById(quizId: Long): Quizz? {
        return quizzesRepository.findById(quizId).getOrNull()
    }

    fun getQuizzesForUser(userId: Long): List<Quizz> {
        return quizzesRepository.getQuizzesForUser(userId)
    }

    fun getQuizzesForUsersAndPrivacySettings(userId: Long, privacySettingsLevel: String): List<Quizz> {
        return quizzesRepository.getQuizzesByPrivacySettingsForUser(privacySettingsLevel, userId)
    }

    fun getQuizzesByPrivacySettings(privacySettingsLevel: String): List<Quizz> {
        return quizzesRepository.getQuizzesByPrivacySettings(privacySettingsLevel)
    }

    fun getQuizzesByDifficultyLevel(difficultyLevel: Int): List<Quizz> {
        return quizzesRepository.getQuizzesByDifficultyLevelReferenceId(difficultyLevel)
    }

    fun getQuizzesByCategoryName(categoryName: String): List<Quizz> {
        return quizzesRepository.getQuizzesByCategoryName(categoryName)
    }

    fun createQuizz(quizz: Quizz): Quizz {
        return quizzesRepository.save(quizz)
    }
    fun updateQuiz(quizz: Quizz): Quizz {
        return quizzesRepository.save(quizz)
    }

    fun deleteQuiz(quizz: Quizz) {
        return quizzesRepository.delete(quizz)
    }
}