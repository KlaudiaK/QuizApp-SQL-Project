package pl.poznan.put.quizzy.friendRequests

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.friendRequests.model.FriendRequest
import pl.poznan.put.quizzy.friendRequests.model.FriendRequestPK

@Repository
interface FriendRequestRepository: JpaRepository<FriendRequest, FriendRequestPK>