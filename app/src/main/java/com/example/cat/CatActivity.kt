package com.example.cat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.cat.databinding.ActivityCatBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val APIKEY = "live_4pNlR3a0TubEa76Peqfzcar0DzDv1OcKGFiWhsUTq384V5IFiLuGPBTaKbzZZL15"
const val HEADER = "x-api-key"
//https://api.thecatapi.com/v1/images/search?limit=10&breed_ids=beng&api_key=REPLACE_ME

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder().addHeader(HEADER, APIKEY).build()
        Log.i("Authorization Interceptor", "newRequest: $newRequest")


        return chain.proceed(newRequest)
    }
}


class CatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatBinding
    private val logging = HttpLoggingInterceptor()
    private val authorization = AuthorizationInterceptor()
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authorization)
        .build()
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .client(client)
        .baseUrl("https://api.thecatapi.com/v1/images/")
        .addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    private val catService: CatService = retrofit.create(CatService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logging.level = HttpLoggingInterceptor.Level.HEADERS
        lifecycleScope.launch {
            try {
                catService.listRepos(30).forEach {
                    Picasso.get()
                        .load(it.url)
                        .into(binding.imageView)
                    delay(5000)
                }

            } catch (e: Exception) {
                Log.e("MainActivity", "error: $e")
            }
            Toast.makeText(this@CatActivity, "Last cat :)", Toast.LENGTH_LONG).show()
        }
    }
}