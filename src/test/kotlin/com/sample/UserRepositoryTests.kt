package com.sample

import com.sample.user.UserRepository
import com.sample.user.dataConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.boot.WebApplicationType
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.fu.kofu.application

@ExperimentalCoroutinesApi
class UserRepositoryTests {

	private val dataApp = application(WebApplicationType.NONE) {
		enable(dataConfig)
	}

	private lateinit var context: ConfigurableApplicationContext

	@BeforeAll
	fun beforeAll() {
		context = app.run(profiles = "test")
	}

	@Test
	fun count() {
		val repository = context.getBean<UserRepository>()
		runBlocking {
			assertEquals(3, repository.count())
		}
	}

	@AfterAll
	fun afterAll() {
		context.close()
	}
}
