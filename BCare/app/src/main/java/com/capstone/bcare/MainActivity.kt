package com.capstone.bcare

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstone.bcare.databinding.ActivityMainBinding
import com.capstone.bcare.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener(View.OnClickListener {

            val t1: Float = binding.d1.text.toString().toFloat()
            val t2: Float = binding.d2.text.toString().toFloat()
            val t3: Float = binding.d3.text.toString().toFloat()
            val t4: Float = binding.d4.text.toString().toFloat()
            val t5: Float = binding.d5.text.toString().toFloat()


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

            binding.textView3.setText("Tinggi Sungai Maksimal Hari Ini Adalah " + outputFeature0[0].toString() + "  cm")

            model.close()
        })
    }
}