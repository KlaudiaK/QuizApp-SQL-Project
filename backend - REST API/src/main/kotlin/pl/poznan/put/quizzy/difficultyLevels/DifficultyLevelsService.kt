package pl.poznan.put.quizzy.difficultyLevels

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.difficultyLevels.model.DifficultyLevel
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class DifficultyLevelsService(
    private val difficultyLevelsRepository: DifficultyLevelsRepository
) {
    fun getDifficultyLevels(): List<DifficultyLevel> {
        return difficultyLevelsRepository.findAll()
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun getDifficultyLevelById(id: Long): DifficultyLevel? {
        return difficultyLevelsRepository.findById(id).getOrNull()
    }
}