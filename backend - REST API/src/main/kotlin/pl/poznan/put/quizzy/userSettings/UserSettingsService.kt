package pl.poznan.put.quizzy.userSettings

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pl.poznan.put.quizzy.userSettings.model.UserSettingsItem

@Service
class UserSettingsService(
    private val userSettingsRepository: UserSettingsRepository
) {
    fun getSettingsForUser(userId: Int): UserSettingsItem? {
        return userSettingsRepository.findById(userId.toLong()).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Settings for user with id: $userId not found"
            )
        }
    }

    fun updateUserSettings(userSettingsItem: UserSettingsItem): UserSettingsItem {
        return userSettingsRepository.save(userSettingsItem)
    }

    fun addUserSettings(userSettingsItem: UserSettingsItem): UserSettingsItem {
        return userSettingsRepository.save(userSettingsItem)
    }

    fun deleteUserSettingsForId(userId: Int) {
        return userSettingsRepository.deleteById(userId.toLong())
    }

    fun getSettings(): List<UserSettingsItem> {
        return userSettingsRepository.findAll()
    }
}