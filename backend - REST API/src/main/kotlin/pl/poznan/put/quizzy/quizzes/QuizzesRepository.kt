package pl.poznan.put.quizzy.quizzes

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.quizzes.model.db.Quizz

@Repository
interface QuizzesRepository: JpaRepository<Quizz, Long> {

    @Query("SELECT * FROM quizes WHERE categories_name=:categoryName", nativeQuery = true)
    fun getQuizzesByCategoryName(@Param("categoryName") categoryName: String): List<Quizz>
    @Query("SELECT * FROM quizes WHERE difficulty_levels_difficulty_levels_id=:level", nativeQuery = true)
    fun getQuizzesByDifficultyLevelReferenceId(@Param("level") level: Long): List<Quizz>

    @Query("SELECT * FROM quizes WHERE privacy_settings=:privacy", nativeQuery = true)
    fun getQuizzesByPrivacySettings(@Param("privacy") privacy: String): List<Quizz>

    @Query("SELECT * FROM quizes WHERE privacy_settings=:privacy and creator_user_id=:userId", nativeQuery = true)
    fun getQuizzesByPrivacySettingsForUser(
        @Param("privacy") privacy: String,
        @Param("userId") userId: Long
    ): List<Quizz>

    @Query("SELECT * FROM quizes WHERE creator_user_id=:userId", nativeQuery = true)
    fun getQuizzesForUser(
        @Param("userId") userId: Long
    ): List<Quizz>


}