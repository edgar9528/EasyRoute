package com.tdt.easyroute.CardViews.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Preventa.Venta.FamiliavenFragment;

import java.util.List;


public class ProductosPrevSecondAdapter extends BaseExpandableListAdapter {

    private Context context;
    private FamiliavenFragment familiavenFragment;

    List<String[]> data;

    String[] headers;


    public ProductosPrevSecondAdapter(Context context, String[] headers, List<String[]> data, FamiliavenFragment familiavenFragment) {
        this.context = context;
        this.data = data;
        this.headers = headers;
        this.familiavenFragment = (FamiliavenFragment) familiavenFragment;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return headers[groupPosition];
    }

    @Override
    public int getGroupCount() {

        return headers.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_second, null);
            TextView text = (TextView) convertView.findViewById(R.id.rowSecondText);
            String groupText = getGroup(groupPosition).toString();
            text.setText(groupText);

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        String[] childData;

        childData = data.get(groupPosition);

        return childData[childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_third, null);

            TextView textView = (TextView) convertView.findViewById(R.id.rowThirdText);
            LinearLayout linearLayout = convertView.findViewById(R.id.linearSeleccionar);

            String[] childArray=data.get(groupPosition);

            final String text = childArray[childPosition];

            textView.setText(text);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    familiavenFragment.ProductoSeleccionado(text);
                }
            });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String[] children = data.get(groupPosition);


        return children.length;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}