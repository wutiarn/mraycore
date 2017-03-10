package ru.mray.core.model

import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant
import java.util.*

class Account() : UserDetails {
    lateinit var email: String
    lateinit var region: Region
    var renewPeriod: Int = 1
    var provisioned: Boolean = false
    var activeUntil: Instant? = null
    @Field("password") var _password: String? = null

    var id: String = UUID.randomUUID().toString()

    constructor(email: String, region: Region, renewPeriod: Int) : this() {
        this.email = email
        this.region = region
        this.renewPeriod = renewPeriod
    }

    enum class Region {
        PH,
        US
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
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