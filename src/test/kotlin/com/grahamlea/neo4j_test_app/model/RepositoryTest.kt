package com.grahamlea.neo4j_test_app.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.neo4j.driver.GraphDatabase
import org.neo4j.ogm.config.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.testcontainers.containers.Neo4jContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@DataNeo4jTest
class RepositoryTest(@Autowired val thingRepository: ThingRepository) {

    class KNeo4jContainer : Neo4jContainer<KNeo4jContainer>("neo4j:4.0.0")

    @Test
    internal fun testIt() {
        Assertions.assertThat(thingRepository.findAll()).hasSize(10);
    }

    companion object {
        @Container
        private val databaseServer: KNeo4jContainer = KNeo4jContainer().withoutAuthentication()

        // Create some test data
        @BeforeAll
        @JvmStatic
        fun createSomeTestDta() {
            GraphDatabase.driver(databaseServer.boltUrl).let { driver ->
                driver.session().let {
                    it.run("unwind range(1,10) as i create (t:Thing {name: 'Thing ' + i}) return t")
                }
            }

        }
    }

    @TestConfiguration
    internal class Config {
        @Bean
        @Primary
        fun configuration(): Configuration {
            return Configuration.Builder().uri(databaseServer.boltUrl).build()
        }
    }
}
