package com.combyne.casestudy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.combyne.casestudy.GetMoviesQuery
import com.combyne.casestudy.R
import com.combyne.casestudy.databinding.ItemMovieBinding

class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ItemViewHolder>() {

    private var movies : List<GetMoviesQuery.Edge> = ArrayList()

    inner class ItemViewHolder(
        private val parent: ViewGroup,
        private val binding: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: GetMoviesQuery.Edge) {
            item.node?.let {
                binding.tvMovieName.text = it.title
                binding.tvReleaseDate.text = "Release Date: ${it.releaseDate}"
                binding.tvSeasons.text = "Seasons: ${it.seasons}"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setMoviesList(list: List<GetMoviesQuery.Edge>){
        this.movies = list
        notifyDataSetChanged()
    }
}