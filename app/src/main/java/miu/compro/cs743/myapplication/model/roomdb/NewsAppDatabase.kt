package miu.compro.cs743.myapplication.model.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import miu.compro.cs743.myapplication.model.roomdb.dao.UserDao
import miu.compro.cs743.myapplication.model.roomdb.entity.User

@Database(entities = [User::class], version = 1)
abstract class NewsAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        const val DB_VERSION = 1
        private const val DB_NAME = "news_app_database"

        @Volatile
        private var INSTANCE: NewsAppDatabase? = null

        operator fun invoke(context: Context): NewsAppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, NewsAppDatabase::class.java, DB_NAME)
                .addMigrations(MIGRATION_1_TO_2)
                .build()

        private val MIGRATION_1_TO_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }

    }
}