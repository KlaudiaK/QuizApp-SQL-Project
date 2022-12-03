package com.android.quizzy.presentation.registration_form

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.quizzy.R
import com.android.quizzy.data.repository.user_repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.prefs.Preferences
import javax.inject.Inject


@OptIn(FlowPreview::class)
@HiltViewModel
class InputValidationAutoDebounceViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val userRepositoryImpl: UserRepositoryImpl,
) : ViewModel() {
    private val _uiState = mutableStateOf(OnboardingState())
    val uiState: State<OnboardingState> = _uiState

    val username = handle.getStateFlow(viewModelScope, "username", InputWrapper())
    val firstname = handle.getStateFlow(viewModelScope, "firstname", InputWrapper())
    val email = handle.getStateFlow(viewModelScope, "email", InputWrapper())
    val password = handle.getStateFlow(viewModelScope, "password", InputWrapper())
    val repeatedPassword = handle.getStateFlow(viewModelScope, "repeatedPassword", InputWrapper())

    val areInputsValid = combine(username, firstname, email, password, repeatedPassword)
    { (username, firstname, email,password, repeatedPassword) ->
        username.value.isNotEmpty() && username.errorId == null &&
        firstname.value.isNotEmpty() && firstname.errorId == null &&
        email.value.isNotEmpty() && email.errorId == null &&
        password.value.isNotEmpty() && password.errorId == null &&
        repeatedPassword.value.isNotEmpty() && repeatedPassword.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    private var focusedTextField = handle["focusedTextField"] ?: FocusedTextFieldKey.USERNAME
        set(value) {
            field = value
            handle["focusedTextField"] = value
        }

    private val _events = Channel<ScreenEvent>()
    val events = _events.receiveAsFlow()
    private val inputEvents = Channel<UserInputEvent>(Channel.CONFLATED)

    init {
        observeUserInputEvents()
        if (focusedTextField != FocusedTextFieldKey.NONE) focusOnLastSelectedTextField()
    }

    private fun observeUserInputEvents() {
        viewModelScope.launch {
            inputEvents.receiveAsFlow()
                .onEach { event ->
                    when (event) {
                        is UserInputEvent.OnUsernameTextFieldValueChanged -> {
                            when (InputValidator.getUsernameErrorIdOrNull(event.input)) {
                                null -> {
                                    username.value =
                                        username.value.copy(value = event.input, errorId = null)
                                }
                                else -> username.value = username.value.copy(value = event.input)
                            }
                        }
                        is UserInputEvent.OnFirstnameTextFieldValueChanged -> {
                            when (InputValidator.getFirstnameErrorIdOrNull(event.input)) {
                                null -> {
                                    firstname.value =
                                        firstname.value.copy(value = event.input, errorId = null)
                                }
                                else -> firstname.value = firstname.value.copy(value = event.input)
                            }
                        }

                        is UserInputEvent.OnEmailTextFieldValueChanged -> {
                            when (InputValidator.getEmailErrorIdOrNull(event.input)) {
                                null -> {
                                    email.value = email.value.copy(
                                        value = event.input,
                                        errorId = null
                                    )
                                }
                                else -> email.value =
                                    email.value.copy(value = event.input)
                            }
                        }
                        is UserInputEvent.OnPasswordTextFieldValueChanged -> {
                            when (InputValidator.getPasswordErrorIdOrNull(event.input)) {
                                null -> {
                                    password.value = password.value.copy(
                                        value = event.input,
                                        errorId = null
                                    )
                                }
                                else -> password.value =
                                    password.value.copy(value = event.input)
                            }

                        }
                        is UserInputEvent.OnRepeatedPasswordTextFieldValueChanged -> {
                            when (InputValidator.getRepeatedPasswordErrorIdOrNull(event.input, password.value.value)) {
                                null -> {
                                    repeatedPassword.value = repeatedPassword.value.copy(
                                        value = event.input,
                                        errorId = null
                                    )
                                }
                                else -> repeatedPassword.value =
                                    repeatedPassword.value.copy(value = event.input)
                            }
                        }
                        UserInputEvent.OnContinueButtonClicked -> {}
                    }
                }
                .debounce(350)
                .collect { event ->
                    when (event) {
                        is UserInputEvent.OnUsernameTextFieldValueChanged -> {
                            val errorId = InputValidator.getUsernameErrorIdOrNull(event.input)
                            username.value = username.value.copy(errorId = errorId)
                        }
                        is UserInputEvent.OnFirstnameTextFieldValueChanged -> {
                            val errorId = InputValidator.getFirstnameErrorIdOrNull(event.input)
                            firstname.value = firstname.value.copy(errorId = errorId)
                        }

                        is UserInputEvent.OnEmailTextFieldValueChanged -> {
                            val errorId = InputValidator.getEmailErrorIdOrNull(event.input)
                            email.value = email.value.copy(errorId = errorId)
                        }
                        is UserInputEvent.OnPasswordTextFieldValueChanged -> {
                            val errorId = InputValidator.getPasswordErrorIdOrNull(event.input)
                            password.value = password.value.copy(errorId = errorId)
                        }
                        is UserInputEvent.OnRepeatedPasswordTextFieldValueChanged -> {
                            val errorId = InputValidator.getRepeatedPasswordErrorIdOrNull(event.input, password.value.value)
                            repeatedPassword.value = repeatedPassword.value.copy(errorId = errorId)
                        }
                        UserInputEvent.OnContinueButtonClicked -> {

                            if(!uiState.value.incorrectEmail){

                                if(!uiState.value.incorrectUsername){
                                    Log.i("Username doesn't exist ", uiState.value.incorrectUsername.toString())
                                    /*
                                    userRepositoryImpl.insertUserToDB(
                                        UserInfo(
                                            username = username.value.value,
                                            firstname = firstname.value.value,
                                            email = email.value.value,
                                            password = password.value.value
                                        )
                                    )
                                     */
                                    //TODO save should show home screen
                                    onContinueClick()
                                }
                                else{
                                    Log.i("Username exist in database", uiState.value.incorrectUsername.toString())
                                  
                                    //TODO incorrect username
                                }
                            }
                            else {
                                Log.i("Email exist in database", uiState.value.incorrectEmail.toString())
                                //TODO incorrect email
                            }}
                    }
                }
        }
    }

    fun onUsernameEntered(input: String) {
        viewModelScope.launch {
            inputEvents.send(UserInputEvent.OnUsernameTextFieldValueChanged(input))
        }
    }

    fun onFirstnameEntered(input: String) {
        viewModelScope.launch {
            inputEvents.send(UserInputEvent.OnFirstnameTextFieldValueChanged(input))
        }
    }


    fun onEmailEntered(input: String) {
        viewModelScope.launch {
            inputEvents.send(UserInputEvent.OnEmailTextFieldValueChanged(input))
        }
    }

    fun onPasswordEntered(input: String) {
        viewModelScope.launch {
            inputEvents.send(UserInputEvent.OnPasswordTextFieldValueChanged(input))
        }
    }

    fun onRepeatedPasswordEntered(input: String) {
        viewModelScope.launch {
            inputEvents.send(UserInputEvent.OnRepeatedPasswordTextFieldValueChanged(input))
        }
    }

    fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
        focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
    }

    fun onUsernameImeActionClick() {
        viewModelScope.launch {
            _events.send(ScreenEvent.MoveFocus())
        }
    }

    fun onFirstnameImeActionClick() {
        viewModelScope.launch {
            _events.send(ScreenEvent.MoveFocus())
        }
    }

    fun onEmailImeActionClick() {
        viewModelScope.launch {
            _events.send(ScreenEvent.MoveFocus())
        }
    }
    fun onPasswordImeActionClick() {
        viewModelScope.launch {
            _events.send(ScreenEvent.MoveFocus())
        }
    }
    fun onContinueClick() {
        viewModelScope.launch {

            Log.i("Valid ", (getInputErrorsOrNull() == null).toString())
            when (val inputErrors = getInputErrorsOrNull()) {
                null -> {
                    clearFocusAndHideKeyboard()
                    _events.send(ScreenEvent.ShowToast(R.string.success))
                    inputEvents.send(UserInputEvent.OnContinueButtonClicked)
                }
                else -> displayInputErrors(inputErrors)
            }
        }
    }

    private fun getInputErrorsOrNull(): InputErrors? {
        val usernameErrorId = InputValidator.getUsernameErrorIdOrNull(username.value.value)
        val firstnameErrorId = InputValidator.getFirstnameErrorIdOrNull(firstname.value.value)
        val emailErrorId = InputValidator.getEmailErrorIdOrNull(email.value.value)
        val passwordErrorId = InputValidator.getPasswordErrorIdOrNull(password.value.value)
        val repeatedPasswordErrorId = InputValidator.getRepeatedPasswordErrorIdOrNull(repeatedPassword.value.value, password.value.value )
        return if (usernameErrorId == null && firstnameErrorId == null  && emailErrorId == null && passwordErrorId == null && repeatedPasswordErrorId == null) {
            null

        } else {
            InputErrors(usernameErrorId, firstnameErrorId,  emailErrorId, passwordErrorId, repeatedPasswordErrorId)
        }


    }

    private fun displayInputErrors(inputErrors: InputErrors) {
        username.value = username.value.copy(errorId = inputErrors.usernameErrorId)
        firstname.value = firstname.value.copy(errorId = inputErrors.firstnameErrorId)
        email.value = email.value.copy(errorId = inputErrors.emailErrorId)
        password.value = password.value.copy(errorId = inputErrors.passwordErrorId)
        repeatedPassword.value = password.value.copy(errorId = inputErrors.repeatedPasswordErrorId)
    }

    private suspend fun clearFocusAndHideKeyboard() {
        _events.send(ScreenEvent.ClearFocus)
        _events.send(ScreenEvent.UpdateKeyboard(false))
        focusedTextField = FocusedTextFieldKey.NONE
    }

    private fun focusOnLastSelectedTextField() {
        viewModelScope.launch {
            _events.send(ScreenEvent.RequestFocus(focusedTextField))
            delay(250)
            _events.send(ScreenEvent.UpdateKeyboard(true))
        }
    }
}