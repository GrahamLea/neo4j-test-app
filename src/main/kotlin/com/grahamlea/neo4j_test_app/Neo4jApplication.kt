package com.grahamlea.neo4j_test_app

import com.grahamlea.neo4j_test_app.model.ThingRepository
import org.neo4j.ogm.config.Configuration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import kotlin.system.exitProcess

@SpringBootApplication
@EnableNeo4jRepositories("com.grahamlea.neo4j_test_app.model")
class GraphApiApplication {
	private val neo4jUrl = "bolt://localhost:7687"

	@Bean
	fun configuration(): Configuration = Configuration.Builder().uri(neo4jUrl).build()

}

fun main(args: Array<String>) {
	val application: ConfigurableApplicationContext = runApplication<GraphApiApplication>(*args)
	val thingRepository = application.getBean(ThingRepository::class.java)
	println("Things:" + thingRepository.findAll())
	exitProcess(0)
}
