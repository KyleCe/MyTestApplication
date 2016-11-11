package com.ce.game.myapplication.view.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.ce.game.myapplication.util.DU

/**
 * Created on 2016/11/3
 * in BlaBla by Kyle
 */

class LayoutTest @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0)
: LinearLayout(context, attrs, defStyleAttr) {

    val TAG:String = javaClass.simpleName

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        DU.pwa(TAG,"on interceptTouchEvent")
        return false
    }

}
