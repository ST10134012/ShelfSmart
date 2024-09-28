package za.co.varsitycollege.st10134012.shelfsmart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.view.Gravity
import android.widget.ImageView
import androidx.core.view.GravityCompat


class SidebarActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_bar)

        drawerLayout = findViewById(R.id.drawer_layout)

        val sideBarIcon: ImageView = findViewById(R.id.side_bar_icon)
        sideBarIcon.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle home click
                }
                R.id.nav_shelf -> {
                    // Handle My Shelf click
                }
                R.id.nav_sharing -> {
                    // Handle Sharing click
                }
                R.id.nav_setting -> {
                    // Handle Setting click
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}
