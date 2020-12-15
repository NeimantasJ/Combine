package lt.neimantasjocius.combine.activities

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.hardware.camera2.CameraCharacteristics
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.about_image.*
import lt.neimantasjocius.combine.R
import lt.neimantasjocius.combine.camera.CameraFragment
import java.io.File


class FirstPhotoActivity : AppCompatActivity(), CameraFragment.OnCaptureFinished {

    val REQUEST_CODE = 100

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
            if (clickedOnce) {
                cameraFragment.cameraRelease()
            }
            openGalleryForImage()
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
                finish()
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

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            val fileUri = data?.data
            val imageFile = File(getRealPathFromURI(fileUri!!));
            filePath = imageFile.absolutePath
            val intent = Intent(this, MagicActivity::class.java)
            intent.putExtra("picture", filePath)
            startActivity(intent)
            finish()
        }
    }

    private fun getRealPathFromURI(contentURI: Uri): String {
        val result: String
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            result = contentURI.path.toString()
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
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
        finish()
    }

    private fun slideInRight(layout: ConstraintLayout) {
        val animation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_in_right
        )
        layout.startAnimation(animation)
    }

    private fun slideInLeft(layout: ConstraintLayout) {
        val animation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_in_left
        )
        layout.startAnimation(animation)
    }
}
