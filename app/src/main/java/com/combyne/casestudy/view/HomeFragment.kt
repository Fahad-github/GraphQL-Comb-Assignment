package com.combyne.casestudy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.combyne.casestudy.R
import com.combyne.casestudy.base.BaseFragment
import com.combyne.casestudy.databinding.FragmentHomeBinding
import com.combyne.casestudy.databinding.FragmentMovieListBinding

class HomeFragment : BaseFragment(), View.OnClickListener {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            binding<FragmentHomeBinding>(
                inflater,
                R.layout.fragment_home,
                container
            ).apply {
                lifecycleOwner = viewLifecycleOwner
                callback =this@HomeFragment
            }
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.buttonAddMovie -> findNavController().navigate(R.id.action_homeFragment_to_createMovieFragment)
            R.id.buttonShowList -> findNavController().navigate(R.id.action_homeFragment_to_movieListFragment)
        }
    }


}