package com.quid.batch.user.repository.jpa

import com.quid.batch.user.repository.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
}