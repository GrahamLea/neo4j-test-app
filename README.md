Demonstration of a simple Neo4j Spring Boot app with a Neo4jRepository interface and an
integration test.

Branches:
* **master**: Integration test uses [TestContainers](https://www.testcontainers.org/modules/databases/neo4j/) 
to bring up Neo4j in a Docker container
and the [@DataNeo4jTest](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-test-autoconfigure/src/main/java/org/springframework/boot/test/autoconfigure/data/neo4j/DataNeo4jTest.java) 
annotation as [recommended by Michael Simons](https://medium.com/neo4j/testing-your-neo4j-based-java-application-34bef487cc3c).
* neo4j-harness: Integration test uses the 
[neo4j-harness](https://neo4j.com/docs/developer-manual/3.4/extending-neo4j/procedures/#_writing_integration_tests), 
which creates an in-memory Neo4j instance.
