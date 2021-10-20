package miu.compro.cs743.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*


abstract class BaseActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) : AppCompatActivity() {
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }

    fun changeLanguage(code: String) {
        // return the resource's current configuration.
        val config = resources.configuration
        //Construct a locale from language and country.
        val locale = Locale(code)
        Locale.setDefault(locale)
        config.locale = locale
        /* Return the current display metrics that are in effect for this resource object
        * A structure describing general information about a display, such as its size, density, and font scaling.*/
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
    }
}