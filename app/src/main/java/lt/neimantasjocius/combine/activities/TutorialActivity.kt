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
            title = "Trumpas apmokymas...",
            description = "Ši programėlė sujungia dvejas nuotraukas į vieną ir leidžia sukurti įdomių efektų jūsų nuotraukoms...",
            imageDrawable = R.drawable.effect,
            titleColor = Color.BLACK,
            descriptionColor = Color.BLACK,
            backgroundColor = Color.WHITE,
            titleTypefaceFontRes = R.font.roboto_medium,
            descriptionTypefaceFontRes = R.font.roboto_medium
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "1. Pasirinkite",
            description = "Nufotografuokite arba pasirinkite jau esamą nuotrauką...",
            imageDrawable = R.drawable.picture,
            titleColor = Color.BLACK,
            descriptionColor = Color.BLACK,
            backgroundColor = Color.WHITE,
            titleTypefaceFontRes = R.font.roboto_medium,
            descriptionTypefaceFontRes = R.font.roboto_medium
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "2. Pritaikykite filtrą",
            description = "Pasirinkite filtrą - paveiksliuką iš jau galimų,  pagal kurį ir bus pertvarkyta pirmoji nuotrauka",
            imageDrawable = R.drawable.tap,
            titleColor = Color.BLACK,
            descriptionColor = Color.BLACK,
            backgroundColor = Color.WHITE,
            titleTypefaceFontRes = R.font.roboto_medium,
            descriptionTypefaceFontRes = R.font.roboto_medium
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "3. Išsaugokite",
            description = "Peržiūrėkite sugeneruotą rezultatą ir išsaugokite mobiliajame įrenginyje",
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