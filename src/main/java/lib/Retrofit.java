package lib;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;

public class Retrofit {
    public static <T> T create(Class<T> tClass) {
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(),
                new Class<?>[]{tClass}, (proxy, method, args) -> {
                    System.out.println(method.getName());
                    Url annotation = method.getAnnotation(Url.class);
                    if (annotation == null) {
                        return null;
                    } else {
                        String url = annotation.value();
                        if(method.getReturnType().getCanonicalName().equals("lib.Call")){
                            return new ExecutorCallback<>(url);
                        }else{
                            URL connectionUrl = new URL(url);
                            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connectionUrl.openConnection();
                            if(httpsURLConnection.getResponseCode() < 200 || httpsURLConnection.getResponseCode() >=400){
                                return "Response code: "+httpsURLConnection.getResponseCode();
                            }
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(httpsURLConnection.getInputStream()));

                            String input;

                            StringBuilder content = new StringBuilder();
                            while ((input = br.readLine()) != null) {
                                content.append(input + "\n");
                            }
                            br.close();

                            return content.toString();
                        }

                    }
                });
    }
}
