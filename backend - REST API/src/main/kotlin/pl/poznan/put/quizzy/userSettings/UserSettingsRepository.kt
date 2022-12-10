package pl.poznan.put.quizzy.userSettings

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.poznan.put.quizzy.userSettings.model.UserSettingsItem

@Repository
interface UserSettingsRepository: JpaRepository<UserSettingsItem, Long>