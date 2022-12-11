package pl.poznan.put.quizzy.quizzes

import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.quizzes.model.Quizz
import java.util.*

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

    fun deleteQuiz(id: Long) {
        return quizzesRepository.deleteById(id)
    }
}