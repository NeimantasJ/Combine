package lt.neimantasjocius.combine.activities

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import lt.neimantasjocius.combine.R
import lt.neimantasjocius.combine.adapters.ImageListAdapter
import lt.neimantasjocius.combine.sql.AppDatabase
import lt.neimantasjocius.combine.sql.Image

class ImageHistoryActivity : AppCompatActivity() {

    private lateinit var listView: RecyclerView
    private lateinit var adapter: ImageListAdapter

    private var data = mutableListOf<Image>()
    private lateinit var database: AppDatabase
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_history)

        database = AppDatabase.getInstance(this)!!

        listView = findViewById(R.id.imagesRV)
        listView.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false) //pakeist į grid layout manager
        adapter = ImageListAdapter(data)
        listView.adapter = adapter
        loadAllImages()

        val imageUri = intent.getStringExtra("uri")

        if (imageUri != null) {
            val image = Image(0, imageUri)
            saveToDB(image)
        }

        // Button actions
        val back: ImageButton = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
        //X
    }

    private fun saveToDB(image: Image) {
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
    }

    private fun loadAllImages() {
        disposable = database
            .getImageDao()
            .getAllImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    data.clear()
                    if (!it.isNullOrEmpty()) {
                        data.addAll(it)
                    }
                    adapter.notifyDataSetChanged()
                    disposable = null
                },
                {
                    Toast.makeText(this, "Failed to add new image", Toast.LENGTH_SHORT).show()
                }
            )
    }
}