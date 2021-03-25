package com.adnan.todoassignment.ui.addeditsubtask

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adnan.todoassignment.data.SubTask
import com.adnan.todoassignment.data.TaskDao
import com.adnan.todoassignment.ui.ADD_TASK_RESULT_OK
import com.adnan.todoassignment.ui.EDIT_TASK_RESULT_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditSubTaskFragmentViewModel @ViewModelInject constructor(
    private val subTaskDao: TaskDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val subTask = state.get<SubTask>("subTask")

    var categoryId = state.get<Int>("categoryId") ?: subTask?.id ?: 0
        set(value) {
            field = value
            state.set("categoryId", value)
        }

    var subTaskName = state.get<String>("subTaskName") ?: subTask?.name ?: ""
        set(value) {
            field = value
            state.set("subTaskName", value)
        }

    var subTaskPrice = state.get<String>("subTaskPrice") ?: subTask?.price ?: ""
        set(value) {
            field = value
            state.set("subTaskPrice", value)
        }

    var subTaskImportance = state.get<Boolean>("subTaskImportance") ?: subTask?.important ?: false
        set(value) {
            field = value
            state.set("subTaskImportance", value)
        }

    private val addEditSubTaskEventChannel = Channel<AddEditTaskSubTaskEvent>()
    val addEditSubTaskEvent = addEditSubTaskEventChannel.receiveAsFlow()

    fun onSaveClick(categoryId : Int) {
        if (subTaskName.isBlank()) {
            showInvalidInputMessage("Item Name cannot be empty")
            return
        } else if( subTaskPrice.isBlank()){
            showInvalidInputMessage("Item Price cannot be empty")
            return
        }

        if (subTask != null) {
            val updatedTask = subTask.copy(name = subTaskName,price = subTaskPrice, important = subTaskImportance)
            updateSubTask(updatedTask)
        } else {
            val newTask = SubTask(categoryId = categoryId,name = subTaskName,price = subTaskPrice, important = subTaskImportance)
            createSubTask(newTask)
        }
    }

    private fun createSubTask(subTask: SubTask) = viewModelScope.launch {
        subTaskDao.insertSubTask(subTask)
        addEditSubTaskEventChannel.send(AddEditTaskSubTaskEvent.NavigateBackWithResult(ADD_TASK_RESULT_OK))
    }

    private fun updateSubTask(subTask: SubTask) = viewModelScope.launch {
        subTaskDao.updateSubTask(subTask)
        addEditSubTaskEventChannel.send(AddEditTaskSubTaskEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditSubTaskEventChannel.send(AddEditTaskSubTaskEvent.ShowInvalidInputMessage(text))
    }

    sealed class AddEditTaskSubTaskEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditTaskSubTaskEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditTaskSubTaskEvent()
    }
}