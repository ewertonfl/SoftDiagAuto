package com.fatec.tg.softdiagauto.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.content.Context;

import com.fatec.tg.softdiagauto.R;


/**
 * Created by Erick on 07/11/2016.
 */

public class dataListAdapter extends BaseAdapter{
    String[] Title, Detail;
    int[] imge;
    Activity context;
    dataListAdapter() {
        Title = null;
        Detail = null;
        imge=null;
    }

    public dataListAdapter(Activity context, String[] text, String[] text1,int[] text3) {
        super();
        this.context = context;
        Title = text;
        Detail = text1;
        imge = text3;

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return Title.length;
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row;
        row = inflater.inflate(R.layout.data_base_adapter, parent, false);
        TextView title, detail;
        ImageView i1;
        title = (TextView) row.findViewById(R.id.title);
        detail = (TextView) row.findViewById(R.id.detail);
        i1=(ImageView)row.findViewById(R.id.img);
        title.setText(Title[position]);
        detail.setText(Detail[position]);
        i1.setImageResource(imge[position]);

        return (row);



    }


}
