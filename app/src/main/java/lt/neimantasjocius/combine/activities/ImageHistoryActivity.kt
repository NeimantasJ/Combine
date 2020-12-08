package lt.neimantasjocius.combine.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.Disposable
import lt.neimantasjocius.combine.R
import lt.neimantasjocius.combine.adapters.ImageListAdapter
import lt.neimantasjocius.combine.sql.AppDatabase
import lt.neimantasjocius.combine.sql.Image

class ImageHistoryActivity : AppCompatActivity() {

    lateinit var listView: RecyclerView
    lateinit var adapter: ImageListAdapter

    var data = mutableListOf<Image>()
    lateinit var database: AppDatabase
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_history)

        database = AppDatabase.getInstance(this)!!

        listView = findViewById(R.id.imagesRV)
        listView.layoutManager = LinearLayoutManager(this) //pakeist į grid layout manager

        adapter = ImageListAdapter(data)
        listView.adapter = adapter
    }
}