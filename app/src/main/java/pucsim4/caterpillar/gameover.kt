package pucsim4.caterpillar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pucsim4.caterpillar.databinding.ActivityGameoverBinding

class gameover : AppCompatActivity() {
    lateinit var binding: ActivityGameoverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameover)
        binding= ActivityGameoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val s=intent.getIntExtra("score",0)
        binding.score.setText(s.toString())

        binding.restart.setOnClickListener{
            val intent = Intent(this@gameover, game::class.java)
            startActivity(intent)
            finish()
        }
        binding.back.setOnClickListener{
            val intent = Intent(this@gameover, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
