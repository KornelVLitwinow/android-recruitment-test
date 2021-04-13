package dog.snow.androidrecruittest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ListItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listItemDao(): ListItemDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return (instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java,
                    "app_database"
                ).build()
                instance = newInstance
                newInstance
            })
        }
    }
}