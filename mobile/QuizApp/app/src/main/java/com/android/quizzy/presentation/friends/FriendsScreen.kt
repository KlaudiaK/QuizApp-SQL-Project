package com.android.quizzy.presentation.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.viewmodel.FriendsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination("friends")
fun FriendsScreen(
    navigator: DestinationsNavigator,
    viewModel: FriendsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val friends = viewModel.userFriends
    val requests = viewModel.userFriendsRequest
    val peopleToInvite = viewModel.peopleToInvite
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp)
                    .clickable { navigator.popBackStack() }
            )

            Text(
                text = AnnotatedString("Community"),
                modifier = Modifier.padding(4.dp),
                softWrap = false,
            )

            Icon(
                imageVector = Icons.Filled.PersonAdd,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Friends", style = MaterialTheme.typography.bodyLarge)
            Box(modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.inverseOnSurface)) {
                LazyColumn() {
                    items(friends.value) { friends ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(80.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = friends.userName, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = friends.rank.toString(), textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = friends.totalPoints.toString(), textAlign = TextAlign.Center)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Friends request", style = MaterialTheme.typography.bodyLarge)
            Box(modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.inverseOnSurface)){
                if (requests.value.isNotEmpty()) {
                    LazyColumn() {
                        items(requests.value) { ivitedPerson ->
                            var visible by remember { mutableStateOf(true) }
                            if (visible) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .height(80.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = ivitedPerson.userName, textAlign = TextAlign.Center)
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = ivitedPerson.rank.toString(),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = ivitedPerson.totalPoints.toString(),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Button(
                                        onClick = {
                                            viewModel.updateFriendRequest(fromId = ivitedPerson.id)
                                            visible = false
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = null
                                        )

                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }

                } else {
                    Text(text = "No requests")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Invite friends", style = MaterialTheme.typography.bodyLarge)
            Box(modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.inverseOnSurface)) {


                LazyColumn() {
                    items(peopleToInvite.value) { toInvitePerson ->
                        var visible by remember { mutableStateOf(true) }
                        if (visible) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .height(80.dp), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = toInvitePerson.userName, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = toInvitePerson.rank.toString(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = toInvitePerson.totalPoints.toString(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                onClick = {
                                    viewModel.sentFriendRequest(toId = toInvitePerson.id)
                                    visible = false
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PersonAdd,
                                    contentDescription = null
                                )

                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                }
            }
        }
    }
}