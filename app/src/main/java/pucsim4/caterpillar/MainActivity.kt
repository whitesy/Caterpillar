package pucsim4.caterpillar

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pucsim4.caterpillar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var username: String? = null
    lateinit var mper: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username= intent.getStringExtra(login.EXTRA_NAME)
        binding.textDisplayName.text=username

        //播放首頁背景音樂
        mper = MediaPlayer()
        mper = MediaPlayer.create(this, R.raw.bgmusic)
        mper.start()
        mper.setLooping(true)


        binding.gamestart.setOnClickListener{
            val intent = Intent(this,game::class.java)
            startActivity(intent);
        }
        binding.logout.setOnClickListener{
            Firebase.auth.signOut()

            val intent = Intent(applicationContext,login::class.java)
            startActivity(intent)
            finish()
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
    override fun onDestroy() {
        super.onDestroy()
        if(mper != null) {
            mper.release()
        }
    }
    override fun onPause() {
        super.onPause()
        if(mper != null && mper.isPlaying()){
            mper.pause()
        }
        else{
            mper.reset()
        }
    }

    override fun onResume() {
        super.onResume()
        if(mper != null){
            mper.start()
        }
    }
}
