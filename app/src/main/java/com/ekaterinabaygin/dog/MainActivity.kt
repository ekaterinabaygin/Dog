package com.ekaterinabaygin.dog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val imageViewDog: ImageView by lazy {
        findViewById(R.id.imageViewDog)
    }

    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.progressBar)
    }

    private val buttonLoadImage: Button by lazy {
        findViewById(R.id.buttonLoadImage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.dogImageState.observe(this) { dogImageState ->
            when (dogImageState) {
                is MainViewModel.DogImageState.Loading -> showLoading()
                is MainViewModel.DogImageState.Data -> showDogImage(dogImageState.image)
                is MainViewModel.DogImageState.Error -> showError(dogImageState.message)
            }
        }

        buttonLoadImage.setOnClickListener {
            viewModel.loadRandomDogImage()
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        imageViewDog.visibility = View.GONE
    }

    private fun showDogImage(dogImage: DogImage) {
        progressBar.visibility = View.GONE
        imageViewDog.visibility = View.VISIBLE

        Glide.with(this)
            .load(dogImage.message)
            .into(imageViewDog)
    }

    private fun showError(errorMessage: String) {
        progressBar.visibility = View.GONE
        imageViewDog.visibility = View.GONE

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

}