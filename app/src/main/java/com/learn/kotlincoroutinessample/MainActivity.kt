package com.learn.kotlincoroutinessample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.learn.kotlincoroutinessample.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "Result_1"
    private val RESULT_2 = "Result_2"

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    fun clickMe(view: View) {
        CoroutineScope(IO).launch {
            fakeAPIRequest()
        }
    }

    private suspend fun fakeAPIRequest() {
        val result1 = getResult1FromApi()
        println("debug: $result1")

        val result2 = getResult2FromApi()
        println("debug: $result2")

        setTextOnMainThread(result2)
    }

    private fun setNewText(input: String) {
        val newText = binding.tvDesc.text.toString() + "\n$input"
        binding.tvDesc.text = newText
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main)
        {
            setNewText(input)
        }
    }

    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
        delay(1000)
        return RESULT_1
    }

    private suspend fun getResult2FromApi(): String {
        logThread("getResult1FromApi")
        delay(1000)
        return RESULT_2
    }

    private fun logThread(methodName: String) {
        println("debug:${methodName} : ${Thread.currentThread().name}")
    }
}