package com.game.edu.ibuzzserver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LineAdapter  extends BaseAdapter {
    private Context context;
    private final String[] lineValues;

    public LineAdapter(Context context, String[] lineValues) {
        this.context = context;
        this.lineValues = lineValues;
    }

    @Override
    public int getCount() {
        return lineValues.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (view == null) {

            gridView = new View(context);
            System.out.println("lineValues:i" + lineValues[i]);
            // get layout
            gridView = inflater.inflate(R.layout.gridline, null);
            String[] lineValues1 = lineValues[i].split(",");
            // set value into textview
            TextView textView1 = (TextView) gridView.findViewById(R.id.dateValue);
            textView1.setText(lineValues1[0]);
            TextView textView2 = (TextView) gridView.findViewById(R.id.nameValue);
            textView2.setText(lineValues1[1]);
            TextView textView3 = (TextView) gridView.findViewById(R.id.scoreValue);
            textView3.setText(lineValues1[2]);

        } else {
            gridView = (View) view;
        }

        return gridView;
    }
}
