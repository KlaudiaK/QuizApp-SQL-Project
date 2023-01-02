package pl.poznan.put.quizzy.questions

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.questions.model.db.Question

@Repository
interface QuestionsRepository: JpaRepository<Question, Long> {

    @Query("SELECT * FROM questions where quizes_id=:quizId", nativeQuery = true)
    fun findQuestionByQuizReferenceId(@Param("quizId") quizId: Long): List<Question>
}