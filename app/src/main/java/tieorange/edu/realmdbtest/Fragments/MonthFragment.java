package tieorange.edu.realmdbtest.Fragments;


import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import io.realm.Realm;
import tieorange.edu.realmdbtest.Activities.ChartsActivity;
import tieorange.edu.realmdbtest.DataHelpers.POJOHelper;
import tieorange.edu.realmdbtest.DataHelpers.RealmHelper;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;
import tieorange.edu.realmdbtest.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {
    private View mView;
    private LineChart mUiLineChart;
    private Realm mRealm;
    private ChartsActivity mActivity;

    public MonthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_month, container, false);
        mActivity = (ChartsActivity) getActivity();
        mRealm = mActivity.getRealm();
        // Inflate the layout for this fragment

        setupLineChart();
        return mView;
    }

    public static MonthFragment newInstance(String param1, String param2) {
        MonthFragment fragment = new MonthFragment();
        return fragment;
    }

    public void setupLineChart() {
        // create dataset:
        mUiLineChart = (LineChart) mView.findViewById(R.id.month_line_chart);

        ArrayList<Entry> entries = new ArrayList<>(); // pagesCount are here
        ArrayList<String> labels = new ArrayList<>(); // day

        // TODO: get data from realm, not dummy

        final Map<Date, List<ReadingEntry>> groupedReadingEntries = RealmHelper.getGroupedReadingEntriesMap(mRealm);
        int day = 0;
        int yesterdayPagesCount = 0; // pagesCount from previous day (n-1). Current day is "entry"
        for (Map.Entry<Date, List<ReadingEntry>> entry : groupedReadingEntries.entrySet()) {
            final List<ReadingEntry> dayReadingEntriesList = entry.getValue(); // all reading entries from 1 day
            final ReadingEntry lastReadingEntry = dayReadingEntriesList.get(dayReadingEntriesList.size() - 1); // last reading entry of the day (show only last chart)
            int currentPage = lastReadingEntry.getCurrentPage();

            entries.add(new Entry(currentPage - yesterdayPagesCount, day)); // set pagesCount,  show the delta of today and yesterday pages on chart (progress)
            yesterdayPagesCount = currentPage; // save yesterday pagesCount
            day++;
            labels.add(String.valueOf(day)); // set day
        }


        LineDataSet dataSet = new LineDataSet(entries, "Pages per day");
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        LineData lineData = new LineData(labels, dataSet);

        mUiLineChart.setData(lineData);
        mUiLineChart.setDescription("Per month");
        mUiLineChart.animateXY(2000, 2000);

        // set LIMIT LINE
        int goal = mActivity.getBookGoal().getGoal();
        LimitLine limitLine = new LimitLine(goal); // LIMIT LINE // TODO: set goal from realm
        limitLine.setLineColor(Color.GREEN);
        limitLine.setLabel("Goal");
        limitLine.setLineWidth(2f);
        final YAxis axisLeft = mUiLineChart.getAxisLeft();
        axisLeft.addLimitLine(limitLine);

        mUiLineChart.invalidate();
    }

    public void invalidateChart() {
        mUiLineChart.invalidate();

    }


}
