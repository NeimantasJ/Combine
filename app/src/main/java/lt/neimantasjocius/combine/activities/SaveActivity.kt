package lt.neimantasjocius.combine.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import lt.neimantasjocius.combine.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class SaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        val save : ConstraintLayout = findViewById(R.id.save)
        val back : ImageView = findViewById(R.id.back)
        val next : ImageView = findViewById(R.id.next)

        fade(save)

        val filePath : String? = intent.getStringExtra("picture")

        save.setOnClickListener {
            /*val bMap = BitmapFactory.decodeFile(filePath)
            saveBitmap(bMap)*/
        }

        next.setOnClickListener {
            val intent = Intent(this, FirstPhotoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        back.setOnClickListener {
            finish()
        }
    }

    private fun saveBitmap(bitmap : Bitmap) {
        // Assume block needs to be inside a Try/Catch block.

        // Assume block needs to be inside a Try/Catch block.
        val path: String = Environment.getExternalStorageDirectory().toString()
        var fOut: OutputStream? = null
        val counter = 0
        val file = File(
            path,
            "Combine-$counter.jpg"
        ) // the File to save , append increasing numeric counter to prevent files from getting overwritten.

        fOut = FileOutputStream(file)

        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            85,
            fOut
        )

        fOut.flush() // Not really required

        fOut.close() // do not forget to close the stream


        MediaStore.Images.Media.insertImage(
            contentResolver,
            file.getAbsolutePath(),
            file.getName(),
            file.getName()
        )
    }

    /*private fun saveImageToGallery() {
        imageview.setDrawingCacheEnabled(true)
        val b: Bitmap = imageview.getDrawingCache()
        Images.Media.insertImage(getActivity().getContentResolver(), b, title, description)
    }*/

    private fun fade(layout : ConstraintLayout) {
        val animation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_in
        )
        layout.startAnimation(animation)
    }
}