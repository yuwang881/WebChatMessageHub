package isve.webchat.util.comm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

    private static Properties props = new Properties();

    static {
        try {
            props.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("conf/weixin.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
    
    public static String get(String key, String defaultvalue) {
        return props.getProperty(key,defaultvalue);
    }
    
    public static int getInt(String key){
        int value=0;
        try {
            value = Integer.parseInt(props.getProperty(key));
        } catch (NumberFormatException e){
            value =0;
        }
        return value;
    }

    public static void setProps(Properties p) {
        props = p;
    }
}
