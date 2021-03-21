package com.adnan.todoassignment.ui.alltaskslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnan.todoassignment.R
import com.adnan.todoassignment.databinding.FragmentAllTasksListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTasksFragment : Fragment(R.layout.fragment_all_tasks_list) {

    private val viewModel: AllTasksFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAllTasksListBinding.bind(view)

        val allTaskAdapter = AllTasksAdapter()

        binding.apply {
            rvAllTasks.apply {
                adapter = allTaskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }

        viewModel.tasks.observe(viewLifecycleOwner) {
            allTaskAdapter.submitList(it)
        }

    }


}