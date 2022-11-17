package com.android.quizzy.presentation.login

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.presentation.destinations.CategoriesScreenDestination
import com.android.quizzy.presentation.destinations.InputValidationAutoDebounceScreenDestination
import com.android.quizzy.ui.theme.green60
import com.android.quizzy.ui.theme.orange20
import com.android.quizzy.ui.theme.pastelBlack
import com.android.quizzy.viewmodel.AppViewModel
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
//@RootNavGraph(start = true)
@Destination(route = "login", start = true)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: AppViewModel = hiltViewModel<AppViewModel>(),
    uiViewModel: UiViewModel = hiltViewModel<UiViewModel>()
) {

    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current

    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login to your Account",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 40.dp),
            color = pastelBlack
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.value.email,
            onValueChange = {
                viewModel.onEvent(LoginScreenEvent.OnEmailTextFieldValueChange(it))
            },
            modifier = Modifier.fillMaxWidth(),
            // placeholder = {
            //      Text(text = "Email")
            //  },
            label = {
                Text(text = "Email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            //colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = pastelBlue20)
        )
        if (uiState.value.incorrectEmail) {
            Text(
                text = "This email does't exist",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.value.password,
            onValueChange = {
                viewModel.onEvent(LoginScreenEvent.OnPasswordTextFieldValueChange(it))
            },
            modifier = Modifier.fillMaxWidth(),
            //placeholder = {
            //    Text(text = "Password")
            //  },
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        if (uiState.value.incorrectEmail) {
            Text(
                text = "Incorrect password",
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
                .width(250.dp)
                .fillMaxWidth(),
            onClick = {
                viewModel.onEvent(LoginScreenEvent.OnLoginButtonClicked)
              //  if (!uiState.value.incorrectEmail && !uiState.value.incorrectPassword)
                    navigator.navigate(CategoriesScreenDestination)
            },
            colors = ButtonDefaults.buttonColors(containerColor = green60)
        ) {
            Text(text = "Log in", fontSize = 18.sp, color = pastelBlack)
        }

        TextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navigator.navigate(InputValidationAutoDebounceScreenDestination);
                uiViewModel.onBottomBarVisibilityChange(false)
            }

        ) {
            Text(text = "Create new account", color = orange20)
        }

    }


}

