package com.uwdp.whatyouwant.Search

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.`SearchView$InspectionCompanion`
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.uwdp.whatyouwant.databinding.ActivitySearchBinding

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val CLIENT_ID = "nEUQf0dPDqcvfRoVNlvp"
    private val CLIENT_SECRET = "D59hcg8Ko2"

    private var display : Int = 20
    private var start: Int = 1
    private var searchWord : String? = "사과"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(NaverAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = com.uwdp.whatyouwant.databinding.ActivitySearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkPermission()

        binding.searchViewItems.setOnQueryTextListener(searchViewTextListener)

    }


    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                itemSearchResult(query)
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        }

    private fun itemSearchResult(query : String?){
        val callGetSearchItems = api.getSearchItems(CLIENT_ID,CLIENT_SECRET,query,display,start)
        callGetSearchItems.enqueue(object : Callback<ResultGetSearchItems> {
                override fun onResponse(
                    call: Call<ResultGetSearchItems>,
                    response: Response<ResultGetSearchItems>
                ) {
                    if (response.isSuccessful){
                        val body = response.body()
                        body?.let{
                            SetAdapter(it.items)
                        }
                    }
                }
                override fun onFailure(call: Call<ResultGetSearchItems>, t: Throwable) {
                    Toast.makeText(this@SearchActivity,"검색에 실패 하셨습니다.",Toast.LENGTH_SHORT)
                }
            })
    }


    // 인터넷권한 체크 함수
    private fun checkPermission() {
        val Internetpermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)
        if (Internetpermission == PackageManager.PERMISSION_GRANTED) {
            Log.d("Test", "인터넷체크가 확인되었습니다.")
        } else {
            requestPermission()
        }
    }
    // 인터넷권한 요청 함수
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET), 99)
    }


    //리사이클러뷰 선언함수
    private fun SetAdapter(items : List<Items>){
        val myadapter = SearchAdapter(this, items)
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.searchRecyclerView.adapter = myadapter
        binding.searchRecyclerView.setHasFixedSize(true)
    }



}

