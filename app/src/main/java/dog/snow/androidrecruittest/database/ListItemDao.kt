package dog.snow.androidrecruittest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ListItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listItemEntities: List<ListItemEntity>): List<Long>

    @Query("SELECT * FROM ListItemEntity")
    fun getAll(): Flow<List<ListItemEntity>>

    @Query("SELECT * FROM ListItemEntity WHERE album_title LIKE '%' || :text || '%' OR title LIKE '%' || :text || '%' ")
    fun searchListItemByPhrase(text: String): Flow<List<ListItemEntity>>
}