package com.firstapp.movie_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.firstapp.movie_app.model.Movies

@Database(
    entities = [Movies::class],
    version = 1,
    exportSchema= false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java, "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}