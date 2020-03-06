package com.grahamlea.neo4j_test_app.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.neo4j.driver.GraphDatabase
import org.neo4j.harness.Neo4jBuilders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = [RepositoryTest.Initializer::class])
class RepositoryTest(@Autowired val thingRepository: ThingRepository) {

    @Test
    internal fun testIt() {
        assertThat(thingRepository.findAll()).hasSize(10)
    }

    companion object {
        private val neo4j = Neo4jBuilders.newInProcessBuilder().build()

        @BeforeAll
        @JvmStatic
        fun createSomeTestData() {
            GraphDatabase.driver(neo4j.boltURI()).let { driver ->
                driver.session().use {
                    it.run("unwind range(1,10) as i create (t:Thing {name: 'Thing ' + i}) return t")
                }
            }
        }

        @AfterAll
        fun shutdownNeo4j() {
            neo4j.close()
        }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.data.neo4j.uri=" + neo4j.boltURI(),
                    "spring.data.neo4j.password="
            ).applyTo(configurableApplicationContext.environment)
        }
    }
}
