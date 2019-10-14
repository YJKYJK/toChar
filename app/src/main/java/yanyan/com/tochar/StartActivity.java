package yanyan.com.tochar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import yanyan.com.tochar.beans.AppInfo;
import yanyan.com.tochar.utils.CoreUtil;
import yanyan.com.tochar.utils.HttpUtil;

public class StartActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        /**
         * 友盟
         */
        UMConfigure.init(this,"5c6eba17b465f53aeb000b8b","Umeng",UMConfigure.DEVICE_TYPE_PHONE,null);
        this.onResume();
        this.onPause();

        getAppInfo();
        Handler handler = new Handler();
        handler.postDelayed(new gotoPage(), 3000);


    }


    /**
     * 统计人数
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    /**
     * 统计时长
     */
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    /**
     * 获取
     */
    public void getAppInfo(){
        Call msg = HttpUtil.getMsg("http://47.107.120.44:8080/web/test/getInfo");
        msg.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res=response.body().string();
                try{
                    CoreUtil.appInfo = JSON.parseObject(res,AppInfo.class);
                }catch (Exception e){
                  System.out.print(e.getMessage());
                }

            }
        });
    }

    class gotoPage implements Runnable{
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(StartActivity.this, MainActivity.class);
            startActivity(intent);
            StartActivity.this.finish();
        }
    }


    private void ztl()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }

    }
}

