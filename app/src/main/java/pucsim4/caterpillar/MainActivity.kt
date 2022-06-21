package pucsim4.caterpillar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pucsim4.caterpillar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username= intent.getStringExtra(login.EXTRA_NAME)
        binding.textDisplayName.text=username


        binding.gamestart.setOnClickListener{
            val intent = Intent(this,game::class.java)
            startActivity(intent);
        }
        binding.logout.setOnClickListener{
            Firebase.auth.signOut()

            val intent = Intent(applicationContext,login::class.java)
            startActivity(intent)
        }
        binding.about.setOnClickListener{
            val view=View.inflate(this@MainActivity,R.layout.dialog_view,null)
            val builder=AlertDialog.Builder(this@MainActivity)
            builder.setView(view)

            val dialog=builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        }
    }
}