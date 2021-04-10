package unit.mongo

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import org.slf4j.LoggerFactory.getLogger
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import playground.Person
import java.lang.invoke.MethodHandles
import kotlin.test.assertEquals

@org.testcontainers.junit.jupiter.Testcontainers
class KmongoTestContainerTest {


    private val persons = listOf(
        Person(name = "James", age = 12)
    )

    companion object {
        private lateinit var mongodbContainer: MongoDBContainer
        private lateinit var client: MongoClient
        private lateinit var database: MongoDatabase
        lateinit var personsCollection: MongoCollection<Person>


        @BeforeAll()
        @JvmStatic
        fun setupClient() {
            @Container
            mongodbContainer = MongoDBContainer("mongo:4.4.5-bionic")
            mongodbContainer.start()

            val address = mongodbContainer.host
            val port = mongodbContainer.firstMappedPort

            client = KMongo.createClient("mongodb://$address:$port")
            database = client.getDatabase("KmongoTest")
            personsCollection = database.getCollection<Person>("persons")
        }

        @AfterAll()
        @JvmStatic
        fun destroyClient() {
            database.drop()
            client.close()
        }
    }


    @Test
    @Order(0)
    fun addPersonsToCollectionTest() {
        val result = personsCollection.insertMany(persons)
        assertEquals(true, result.wasAcknowledged())
    }

    @Test
    @Order(1)
    fun getPersonsFromCollectionTest() {
        val personsResultList = personsCollection.find().toList()
        assertEquals(persons.size, personsResultList.size)
    }
}