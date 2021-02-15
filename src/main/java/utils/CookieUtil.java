package utils;

import com.example.hydss.config.HydssConst;
import com.example.hydss.pojo.Acc;
import org.openqa.selenium.Cookie;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class CookieUtil {

    public static List<String> cookieSet2List(Set<Cookie> cookieSet) {
        List<String> cookieList = new ArrayList<>();
        for (Cookie i : cookieSet) {
            cookieList.add(i.getName() + "=" + i.getValue());
        }
        return cookieList;
    }

    public static void saveObjectByObjectOutput(Object o, File file) throws IOException {
        String parent = file.getParent();
        File parentFile = file.getParentFile();
        if (!parentFile.exists() && !parentFile.isDirectory()) {
            parentFile.mkdirs();
        }
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(o);
        objectOutputStream.close();

    }

    public static Object getObjectByObjectInput(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        Object o = inputStream.readObject();
        inputStream.close();
        return o;
    }
}
