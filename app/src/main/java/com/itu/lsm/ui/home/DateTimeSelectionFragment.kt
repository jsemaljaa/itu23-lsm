// Author: Alina Vinogradova (xvinog00)


package com.itu.lsm.ui.home

import com.itu.lsm.ui.home.SharedReservationViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.itu.lsm.R
import com.itu.lsm.databinding.FragmentDatetimeSelectionBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateTimeSelectionFragment : Fragment() {

    private var _binding: FragmentDatetimeSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDatetimeSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedReservationViewModel::class.java)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        binding.datePicker.minDate = calendar.timeInMillis

        binding.btnConfirmDateTime.setOnClickListener {
            val selectedDate = formatDate(getDateFromDatePicker())
            val time = getTimeFromTimePicker()
            val selectedTime = formatTime(time.first, time.second)

            sharedViewModel.selectDateTime(selectedDate + " " + selectedTime)

            findNavController().navigate(R.id.action_dateTimeSelection_to_reservationConfirmation)
        }
    }

    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())
        return formatter.format(date).toUpperCase(Locale.getDefault())
    }

    fun formatTime(hour: Int, minute: Int): String {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    private fun getDateFromDatePicker(): Date {
        val year = binding.datePicker.year
        val month = binding.datePicker.month
        val day = binding.datePicker.dayOfMonth
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.time
    }

    private fun getTimeFromTimePicker(): Pair<Int, Int> {
        val hour = binding.timePicker.currentHour
        val minute = binding.timePicker.currentMinute
        return Pair(hour, minute)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
