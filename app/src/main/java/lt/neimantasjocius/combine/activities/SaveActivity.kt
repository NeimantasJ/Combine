package lt.neimantasjocius.combine.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import lt.neimantasjocius.combine.R

class SaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        val back : ImageView = findViewById(R.id.back)
        val next : ImageView = findViewById(R.id.next)

        val filePath : String? = intent.getStringExtra("picture")

        next.setOnClickListener {
            val intent = Intent(this, FirstPhotoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        back.setOnClickListener {
            finish()
        }
    }
}