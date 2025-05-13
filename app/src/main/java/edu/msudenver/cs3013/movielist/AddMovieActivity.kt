//Garret jordan

package edu.msudenver.cs3013.movielist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddMovieActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_add_movie)
}

    fun backToFirst(v : View) {
        var textTitle = findViewById<EditText>(R.id.editTitle)
        var textYear = findViewById<EditText>(R.id.editYear)
        var textGenre = findViewById<EditText>(R.id.editGenre)
        var textRating = findViewById<EditText>(R.id.editRating)
        // get the string versions of numbers
        var title = textTitle.getText().toString()
        var year = textYear.getText().toString()
        var genre = textGenre.getText().toString()
        var rating = textRating.getText().toString()



        setMovieInfo(title, year, genre, rating)
    }
    private fun setMovieInfo(title: String, year: String, genre: String, rating: String) {
        val movieInfoIntent = Intent()
        movieInfoIntent.putExtra("title", title)
        movieInfoIntent.putExtra("year", year)
        movieInfoIntent.putExtra("genre", genre)
        movieInfoIntent.putExtra("rating", rating)
        setResult(Activity.RESULT_OK, movieInfoIntent)
        finish()
    }

    }