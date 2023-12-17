package com.ekaterinabaygin.dog.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ekaterinabaygin.dog.R
import com.ekaterinabaygin.dog.data.Dog
import com.ekaterinabaygin.dog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        observeData()
    }

    private fun initView() {
        binding.loadImageButton.setOnClickListener {
            viewModel.loadRandomDog()
        }
    }

    private fun observeData() {
        viewModel.dogImageState.observe(this) { dogImageState ->
            when (dogImageState) {
                is DogImageState.Loading -> showLoading()
                is DogImageState.Data -> showDogImage(dogImageState.image)
                is DogImageState.Error -> showError(dogImageState.message)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.dogImage.visibility = View.GONE
    }

    private fun showDogImage(dog: Dog) {
        binding.progressBar.visibility = View.GONE
        binding.dogImage.visibility = View.VISIBLE
        Glide.with(this)
            .load(dog.imageUrl)
            .error(R.drawable.ic_error)
            .into(binding.dogImage)
    }

    private fun showError(errorMessage: String) {
        binding.progressBar.visibility = View.GONE
        binding.dogImage.visibility = View.GONE
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}