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

        disposable = database.getImageDao()
            .getAllImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    data.clear()
                    if(!it.isNullOrEmpty())
                    {
                        data.addAll(it)
                    }
                    adapter.notifyDataSetChanged()
                    disposable = null
                },
                {
                    Toast.makeText(this, "Error retrieving customers!", Toast.LENGTH_LONG).show()
                }
            )

        // Button actions
        val back: ImageButton = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, SaveActivity::class.java)
            startActivity(intent)
            finish()
        }
        //X
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 0){

        } else {

        }



    }
}