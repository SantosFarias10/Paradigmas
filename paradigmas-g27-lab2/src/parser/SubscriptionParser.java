package parser;

import java.io.FileReader;
import java.io.IOException;

import org.json.JSONTokener;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.ArrayList;

import subscription.SingleSubscription;
import subscription.Subscription;

/*
 * Importamos la clase FileReader que forma parte del paquete java.io para poder
 * leer suscription.json. 
 */

/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 */

public class SubscriptionParser extends GeneralParser {
    
    private String url;
    private JSONArray urlParamsArray;
    private String urlType;
    private Subscription subscriptions = new Subscription();

    /* Constructor */
    public SubscriptionParser(){
        super();
        /* 
         * Por alguna razon se recomienda no llamar metodos sobrescritos en el 
         * constructor (@Override)
         */
        // reader("bin/config/subscriptions.json");
    }
   
    @Override
    public Subscription reader(String fileName){
        
        try (FileReader reader = new FileReader(fileName)) {
            /*
             * JSONArray recibe String, tambien acepta un JSONTokener
             * JSONTokene actua como un "analizador lexico" para leer texto 
             * JSON y convertirlos en JSONObject/JSONTokener.
             */
            JSONArray array = new JSONArray(new JSONTokener(reader));

            for (int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);

                url = obj.getString("url");
                urlParamsArray = obj.getJSONArray("urlParams");
                urlType = obj.getString("urlType");
                
                /* Convertimos los JSONArray a List<String> */
                List<String> urlParams = new ArrayList<>();
                if (urlParamsArray != null) {
                    for (int j = 0; j < urlParamsArray.length(); j++){
                        urlParams.add(urlParamsArray.getString(j));
                    }
                }

                /* Agregamos la suscripcion a una lista */
                subscriptions.addSingleSubscription(new SingleSubscription(url, urlParams, urlType));
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Error en el JSON: " + e.getMessage());
        }
        return subscriptions;
    }
 
    /* Main solo para testear */
    public static void main(String[] args){
        SubscriptionParser test = new SubscriptionParser();
        Subscription subTest = test.reader("bin/config/subscriptions.json");
        subTest.prettyPrint();
    }
}

/* 
 * PARA TESTEAR USAR MAKEFILE <make testSubscriptionParser>.
 * EL json-20250107.jar ES LA LIBRERIA DE org.json QUE USAMOS POR AHORA SOLO EN SubscriptionParser.
 */
