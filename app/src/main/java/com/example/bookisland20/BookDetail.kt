package com.example.bookisland20

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class BookDetail: AppCompatActivity() {
    private lateinit var BookTitle: TextView
    private lateinit var BookAuthors: TextView
    private lateinit var BookImage: ImageView
    private lateinit var BookDescription: TextView
    private lateinit var BookPrice: TextView
    private lateinit var BookSaleability: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_detail)

        BookAuthors= findViewById(R.id.authors)
        BookPrice = findViewById(R.id.price)
        BookTitle = findViewById(R.id.BookTitle)
        BookImage = findViewById(R.id.BookImage)
        BookDescription = findViewById(R.id.Description)
        BookSaleability = findViewById(R.id.saleability)

        var bundle = intent.getExtras()
        val book = bundle?.getSerializable("value") as BookEntity
        //val book = intent.getSerializableExtra("Book Details") as BookEntity

        BookPrice.text = "Price: " + book.price.toString() + "USD"
        BookTitle.text = "Title: " + book.title
        BookDescription.text = book.description
        BookSaleability.text = "Availability: " + book.saleability
        BookAuthors.text = book.authors

        Glide.with(this)
            .load(book.thumbnail)
            .centerInside()
            .into(BookImage)
    }
}
