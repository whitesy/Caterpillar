package pucsim4.caterpillar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.icu.text.Transliterator

class crawl(context: Context) {
    val res = context.resources  //讀取資源
    var x:Int = 0
    var y:Int = res.displayMetrics.heightPixels/2
    var w:Int
    var h:Int
    var image: Bitmap
    var SrcRect: Rect
    lateinit var DestRect: Rect
    var count: Int = 1
    var shoot: Int = 0
    lateinit var pos: Bitmap


    init {
        image = BitmapFactory.decodeResource(res, R.drawable.c1)
        SrcRect = Rect(0, 0, image.width, image.height) //裁切
        w = image.width/10
        h = image.height/10
        y -= h/2  //螢幕高度-圖片/8=範圍
    }
    fun draw(canvas: Canvas) {
        DestRect = Rect(x, y, w, y + h)
        canvas.drawBitmap(image, SrcRect, DestRect, null)
    }
    fun update(){
        if(shoot==0){
            when(count){
                1 -> image = BitmapFactory.decodeResource(res,R.drawable.c1)
                2 -> image = BitmapFactory.decodeResource(res,R.drawable.c2)
            }
            count++
            if(count>2){
                count = 0
            }
        }
        else{
            when (shoot) {
                1 -> image = BitmapFactory.decodeResource(res, R.drawable.icecream)
                2 -> image = BitmapFactory.decodeResource(res, R.drawable.icecream)
                3 -> image = BitmapFactory.decodeResource(res, R.drawable.icecream)
                4 -> image = BitmapFactory.decodeResource(res, R.drawable.icecream)
                5 -> image = BitmapFactory.decodeResource(res, R.drawable.icecream)
            }
            shoot++
            if (shoot>5){
                shoot=0
            }
        }
    }
}