package android.example.homework4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*

class AnalogClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var hour = 0f
    private var minutes = 0f
    private var seconds = 0f

    private var hourHandColor = 0
    private var minuteHandColor = 0
    private var secondHandColor = 0

    private var hourHandSize = 0f
    private var minuteHandSize = 0f
    private var secondHandSize = 0f

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnalogClockView)

        hourHandColor = typedArray.getColor(R.styleable.AnalogClockView_hour_hand_color, Color.BLACK)
        minuteHandColor = typedArray.getColor(R.styleable.AnalogClockView_minute_hand_color, Color.RED)
        secondHandColor = typedArray.getColor(R.styleable.AnalogClockView_second_hand_color, Color.BLUE)

        hourHandSize = typedArray.getDimension(R.styleable.AnalogClockView_hour_hand_size, 320f)
        minuteHandSize = typedArray.getDimension(R.styleable.AnalogClockView_minute_hand_size, 240f)
        secondHandSize = typedArray.getDimension(R.styleable.AnalogClockView_second_hand_size, 180f)

        typedArray.recycle()
    }

    private val circlePaint: Paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 15f
        isAntiAlias = true
        this.style = Paint.Style.STROKE
    }

    private val blackPaint: Paint = Paint().apply {
        color = hourHandColor
        strokeWidth = 20f
        isAntiAlias = true
        this.style = Paint.Style.STROKE
    }

    private val redPaint: Paint = Paint().apply {
        color = minuteHandColor
        strokeWidth = 15f
        isAntiAlias = true
        this.style = Paint.Style.STROKE
    }

    private val bluePaint: Paint = Paint().apply {
        color = secondHandColor
        strokeWidth = 10f
        isAntiAlias = true
        this.style = Paint.Style.STROKE
    }

    override fun invalidate() {
        super.invalidate()

        val calendar: Calendar = Calendar.getInstance()
        hour = calendar[Calendar.HOUR_OF_DAY].toFloat()
        minutes = calendar[Calendar.MINUTE].toFloat()
        seconds = calendar[Calendar.SECOND].toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate(width.toFloat() / 2, height.toFloat() / 2)
        canvas.drawCircle(0f, 0f, 330f, circlePaint)

        for (i in 1..12) {
            canvas.drawLine(0f, 330f, 0f, 290f, circlePaint)
            canvas.rotate(30f)
        }

        drawHand(canvas)
        postInvalidateDelayed(1000)
    }

    private fun drawHand(canvas: Canvas) {

        val secInMinuteDegree = (60f * 60f) / 360f
        val secInHourDegree = (12f * 60f * 60f) / 360f

        val minutesTimeInSec = minutes * 60f + seconds
        val hourTimeInSec = hour * 60f * 60f + minutesTimeInSec

        canvas.save()
        canvas.rotate(hourTimeInSec / secInHourDegree, 0f, 0f)
        canvas.drawLine(0f, hourHandSize * 0.33f, 0f, hourHandSize * -0.66f, blackPaint)
        canvas.restore()

        canvas.save()
        canvas.rotate(minutesTimeInSec / secInMinuteDegree, 0f, 0f)
        canvas.drawLine(0f, minuteHandSize * 0.33f, 0f, minuteHandSize * -0.66f, redPaint)
        canvas.restore()

        canvas.save()
        canvas.rotate(seconds * 6f, 0f, 0f)
        canvas.drawLine(0f, secondHandSize * 0.33f, 0f, secondHandSize * -0.66f, bluePaint)
        canvas.restore()
    }
}