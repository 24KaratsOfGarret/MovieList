//Garret jordan

package edu.msudenver.cs3013.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private val movieList: MutableList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    val swipeToDeleteCallback = SwipeToDeleteCallback()

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var titleTextView: TextView = itemView.findViewById<TextView?>(R.id.titleTextView)
            var yearTextView: TextView = itemView.findViewById<TextView?>(R.id.yearTextView)
            var genreTextView: TextView = itemView.findViewById<TextView?>(R.id.genreTextView)
            var ratingTextView: TextView = itemView.findViewById<TextView?>(R.id.ratingTextView)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList.get(position)
        holder.titleTextView.setText(movie.title)
        holder.yearTextView.setText(movie.year)
        holder.genreTextView.setText(movie.genre)
        holder.ratingTextView.setText(movie.rating)
    }

    override fun getItemCount(): Int{
        return movieList.size
    }

    fun removeItem(position: Int) {
        movieList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class SwipeToDeleteCallback :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        )=
             if (viewHolder is MovieViewHolder) {
                 makeMovementFlags(
                     ItemTouchHelper.ACTION_STATE_IDLE,
                     ItemTouchHelper.RIGHT
                 ) or makeMovementFlags(
                     ItemTouchHelper.ACTION_STATE_SWIPE,
                     ItemTouchHelper.RIGHT
                 )
             } else {
                 0
             }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            removeItem(position)
        }
    }
}