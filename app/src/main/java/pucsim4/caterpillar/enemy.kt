package pucsim4.caterpillar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect

class enemy(context: Context) {
    val res = context.resources  //讀取資源
    var BirdX:Int = res.displayMetrics.widthPixels  //讀取螢幕寬度

    var w:Int
    var h:Int
    var BirdY:Int
    var image: Bitmap
    var SrcRect: Rect
    var speed:Int=20
    lateinit var DestRect: Rect
    var pos:Int=0

    init {
        image = BitmapFactory.decodeResource(res, R.drawable.enemy)
        w = image.width/2
        h = image.height/2
        BirdX -= w  //螢幕左邊飛出
        BirdY=(1..res.displayMetrics.heightPixels-h).random()
        SrcRect = Rect(0, 0, image.width, image.height) //裁切
    }

    fun draw(canvas: Canvas) {
        update()
        DestRect = Rect(BirdX, BirdY, BirdX + w, BirdY + h)
        canvas.drawBitmap(image, SrcRect, DestRect, null)
    }
    fun update(){
        pos=0
        /*count++
        if (count>4){
            count = 1
        }*/
        BirdX -= speed
        if (BirdX<=0){
            pos=1
            BirdX = res.displayMetrics.widthPixels - w
            BirdY=(1..res.displayMetrics.heightPixels-h).random()
        }
    }
}
