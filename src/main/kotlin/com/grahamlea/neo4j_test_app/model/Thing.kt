package com.grahamlea.neo4j_test_app.model

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.springframework.data.neo4j.repository.Neo4jRepository

class Thing(val name: String) {
    @Id
    @GeneratedValue
    var id: Long? = null
}

interface ThingRepository: Neo4jRepository<Thing, Long> {

}
