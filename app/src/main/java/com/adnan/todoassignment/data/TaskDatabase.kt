package com.adnan.todoassignment.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.adnan.todoassignment.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1,exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Potatoes", "50"))
                dao.insert(Task("Tomatoes", "100"))
                dao.insert(Task("Fish","500", important = true))
                dao.insert(Task("Dishes","300", completed = true))
                dao.insert(Task("Drinks","150"))
                dao.insert(Task("Glasses","450", completed = true))
            }
        }
    }
}