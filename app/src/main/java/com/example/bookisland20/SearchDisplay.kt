package com.example.bookisland20

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers


class SearchDisplay : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: SearchDisplayRecycleAdapter
    private var bookSearchList = mutableListOf<BookEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_display)
        val query = intent.getStringExtra("Query")
        searchBooks(query)
        recyclerView = findViewById(R.id.bookSearchOutput)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        bookAdapter = SearchDisplayRecycleAdapter(this, bookSearchList)
        recyclerView.adapter = bookAdapter
    }

    private fun searchBooks(query : String?)
    {
        val client = AsyncHttpClient()
        val url = "https://www.googleapis.com/books/v1/volumes?q=${query}"
        client[
                url,
                object : JsonHttpResponseHandler()
                //connect these callbacks to your API call
                {
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String,
                        throwable: Throwable?
                    ) {
                        throwable?.message?.let {
                            Log.e("SearchFragment", response)
                        }
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JsonHttpResponseHandler.JSON) {
                        val resultsJSON = json.jsonObject.getJSONArray("items")
                        var i = 0
                        while(i< resultsJSON.length()){
                            val book = resultsJSON.getJSONObject(i)
                            val volumeInfo = book.getJSONObject("volumeInfo")
                            var model = BookEntity(null,null,null,null,null,null)
                            model.title = volumeInfo.getString("title")
                            model.authors = volumeInfo.getString("authors")
                            model.thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail")
                            model.description = volumeInfo.getString("description")
                            model.saleability = book.getJSONObject("saleInfo").getString("saleability")
                            if(model.saleability != "NOT_FOR_SALE") {
                                model.price =
                                    book.getJSONObject("saleInfo").getJSONObject("retailPrice")
                                        .getDouble("amount")
                            }
                            bookSearchList.add(model)
                            i++
                        }
                        bookAdapter.notifyDataSetChanged()
                        Log.d("SearchDisplay", "response successful")
                    }
                }]
    }
}