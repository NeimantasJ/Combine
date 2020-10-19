package lt.neimantasjocius.combine.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import kotlinx.android.synthetic.main.activity_launch.*
import lt.neimantasjocius.combine.R

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        val gif : DrawableImageViewTarget = DrawableImageViewTarget(logo)
        Glide.with(this).load(R.raw.logo).into(gif)

        val timer = object: CountDownTimer(4200, 4200) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                val intent = Intent(applicationContext, TutorialActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        timer.start()
    }
}