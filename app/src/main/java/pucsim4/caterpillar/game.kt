package pucsim4.caterpillar

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
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
    //var score:Int=binding.mysv.Score
    lateinit var builder:AlertDialog.Builder

    /*override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent);
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_game)
        binding=ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.start.isEnabled = true
        binding.img1.setOnTouchListener(this)
        builder=AlertDialog.Builder(this)


        binding.start.setOnClickListener(object:View.OnClickListener {
            override fun onClick(p0: View?){
                job= GlobalScope.launch(Dispatchers.Main) {
                    while(job.isActive) {
                        a=1
                        //conut++
                        binding.start.isEnabled = false
                        binding.start.visibility = INVISIBLE
                        delay(25)
                        binding.mysv.crawl.update()
                        val canvas: Canvas = binding.mysv.holder.lockCanvas()
                        binding.mysv.drawSomething(canvas)
                        binding.mysv.holder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        })
        binding.resume.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                job = GlobalScope.launch(Dispatchers.Main) {
                    while(job.isActive) {
                        a=1
                        binding.resume.visibility=INVISIBLE
                        delay(25)
                        val canvas: Canvas = binding.mysv.holder.lockCanvas()
                        binding.mysv.drawSomething(canvas)
                        binding.mysv.holder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        })
    }
    override fun onPause() {
        super.onPause()
        if(a==1) {
            a=0
            job.cancel()

        }else{
            Toast.makeText(this, "返回首頁", Toast.LENGTH_LONG).show();
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent);
        }
    }

    override fun onResume() {
        super.onResume()
        a=0
        if (binding.start.isEnabled == false){
            binding.resume.visibility=VISIBLE
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_MOVE){
            v?.x = event.rawX - v!!.width/2
            v?.y = event.rawY - v!!.height/2
            var r1: Rect = Rect(v.x.toInt(), v.y.toInt(),v.x.toInt() + v.width, v.y.toInt() + v.height)
            var r2: Rect = Rect(binding.mysv.enemy.BirdX, binding.mysv.enemy.BirdY, binding.mysv.enemy.BirdX +binding.mysv.enemy. w, binding.mysv.enemy.BirdY +binding.mysv.enemy. h)
            if(r1.intersect(r2)) {
                job.cancel()
                Toast.makeText(this, "寄生獸吃飽離開你了", Toast.LENGTH_LONG).show();
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent);
                return false
            }
        }
        return true

    }

}