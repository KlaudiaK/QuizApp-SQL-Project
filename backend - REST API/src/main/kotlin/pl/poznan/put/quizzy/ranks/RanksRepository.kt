package pl.poznan.put.quizzy.ranks

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.ranks.model.Rank

@Repository
interface RanksRepository: JpaRepository<Rank, Long>