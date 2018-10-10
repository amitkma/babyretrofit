package lib;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ExecutorCallback<T> implements Call<T> {
    private String url;
    ExecutorCallback(String url) {
        this.url = url;
    }

    @Override
    public void enqueue(Callback<T> callback) {
        URL connectionUrl = null;
        try {
            connectionUrl = new URL(url);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connectionUrl.openConnection();
            if(httpsURLConnection.getResponseCode() < 200 || httpsURLConnection.getResponseCode() >=400){
                callback.onError("Response code: "+httpsURLConnection.getResponseCode());
                return;
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpsURLConnection.getInputStream()));

            String input;

            StringBuilder content = new StringBuilder();
            while ((input = br.readLine()) != null) {
                content.append(input + "\n");
            }
            br.close();
            if(httpsURLConnection.getResponseCode() == 200){
                callback.onSuccess((T) content.toString());
            }else{
                callback.onError("Response code: "+httpsURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError(e.getMessage());
        }

    }
}
