package com.fatec.tg.softdiagauto.view;

import android.app.ActionBar;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.fatec.tg.softdiagauto.model.Sensor;
import com.fatec.tg.softdiagauto.util.dataListAdapter;
import com.fatec.tg.softdiagauto.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gabriel Rubio on 19/10/2016.
 */

public class ActivityParametros extends AppCompatActivity {

    ListView l1;
    Double[] d2 ={700.0,56.0,12.0};
    Double [] tempo ={0.00,0.01,0.02,0.03,0.04,0.05,0.06,0.07,0.08,0.09,0.010};
    ArrayList<Sensor> sensors;
    String textoRel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar act = getActionBar();

        if(act == null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        else
            act.setDisplayHomeAsUpEnabled(true);

        sensors = new ArrayList<>();
        sensors.add(new Sensor("Rotação do motor","RPM",R.drawable.rotacao_motor,1));
        sensors.add(new Sensor("Temperatura da água","ºC",R.drawable.termometro,1));
        sensors.add(new Sensor("Tensão da bateria","V",R.drawable.bateria,1));

        setContentView(R.layout.activity_parametros);
        l1=(ListView)findViewById(R.id.listViewParametros);
        l1.setAdapter(new dataListAdapter(this, sensors));
        gerarGrafico(tempo[0],d2[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_parametros,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.gerar_relatorio_parametros:
                gerarRelatorio();
                break;
            case R.id.filtrar_parametros:
                Toast.makeText(this, "Filtro Parâmetros", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void gerarRelatorio() {

        int j = 1;
        for(Sensor s0 : sensors) {
            //s0.getNome();
            //s0.getUnidade();
            //s0.getIconDrawable();
            //s0.getStatus();

            textoRel+="Sensor "+j+": "+s0.getNome() + ":\nLeitura: " + s0.getUnidade() + "\n";
            textoRel+="\n";
            j++;
        }

        String[] dth = horarioAtual().split(" ");
        textoRel="\nLista de falhas: \n"+textoRel;
        textoRel="Horário emissão: "+dth[1]+" \n"+textoRel;
        textoRel="Data emissão: "+dth[0]+" \n"+textoRel;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        //i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ewerton.oliveira@kplay.com.br"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Relatório de parâmetros.");
        i.putExtra(Intent.EXTRA_TEXT   , textoRel);
        try {
            startActivity(Intent.createChooser(i, "Salvar relatório de parâmetros."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ActivityParametros.this, "Não há cliente de email instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    public String horarioAtual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        return dateFormat.format(data_atual);
    }

    public void gerarGrafico(Double tempo,Double valor){

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(
                new DataPoint[] {
                        new DataPoint(tempo,valor)
                });
        graph.addSeries(series);
    }


}