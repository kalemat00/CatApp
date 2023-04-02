package com.example.cat.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.cat.CatApplication
import com.example.cat.R
import com.example.cat.databinding.ActivityCatBinding
import com.example.cat.usecase.CatViewModel
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

        viewModel = (application as CatApplication).catViewModel.create(CatViewModel::class.java)
        viewModel.send(CatViewModel.CatViewModelEvent.RetrieveCatsRepos(lastCatSnackBar(), 3))
        observeRepos()
    }

    private fun observeRepos() {
        viewModel.result.observe(this){
            when(it){
                is CatViewModel.CatViewModelResult.Result -> {
                    setUiWithPicasso(it.cat.url, binding.imageView)
                }
                is CatViewModel.CatViewModelResult.Error -> {
                    Snackbar.make(binding.root, "Error retrieving cats: ${it.message}", Snackbar.LENGTH_INDEFINITE)
                        .setAction("retry") {
                            viewModel.send(CatViewModel.CatViewModelEvent.RetrieveCatsRepos(lastCatSnackBar(), 3))
                        }.show()
                }
            }
        }
    }

    private fun lastCatSnackBar(): () -> Unit = {
        Snackbar.make(binding.root, "Want more cats?", Snackbar.LENGTH_INDEFINITE)
            .setAction("yes") {
                viewModel.send(CatViewModel.CatViewModelEvent.RetrieveCatsRepos(lastCatSnackBar(), 3))
            }.show()
    }

    private fun setUiWithPicasso(url: String, viewToPutInto: ImageView){
        Picasso.get()
            .load(url)
            .error(R.drawable.no_internet_connection)
            .into(viewToPutInto)
    }
}