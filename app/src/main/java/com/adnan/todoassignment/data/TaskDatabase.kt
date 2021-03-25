package com.adnan.todoassignment.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.adnan.todoassignment.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class, SubTask::class], version = 1,exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            /*applicationScope.launch {
                dao.insert(Task("1", "50"))
                dao.insert(Task("2", "100"))
                dao.insert(Task("3","500", important = true))
                dao.insert(Task("4","300", completed = true))
                dao.insert(Task("5","150"))
                dao.insert(Task("6","450", completed = true))
            }*/
        }
    }
}