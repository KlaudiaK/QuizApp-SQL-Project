package com.android.quizzy.presentation.registration_form

sealed class UserInputEvent {
    class OnUsernameTextFieldValueChanged(val input: String) : UserInputEvent()
    class OnFirstnameTextFieldValueChanged(val input: String) : UserInputEvent()
    class OnEmailTextFieldValueChanged(val input: String): UserInputEvent()
    class OnPasswordTextFieldValueChanged(val input: String) : UserInputEvent()
    class OnRepeatedPasswordTextFieldValueChanged(val input: String) : UserInputEvent()
    object OnContinueButtonClicked: UserInputEvent()
}
