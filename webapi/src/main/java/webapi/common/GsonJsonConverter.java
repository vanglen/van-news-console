package webapi.common;

import com.google.gson.GsonBuilder;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Created by Administrator on 2017/8/30.
 */
public class GsonJsonConverter extends GsonHttpMessageConverter {
    public GsonJsonConverter(){
        //更换Gson转换器
        super.setGson(new GsonBuilder()
                .serializeNulls()		//null值属性也需要序列化
                .setDateFormat("yyyy-MM-dd HH:mm:ss") //设置日期转换
                .create());
    }
}
