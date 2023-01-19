package com.android.quizzy.presentation.settings

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.quizzy.R
import com.android.quizzy.presentation.login.LoginScreenEvent
import com.android.quizzy.ui.theme.black60
import com.android.quizzy.ui.theme.brown80
import com.android.quizzy.ui.theme.pastelBlack
import com.android.quizzy.viewmodel.SettingsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(route = "settings")
fun SettingsScreen(
    navigator: DestinationsNavigator,
    settingsViewModel: SettingsViewModel = hiltViewModel<SettingsViewModel>()
) {
    val uiState = settingsViewModel.uiState
    val password = settingsViewModel.password
    val settings = settingsViewModel.settings
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
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
                text = AnnotatedString("Your settings"),
                modifier = Modifier.padding(4.dp),
                softWrap = false,
            )

            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(40.dp)
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp),
                shape = RectangleShape,

                colors = CardDefaults.elevatedCardColors(containerColor = brown80.copy(0.8F))
            ) {
                Card(
                    modifier = Modifier
                        .padding(start = 100.dp, end = 100.dp)
                        .height(140.dp),

                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                uiState.value.avatar
                                    ?.replace("http://", "https://") ?: "https://www.pngitem.com/pimgs/m/150-1503945_transparent-user-png-default-user-image-png-png.png"
                            )
                            .crossfade(true)
                            .build(),
                            contentDescription = "thumbnail",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                                .padding(top = 32.dp),
                            alpha = 0.95f,
                            onLoading = { Log.i("Load", "Loading") },
                            onSuccess = { Log.i("Load", "Success") },
                            onError = { Log.i("Load", "Error") },
                        )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(12.dp),
                    textAlign = TextAlign.Center,
                    text = uiState.value.userName,
                    style = MaterialTheme.typography.displaySmall
                )
                Divider(
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally), color = pastelBlack, thickness = 1.dp
                )
                TextField(
                    value = uiState.value.email,
                    onValueChange = { settingsViewModel.changeEmail(it)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Spacer(modifier = Modifier.background(black60).height(2.dp))
        Spacer(modifier = Modifier.height(5.dp))
        //todo darkMode, preferredLanguage
        OutlinedTextField(
            label = { Text(text ="Name")},
            value = uiState.value.name,
            onValueChange = { settingsViewModel.changeName(it)},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            textStyle = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        Spacer(modifier = Modifier.background(black60).height(2.dp))
        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = password.value.password ?: "",
            onValueChange = {
                settingsViewModel.changePassword(it)
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            //placeholder = {
            //    Text(text = "Password")
            //  },
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(5.dp))
        Spacer(modifier = Modifier.background(black60).height(2.dp))
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            label = { Text(text ="Avatar url")},
            value = uiState.value.avatar ?: "",
            onValueChange = { settingsViewModel.changeAvatar(it)},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            textStyle = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        Spacer(modifier = Modifier.background(black60).height(2.dp))
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            label = { Text(text ="Preferred language")},
            value = settings.value.preferredLanguage ?: "",
            onValueChange = { settingsViewModel.changePreferredLanguage(it)},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            textStyle = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(5.dp))
        Spacer(modifier = Modifier.background(black60).height(2.dp))
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            label = { Text(text ="Dark mode [Y/N]")},
            value = settings.value.darkMode.toString() ?: "",
            onValueChange = { settingsViewModel.changeDarkMode(it)},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            textStyle = MaterialTheme.typography.bodyLarge
        )
        Button(
            onClick = {
                settingsViewModel.saveUser()
                navigator.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Save settings")
        }
    }
}