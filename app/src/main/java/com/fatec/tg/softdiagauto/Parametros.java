package com.fatec.tg.softdiagauto;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Gabriel Rubio on 19/10/2016.
 */

public class Parametros extends AppCompatActivity {

    ListView l1;
    String[] t1={"Rotação do motor","Temperatura da água","Tensão da bateria"};
    String[] d1={"700 Rpm","56 C°","12 V"};
    int[] i1 ={R.drawable.rotacao_motor,R.drawable.termometro,R.drawable.bateria};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametros);
        l1=(ListView)findViewById(R.id.listViewParametros);
        l1.setAdapter(new dataListAdapter(t1,d1,i1));
    }

    class dataListAdapter extends BaseAdapter {
        String[] Title, Detail;
        int[] imge;

        dataListAdapter() {
            Title = null;
            Detail = null;
            imge=null;
        }

        public dataListAdapter(String[] text, String[] text1,int[] text3) {
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

            LayoutInflater inflater = getLayoutInflater();
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
}