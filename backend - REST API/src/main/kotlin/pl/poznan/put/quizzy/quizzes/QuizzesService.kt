package pl.poznan.put.quizzy.quizzes

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.poznan.put.quizzy.quizzes.model.api.QuizzResponse
import pl.poznan.put.quizzy.quizzes.model.db.Quizz
import pl.poznan.put.quizzy.quizzes.model.mapper.mapToDBModel

@Service
class QuizzesService(
    private val quizzesRepository: QuizzesRepository
) {

    fun getAllQuizes(): List<Quizz> {
        return quizzesRepository.findAll()
    }

    fun getQuizById(quizId: Long): Quizz? {
        return quizzesRepository.findById(quizId).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User with id $quizId not found"
            )
        }
    }

    fun getQuizzesForUser(userId: Long): List<Quizz> {
        return quizzesRepository.getQuizzesForUser(userId)
    }

    fun getQuizzesForUsersAndPrivacySettings(userId: Long, privacySettingsLevel: String): List<Quizz> {
        return quizzesRepository.getQuizzesByPrivacySettingsForUser(privacySettingsLevel, userId)
    }

    fun getQuizzesByPrivacySettings(privacySettings: String): List<Quizz> {
        return quizzesRepository.getQuizzesByPrivacySettings(privacySettings)
    }

    fun getQuizzesByDifficultyLevel(difficultyLevel: Long): List<Quizz> {
        return quizzesRepository.getQuizzesByDifficultyLevelReferenceId(difficultyLevel)
    }

    fun getQuizzesByCategoryName(categoryName: String): List<Quizz> {
        return quizzesRepository.getQuizzesByCategoryName(categoryName)
    }

    fun createQuizz(quizz: QuizzResponse): Quizz {
        return quizzesRepository.save(quizz.mapToDBModel())
    }

    fun updateQuiz(quizz: QuizzResponse): Quizz {
        return quizzesRepository.save(quizz.mapToDBModel())
    }

    fun deleteQuiz(id: Long) {
        return quizzesRepository.deleteById(id)
    }
}