package dog.snow.androidrecruittest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dog.snow.androidrecruittest.database.AppDatabase
import dog.snow.androidrecruittest.database.ListItemDao
import dog.snow.androidrecruittest.database.ListItemEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ListItemDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var listItemDao: ListItemDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        listItemDao = database.listItemDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insert_list_items_to_database_return_is_not_null_or_empty() = runBlocking {
        listItemDao.insert(listMocks)
        val listEntities = listItemDao.getAll().first()
        assertNotNull(listEntities)
        assertNotEquals(0, listEntities.size)
    }

    @Test
    fun search_by_album_title_is_not_null_or_empty() = runBlocking {
        listItemDao.insert(listMocks)
        val searchedEntity = listItemDao.searchListItemByPhrase(firstListItemEntityMock.albumTitle)
        assertNotNull(searchedEntity)
    }

    @Test
    fun search_by_title_is_not_null_or_empty() = runBlocking {
        listItemDao.insert(listMocks)
        val searchedEntity = listItemDao.searchListItemByPhrase(firstListItemEntityMock.title)
        assertNotNull(searchedEntity)
    }

    @Test
    fun get_all_entities_is_null_or_empty() = runBlocking {
        val entities = listItemDao.getAll().first()
        assertEquals(true, entities.isNullOrEmpty())
    }

    @Test
    fun check_whether_database_return_two() = runBlocking {
        listItemDao.insert(listOf(firstListItemEntityMock))
        listItemDao.insert(listOf(secondListItemEntityMock))
        val entities = listItemDao.getAll().first()
        assertEquals(2, entities.size)
    }

    private var firstListItemEntityMock = ListItemEntity(
        1,
        1,
        "Test title first",
        "album title first",
        "john",
        "sdjsj@gmail.com",
        "7777777",
        "https://via.placeholder.com/600/14ba42",
        "https://via.placeholder.com/150/14ba42"
    )

    private var secondListItemEntityMock = ListItemEntity(
        2,
        2,
        "Test title second",
        "album title second",
        "john",
        "sdjsj@gmail.com",
        "7777777",
        "https://via.placeholder.com/600/14ba42",
        "https://via.placeholder.com/150/14ba42"
    )

    private var thirdListItemEntityMock = ListItemEntity(
        3,
        3,
        "Test title third",
        "album title third",
        "john",
        "sdjsj@gmail.com",
        "7777777",
        "https://via.placeholder.com/600/14ba42",
        "https://via.placeholder.com/150/14ba42"
    )

    private val listMocks = mutableListOf<ListItemEntity>().apply {
        add(firstListItemEntityMock)
        add(secondListItemEntityMock)
        add(thirdListItemEntityMock)
    }
}