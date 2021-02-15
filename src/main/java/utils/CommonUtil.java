package utils;

import com.example.hydss.config.HydssConst;
import com.example.hydss.pojo.Acc;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommonUtil {

    //public static String convertStreamToString(MultipartFile file) throws IOException {
    public static Map<String, ArrayList<Acc>> convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        Map<String,ArrayList<Acc>> accMap=new HashMap();
        try {
            while ((line = reader.readLine()) != null) {
                int x=line.charAt(0);
                if (x==HydssConst.SPACE_ASC || x==HydssConst.TAB_ASC){
                    continue;
                }
                int i = line.indexOf(",");
                int j = line.indexOf(":");
                String shopName=line.substring(0,j).toUpperCase();
                int shop = HydssConst.SHOPS.indexOf(shopName);
                ArrayList<Acc> accs = accMap.get(shopName);
                if(accs==null) {
                    accs = new ArrayList();
                    accMap.put(shopName,accs);
                }
                Acc acc = new Acc(line.substring(j+1, i), line.substring(i + 1), shop);
                accs.add(acc);
            }
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("accMap:"+accMap);
        return accMap;
    }
    //返回项目的根目录(src的路径)
    public static String getProjectPath() {
        //return new ClassPathResource("").getFile().getAbsolutePath();
        return System.getProperty("user.dir");
    }
    //返回电商账号的cookie的文件名
    public static String getCookieFilename(Acc acc) {
        return  "cookies/"+HydssConst.SHOPS.get(acc.getShop())+"_"+acc.getUser() + ".txt";
    }
}
