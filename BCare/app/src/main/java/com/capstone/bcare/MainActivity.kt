package com.capstone.bcare

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.capstone.bcare.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer


class MainActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.button)

        btn.setOnClickListener(View.OnClickListener {

            val ed1: EditText = findViewById(R.id.d1)
            val ed2: EditText = findViewById(R.id.d2)
            val ed3: EditText = findViewById(R.id.d3)
            val ed4: EditText = findViewById(R.id.d4)
            val ed5: EditText = findViewById(R.id.d5)


            val t1: Float = ed1.text.toString().toFloat()
            val t2: Float = ed2.text.toString().toFloat()
            val t3: Float = ed3.text.toString().toFloat()
            val t4: Float = ed4.text.toString().toFloat()
            val t5: Float = ed5.text.toString().toFloat()


            val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(5 * 4)
            byteBuffer.putFloat(t1)
            byteBuffer.putFloat(t2)
            byteBuffer.putFloat(t3)
            byteBuffer.putFloat(t4)
            byteBuffer.putFloat(t5)


            val model = Model.newInstance(this)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 5), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            val tv: TextView = findViewById(R.id.textView3)

            tv.setText("Tinggi Sungai Maksimal Hari Ini Adalah " + outputFeature0[0].toString() + "  cm")

            model.close()
        })
    }
}