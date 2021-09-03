package com.combyne.casestudy.view

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.combyne.casestudy.CreatMoviesMutation
import com.combyne.casestudy.R
import com.combyne.casestudy.base.BaseFragment
import com.combyne.casestudy.data.TvShowManagerResult
import com.combyne.casestudy.databinding.FragmentCreateMovieBinding
import com.combyne.casestudy.util.gone
import com.combyne.casestudy.util.isEmptyNullOrBlank
import com.combyne.casestudy.util.visible
import com.combyne.casestudy.viewmodel.CreateMovieViewModel
import java.text.SimpleDateFormat
import java.util.*


class CreateMovieFragment : BaseFragment(), View.OnClickListener, OnDateSetListener {

    lateinit var binding: FragmentCreateMovieBinding
    private val viewModel: CreateMovieViewModel by viewModels()
    private val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            binding<FragmentCreateMovieBinding>(
                inflater,
                R.layout.fragment_create_movie,
                container
            ).apply {
                lifecycleOwner = viewLifecycleOwner
                callback = this@CreateMovieFragment
            }


        viewModel.createdMovie.observe(viewLifecycleOwner, { response ->
            when (response) {
                is TvShowManagerResult.Success -> {
                    binding.pb.gone()
                    binding.buttonSaveMovie.visible()

                    response.data?.let {
                        Toast.makeText(
                            requireContext(),
                            "${it.movie.title} is saved in the database",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }
                }
                is TvShowManagerResult.Error -> {
                    binding.pb.gone()
                    binding.buttonSaveMovie.visible()

                    response.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
                is TvShowManagerResult.Loading -> {
                    binding.pb.visible()
                    binding.buttonSaveMovie.gone()
                }
            }
        })


        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.buttonSaveMovie -> saveMovie()
            R.id.etDate -> openCalendar()
        }
    }

    private fun openCalendar() {
        DatePickerDialog(
            requireContext(), this, myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        ).show()
    }

    private fun saveMovie() {
        if (!binding.etTitle.isEmptyNullOrBlank()) {
            if (!binding.etDate.isEmptyNullOrBlank()) {
                if (!binding.etSeasons.isEmptyNullOrBlank()) {

                    val movie = CreatMoviesMutation(
                        binding.etTitle.text.toString(),
                        binding.etDate.text!!,
                        binding.etSeasons.text.toString().toDouble()
                    )
                    viewModel.createMovie(movie = movie)

                }
            }
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy/MMM/dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.etDate.setText(sdf.format(myCalendar.time))

    }

    override fun onDateSet(p0: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel()
    }
}