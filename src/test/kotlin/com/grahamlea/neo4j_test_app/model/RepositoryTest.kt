package com.grahamlea.neo4j_test_app.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.neo4j.driver.GraphDatabase
import org.neo4j.harness.Neo4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RepositoryTest(@Autowired val neo4j: Neo4j, @Autowired val thingRepository: ThingRepository) {

    @Test
    internal fun testIt() {
        assertThat(thingRepository.findAll()).hasSize(10)
    }

    @BeforeAll
    fun createSomeTestData() {
        GraphDatabase.driver(neo4j.boltURI()).let { driver ->
            driver.session().use {
                it.run("unwind range(1,10) as i create (t:Thing {name: 'Thing ' + i}) return t")
            }
        }
    }
}
