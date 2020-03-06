package com.grahamlea.neo4j_test_app

import com.grahamlea.neo4j_test_app.model.ThingRepository
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.SessionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager
import kotlin.system.exitProcess

@SpringBootApplication
@ComponentScan("com.grahamlea.neo4j_test_app")
@EnableNeo4jRepositories("com.grahamlea.neo4j_test_app.model")
@org.springframework.context.annotation.Configuration
class GraphApiApplication {
	private val neo4jUrl = "bolt://localhost:7687"

	@Bean
	fun configuration(): Configuration = Configuration.Builder().uri(neo4jUrl).build()

	@Bean
	fun sessionFactory() = SessionFactory(configuration(), "com.grahamlea.neo4j_test_app")

	@Bean
	fun transactionManager() = Neo4jTransactionManager(sessionFactory())
}

fun main(args: Array<String>) {
	val application: ConfigurableApplicationContext = runApplication<GraphApiApplication>(*args)
	val thingRepository = application.getBean(ThingRepository::class.java)
	println("Things:" + thingRepository.findAll())
	exitProcess(0)
}
