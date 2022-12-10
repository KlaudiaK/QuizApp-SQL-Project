package pl.poznan.put.quizzy.friendRequests

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.friendRequests.model.FriendRequest

@Service
class FriendRequestService(
    private val friendRequestRepository: FriendRequestRepository
) {
    fun getSentFriendsRequestsForUser(userId: Long): List<FriendRequest> {
        return friendRequestRepository.findAll().filter { it.fromUserReferenceId.toLong() == userId }
    }
    fun getReceivedFriendsRequestsForUser(userId: Long): List<FriendRequest> {
        return friendRequestRepository.findAll().filter { it.toUserReferenceId.toLong() == userId }
    }

    fun addFriendsRequest(friendRequest: FriendRequest): FriendRequest {
        return friendRequestRepository.save(friendRequest)
    }

    fun deleteFriendRequest(friendRequest: FriendRequest) {
        return friendRequestRepository.delete(friendRequest)
    }
}