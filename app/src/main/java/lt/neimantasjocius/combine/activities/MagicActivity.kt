package lt.neimantasjocius.combine.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import lt.neimantasjocius.combine.R
import lt.neimantasjocius.combine.ai.*
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.concurrent.Executors

class MagicActivity :
    AppCompatActivity(),
    StyleFragment.OnListFragmentInteractionListener {

    private var isRunningModel = false
    private val stylesFragment: StyleFragment = StyleFragment()
    private var selectedStyle: String = ""

    private lateinit var viewModel: MLExecutionViewModel
    private lateinit var originalImageView: ImageView
    private lateinit var styleImageView: ConstraintLayout
    private lateinit var picture_frame: ConstraintLayout
    private lateinit var back : ImageButton
    private lateinit var next : ImageButton

    private var lastSavedFile = ""
    private var useGPU = false
    private lateinit var styleTransferModelExecutor: StyleTransferModelExecutor
    private val inferenceThread = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val mainScope = MainScope()
    private var finishedBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        originalImageView = findViewById(R.id.picture)
        styleImageView = findViewById(R.id.effect)
        picture_frame = findViewById(R.id.picture_frame)
        back = findViewById(R.id.back)
        next = findViewById(R.id.next)

        slideInRight(picture_frame)
        slideInLeft(styleImageView)

        lastSavedFile = intent.getStringExtra("picture")

        next.setOnClickListener {
            if(finishedBitmap != null) {
                val stream = ByteArrayOutputStream()
                finishedBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                val intent = Intent(this, SaveActivity::class.java)
                intent.putExtra("picture", byteArray)
                startActivity(intent)
            }
        }
        back.setOnClickListener {
            finish()
        }

        viewModel = AndroidViewModelFactory(application).create(MLExecutionViewModel::class.java)

        viewModel.styledBitmap.observe(
            this,
            Observer { resultImage ->
                if (resultImage != null) {
                    finishedBitmap = resultImage.styledImage
                    updateUIWithResults(resultImage)
                }
            }
        )

        mainScope.async(inferenceThread) {
            styleTransferModelExecutor = StyleTransferModelExecutor(this@MagicActivity, useGPU)
        }

        styleImageView.setOnClickListener {
            if (!isRunningModel) {
                stylesFragment.show(supportFragmentManager, "StylesFragment")
            }
        }

        setImageView(originalImageView, lastSavedFile)
    }

    private fun setImageView(imageView: ImageView, image: Bitmap) {
        Glide.with(baseContext)
            .load(image)
            .override(512, 512)
            .fitCenter()
            .into(imageView)
    }

    private fun setImageView(imageView: ImageView, imagePath: String) {
        Glide.with(baseContext)
            .asBitmap()
            .load(imagePath)
            .override(512, 512)
            .apply(RequestOptions().transform(CropTop()))
            .into(imageView)
    }

    private fun updateUIWithResults(modelExecutionResult: ModelExecutionResult) {
        setImageView(originalImageView, modelExecutionResult.styledImage)
    }

    override fun onListFragmentInteraction(item: String) {
        selectedStyle = item
        stylesFragment.dismiss()

        startRunningModel()
    }

    private fun getUriFromAssetThumb(thumb: String): String {
        return "file:///android_asset/thumbnails/$thumb"
    }

    private fun startRunningModel() {
        if (!isRunningModel && lastSavedFile.isNotEmpty() && selectedStyle.isNotEmpty()) {
            viewModel.onApplyStyle(
                baseContext, lastSavedFile, selectedStyle, styleTransferModelExecutor,
                inferenceThread
            )
        } else {
            Toast.makeText(this, "Previous Model still running", Toast.LENGTH_SHORT).show()
        }
    }

    class CropTop : BitmapTransformation() {
        override fun transform(
            pool: BitmapPool,
            toTransform: Bitmap,
            outWidth: Int,
            outHeight: Int
        ): Bitmap {
            return if (toTransform.width == outWidth && toTransform.height == outHeight) {
                toTransform
            } else ImageUtils.scaleBitmapAndKeepRatio(toTransform, outWidth, outHeight)
        }

        override fun equals(other: Any?): Boolean {
            return other is CropTop
        }

        override fun hashCode(): Int {
            return ID.hashCode()
        }

        override fun updateDiskCacheKey(messageDigest: MessageDigest) {
            messageDigest.update(ID_BYTES)
        }

        companion object {
            private const val ID = "org.tensorflow.lite.examples.styletransfer.CropTop"
            private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))
        }
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
