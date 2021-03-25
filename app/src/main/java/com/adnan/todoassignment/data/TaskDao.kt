package com.adnan.todoassignment.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    fun getTasks(query: String, sortOrder: SortOrder, hideCompleted: Boolean): Flow<List<Task>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getTasksSortedByDateCreated(query, hideCompleted)
            SortOrder.BY_NAME -> getTasksSortedByName(query, hideCompleted)
        }

    fun getSubTasks(categoryId:String, query: String, sortOrder: SortOrder, hideCompleted: Boolean): Flow<List<SubTask>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getSubTasksSortedByDateCreated(query, hideCompleted)
            SortOrder.BY_NAME -> getSubTasksSortedByCategoryId(categoryId, hideCompleted)
        }

    @Query("SELECT * FROM task_table WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, name")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, created")
    fun getTasksSortedByDateCreated(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>


    //St
    @Query("SELECT * FROM sub_task_table WHERE(categoryId == :searchQuery) AND (completed != :hideCompleted OR completed = 0)  ORDER BY important DESC, name")
    fun getSubTasksSortedByCategoryId(searchQuery: String, hideCompleted: Boolean): Flow<List<SubTask>>

    @Query("SELECT * FROM sub_task_table WHERE (completed != :hideCompleted OR completed = 0) AND categoryId == :searchQuery ORDER BY important DESC, created")
    fun getSubTasksSortedByDateCreated(searchQuery: String, hideCompleted: Boolean): Flow<List<SubTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table WHERE completed = 1")
    suspend fun deleteCompletedTasks()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTask(subTask: SubTask)

    @Update
    suspend fun updateSubTask(subTask: SubTask)
}