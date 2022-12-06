package pl.poznan.put.quizzy.solvedQuizzes

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.solvedQuizzes.model.SolvedQuizz
import pl.poznan.put.quizzy.solvedQuizzes.model.SolvedQuizzPK

@Repository
interface SolvedQuizzesRepository: JpaRepository<SolvedQuizz, SolvedQuizzPK> {

    @Query("SELECT * from \"Solved Quizes\" where user_id=:userId", nativeQuery = true)
    fun getSolvedQuizzByUserReferenceId(@Param("userId") userId: Long): List<SolvedQuizz>

    @Query("SELECT * from \"Solved Quizes\" where quiz_id=:quizId", nativeQuery = true)
    fun getSolvedQuizzByQuizReferenceId(@Param("quizId") quizId: Long): List<SolvedQuizz>
}