package my.retail.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class MyRetailGson {

    private static Logger LOGGER = LoggerFactory.getLogger(MyRetailGson.class);

    private MyRetailGson() {

    }

    private Gson gson = new Gson();

    private static MyRetailGson instance;

    public static MyRetailGson getInstance() {

        if (instance == null) {
            instance = new MyRetailGson();
        }
        
        return instance;
    }

    public String toString(Object obj) {

        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            LOGGER.error("MyRetailGson_ERROR: {}", e);
            return "";
        }
    }
}
