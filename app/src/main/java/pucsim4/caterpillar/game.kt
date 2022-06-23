package pucsim4.caterpillar

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.*
import pucsim4.caterpillar.databinding.ActivityGameBinding

class game : AppCompatActivity() ,View.OnTouchListener{
    lateinit var binding: ActivityGameBinding
    lateinit var job: Job
    lateinit var mysv : MySurfaceView
    var a:Int=0//控制返回鍵 0回首頁
    var score:Int=0
    lateinit var builder:AlertDialog.Builder
    lateinit var mper: MediaPlayer

    //var username: String?=intent.getStringExtra("username")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_game)
        binding=ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.start.isEnabled = true
        builder=AlertDialog.Builder(this)

        //播放遊戲背景音樂
        mper = MediaPlayer()
        mper = MediaPlayer.create(this, R.raw.bgmusic)
        mper.start()
        mper.setLooping(true)

        binding.start.setOnClickListener(object:View.OnClickListener {
            override fun onClick(p0: View?){
                job= GlobalScope.launch(Dispatchers.Main) {
                    while(job.isActive) {
                        a=1
                        if(binding.mysv.enemy1.pos==1 || binding.mysv.enemy2.pos==1||binding.mysv.enemy3.pos==1){
                            score++
                            binding.Score.setText(score.toString())
                        }
                        binding.start.isEnabled = false
                        binding.start.visibility = INVISIBLE
                        delay(25)
                        binding.mysv.crawl.update()
                        val canvas: Canvas = binding.mysv.holder.lockCanvas()
                        binding.mysv.drawSomething(canvas)
                        binding.mysv.holder.unlockCanvasAndPost(canvas)
                        if (Rect.intersects(binding.mysv.crawl.DestRect,binding.mysv.enemy1.DestRect)||Rect.intersects(binding.mysv.crawl.DestRect,binding.mysv.enemy2.DestRect)||Rect.intersects(binding.mysv.crawl.DestRect,binding.mysv.enemy3.DestRect)){
                            //播放死亡音樂
                            mper.reset()
                            mper = MediaPlayer.create(this@game, R.raw.dead)
                            mper.start()
                            delay(1000)
                            job.cancel()
                            val intent = Intent(this@game, gameover::class.java)
                            intent.putExtra("score",score)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        })

        binding.resume.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                mper = MediaPlayer.create(this@game, R.raw.bgmusic)
                mper.start()
                mper.setLooping(true)
                job = GlobalScope.launch(Dispatchers.Main) {
                    while(job.isActive) {
                        a=1
                        if(binding.mysv.enemy1.pos==1 || binding.mysv.enemy2.pos==1||binding.mysv.enemy3.pos==1){
                            score++
                            binding.Score.setText(score.toString())
                        }
                        binding.resume.visibility=INVISIBLE
                        delay(25)
                        val canvas: Canvas = binding.mysv.holder.lockCanvas()
                        binding.mysv.drawSomething(canvas)
                        binding.mysv.holder.unlockCanvasAndPost(canvas)
                        if (Rect.intersects(binding.mysv.crawl.DestRect,binding.mysv.enemy1.DestRect)||Rect.intersects(binding.mysv.crawl.DestRect,binding.mysv.enemy2.DestRect)||Rect.intersects(binding.mysv.crawl.DestRect,binding.mysv.enemy3.DestRect)){
                            mper.reset()
                            mper = MediaPlayer.create(this@game, R.raw.dead)
                            mper.start()
                            delay(1000)
                            job.cancel()
                            val intent = Intent(this@game, gameover::class.java)
                            intent.putExtra("score",score)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        })
        //音樂控制鈕
        binding.music.setOnClickListener{
            if(mper != null && mper.isPlaying()){
                mper.stop()
                binding.music.setImageResource(R.drawable.ic_baseline_music_note_24)
            }else{
                mper.reset()
                mper = MediaPlayer.create(this, R.raw.bgmusic)
                mper.start()
                mper.setLooping(true)
                binding.music.setImageResource(R.drawable.ic_baseline_music_off_24)
            }
        }
    }
    override fun onPause() {
        super.onPause()
        if(a==1) {
            a=0
            job.cancel()
        }else{
            Toast.makeText(this, "返回首頁", Toast.LENGTH_LONG).show();
            val intent = Intent(this,MainActivity::class.java)
            //intent.putExtra("username",username)
            startActivity(intent)
            //finish()
        }
        if(mper != null && mper.isPlaying()){
            mper.pause()
        }else{
            mper.reset()
        }
    }

    override fun onResume() {
        super.onResume()
        a=0
        if (binding.start.isEnabled == false){
            binding.resume.visibility=VISIBLE
        }else if(mper != null){
            mper.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(mper != null) {
            mper.release()
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true
    }
}
