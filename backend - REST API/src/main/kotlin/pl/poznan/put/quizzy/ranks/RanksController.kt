package pl.poznan.put.quizzy.ranks

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.quizzy.ranks.model.Rank

@RestController
@RequiredArgsConstructor
class RanksController(
    private val ranksService: RanksService
) {

    @GetMapping("/api/ranks")
    fun getRanks(): List<Rank> {
        return ranksService.getAllRanks()
    }
}