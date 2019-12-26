package com.tdt.easyroute.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.tdt.easyroute.R;
import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listTitle;
    private Map<String,List<String>> listItem;

    public CustomExpandableListAdapter(Context context, List<String> listTitle, Map<String, List<String>> listItem) {
        this.context = context;
        this.listTitle = listTitle;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return listTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listItem.get(listTitle.get(groupPosition).toString()).size();
        //return listItem.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItem.get(listTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title= (String)getGroup(groupPosition);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.list_group,null);

        }
        TextView txtTitle= (TextView) convertView.findViewById(R.id.listTitle);
        txtTitle.setTypeface(null,Typeface.BOLD);
        txtTitle.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title= (String)getChild(groupPosition,childPosition);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item,null);
        }
        TextView txtChild= (TextView) convertView.findViewById(R.id.expandabledListItem);
        ImageView ivChild= (ImageView) convertView.findViewById(R.id.expandabledListImage);

        txtChild.setText(title);

        ivChild.setBackground(imagen(groupPosition,childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public Drawable imagen(int grupo, int hijo)
    {
        Drawable drawable=null;

        if(grupo==0 && hijo==0)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_home, null);
        else
        if(grupo==0 && hijo==1)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_ini, null);
        else
        if(grupo==1 && hijo==0)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_carga, null);
        else
        if(grupo==1 && hijo==1)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_inv, null);
        else
        if(grupo==1 && hijo==2)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_recarga, null);
        else
        if(grupo==1 && hijo==3)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_devo, null);
        else
        if(grupo==1 && hijo==4)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_descarga, null);
        else
        if(grupo==2 && hijo==0)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_pedidos, null);
        else
        if(grupo==3 && hijo==0)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_con, null);
        else
        if(grupo==3 && hijo==1)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_ped, null);
        else
        if(grupo==3 && hijo==2)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_dev, null);
        else
        if(grupo==4 && hijo==0)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_arq, null);
        else
        if(grupo==4 && hijo==1)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_ven, null);
        else
        if(grupo==5 && hijo==0)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_cli1, null);
        else
        if(grupo==5 && hijo==1)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_cli2, null);
        else
        if(grupo==5 && hijo==2)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_cli3, null);
        else
        if(grupo==6 && hijo==0)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_sug, null);
        else
        if(grupo==6 && hijo==1)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_tans, null);
        else
        if(grupo==6 && hijo==2)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_borrar, null);
        else
        if(grupo==6 && hijo==3)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_fin, null);
        else
        if(grupo==7 && hijo==0)
            drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.icon_ajus, null);
        else
            drawable=null;

        return drawable;
    }
}
