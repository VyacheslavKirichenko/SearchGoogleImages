package com.example.searchgoogleimages

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.koushikdutta.ion.Ion
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var key = "AIzaSyAexd7qwxuPXfi-1Jumvw7dcl2B5Z1lmM8" // API key
    private var cx = "010314913896689148381:nmmerom-oxi" // search engine key
    private var searchQuery = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null){
        }
    }

    fun pictureFindClickButton(view: View) {

        var search_field = findViewById<EditText>(R.id.input_field)
        image_layout.removeAllViews()
        searchQuery = search_field.text.toString().replace(" ", "+")
        val urlString =
            "https://www.googleapis.com/customsearch/v1?searchType=image&q=$searchQuery&key=$key&cx=$cx&alt=json"

        Ion.with(this)
            .load(urlString)
            .asString()
            .setCallback { _, result ->

                val items = JSONObject(result)
                    .optJSONArray("items")
                if (items != null) {
                    for (i in 0..(items.length() - 1)) {
                        val item = items.getJSONObject(i).getString("link")
                        Log.i("Link", item)
                        loadImagetoLayout(item)
                    }
                }
            }
    }

    fun loadImagetoLayout(imageURL: String) {

        var image = ImageView(baseContext)
        image.scaleType = ImageView.ScaleType.CENTER
        var width = getApplicationContext().getResources().getDisplayMetrics().widthPixels

        Picasso.get()
            .load(imageURL)
            .resize(width / 1, width / 1)
            .centerCrop()
            .into(image)
        image_layout.addView(image)
    }

}





