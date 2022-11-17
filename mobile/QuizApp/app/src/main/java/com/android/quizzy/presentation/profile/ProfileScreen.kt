package com.android.quizzy.presentation.profile

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Create
import androidx.compose.material.icons.twotone.DoneAll
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.presentation.destinations.LoginScreenDestination
import com.android.quizzy.ui.theme.black80
import com.android.quizzy.ui.theme.pastelBlue20
import com.android.quizzy.ui.theme.pastelPink
import com.android.quizzy.viewmodel.ProfileViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(route = "profile")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>(),
    uiViewMode: UiViewModel = hiltViewModel<UiViewModel>()
) {
    val bottomSheetModalState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val uiState = viewModel.uiState
    Surface(modifier = Modifier.fillMaxSize(), color = black80) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,

            //verticalArrangement = Arrangement.Center
        ) {
            /*
        ProfileTopCard(
            uiState = uiState,
            bitmap = bitmap,
            bottomSheetModalState = bottomSheetModalState
        )

         */


            //TODO

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp),
                shape = RectangleShape,
                colors = CardDefaults.elevatedCardColors(containerColor = pastelBlue20)
            ) {
                TakePicture(
                    uiState = uiState,
                    bottomSheetModalState = bottomSheetModalState
                )
            }

        }
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 70.dp)
        ) {
            Achievements()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {


            OutlinedButton(
                onClick = { navigator.navigate(LoginScreenDestination) }, modifier = Modifier
                    .width(120.dp)
                    .height(50.dp)
            ) {
                Text("Log out", fontSize = 18.sp, color = Color.Black)
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Achievements() {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .width(330.dp)
            .height(100.dp), shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = pastelPink)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.TwoTone.Star, contentDescription = "Created", tint = black80)
                Text(
                    text = "Points",
                    style = TextStyle(
                        fontWeight = FontWeight.W500,
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "100",
                    style = TextStyle(fontWeight = FontWeight.W400, color = Color.Black)
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(0.5.dp)
                    .padding(vertical = 12.dp),
                color = Color.Black
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.TwoTone.Create, contentDescription = "Created", tint = black80)
                Text(
                    text = "Created",
                    style = TextStyle(
                        fontWeight = FontWeight.W500,
                        color = Color.Black,
                        fontSize = 20.sp
                    ),
                )
                Text(
                    text = "100",
                    style = TextStyle(fontWeight = FontWeight.W400, color = Color.Black)
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(0.5.dp)
                    .padding(vertical = 12.dp),
                color = Color.Black
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.TwoTone.DoneAll, contentDescription = "Created", tint = black80)
                Text(
                    text = "Solved",
                    style = TextStyle(
                        fontWeight = FontWeight.W500,
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "100",
                    style = TextStyle(fontWeight = FontWeight.W400, color = Color.Black)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContent() {
    Achievements()
}

