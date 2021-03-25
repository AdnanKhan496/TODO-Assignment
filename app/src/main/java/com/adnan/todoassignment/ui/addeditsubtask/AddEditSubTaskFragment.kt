package com.adnan.todoassignment.ui.addeditsubtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adnan.todoassignment.R
import com.adnan.todoassignment.databinding.FragmentAddEditSubTaskBinding
import com.adnan.todoassignment.util.SharedViewModel
import com.adnan.todoassignment.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class AddEditSubTaskFragment : Fragment(R.layout.fragment_add_edit_sub_task) {

    private val viewModel: AddEditSubTaskFragmentViewModel by viewModels()

    lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditSubTaskBinding.bind(view)



        binding.apply {
            editTextTaskName.setText(viewModel.subTaskName)
            editTextTaskPrice.setText(viewModel.subTaskPrice)
            checkBoxImportant.isChecked = viewModel.subTaskImportance
            checkBoxImportant.jumpDrawablesToCurrentState()
            textViewDateCreated.isVisible = viewModel.subTask != null
            textViewDateCreated.text = "Created: ${viewModel.subTask?.createdDateFormatted}"

            editTextTaskName.addTextChangedListener {
                viewModel.subTaskName = it.toString()
            }

            editTextTaskPrice.addTextChangedListener {
                viewModel.subTaskPrice = it.toString()
            }

            checkBoxImportant.setOnCheckedChangeListener { _, isChecked ->
                viewModel.subTaskImportance = isChecked
            }

            fabSaveTask.setOnClickListener {
                viewModel.onSaveClick(sharedViewModel.categoryId)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditSubTaskEvent.collect { event ->
                when (event) {
                    is AddEditSubTaskFragmentViewModel.AddEditTaskSubTaskEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is AddEditSubTaskFragmentViewModel.AddEditTaskSubTaskEvent.NavigateBackWithResult -> {
                        binding.editTextTaskName.clearFocus()
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }
}