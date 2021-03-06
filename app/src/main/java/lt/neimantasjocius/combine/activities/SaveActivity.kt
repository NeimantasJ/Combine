package lt.neimantasjocius.combine.activities

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
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
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class SaveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        val save : ConstraintLayout = findViewById(R.id.save)
        val list : ConstraintLayout = findViewById(R.id.list)
        val back : ImageView = findViewById(R.id.back)
        val next : ImageView = findViewById(R.id.next)

        fade(save)

        val byteArray = intent.getByteArrayExtra("picture")
        val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)


        save.setOnClickListener {
            val imageUri = saveImage(bmp, this, "Combine")
            val intent = Intent(this, ImageHistoryActivity::class.java)
            val imageFile = File(getRealPathFromURI(imageUri!!))
            val imagePath = imageFile.absolutePath
            intent.putExtra("uri", imagePath)
            startActivity(intent)
            //finish()
        }

        list.setOnClickListener {
            val intent = Intent(this, ImageHistoryActivity::class.java)
            startActivity(intent)
            //finish()
        }

        next.setOnClickListener {
            val intent = Intent(this, FirstPhotoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            //finish()
        }
        back.setOnClickListener {
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

    private fun saveImage(bitmap: Bitmap, context: Context, folderName: String): Uri? {
        val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date())
        var imageUri: Uri? = null

        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName)
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_$timestamp")

            val uri: Uri? = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
            if (uri != null) {
                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
                imageUri = uri
            }
        } else {
            val directory = File(
                Environment.getExternalStorageDirectory().toString() + separator + folderName
            )

            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName = "IMG_$timestamp.png"
            val file = File(directory, fileName)
            saveImageToStream(bitmap, FileOutputStream(file))
            val values = contentValues()
            values.put(MediaStore.Images.Media.DATA, file.absolutePath)
            // .DATA is deprecated in API 29
            imageUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }
        return imageUri
    }

    private fun contentValues() : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun fade(layout: ConstraintLayout) {
        val animation: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.fade_in
        )
        layout.startAnimation(animation)
    }
}