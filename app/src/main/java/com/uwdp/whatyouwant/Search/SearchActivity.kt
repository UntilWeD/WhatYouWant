package com.uwdp.whatyouwant.Search

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.`SearchView$InspectionCompanion`
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.uwdp.whatyouwant.R
import com.uwdp.whatyouwant.databinding.ActivitySearchBinding

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val CLIENT_ID = "nEUQf0dPDqcvfRoVNlvp"
    private val CLIENT_SECRET = "D59hcg8Ko2"

    private var display : Int = 20
    private var start: Int = 1
    private var searchWord : String? = ""
    private var sort : String = "sim"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(NaverAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        var DisplaySpinner = binding.spinnerDisplay
        var SortSpinner = binding.spinnerSort


        checkPermission()

        binding.searchViewItems.setOnQueryTextListener(searchViewTextListener)

        DisplaySpinner.adapter= ArrayAdapter.createFromResource(this, R.array.spinner_display, android.R.layout.simple_spinner_item)
        SortSpinner.adapter = ArrayAdapter.createFromResource(this,R.array.spinner_sort, android.R.layout.simple_spinner_item)


        // 검색 디스플레이 스피너
        DisplaySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                when(position){
                    // 첫번째항목, 20개
                    0 -> {
                        display = 20
                        itemSearchResult(searchWord)
                        if (view != null){
                            Toast.makeText(this@SearchActivity,"20개로 설정되었습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
                    1 -> {
                        display = 50
                        itemSearchResult(searchWord)
                        if (view != null){
                            Toast.makeText(this@SearchActivity,"50개로 설정되었습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
                    2 -> {
                        display = 100
                        itemSearchResult(searchWord)
                        if (view != null){
                            Toast.makeText(this@SearchActivity,"100개로 설정되었습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }

        // 소트 디스플레이 스피너
        SortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                when(position){
                    // 유사도순
                    0 -> {
                        sort = "sim"
                        itemSearchResult(searchWord)
                        if (view != null){
                            Toast.makeText(this@SearchActivity,"20개로 설정되었습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
                    // 등록일순
                    1 -> {
                        sort = "date"
                        itemSearchResult(searchWord)
                        if (view != null){
                            Toast.makeText(this@SearchActivity,"50개로 설정되었습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
                    // 최저가순
                    2 -> {
                        sort = "asc"
                        itemSearchResult(searchWord)
                        if (view != null){
                            Toast.makeText(this@SearchActivity,"100개로 설정되었습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }
                    // 최고가순
                    3 -> {
                        sort = "dsc"
                        itemSearchResult(searchWord)
                        if (view != null){
                            Toast.makeText(this@SearchActivity,"100개로 설정되었습니다.",Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }

        setContentView(binding.root)
    }






    //검색버튼을 눌렀을때
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


    // 검색기능-레트로핏2
    private fun itemSearchResult(query : String?){
        val callGetSearchItems = api.getSearchItems(CLIENT_ID,CLIENT_SECRET,query,display,start,sort)
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

        myadapter.SetOnItemClickListener(object : SearchAdapter.OnItemClickListener{
            override fun onItemClick(v: View, item: Items, pos: Int) {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("${item.link}"))
                startActivity(intent)
            }
        })
    }



}

