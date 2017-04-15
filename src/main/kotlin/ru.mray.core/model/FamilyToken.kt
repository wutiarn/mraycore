package ru.mray.core.model

import java.time.LocalDate
import java.util.*

class FamilyToken() {
    var id: String = UUID.randomUUID().toString()
    lateinit var region: Account.Region
    lateinit var familyLogin: String
    var slot: Int = 0 // Initialization is required for primitive types
    lateinit var token: String
    lateinit var paidUntil: LocalDate
    var account: String? = null
    var assignManually: Boolean = false

    constructor(region: Account.Region, familyLogin: String,
                slot: Int, token: String, paidUntil: LocalDate, assignManually: Boolean = false) : this() {
        this.region = region
        this.familyLogin = familyLogin
        this.slot = slot
        this.token = token
        this.paidUntil = paidUntil
        this.assignManually = assignManually
    }
}