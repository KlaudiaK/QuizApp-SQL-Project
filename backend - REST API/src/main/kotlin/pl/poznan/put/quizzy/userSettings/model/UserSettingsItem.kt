package pl.poznan.put.quizzy.userSettings.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder
@Table(name="user_settings")
@NoArgsConstructor
@AllArgsConstructor
@Entity
data class UserSettingsItem (
    @Id
    @Column(name = "users_id")
    val userReferenceId: Int,

    @Column(name = "dark_mode")
    val darkMode: Char? = 'N',

    @Column(name = "preferred_language")
    val preferredLanguage: String? = "EN"
)