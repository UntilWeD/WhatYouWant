package com.uwdp.whatyouwant.Search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.uwdp.whatyouwant.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val CLIENT_ID = "nEUQf0dPDqcvfRoVNlvp"
    private val CLIENT_SECRET = "D59hcg8Ko2"

    private var display : Int = 10
    private var start: Int = 0
    private var searchWord : String = "사과"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(NaverAPI::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener{
            itemSearchResult()
        }

    }

    private fun itemSearchResult(){
        api.getSearchItems(CLIENT_ID,CLIENT_SECRET,searchWord,display,start)
            .enqueue(object : Callback<ResultGetSearchItems> {
                override fun onResponse(
                    call: Call<ResultGetSearchItems>,
                    response: Response<ResultGetSearchItems>
                ) {
                    if (response.isSuccessful){
                        Log.d("Test", "성공")
                    }
                }
                override fun onFailure(call: Call<ResultGetSearchItems>, t: Throwable) {
                    Log.d("Test", "실패")
                }
            })
    }


}

