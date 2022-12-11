package pl.poznan.put.quizzy.friendRequests

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.friendRequests.model.FriendRequest

@RestController
@RequiredArgsConstructor
class FriendRequestController(
    private val friendRequestService: FriendRequestService
) {

    @GetMapping("/api/requests")
    fun getRequests(
        @RequestParam fromUser: Long?,
        @RequestParam toUser: Long?
    ): List<FriendRequest> {
        return when{
            fromUser != null && toUser != null -> friendRequestService.getFriendsRequestForUsersIfExists(fromUser, toUser)
            fromUser != null -> friendRequestService.getSentFriendsRequestsForUser(fromUser)
            toUser != null -> friendRequestService.getReceivedFriendsRequestsForUser(toUser)
            else -> listOf()
        }
    }

    @PostMapping("/api/requests")
    fun createFriendRequest(@RequestBody friendRequest: FriendRequest): FriendRequest {
        return friendRequestService.addFriendsRequest(friendRequest)
    }

    @DeleteMapping("/api/requests")
    fun deleteFriendRequest(@RequestBody friendRequest: FriendRequest) {
        return friendRequestService.deleteFriendRequest(friendRequest)
    }
}