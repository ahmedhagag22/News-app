package com.example.new_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.new_app.api.ApiManger
import com.example.new_app.api.SourcesResponse
import com.example.new_app.api.apiServes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var text: TextView = findViewById(R.id.txtview)
        var apiky = "598c8a4e6ec1445d96b1f169c75fd2ae"

        ApiManger.getApis().getSources(apiky).enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                text.setText(response.body()?.sources.toString())
                Log.e("api", response.body().toString())

            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                TODO("Not yet implemented")
                Log.e("error", t?.localizedMessage ?: "")
            }

        })
    }
}