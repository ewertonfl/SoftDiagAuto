package com.fatec.tg.softdiagauto.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.fatec.tg.softdiagauto.R;

import java.util.ArrayList;

/**
 * Created by Ewerton on 10/12/2016.
 */

public class ActivityFiltros extends Activity  {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    private ArrayAdapter<String> xAdapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
    int clickCounter=0;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_filtros);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);

        xAdapter = new ArrayAdapter<String>(this, R.layout.activity_filtros);
        xAdapter.add("Teste");
        //setListAdapter(adapter);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        listItems.add("Clickeddddddd : "+clickCounter++);
        adapter.notifyDataSetChanged();
    }
}
