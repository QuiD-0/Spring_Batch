package com.quid.batch.user.repository

import com.quid.batch.user.domain.User
import com.quid.batch.user.repository.entity.toEntity
import com.quid.batch.user.repository.jpa.UserJpaRepository
import org.springframework.stereotype.Repository

interface UserRepository {

    fun save(user: User): User

    @Repository
    class UserRepositoryImpl(
            private val userJpaRepository: UserJpaRepository
    ) : UserRepository {
        override fun save(user: User): User {
            return userJpaRepository.save(toEntity(user)).toUser()
        }
    }
}