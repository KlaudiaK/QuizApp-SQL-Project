package com.android.quizzy.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.data.repository.user_repository.UserRepository
import com.android.quizzy.domain.model.FriendRequest
import com.android.quizzy.domain.model.User
import com.android.quizzy.domain.model.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {
    private val _uiState = mutableStateOf(User(-1, "", "", ""))
    val uiState: State<User> = _uiState
    private val _requests = mutableStateOf(listOf<FriendRequest>())
    val request: State<List<FriendRequest>> = _requests
    private val _userFriends = mutableStateOf(listOf<User>())
    val userFriends: State<List<User>> = _userFriends
    private val _userFriendsRequest = mutableStateOf(listOf<User>())
    val userFriendsRequest: State<List<User>> = _userFriendsRequest
    private val _peopleToInvite = mutableStateOf(listOf<User>())
    val peopleToInvite: State<List<User>> = _peopleToInvite

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val userId = sharedPreferences.getString("user_id", "")
            if (!userId.isNullOrEmpty()) {
                val user  = userRepository.getUser(userId)
                _uiState.value = uiState.value.copy(
                    id = user.id,
                    userName = user.userName,
                    email = user.email,
                    name = user.name,
                    createdQuizes = user.createdQuizes,
                    solvedQuizes = user.solvedQuizes,
                    totalPoints = user.totalPoints,
                    avatar = user.avatar,
                    rank = user.rank
                )
                val friendsRequestAll = userRepository.getFriendsRequests()
                _requests.value = friendsRequestAll
                val friendsRequestForUser = friendsRequestAll.filter { it.toUserReferenceId == userId.toInt() }
                _userFriendsRequest.value = friendsRequestForUser.filter { it.status == "Sent" }.map { userRepository.getUser(it.fromUserReferenceId.toString()) }
                val friends = friendsRequestForUser.filter { it.status == "Accepted" }.map { userRepository.getUser(it.fromUserReferenceId.toString()) }
                _userFriends.value = friends
                val filterPeopleToInvite = userRepository.getUsers().filter { !userFriends.value.contains(it) && !userFriendsRequest.value.contains(it) && userId.toInt() != it.id}
                _peopleToInvite.value = filterPeopleToInvite
            }
        }
    }

    fun sentFriendRequest(fromId: Int = uiState.value.id, toId: Int) {
        viewModelScope.launch {
            userRepository.updateFriendsRequests(FriendRequest(fromUserReferenceId = fromId, toUserReferenceId = toId, status = "Sent"))
            getData()
        }
    }
    fun updateFriendRequest(fromId: Int, toId: Int = uiState.value.id) {
        viewModelScope.launch {
            userRepository.updateFriendsRequests(FriendRequest(fromUserReferenceId = fromId, toUserReferenceId = toId, status = "Accepted"))
            getData()
        }
    }
}
