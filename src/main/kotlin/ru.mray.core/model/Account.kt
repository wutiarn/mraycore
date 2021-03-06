package ru.mray.core.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Duration
import java.time.Instant
import java.time.Period
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "accounts")
class Account(
        val email: String,
        @Enumerated(EnumType.STRING) val region: Region,
        var renewPeriod: Int = 1,
        var registeredAt: Instant = Instant.now(),

        @OneToOne(mappedBy = "account")
        var familyToken: FamilyToken? = null,

        var activeUntil: Instant? = null,
        var renewNotificationSentAt: Instant? = null,
        var admin: Boolean = false,
        @Column(name = "password") var _password: String? = null,
        @Id val id: String = UUID.randomUUID().toString()
) : UserDetails {

    enum class Region {
        PH,
        US
    }

    val ttl: String?
    get() {
        if (activeUntil == null) {
            return null
        }

        val daySeconds = 60 * 60 * 24

        val activeUntilEpoch = this.activeUntil!!.epochSecond
        val nowEpoch = Instant.now().epochSecond

        val diff = activeUntilEpoch - nowEpoch
        val days = (diff / daySeconds).toInt()

        val remaining = diff % daySeconds
        val hours = remaining / 3600
        val minutes = remaining % 3600 / 60
        val sec = remaining % 60

        val result = "${days}D${hours}H${minutes}M${sec}S"
        return result
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
        if (admin) {
            authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
        }
        return authorities
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return email
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String? {
        return _password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}