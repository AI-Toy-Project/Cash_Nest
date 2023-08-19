package com.example.cashnest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.content.Intent
import android.provider.Settings.Global
import android.widget.Button
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.github.mikephil.charting.charts.BarChart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.cashnest.MainActivity2
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

//성공주석
class MainActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://61.39.51.225:5000")
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)
    private val apiService2 = retrofit.create(ApiService2::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1: ImageButton = findViewById(R.id.button1)

        button1.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val response = apiService2.getPrediction()
                    if (response.isSuccessful) {
                        val predictionData: PredictionData? = response.body()
                        intent.putExtra("one", predictionData?.category01 ?: 0)
                        intent.putExtra("two", predictionData?.category02 ?: 0)
                        intent.putExtra("three", predictionData?.category03 ?: 0)
                        intent.putExtra("four", predictionData?.category04 ?: 0)
                        intent.putExtra("five", predictionData?.category05 ?: 0)
                        intent.putExtra("six", predictionData?.category06 ?: 0)
                        intent.putExtra("seven", predictionData?.category07 ?: 0)
                        intent.putExtra("eight", predictionData?.category08 ?: 0)
                        intent.putExtra("nine", predictionData?.category09 ?: 0)
                        intent.putExtra("ten", predictionData?.category10 ?: 0)
                        intent.putExtra("eleven", predictionData?.category11 ?: 0)
                        intent.putExtra("twelve", predictionData?.category12 ?: 0)
                        intent.putExtra("thirteen", predictionData?.category13 ?: 0)
                        intent.putExtra("fourteen", predictionData?.category14 ?: 0)
                        intent.putExtra("fifteen", predictionData?.category15 ?: 0)
                        intent.putExtra("sixteen", predictionData?.category16 ?: 0)
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        val button2: ImageButton = findViewById(R.id.button2)
        button2.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            val call = apiService.getPrediction()
            call.enqueue(object : Callback<Map<String, Int>> {
                override fun onResponse(
                    call: Call<Map<String, Int>>,
                    response: Response<Map<String, Int>>
                ) {
                    if (response.isSuccessful) {
                        val predictionAmount = response.body()?.get("amount")
                        intent.putExtra("predictionAmount", predictionAmount)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<Map<String, Int>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(8, TimeUnit.MINUTES)
            .readTimeout(8, TimeUnit.MINUTES)
            .writeTimeout(8, TimeUnit.MINUTES)
            .build()
    }
}