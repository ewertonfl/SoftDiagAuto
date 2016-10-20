package com.fatec.tg.softdiagauto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class InformacoesVeiculo extends AppCompatActivity {

    ListView l1;
    String[] t1={"Chassi","Número do motor"};
    String[] d1={"XXXXXXXXXXXXXXXXX","0678°"};
    int[] i1 ={R.drawable.chassis,R.drawable.motor};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_veiculo);
        l1=(ListView)findViewById(R.id.listViewInformacoes);
        l1.setAdapter(new InformacoesVeiculo.dataListAdapter(t1,d1,i1));
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