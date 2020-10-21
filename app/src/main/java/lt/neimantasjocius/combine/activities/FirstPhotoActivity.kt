package lt.neimantasjocius.combine.activities

import android.content.Intent
import android.hardware.camera2.CameraCharacteristics
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import lt.neimantasjocius.combine.R
import lt.neimantasjocius.combine.camera.CameraFragment
import java.io.File


class FirstPhotoActivity : AppCompatActivity(), CameraFragment.OnCaptureFinished {

    private lateinit var frame: FrameLayout
    private lateinit var cameraFragment: CameraFragment
    private var lensFacing = CameraCharacteristics.LENS_FACING_BACK
    private var clickedOnce: Boolean = false
    private var filePath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_photo)

        frame = findViewById(R.id.view_finder)
        val camera_png: ImageView = findViewById(R.id.camera_png)
        val camera_text: TextView = findViewById(R.id.camera_text)

        // Block actions
        val camera: ConstraintLayout = findViewById(R.id.camera)
        camera.setOnClickListener {
            if (clickedOnce) {
                it.clearAnimation()
                cameraFragment.takePicture()
            } else {
                camera_png.visibility = View.GONE
                camera_text.visibility = View.GONE
                frame.visibility = View.VISIBLE

                addCameraFragment()
                clickedOnce = true
            }
        }
        val gallery: ConstraintLayout = findViewById(R.id.gallery)
        gallery.setOnClickListener {

        }
        //X

        slideInRight(camera)
        slideInLeft(gallery)

        // Button actions
        val next: ImageButton = findViewById(R.id.next)
        next.setOnClickListener {
            if (!filePath.equals("")) {
                val intent = Intent(this, MagicActivity::class.java)
                startActivity(intent)
            }
        }
        val back: ImageButton = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
            finish()
        }
        //X
    }

    private fun addCameraFragment() {
        cameraFragment = CameraFragment.newInstance()
        cameraFragment.setFacingCamera(lensFacing)
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.view_finder, cameraFragment)
            .commit()
    }

    override fun onCaptureFinished(file: File) {
        filePath = file.absolutePath
        val intent = Intent(this, MagicActivity::class.java)
        intent.putExtra("picture", filePath)
        startActivity(intent)
    }

    private fun slideInRight(layout : ConstraintLayout) {
        val animation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_in_right
        )
        layout.startAnimation(animation)
    }

    private fun slideInLeft(layout : ConstraintLayout) {
        val animation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_in_left
        )
        layout.startAnimation(animation)
    }
}
