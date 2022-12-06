package pl.poznan.put.quizzy.solvedQuizzes

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.solvedQuizzes.model.SolvedQuizz

@Service
class SolvedQuizzesService(
    private val solvedQuizzesRepository: SolvedQuizzesRepository
) {

    fun getAllSolvedQuizzes(): List<SolvedQuizz> {
        return solvedQuizzesRepository.findAll()
    }

    fun getAllSolvedQuizzesByUserId(userId: Long): List<SolvedQuizz> {
        return solvedQuizzesRepository.getSolvedQuizzByUserReferenceId(userId)
    }

    fun getAllSolvedQuizzesByQuizId(quizId: Long): List<SolvedQuizz> {
        return solvedQuizzesRepository.getSolvedQuizzByQuizReferenceId(quizId)
    }

    fun addSolvedQuizz(solvedQuizz: SolvedQuizz): SolvedQuizz {
        return solvedQuizzesRepository.save(solvedQuizz)
    }

    fun deleteSolvedQuizz(solvedQuizz: SolvedQuizz) {
        return solvedQuizzesRepository.delete(solvedQuizz)
    }

}