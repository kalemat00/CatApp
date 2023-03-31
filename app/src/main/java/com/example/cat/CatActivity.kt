package com.example.cat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cat.databinding.ActivityCatBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

//https://api.thecatapi.com/v1/images/search?limit=10&breed_ids=beng&api_key=REPLACE_ME


class CatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatBinding
    private lateinit var viewModel: CatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CatViewModel::class.java)
        viewModel.retrieveRepos(lastCatSnackBar())
        observeRepos()
    }
    private fun observeRepos() {

        viewModel.cat.observe(this) {
            Picasso.get()
                .load(it.url)
                .error(R.drawable.no_internet_connection)
                .into(binding.imageView)
        }

        viewModel.error.observe(this) {
            Snackbar.make(binding.root, "Error retrieving cats: $it", Snackbar.LENGTH_INDEFINITE)
                .setAction("retry") {
                    viewModel.retrieveRepos(lastCatSnackBar())
                }.show()
        }
    }

    private fun lastCatSnackBar(): () -> Unit = {
        Snackbar.make(binding.root, "Want more cats?", Snackbar.LENGTH_INDEFINITE)
            .setAction("yes") {
                viewModel.retrieveRepos(lastCatSnackBar())
            }.show()
    }
}