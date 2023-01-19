package pl.poznan.put.quizzy.userSettings

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import pl.poznan.put.quizzy.userSettings.model.UserSettingsItem

@RestController
@RequiredArgsConstructor
class UserSettingsController(
    private val userSettingsService: UserSettingsService
) {

    @GetMapping("/api/user/settings")
    fun getUserSettings(@RequestParam("id") id: Int): UserSettingsItem? {
        return userSettingsService.getSettingsForUser(id)
    }
    @GetMapping("/api/user/settings/all")
    fun getSettings(): List<UserSettingsItem> {
        return userSettingsService.getSettings()
    }

    @PutMapping("/api/user/settings")
    fun updateUserSettings(@RequestBody userSettingsItem: UserSettingsItem): UserSettingsItem? {
        return userSettingsService.updateUserSettings(userSettingsItem)
    }

    @PostMapping("/api/user/settings")
    fun addUserSettings(@RequestBody userSettingsItem: UserSettingsItem): UserSettingsItem? {
        return userSettingsService.addUserSettings(userSettingsItem)
    }

    @DeleteMapping("/api/user/settings")
    fun deleteUserSettings(@RequestParam("id") id: Int) {
        return userSettingsService.deleteUserSettingsForId(id)
    }

}