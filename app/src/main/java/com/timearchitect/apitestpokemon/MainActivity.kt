package com.timearchitect.apitestpokemon

import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import coil3.load
import coil3.request.error //kotlin har en egen version av metoden!!!
import coil3.request.CachePolicy
import coil3.request.fallback
import coil3.request.placeholder
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

/*import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.fallback
import coil3.request.placeholder
import coil3.request.target*/


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var tv=findViewById<TextView>(R.id.textView2)
        var imv=findViewById<ImageView>(R.id.imageView)
        var rq = newRequestQueue(this)
        val url="https://pokeapi.co/api/v2/pokemon/ditto"
        var request = StringRequest(
            Request.Method.GET,
                url,
                {res ->
                    val json = JSONObject(res)

                    var shinyUrl =
                        json.getJSONObject("sprites")
                            .getJSONObject("other")
                            .getJSONObject("home")
                            .getString("front_shiny")
                    Snackbar.make(this, findViewById(R.id.main), "Snackbar",Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(getColor(R.color.red))
                        .setAction("click here", { Toast.makeText(this,"Toastception",Toast.LENGTH_LONG).show()  })
                        .show()

                    imv.load(shinyUrl)
                    var namn =json.getString("name")
                    tv.setText( namn )
                    Log.i("ALRIK", "onCreate: "+json)

                }, {res ->
                    imv.load(R.drawable.error)
                }
            )

        rq.add(request)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        var navController = navHostFragment.navController


        findViewById<Button>(R.id.button).setOnClickListener {
            navController.navigate(R.id.action_blankFragment_to_blankFragment2)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            navController.navigate(R.id.action_blankFragment2_to_blankFragment3)
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            navController.navigate(R.id.action_blankFragment2_to_blankFragment4)
        }


        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/media/master/logo/pokeapi_256.png"
       /* imv.load(imageUrl){
            placeholder(R.drawable.loading)
            error(R.drawable.error)
            fallback(R.drawable.not_found)
            memoryCachePolicy(CachePolicy.DISABLED) // behövs för att deativera cache
            diskCachePolicy(CachePolicy.DISABLED) // behövs för att deativera cache
        }*/








        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}