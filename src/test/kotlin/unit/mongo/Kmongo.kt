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
import playground.Person
import kotlin.test.assertEquals

class KmongoTest {

    private val persons = listOf(
        Person(name = "James", age = 12)
    )

    companion object {
        lateinit var client: MongoClient
        lateinit var database: MongoDatabase
        lateinit var personsCollection: MongoCollection<Person>

        @BeforeAll()
        @JvmStatic
        fun setupClient() {
            client = KMongo.createClient()
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