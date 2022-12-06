package pl.poznan.put.quizzy.difficultyLevels

import org.springframework.data.jpa.repository.JpaRepository
import pl.poznan.put.quizzy.difficultyLevels.model.DifficultyLevel

interface DifficultyLevelsRepository: JpaRepository<DifficultyLevel, Long>