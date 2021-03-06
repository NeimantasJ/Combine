package lt.neimantasjocius.combine.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import lt.neimantasjocius.combine.R


class PermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        permissions()
    }

    private fun permissions() {
        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("Norint naudotis programėle privaloma suteikti\nkameros leidimus, kitaip programėlė neveiks")
            .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .check()
    }

    private var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            startActivity(Intent(this@PermissionActivity, LaunchActivity::class.java))
            finish()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            permissions()
        }
    }
}