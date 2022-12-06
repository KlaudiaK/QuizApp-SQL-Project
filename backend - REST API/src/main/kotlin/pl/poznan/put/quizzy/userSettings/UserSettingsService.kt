package pl.poznan.put.quizzy.userSettings

import org.springframework.stereotype.Service
import pl.poznan.put.quizzy.userSettings.model.UserSettingsItem
import kotlin.jvm.optionals.getOrNull

@Service
class UserSettingsService(
    private val userSettingsRepository: UserSettingsRepository
) {
    @OptIn(ExperimentalStdlibApi::class)
    fun getSettingsForUser(userId: Long): UserSettingsItem? {
        return userSettingsRepository.findById(userId).getOrNull()
    }

    fun updateUserSettings(userSettingsItem: UserSettingsItem): UserSettingsItem {
        return userSettingsRepository.save(userSettingsItem)
    }
    fun addUserSettings(userSettingsItem: UserSettingsItem): UserSettingsItem {
        return userSettingsRepository.save(userSettingsItem)
    }
    fun deleteUserSettingsForId(userId: Long) {
        return userSettingsRepository.deleteById(userId)
    }
}