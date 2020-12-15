package lt.neimantasjocius.combine.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import lt.neimantasjocius.combine.R
import lt.neimantasjocius.combine.adapters.ImageListAdapter
import lt.neimantasjocius.combine.sql.AppDatabase
import lt.neimantasjocius.combine.sql.Image

class ImageHistoryActivity : AppCompatActivity() {

    lateinit var listView: RecyclerView
    lateinit var back: ImageButton
    lateinit var adapter: ImageListAdapter

    var data = mutableListOf<Image>()
    lateinit var database: AppDatabase
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_history)

        database = AppDatabase.getInstance(this)!!

        back = findViewById(R.id.back)
        listView = findViewById(R.id.imagesRV)
        listView.layoutManager = LinearLayoutManager(this) //pakeist į grid layout manager

        val imageUri = intent.getStringExtra("uri")
        val image = Image(0, imageUri)

        disposable = database
            .getImageDao()
            .insert(image)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    this.data.add(
                        Image(
                            it.toInt(),
                            image.path
                        )
                    )
                    adapter.notifyItemChanged(this.data.size)
                    disposable = null
                },
                {
                    Toast.makeText(this, "Failed to add new image", Toast.LENGTH_SHORT).show()
                }
            )

        adapter = ImageListAdapter(data)
        listView.adapter = adapter

        back.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}