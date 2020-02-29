package com.tdt.easyroute.CardViews.Adapter;

import android.content.Context;
import android.widget.ExpandableListView;

public class ProductosSecondExpandableListView extends ExpandableListView
{

        public ProductosSecondExpandableListView(Context context) {
			super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations. 
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
}