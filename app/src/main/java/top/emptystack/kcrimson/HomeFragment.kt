package top.emptystack.kcrimson

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entries: ArrayList<PieEntry> = arrayListOf()

        val now = Calendar.getInstance()
        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 0, 0, 0)
        val currentTime = now.timeInMillis
        Log.d("home", "currentTime is $currentTime")

        val events = getStoredEvents(context)

        if (events.isEmpty()) {
            tv_nodata.visibility = View.VISIBLE
            pieChart.visibility = View.INVISIBLE
        } else {
            tv_nodata.visibility = View.INVISIBLE
            pieChart.visibility = View.VISIBLE
            for (event in events) {
                var value = event.ddl.toFloat();
                if (value==0f){
                    value = 0.5f;
                }
                Log.d("chart", "value:${1 / value}, label:${event.name}")
                print(value);
                entries.add(PieEntry(1 / value, event.name))
            }

            val closest = entries.first().label
            val set = PieDataSet(entries, "任务名")
            set.colors = ColorTemplate.COLORFUL_COLORS.toList()
            set.setDrawValues(true)
            val data = PieData(set)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(25f)
            data.setValueTextColor(Color.WHITE)
            pieChart.setHoleColor(Color.parseColor("#fff8e1"));
            pieChart.data = data
            pieChart.setEntryLabelTextSize(25f);
            pieChart.invalidate()
            pieChart.setCenterTextColor(Color.RED);
            pieChart.centerText = "该去完成${closest}了！！！"
            pieChart.setCenterTextSize(25f)
            pieChart.setUsePercentValues(true)
            pieChart.getDescription().setEnabled(false);

            val lengend = pieChart.legend
            lengend.textSize = 20f;
            lengend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        }
    }
}
