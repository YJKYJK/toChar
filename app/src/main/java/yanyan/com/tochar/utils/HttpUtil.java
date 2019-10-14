package yanyan.com.tochar.utils;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
  private static  OkHttpClient okHttpClient=new OkHttpClient();
    public static Call getMsg(String url) {
        Request request = new Request.Builder().url(url).get().build();
        Call call =okHttpClient.newCall(request);
        return call;
    }
}
