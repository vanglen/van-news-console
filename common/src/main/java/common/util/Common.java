package common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with Administrator
 * DATE:2016/12/22
 * Time:13:19
 */
public class Common {
    public static void ShowMessage(Object o) {
        System.out.println(o);
    }

    /**
     * util.data转sql.date
     * 2016/9/14  11:13
     */
    public static java.sql.Date GetSqlDate(String pubDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        if (!pubDate.equals("")) {
            d = sdf.parse(pubDate);
        }
        return new java.sql.Date(d.getTime());
    }


    /**
     * @param pubDate 时间字符串
     * @return yyyy-MM-dd HH:mm:ss
     * @throws ParseException err
     */
    public static java.sql.Timestamp GetSqlTimeSpan(String pubDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date();
        if (!pubDate.equals("")) {
            d = sdf.parse(pubDate);
        }
        return new java.sql.Timestamp(d.getTime());
    }


    public static java.sql.Timestamp GetSqlTimeSpan2(String pubDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d = new Date();
        if (!pubDate.equals("")) {
            d = sdf.parse(pubDate);
        }
        return new java.sql.Timestamp(d.getTime());
    }


    /**
     * @param picUrl 原图地址
     * @param size   需要转换的4：3图片  12090 220165
     * @return 返回新的图片地址
     */
    public static String GetClubThumbnail(String picUrl, String size) {
        String result = "";
        if (!picUrl.equals("")) {
            int pos = picUrl.lastIndexOf("/");
            result = picUrl.substring(0, pos + 1) + size + "_" + picUrl.substring(pos + 1, picUrl.length());
        }
        return result;

    }

    /**
     * 获取文件内容
     *
     * @param fileName 文件名  //   String path = new AutoClubJob().getFile("conf/AutoClubConfig.xml");
     * @return 文件内容
     */
    public String getFile(String fileName) {
        StringBuilder builder = new StringBuilder("");
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            URL url = classLoader.getResource(fileName);
            if (url != null) {
                File file = new File(url.getFile());

                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    builder.append(line).append("\n");
                }
                scanner.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将unix时间戳转换为格式（1486429331－2017-2-7 9:2:11）
     *
     * @param s unix时间戳(10位)
     * @return
     */
    public static String unixStampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 获取抓取数据时间。
     *
     * @return 返回抓取数据的时间
     */
    public static Date getSpiderDate(int inDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, -inDay);
        try {
            return dft.parse(dft.format(c.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 使用分隔符将List转换为String
     *
     * @param list      List
     * @param separator 分隔符
     * @return 返回结果
     */
    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (Object aList : list) {
            sb.append(aList).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }


    /**
     * 获取传入时间和当前系统时间的差值：天
     *
     * @param date 传入时间
     * @return 返回差值int天数
     */

    public static int differenceDay(String date) {
        int days = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowDate = simpleDateFormat.format(new Date());
        try {
            long from = simpleDateFormat.parse(date).getTime();
            long to = simpleDateFormat.parse(nowDate).getTime();
            days = (int) ((to - from) / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;

    }

    /**
     * 格式化时间（将"1484884348913+0800"格式化为"2017-01-20 11:52:28.913"）
     *
     * @param str
     * @return
     */
    public static String timeStampWithZoneToString(String str) {
        str = str.replace("/Date(", "").replace(")/", "");
        String time = str;
        if (str.length() == 18) {
            str.substring(0, str.length() - 5);
        }
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return format.format(date);
    }

    /**
     * 根据名称获取配置信息
     *
     * @param name
     * @return
     */
    public static String getConfigDB(String name, String path) {
        String result = "";
        if (!name.equals("")) {
            result = Common.getConfig(path, name);
        }
        return result;
    }

    /**
     * 根据名称获取配置信息
     *
     * @param name name
     * @return key
     */
    public static String getConfigCommon(String name, String path) {
        String result = "";
        if (!name.equals("")) {
            result = Common.getConfig(path, name);
        }
        return result;
    }

    /**
     * 根据名称获取配置信息
     *
     * @param file 文件路径
     * @param name 节点名称
     * @return
     */
    private static String getConfig(String file, String name) {
        String result = "";
        if (!file.equals("") && !name.equals("")) {
            try {
                Properties pro = new Properties();
                FileInputStream in = new FileInputStream(Common.class.getResource(file).getPath());
                pro.load(in);
                in.close();
                result = pro.getProperty(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
