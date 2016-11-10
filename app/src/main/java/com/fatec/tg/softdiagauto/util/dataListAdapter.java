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
import com.fatec.tg.softdiagauto.model.Sensor;

import java.util.ArrayList;


/**
 * Created by Erick on 07/11/2016.
 */

public class dataListAdapter extends BaseAdapter{
    ArrayList<Sensor> sensors;
    Activity context;

    public dataListAdapter(Activity context, ArrayList<Sensor> sensors) {

        this.context = context;
        this.sensors = new ArrayList<>();

        for(Sensor sensor: sensors){
            if(sensor.getStatus() == 1){
                this.sensors.add(sensor);
            }
        }

    }

    public int getCount() {
        // TODO Auto-generated method stub
        return sensors.size();
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
        row = inflater.inflate(R.layout.activity_list_view, parent, false);
        TextView title, detail;
        ImageView i1;
        title = (TextView) row.findViewById(R.id.title);
        detail = (TextView) row.findViewById(R.id.detail);
        i1=(ImageView)row.findViewById(R.id.img);
        title.setText(sensors.get(position).getNome());
        detail.setText(String.valueOf(sensors.get(position).getValores().get(19))+
                sensors.get(position).getUnidade());
        i1.setImageResource(sensors.get(position).getIconDrawable());

        return (row);



    }


}
