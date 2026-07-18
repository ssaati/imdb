package ir.saeid.imdb.model;

import java.util.Arrays;
import java.util.List;

public class DataUtils {
    public static int getInteger(String value){
        if(value == null || value.equals("\\N"))
            return 0;
        return Integer.valueOf(value);
    }
    public static double getDouble(String value){
        if(value == null || value.equals("\\N"))
            return 0;
        return Double.valueOf(value);
    }
    public static List<String> getList(String value){
        if(value == null || value.equals("\\N"))
            return null;
        return Arrays.asList(value.split(","));
    }
}
