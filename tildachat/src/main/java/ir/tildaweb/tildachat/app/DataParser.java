package ir.tildaweb.tildachat.app;

import com.google.gson.GsonBuilder;

public class DataParser {

    public static  <T> T fromJson(String json, Class<T> classOfT){
        return new GsonBuilder().create().fromJson(json , classOfT);
    }

    public static String toJson(Object object){
        return new GsonBuilder().create().toJson(object);
    }
}
