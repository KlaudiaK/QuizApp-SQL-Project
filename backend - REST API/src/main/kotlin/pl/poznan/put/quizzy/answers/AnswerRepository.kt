package pl.poznan.put.quizzy.answers

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.answers.model.Answer

@Repository
interface AnswerRepository: JpaRepository<Answer, Long> {

    @Query("SELECT * FROM answers where questions_quizes_id = :questionId", nativeQuery = true)
    fun findAnswersByQuestionReference(@Param("questionId") questionId: Long) : List<Answer>
}