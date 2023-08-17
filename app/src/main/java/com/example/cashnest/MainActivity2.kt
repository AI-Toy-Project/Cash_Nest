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


class MainActivity2 : AppCompatActivity() {

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
        entries.add(BarEntry(3.0f, 20.0f))
        entries.add(BarEntry(3.43f, 70.0f))
        entries.add(BarEntry(4.4f, 30.0f))
        entries.add(BarEntry(4.83f, 90.0f))
        entries.add(BarEntry(5.8f, 70.0f))
        entries.add(BarEntry(6.23f, 30.0f))
        entries.add(BarEntry(7.2f, 90.0f))
        entries.add(BarEntry(7.63f, 40.0f))

        val entries2 = ArrayList<BarEntry>() // y좌표는 데이터 값으로 정해짐 (임의로 표시)
        entries2.add(BarEntry(3.0f, 20.0f))
        entries2.add(BarEntry(3.43f, 70.0f))
        entries2.add(BarEntry(4.4f, 30.0f))
        entries2.add(BarEntry(4.83f, 90.0f))
        entries2.add(BarEntry(5.8f, 70.0f))
        entries2.add(BarEntry(6.23f, 30.0f))
        entries2.add(BarEntry(7.2f, 90.0f))
        entries2.add(BarEntry(7.63f, 40.0f))

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
        val data2 = BarData(dataSet1)
        data2.barWidth = 0.35f
        barchart2.run {
            this.data = data2
            setFitBars(true)
            invalidate()
        }

    }
}



