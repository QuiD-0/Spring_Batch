package com.quid.batch.user.repository.entity

import com.quid.batch.user.domain.Tier
import com.quid.batch.user.domain.User
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GenerationType.*

@Entity
@Table(name = "user")
class UserEntity(
        @Id @GeneratedValue(strategy = IDENTITY)
        val id: Long? = null,
        val name: String = "",
        val age: Int = 0,
        val email: String = "",
        val isActive: Boolean = true,
        val createdAt: LocalDateTime = LocalDateTime.now(),
        @Enumerated(EnumType.STRING)
        val tier: Tier = Tier.BASIC,
) {

    fun toUser(): User = User(
            id = id,
            name = name,
            age = age,
            email = email,
            isActive = isActive,
            createdAt = createdAt,
            tier = tier,
    )
}

fun toEntity(user: User) = UserEntity(
        id = user.id,
        name = user.name,
        age = user.age,
        email = user.email,
        isActive = user.isActive,
        createdAt = user.createdAt,
        tier = user.tier,
)
