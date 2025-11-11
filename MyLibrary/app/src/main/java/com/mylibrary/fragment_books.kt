package com.mylibrary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

import androidx.recyclerview.widget.RecyclerView


class fragment_books : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_books, container, false)

        recyclerView = view.findViewById(R.id.booksRecyclerView)
        loadBooks()

        return view
    }

    private fun loadBooks() {

        lifecycleScope.launch {
            val books = fetchBooksFromApi()
            setupRecyclerView(books)
        }
    }

    private suspend fun fetchBooksFromApi(): List<Book> = withContext(Dispatchers.IO) {
        val books = mutableListOf<Book>()

        try {

            val url = URL("https://www.googleapis.com/books/v1/volumes?q=programming&maxResults=10")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"


            val json = connection.inputStream.bufferedReader().use { it.readText() }


            val jsonObject = JSONObject(json)
            val items = jsonObject.getJSONArray("items")

            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                val volumeInfo = item.getJSONObject("volumeInfo")


                val title = volumeInfo.optString("title", "Без названия")
                val authorsArray = volumeInfo.optJSONArray("authors")
                val author = authorsArray?.getString(0) ?: "Неизвестный автор"
                val imageUrl = volumeInfo.optJSONObject("imageLinks")?.optString("thumbnail")


                books.add(Book(title, author, imageUrl))
            }
        } catch (e: Exception) {

            Toast.makeText(requireContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show()
        }

        return@withContext books
    }

    private fun setupRecyclerView(books: List<Book>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = BooksAdapter(books)
    }
}