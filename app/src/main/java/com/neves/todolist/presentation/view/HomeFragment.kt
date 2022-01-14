package com.neves.todolist.presentation.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.neves.domain.model.Task
import com.neves.todolist.R
import com.neves.todolist.databinding.DialogTaskBinding
import com.neves.todolist.databinding.FragmentHomeBinding
import com.neves.todolist.presentation.adapter.TaskItemViewAdapter
import com.neves.todolist.presentation.extensions.*
import com.neves.todolist.presentation.mapper.exception
import com.neves.todolist.presentation.state.TaskItemUiState
import com.neves.todolist.presentation.state.UiState
import com.neves.todolist.presentation.viewModels.TaskViewModel
import com.neves.todolist.presentation.viewModels.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject

class HomeFragment : Fragment(), View.OnClickListener {

    private val calendar = Calendar.getInstance()
    private var _binding: FragmentHomeBinding? = null
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var taskViewModel: TaskViewModel

    private val binding get() = _binding!!
    private val taskAdapter by lazy { TaskItemViewAdapter() }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        taskViewModel = viewModelFactory.create(TaskViewModel::class.java)
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            taskViewModel.getTasks(calendar.start(year, month, dayOfMonth), calendar.end(year, month, dayOfMonth))
        }

        taskViewModel.getTasks(calendar.startToday(), calendar.endToday())

        binding.rvTask.adapter = taskAdapter

        lifecycle(taskViewModel.taskLiveData, ::handleListTask)
        lifecycle(taskViewModel.uiState, ::handleUiEvents)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTask.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleListTask(taskUiState: List<TaskItemUiState>){
        taskAdapter.submitList(taskUiState)
    }

    private fun handleUiEvents(uiState: UiState){
        binding.progressTask.isVisible = false
        when(uiState){
            is UiState.Success -> toast(getString(R.string.msgSucess))
            is UiState.Failure -> toast(exception(uiState.exception))
            is UiState.Loading -> binding.progressTask.isVisible = true
            else -> Unit
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.add_task) {
            taskDialog()
        }
    }

    private fun taskDialog() {
        BottomSheetDialog(requireContext()).apply {
            val binding = DialogTaskBinding.inflate(
                layoutInflater
            )
            setContentView(binding.root)

            // Hide Keyboard
            Objects.requireNonNull(window)!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            // Listeners
            binding.btnConfirm.setOnClickListener {
                val task = Task(
                    0,
                    Objects.requireNonNull(binding.title.text).toString(),
                    Objects.requireNonNull(binding.description.text).toString(),
                    Date(_binding?.calendarView!!.date),
                )
                taskViewModel.insertTask(task)
                dismiss()
            }

            binding.btnCancel.setOnClickListener{dismiss()}

            show()
        }
    }
}