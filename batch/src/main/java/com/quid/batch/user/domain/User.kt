package com.quid.batch.user.domain

import java.time.LocalDateTime

class User(
    val id: Long? = null,
    val name: String,
    val age: Int,
    val email: String,
    val isActive: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val tier: Tier = Tier.BASIC,
) {
    init {
        require(age > 0) { "age must be positive" }
        require(name.isNotBlank()) { "name must not be blank" }
        require(email.isNotBlank()) { "email must not be blank" }
    }
}

fun createUser(
    name: String,
    age: Int,
    email: String,
) = User(
    name = name,
    age = age,
    email = email,
)