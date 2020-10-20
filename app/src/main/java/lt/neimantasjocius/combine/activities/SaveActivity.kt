package lt.neimantasjocius.combine.activities

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_save.*
import lt.neimantasjocius.combine.R

class SaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        val picture : ImageView = findViewById(R.id.picture)
        val back : ImageView = findViewById(R.id.back)
        val next : ImageView = findViewById(R.id.next)

        val filePath : String? = intent.getStringExtra("picture")

        setImageView(picture, filePath)

        next.setOnClickListener {
            /*val intent : Intent = Intent(this, SaveActivity::class.java)
            intent.putExtra("picture", getLastTakenPicture())
            startActivity(intent)*/
        }
        back.setOnClickListener {
            finish()
        }
    }

    private fun setImageView(imageView: ImageView, imagePath: String?) {
        Glide.with(baseContext)
            .asBitmap()
            .load(imagePath)
            .override(512, 512)
            .apply(RequestOptions().transform(MainActivity.CropTop()))
            .into(imageView)
    }
}