package common.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created with Administrator
 * DATE:2016/12/22
 * Time:13:38
 */
public class HttpRequestUtil {
    /**
     * 向指定URL发送GET方法的请求
     * 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     *
     * @param url   URL
     * @param param name1=value1&name2=value2 的形式。
     * @return
     */
    public static String sendGet(String url, String param) {
        return HttpRequestUtil.sendGet(url, param, null);
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url    URL
     * @param param  name1=value1&name2=value2 的形式。
     * @param cookie 增加COOKIE
     * @return 返回请求数据
     */
    public static String sendGet(String url, String param, String cookie) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();

            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("contentType", "UTF-8");
            connection.setRequestProperty("user-agent", UserAgents.getRandom());//"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"
            if (cookie != null) {
                connection.setRequestProperty("Cookie", cookie);
            }
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
//            for (String key : map.keySet()) {
//             //   System.out.println(key + "--->" + map.get(key));
//            }
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (Exception e) {

            Common.ShowMessage("发送GET请求异常:" + e + "url:" + url + "?" + param);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * 2016/10/14  9:36
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("contentType", "UTF-8");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            Common.ShowMessage("发送POST请求异常:" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
