package com.quid.batch.user.repository

import com.quid.batch.user.domain.createUser
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.stream.IntStream

@Disabled
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun insertUser(){
        IntStream.range(0, 100).forEach {
            userRepository.save(createUser(
                    name = "name$it",
                    age = it/10 + 20,
                    email = "email$it@quid.com"
            ))
        }
    }
}