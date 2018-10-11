import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zhongzhiqiang
 * @date 2018/10/10  15:19
 */
public class Url {
    public static void main(String[] args) throws  Exception{
        String urll = "http://www.xinshubao.net/0/410/";
        String prefix= "10943170";
        String suffix = ".html";
        boolean flag = true;
        int count = 0;
//        for (int i=0; i<2; i++){
        while(true){
//            if(count > 0){
//                prefix = prefix + "_2";
//            }
            count ++;
            System.out.println("============" + urll + prefix + suffix);
            URL url = new URL(urll + prefix + suffix);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            InputStream inputStream = conn.getInputStream();
            String line = "";
            byte[] bb = new byte[1024];
            while (inputStream.read(bb) != -1) {
                String str = new String(bb, 0, bb.length, "gbk");
                line += str;
            }
            String fix = "<div id=\"content\">";
            line = line.substring(line.indexOf(fix) + fix.length());
            line = line.replaceAll("&nbsp;", "").replaceAll("<br />", "");
            String prefix1 = line.substring(line.lastIndexOf("var next_page = \"") + "var next_page = \"".length(), line.lastIndexOf(".html\""));
            line = line.substring(0, line.indexOf("</div>"));
            System.out.println(line);
            System.out.println(prefix1);
            prefix = prefix1;
            inputStream.close();
        }

    }
}
