package pl.poznan.put.quizzy.ranks

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.ranks.model.Rank

@Service
class RanksService(
    private val ranksRepository: RanksRepository
) {

    fun getAllRanks(): List<Rank> {
        return ranksRepository.findAll()
    }
}