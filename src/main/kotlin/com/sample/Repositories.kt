package com.sample

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.asType
import org.springframework.data.r2dbc.core.await
import org.springframework.data.r2dbc.core.awaitOne
import org.springframework.data.r2dbc.core.flow
import org.springframework.data.r2dbc.core.into

class UserRepository(private val client: DatabaseClient) {

	suspend fun count() =
			client.execute("SELECT COUNT(*) FROM users").asType<Long>().fetch().awaitOne()

	@ExperimentalCoroutinesApi
	fun findAll() = client.select().from("users").asType<User>().fetch().flow()

	suspend fun findOne(id: String) =
			client.execute("SELECT * FROM users WHERE login = :login").bind("login", id).asType<User>().fetch().awaitOne()

	suspend fun deleteAll() =
		client.execute("DELETE FROM users").await()

	suspend fun save(user: User)=
		client.insert().into<User>().table("users").using(user).await()


	suspend fun init() {
		client.execute("CREATE TABLE IF NOT EXISTS users (login varchar PRIMARY KEY, firstname varchar, lastname varchar);").await()
		deleteAll()
		save(User("smaldini", "Stéphane", "Maldini"))
		save(User("sdeleuze", "Sébastien", "Deleuze"))
		save(User("bclozel", "Brian", "Clozel"))
	}
}