package lt.neimantasjocius.combine.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment

class TutorialActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(
            AppIntroFragment.newInstance(
            title = "Sukurk įdomių paveiksliukų!",
            description = "Ši programėlė sujungia dvejas nuotraukas į vieną ir leidžia sukurti įdomių efektų jūsų nuotraukoms..."
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "1. Pasirinkite",
            description = "Nufotografuokite arba pasirinkite jau esamą nuotrauką..."
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "2. Pritaikykite filtrą",
            description = "Pasirinkite sekantį paveiksliuką iš jau galimų"
        ))
        addSlide(AppIntroFragment.newInstance(
            title = "3. Išsaugokite",
            description = "Peržiūrėkite sugeneruotą rezultatą ir išsaugokite mobiliajame įrenginyje"
        ))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        val intent : Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}