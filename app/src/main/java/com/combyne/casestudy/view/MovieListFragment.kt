package com.combyne.casestudy.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.combyne.casestudy.GetMoviesQuery
import com.combyne.casestudy.R
import com.combyne.casestudy.adapter.MovieListAdapter
import com.combyne.casestudy.base.BaseFragment
import com.combyne.casestudy.data.TvShowManagerResult
import com.combyne.casestudy.databinding.FragmentMovieListBinding
import com.combyne.casestudy.util.gone
import com.combyne.casestudy.util.visible
import com.combyne.casestudy.viewmodel.MovieListViewModel

class MovieListFragment : BaseFragment() {

    lateinit var binding: FragmentMovieListBinding
    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            binding<FragmentMovieListBinding>(
                inflater,
                R.layout.fragment_movie_list,
                container
            ).apply {
                lifecycleOwner = viewLifecycleOwner
            }

        viewModel.movies.observe(viewLifecycleOwner, { response ->
            when (response) {
                is TvShowManagerResult.Success -> {
                    binding.pb.gone()
                    response.data?.let {
                        if (it.count > 0) setRecyclerView(it.edges)
                    }
                }
                is TvShowManagerResult.Error -> {
                    binding.pb.gone()
                    response.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
                is TvShowManagerResult.Loading -> binding.pb.visible()
            }
        })


        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun setRecyclerView(it: List<GetMoviesQuery.Edge?>?){

        val movieListAdapter=MovieListAdapter()

        movieListAdapter.setMoviesList(it as List<GetMoviesQuery.Edge>)

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

}