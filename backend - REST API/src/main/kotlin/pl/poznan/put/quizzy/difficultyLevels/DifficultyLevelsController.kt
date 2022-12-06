package pl.poznan.put.quizzy.difficultyLevels

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.quizzy.difficultyLevels.model.DifficultyLevel

@RestController
@RequiredArgsConstructor
class DifficultyLevelsController(
    private val difficultyLevelsService: DifficultyLevelsService
) {

    @GetMapping("/api/difficulty_levels")
    fun getDifficultyLevels(): List<DifficultyLevel> {
        val result = difficultyLevelsService.getDifficultyLevels()
        return result
    }

    @GetMapping("/api/difficulty_levels/id={id}")
    fun getDifficultyLevelById(@PathVariable id: Long): DifficultyLevel? {
        val result = difficultyLevelsService.getDifficultyLevelById(id)
        return result
    }
}