package pl.poznan.put.quizzy.solvedQuizzes

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.solvedQuizzes.mapper.mapToSolvedQuiz
import pl.poznan.put.quizzy.solvedQuizzes.mapper.mapToSolvedQuizResponse
import pl.poznan.put.quizzy.solvedQuizzes.model.SolvedQuizResponse
import pl.poznan.put.quizzy.solvedQuizzes.model.SolvedQuizz

@Service
class SolvedQuizzesService(
    private val solvedQuizzesRepository: SolvedQuizzesRepository
) {

    fun getAllSolvedQuizzes(): List<SolvedQuizResponse> {
        return solvedQuizzesRepository.findAll().map { it.mapToSolvedQuizResponse() }
    }

    fun getAllSolvedQuizzesByUserId(userId: Long): List<SolvedQuizResponse> {
        return solvedQuizzesRepository.getSolvedQuizzByUserReferenceId(userId).map { it.mapToSolvedQuizResponse() }
    }

    fun getAllSolvedQuizzesByQuizId(quizId: Long): List<SolvedQuizResponse> {
        return solvedQuizzesRepository.getSolvedQuizzByQuizReferenceId(quizId).map {it.mapToSolvedQuizResponse()}
    }

    fun addSolvedQuizz(solvedQuizz: SolvedQuizResponse): SolvedQuizz {
        return solvedQuizzesRepository.save(solvedQuizz.mapToSolvedQuiz())
    }

    fun deleteSolvedQuizz(solvedQuizz: SolvedQuizResponse) {
        return solvedQuizzesRepository.delete(solvedQuizz.mapToSolvedQuiz())
    }

}