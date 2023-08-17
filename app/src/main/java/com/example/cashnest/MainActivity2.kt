package com.example.cashnest

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.cashnest.MainActivity


class MainActivity2 : AppCompatActivity() {
    companion object {
        var one: Int = 0
        var two: Int = 0
        var three: Int = 0
        var four: Int = 0
        var five: Int = 0
        var six: Int = 0
        var seven: Int = 0
        var eight: Int = 0
        var nine: Int = 0
        var ten: Int = 0
        var eleven: Int = 0
        var twelve: Int = 0
        var thirteen: Int = 0
        var fourteen: Int = 0
        var fifteen: Int = 0
        var sixteen: Int = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val barchart1: BarChart = findViewById(R.id.chart1)
        val barchart2: BarChart = findViewById(R.id.chart2)
        val left_button: Button = findViewById(R.id.button)
        val baryellow = ContextCompat.getColor(this, R.color.BarYell)
        val bargray = ContextCompat.getColor(this, R.color.BarGrey)
        val colors = mutableListOf(baryellow, bargray, baryellow, bargray, baryellow, bargray, baryellow , bargray)

        left_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val entries = ArrayList<BarEntry>() // y좌표는 데이터 값으로 정해짐 (임의로 표시)
        entries.add(BarEntry(3.0f,one.toFloat()))
        entries.add(BarEntry(3.43f, two.toFloat()))
        entries.add(BarEntry(4.4f, three.toFloat()))
        entries.add(BarEntry(4.83f, four.toFloat()))
        entries.add(BarEntry(5.8f, five.toFloat()))
        entries.add(BarEntry(6.23f, six.toFloat()))
        entries.add(BarEntry(7.2f, seven.toFloat()))
        entries.add(BarEntry(7.63f, eight.toFloat()))

        val entries2 = ArrayList<BarEntry>() // y좌표는 데이터 값으로 정해짐 (임의로 표시)
        entries2.add(BarEntry(3.0f, nine.toFloat()))
        entries2.add(BarEntry(3.43f, ten.toFloat()))
        entries2.add(BarEntry(4.4f, eleven.toFloat()))
        entries2.add(BarEntry(4.83f, twelve.toFloat()))
        entries2.add(BarEntry(5.8f, thirteen.toFloat()))
        entries2.add(BarEntry(6.23f, fourteen.toFloat()))
        entries2.add(BarEntry(7.2f, fifteen.toFloat()))
        entries2.add(BarEntry(7.63f, sixteen.toFloat()))

        barchart1.run {
            description.isEnabled = false
            setMaxVisibleValueCount(8)
            setPinchZoom(false)
            setDrawBarShadow(false)
            setDrawGridBackground(false)
            axisLeft.run {
                axisMaximum = 101f
                axisMinimum = 0f
                granularity = 50f
                setDrawLabels(false)
                setDrawGridLines(false)
                setDrawAxisLine(false)

            }

            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawAxisLine(false)
                setDrawGridLines(false)
                setDrawLabels(false)
            }

            axisRight.isEnabled = false
            setTouchEnabled(false)
            legend.isEnabled = false;
            animateY(1000)

        }

        var set = BarDataSet(entries, "DataSet1")
        set.colors = colors
        val dataSet1: ArrayList<IBarDataSet> = ArrayList()
        dataSet1.add(set)
        val data = BarData(dataSet1)
        data.barWidth = 0.35f
        barchart1.run {
            this.data = data
            setFitBars(true)
            invalidate()
        }

        barchart2.run {
            description.isEnabled = false
            setMaxVisibleValueCount(8)
            setPinchZoom(false)
            setDrawBarShadow(false)
            setDrawGridBackground(false)

            axisLeft.run {
                axisMaximum = 101f
                axisMinimum = 0f
                granularity = 50f
                setDrawLabels(false)
                setDrawGridLines(false)
                setDrawAxisLine(false)
            }

            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawAxisLine(false)
                setDrawGridLines(false)
                setDrawLabels(false)

            }

            axisRight.isEnabled = false
            setTouchEnabled(false)
            legend.isEnabled = false;
            animateY(1000)

        }

        var set2 = BarDataSet(entries2, "DataSet2")
        set2.colors = colors
        val dataSet2: ArrayList<IBarDataSet> = ArrayList()
        dataSet2.add(set2)
        val data2 = BarData(dataSet2)
        data2.barWidth = 0.35f
        barchart2.run {
            this.data = data2
            setFitBars(true)
            invalidate()
        }

    }
}



