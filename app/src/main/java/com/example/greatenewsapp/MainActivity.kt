package com.example.greatenewsapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter : NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerViews: RecyclerView = findViewById(R.id.recyclerViews)

        recyclerViews.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter( this)
        recyclerViews.adapter = mAdapter

    }
private fun fetchData(){
    val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET,
        url,
        null,
        Response.Listener {
        val newsJsonArray = it.getJSONArray("articles")
            val newsArray = ArrayList<News>()
            for (i in 0 until newsJsonArray.length()){
                val newsJsonObject = newsJsonArray.getJSONObject(i)
                val news = News(
                    newsJsonObject.getString("title"),
                    newsJsonObject.getString("author"),
                    newsJsonObject.getString("url"),
                    newsJsonObject.getString("urlToImage")
                )
                newsArray.add(news)
            }
            mAdapter.updateNews(newsArray)
        },
        Response.ErrorListener {
            Toast.makeText(
                this,
                "error while parsing the jsonObject/array",
                Toast.LENGTH_LONG
            ).show()

        })


    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
}

    override fun onItemClickied(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }
}