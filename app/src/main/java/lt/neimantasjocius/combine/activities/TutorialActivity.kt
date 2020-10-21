package lt.neimantasjocius.combine.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType
import lt.neimantasjocius.combine.R

class TutorialActivity : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Toggle Indicator Visibility
        isIndicatorEnabled = true

        setIndicatorColor(
            selectedIndicatorColor = getColor(R.color.black),
            unselectedIndicatorColor = getColor(R.color.lightGray)
        )
        setProgressIndicator()


        setTransformer(AppIntroPageTransformerType.Parallax(
            titleParallaxFactor = 1.0,
            imageParallaxFactor = -1.0,
            descriptionParallaxFactor = 2.0
        ))
        isColorTransitionsEnabled = true
        addSlide(
            AppIntroFragment.newInstance(
            title = resources.getString(R.string.tutorial1_title),
            description = resources.getString(R.string.tutorial1_description),
            imageDrawable = R.drawable.effect,
            titleColor = Color.BLACK,
            descriptionColor = Color.BLACK,
            backgroundColor = Color.WHITE,
            titleTypefaceFontRes = R.font.roboto_medium,
            descriptionTypefaceFontRes = R.font.roboto_medium
        ))
        addSlide(AppIntroFragment.newInstance(
            title = resources.getString(R.string.tutorial2_title),
            description = resources.getString(R.string.tutorial2_description),
            imageDrawable = R.drawable.picture,
            titleColor = Color.BLACK,
            descriptionColor = Color.BLACK,
            backgroundColor = Color.WHITE,
            titleTypefaceFontRes = R.font.roboto_medium,
            descriptionTypefaceFontRes = R.font.roboto_medium
        ))
        addSlide(AppIntroFragment.newInstance(
            title = resources.getString(R.string.tutorial3_title),
            description = resources.getString(R.string.tutorial3_description),
            imageDrawable = R.drawable.tap,
            titleColor = Color.BLACK,
            descriptionColor = Color.BLACK,
            backgroundColor = Color.WHITE,
            titleTypefaceFontRes = R.font.roboto_medium,
            descriptionTypefaceFontRes = R.font.roboto_medium
        ))
        addSlide(AppIntroFragment.newInstance(
            title = resources.getString(R.string.tutorial4_title),
            description = resources.getString(R.string.tutorial4_description),
            imageDrawable = R.drawable.floppy,
            titleColor = Color.BLACK,
            descriptionColor = Color.BLACK,
            backgroundColor = Color.WHITE,
            titleTypefaceFontRes = R.font.roboto_medium,
            descriptionTypefaceFontRes = R.font.roboto_medium
        ))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        checkIfFirstTime()
        val intent : Intent = Intent(this, FirstPhotoActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        checkIfFirstTime()
        val intent : Intent = Intent(this, FirstPhotoActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkIfFirstTime() {
        val sharedPreferences : SharedPreferences = this.getSharedPreferences("APP", Context.MODE_PRIVATE)
        val firstTime = sharedPreferences.getBoolean("firstTime", true)
        if(firstTime) {
            with(sharedPreferences.edit()) {
                putBoolean("firstTime", false)
                apply()
            }
        }
    }
}