package pl.poznan.put.quizzy.difficultyLevels

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.poznan.put.quizzy.difficultyLevels.model.DifficultyLevel

@Service
class DifficultyLevelsService(
    private val difficultyLevelsRepository: DifficultyLevelsRepository
) {
    fun getDifficultyLevels(): List<DifficultyLevel> {
        return difficultyLevelsRepository.findAll()
    }

    fun getDifficultyLevelById(id: Long): DifficultyLevel? {
        return difficultyLevelsRepository.findById(id).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Difficulty level not found for this id: $id"
            )
        }
    }
}