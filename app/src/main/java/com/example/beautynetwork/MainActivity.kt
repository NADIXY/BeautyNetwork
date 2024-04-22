package com.example.beautynetwork

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.beautynetwork.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Deklariert eine private lateinit Variable namens binding vom Typ ActivityMainBinding
    private lateinit var binding: ActivityMainBinding

    //Überschreibt die Methode onCreate des Elternobjekts.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(2000)
        installSplashScreen()

        //Erstellt ein Binding-Objekt für die Aktivität mithilfe des generierten Binding-Klasse ActivityMainBinding.
        binding = ActivityMainBinding.inflate(layoutInflater)
        //Legt die Ansicht der Aktivität auf die Wurzelansicht des Binding-Objekts fest.
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.profileFragment || destination.id == R.id.makeUpFragment || destination.id == R.id.schedulerFragment
            ) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.bottomNavigationView.setupWithNavController(navHost.navController)
                binding.bottomNavigationView.setOnItemSelectedListener {
                    NavigationUI.onNavDestinationSelected(it, navHost.navController)
                    navHost.navController.popBackStack(it.itemId, false)
                    true
                }
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.fragmentContainerView.findNavController().navigateUp()
            }
        })
    }

}