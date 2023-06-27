package com.quid.batch.user.domain

class User(
        val id: Long,
        val name: String,
        val age: Int,
        val email: String,
        val isActive: Boolean,
        val createdAt: String,
        val tier: Tier,
) {
}