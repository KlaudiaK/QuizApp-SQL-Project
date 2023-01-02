package pl.poznan.put.quizzy.quizzes.model.mapper

import pl.poznan.put.quizzy.quizzes.model.api.QuizzResponse
import pl.poznan.put.quizzy.quizzes.model.db.Quizz
import java.time.LocalDate


fun Quizz.mapToApiModel() = with(this) {
    QuizzResponse(
        id = id,
        creatorId = creatorId,
        creationDate = creationDate.toString(),
        categoryName = categoryName,
        points = points,
        likes = likes,
        privacySettings = privacySettings,
        difficultyLevelReferenceId = difficultyLevelReferenceId,
        modificationDate = modificationDate.toString(),
        name = name,
        description = description,
        image = image
    )
}

fun QuizzResponse.mapToDBModel() = with(this) {
    Quizz(
        id = id,
        creatorId = creatorId,
        creationDate = LocalDate.parse(creationDate),
        categoryName = categoryName,
        points = points,
        likes = likes,
        privacySettings = privacySettings,
        difficultyLevelReferenceId = difficultyLevelReferenceId,
        modificationDate = LocalDate.parse(modificationDate),
        name = name,
        description = description,
        image = image
    )
}