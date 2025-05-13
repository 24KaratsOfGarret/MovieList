//Garret Jordan
package edu.msudenver.cs3013.movielist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var movieList: MutableList<Movie>
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fileDir: File

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val data = activityResult.data
            val title = data?.getStringExtra("title") ?: ""
            val year = data?.getStringExtra("year") ?: ""
            val genre = data?.getStringExtra("genre") ?: ""
            val rating = data?.getStringExtra("rating") ?: ""
            movieList.add(Movie(title, year, genre, rating))
            movieAdapter.notifyDataSetChanged()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        fileDir = filesDir
        movieList = mutableListOf()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieAdapter = MovieAdapter(movieList)
        recyclerView.adapter = movieAdapter

        val itemTouchHelper = ItemTouchHelper(movieAdapter.swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Optional hardcoded movies
        movieList.add(Movie("The Godfather", "1972", "Crime", "9.2"))
        movieList.add(Movie("The Dark Knight", "2008", "Action", "9.0"))
        movieList.add(Movie("The Matrix", "1999", "Science Fiction", "8.7"))

        readFile()
    }

    private fun readFile() {
        val file = File(fileDir, "MOVIELIST.csv")
        if (file.exists()) {
            FileInputStream(file).bufferedReader().useLines { lines ->
                lines.forEach {
                    val tokens = it.split(",")
                    if (tokens.size == 4) {
                        val (title, year, genre, rating) = tokens
                        movieList.add(Movie(title, year, genre, rating))
                    }
                }
            }
            movieAdapter.notifyDataSetChanged()
        }
    }

    private fun writeFile() {
        val file = File(fileDir, "MOVIELIST.csv")
        FileOutputStream(file).bufferedWriter().use { out ->
            for (movie in movieList) {
                out.write("${movie.title},${movie.year},${movie.genre},${movie.rating}\n")
            }
        }
    }

    fun saveList(v: View) {
        writeFile()
    }

    fun startSecond(v: View) {
        startForResult.launch(Intent(this, AddMovieActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MOVIELIST", "options menu")
        when (item.itemId) {
            R.id.ratingSort -> {
                Log.d("MOVIELIST", "onOptions: rating sort")
                movieList?.sortBy { it?.rating }
            }
            R.id.yearSort -> {
                Log.d("MOVIELIST", "onOptions: year sort")
                movieList?.sortBy { it?.year }
            }
            R.id.genreSort -> {
                Log.d("MOVIELIST", "onOptions: genre sort")
                movieList?.sortBy { it?.genre }
            }
        }
        movieAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }
}
