package lt.neimantasjocius.combine.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import lt.neimantasjocius.combine.R

class FirstPhotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_photo)

        // Block actions
        val camera : ConstraintLayout = findViewById(R.id.camera)
        camera.setOnClickListener {

        }
        val gallery : ConstraintLayout = findViewById(R.id.gallery)
        gallery.setOnClickListener {

        }
        //X

        // Button actions
        val next : ImageButton = findViewById(R.id.next)
        next.setOnClickListener {
            val intent : Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val back : ImageButton = findViewById(R.id.back)
        back.setOnClickListener {
            val intent : Intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        }
        //X
    }
}