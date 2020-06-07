package com.dehaat.assignment.ui.main.account.state

sealed class AccountStateEvent {

    class GetAccountPropertiesEvent : AccountStateEvent()

    data class UpdateAccountPropertiesEvent(
        val email: String,
        val userName: String
    ) : AccountStateEvent()

    data class ChangePassWordEvent(
        val currentPassword: String,
        val newPassword: String,
        val confirmNewPassword: String
    ) : AccountStateEvent()

    class None : AccountStateEvent()
}