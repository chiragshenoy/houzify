package com.visualizedsort.chiragshenoy.houzify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by Chirag Shenoy on 22-Dec-15.
 */
public class PlaceholderFragment extends Fragment implements SortObserver {

    private ColumnChartView chart;
    private ColumnChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;
    private int[] randomArray;
    private TextView sort;
    private List<SubcolumnValue> values;
    private int[] sortedArray;
    private TextView generate;
    private EditText numbers;
    private int numbersToBeGenerated = 0;
    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();

    Sorter h;

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_column_chart, container, false);

        init(rootView);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    numbersToBeGenerated = Integer.parseInt(numbers.getText().toString());
                    if (numbersToBeGenerated == 1) {
                        Toast.makeText(getContext(), R.string.oneElement, Toast.LENGTH_SHORT).show();
                    } else {
                        NumberList n = new NumberList(numbersToBeGenerated);
                        randomArray = n.getAnArray();
                        generateData();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.request, Toast.LENGTH_SHORT).show();
                }
            }
        });


        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    h = new HeapSorter(PlaceholderFragment.this, getContext(), randomArray);
                    sortedArray = h.getSortedArray();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Nothing to sort!", Toast.LENGTH_SHORT).show();

                }
            }
        });


        return rootView;
    }

    private void init(View rootView) {

        sort = (TextView) rootView.findViewById(R.id.sort);
        generate = (TextView) rootView.findViewById(R.id.generate);
        numbers = (EditText) rootView.findViewById(R.id.numbers);
        chart = (ColumnChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
    }

    private void swap(int i, int j) {

        Collections.swap(values, i, j);
        List<Column> columns = new ArrayList<Column>();
        Column column = new Column(values);
        columns.add(column);
        data = new ColumnChartData(columns);

        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        if (hasAxesNames) {
            axisX.setName("Axis X");
            axisY.setName("Axis Y");
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        chart.setColumnChartData(data);

    }


    @Override
    public void callback(final int i, final int j) {

        Runnable task = new Runnable() {
            public void run() {
                swap(i, j);
            }
        };
        worker.schedule(task, 0, TimeUnit.SECONDS);
    }


    private void generateData() {

        int numSubcolumns = randomArray.length;
        int numColumns = 1;

        List<Column> columns = new ArrayList<Column>();
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(randomArray[j], ChartUtils.pickColor()));
                Log.e("", "" + randomArray[j]);
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setColumnChartData(data);

    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

}
