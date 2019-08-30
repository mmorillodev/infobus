package com.softbox.infobus;

/**
 * Created by Usuario on 16/11/2017.
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Usuario on 15/11/2017.
 */

public class Conexao {
    public static String postDados(String url_, String parametro){
        URL url;
        HttpURLConnection connection = null;
        try{
            url = new URL(url_);
            connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length","" + Integer.toString(parametro.getBytes().length));

            connection.setRequestProperty("Content-language","pt-BR");

            connection.setUseCaches(false);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            /*
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(parametro);
            dataOutputStream.flush();
            dataOutputStream.close();
            */

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            outputStreamWriter.write(parametro);
            outputStreamWriter.flush();


            InputStream inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String linha;
            StringBuffer resposta = new StringBuffer();

            while((linha = bufferedReader.readLine()) != null){
                resposta.append(linha);
                resposta.append('\r');
            }

            bufferedReader.close();
            return resposta.toString();
        }
        catch(Exception e){
            return null;
        }
        finally {
            if(connection != null){
                connection.disconnect();
            }
        }

    }
}
