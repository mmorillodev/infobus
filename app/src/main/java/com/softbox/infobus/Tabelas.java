package com.softbox.infobus;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.sip.SipAudioCall;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Tabelas extends AppCompatActivity {

    String url ="";
    String parametros="";
    Button btnGo;
    Spinner spinAno, spinMes, spinDia;
    TextView txtDia;
    ListView listTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabelas);

        final String linha = getIntent().getExtras().getString("linha");

        btnGo = (Button) findViewById(R.id.btnGo);
        spinAno = (Spinner) findViewById(R.id.spinAno);
        spinMes = (Spinner) findViewById(R.id.spinMes);
        spinDia = (Spinner)findViewById(R.id.spinDia);
        txtDia = (TextView)findViewById(R.id.txtDia);
        listTable = (ListView)findViewById(R.id.listTable);

        String[]opcoesAno = {"Ano","2017","2016","2015"};
        String[]opcoesMes = {"Mês","1","2","3","4","5","6","7","8", "9", "10", "11","12"};
        String[]opcoesDia = {"Dia","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22"
                ,"23","24","25","26","27","28","29","30","31"};

        ArrayAdapter<String> adapterAno = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,opcoesAno);
        adapterAno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String>adapterMes = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,opcoesMes);
        adapterMes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String>adapterDia = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,opcoesDia);
        adapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinMes.setAdapter(adapterMes);
        spinAno.setAdapter(adapterAno);
        spinDia.setAdapter(adapterDia);



        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                String[] arrayVazio = new String[0];
                ArrayAdapter<String> adapterVazio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayVazio);
                listTable.setAdapter(adapterVazio);

                if(networkInfo == null && !networkInfo.isConnected()){
                    Toast.makeText(getApplicationContext(), R.string.semConexao, Toast.LENGTH_SHORT).show();
                }
                else if(spinAno.getSelectedItem().equals("Mês") || spinAno.getSelectedItem().equals("Ano") || spinDia.getSelectedItem()=="Dia"){
                    Toast.makeText(getApplicationContext(), "Selecione um dia, mês e ano.", Toast.LENGTH_SHORT).show();
                }
                else{
                    txtDia.setText(spinDia.getSelectedItem() + "/" + spinMes.getSelectedItem() + "/" + spinAno.getSelectedItem());
                    url = "https://infobuss.000webhostapp.com/AppInfoBus/qtdPessoas.php";
                    parametros = "mes=" + spinMes.getSelectedItem()
                            + "&ano=" + spinAno.getSelectedItem()
                            + "&linha=" + linha + "&dia=" + spinDia.getSelectedItem();
                   new SolicitaDados().execute(url);
                }

            }
        });

    }
    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String...urls){

            return Conexao.postDados(urls[0], parametros);
        }

        @Override
        protected void onPostExecute(String result){
            if(result.contains("Sem_dados")){
                Toast.makeText(getApplicationContext(), "Não há dados a serem exibidos.", Toast.LENGTH_LONG).show();
            }

            else if (verifyConnection()){
                Toast.makeText(getApplicationContext(), R.string.semConexao, Toast.LENGTH_LONG).show();
            }
            else {
                String arrayPessoa[] = result.split(",");
                String[] arrayPessoaHora = new String[24];


                for (int i = 0; i <= arrayPessoa.length - 2; i++) {
                    arrayPessoaHora[i] = i + ":00 - " + arrayPessoa[i];
                }

                ArrayAdapter<String> adapterPessoaHora = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayPessoaHora);
                listTable.setAdapter(adapterPessoaHora);

            }
        }

        private boolean verifyConnection(){
            ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conectivtyManager.getActiveNetworkInfo() != null
                    && conectivtyManager.getActiveNetworkInfo().isAvailable()
                    && conectivtyManager.getActiveNetworkInfo().isConnected()) return true;
            else return false;
        }
    }
}
