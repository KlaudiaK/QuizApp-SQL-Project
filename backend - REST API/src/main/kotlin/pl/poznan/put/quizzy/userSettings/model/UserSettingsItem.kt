package pl.poznan.put.quizzy.userSettings.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@Table(name="User Settings")
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class UserSettingsItem (
    @Id
    @Column(name = "users_id")
    val userReferenceId: Int,

    @Column(name = "dark_mode")
    val darkMode: Boolean? = false,

    @Column(name = "preffered_language")
    val prefferedLanguage: String? = "EN"
)