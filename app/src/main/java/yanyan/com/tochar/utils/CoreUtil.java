package yanyan.com.tochar.utils;




import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import yanyan.com.tochar.beans.AppInfo;

public class CoreUtil {

    public static AppInfo appInfo=null;

    public static boolean isDetection=true;

    /**
     * 加群
     * @param context
     * @param key
     * @return
     */
    public static boolean joinQQGroup(Context context,String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    /**
     * 获取版本信息
     * @param context
     * @return
     */
    public static String getVsrsionInfo(Context context){
        PackageManager manager=context.getPackageManager();
        try {
            PackageInfo info=manager.getPackageInfo(context.getPackageName(),0);
            String version=info.versionName;
            long longVersionCode = info.versionCode;
            return version+":"+longVersionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static Integer getVersion(Context context){
        PackageManager manager=context.getPackageManager();
        try {
            PackageInfo info=manager.getPackageInfo(context.getPackageName(),0);
            long longVersionCode = info.versionCode;
            return Integer.parseInt(longVersionCode+"");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static void detectionUpdate2(Context context){
        Integer version = getVersion(context);
        AppInfo appInfo=CoreUtil.appInfo;
        if(appInfo!=null){
            int vers = appInfo.getVersion();
            if(vers>version){
                DialogUtil dialogUtil=new DialogUtil(context,"更新",
                        appInfo.getUpdateNotic(),"取消","更新");

                dialogUtil.show();
                //跳转更新
                dialogUtil.setRightOnclick(new DialogUtil.RightOnlistener() {
                    @Override
                    public void rightOnclick() {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(appInfo.getUpdateUrl());
                        intent.setData(content_url);
                        context.startActivity(intent);
                    }
                });
            }else {
                ToastUtil.showLongToast(context,"已是最新版本");
            }
        }else {
            ToastUtil.showLongToast(context,"已是最新版本");
        }

    }


    /**
     * 检查更新
     */
    public static void detectionUpdate(Context context){
        Integer version = getVersion(context);
        AppInfo appInfo=CoreUtil.appInfo;
        if(appInfo!=null){
            int vers = appInfo.getVersion();
            if(vers>version){
                DialogUtil dialogUtil=new DialogUtil(context,"更新",
                        appInfo.getUpdateNotic(),"取消","更新");

                dialogUtil.show();
                //跳转更新
                dialogUtil.setRightOnclick(new DialogUtil.RightOnlistener() {
                    @Override
                    public void rightOnclick() {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(appInfo.getUpdateUrl());
                        intent.setData(content_url);
                        context.startActivity(intent);
                    }
                });
            }else {
                //检查公告
                if(appInfo.getIspopup().equals("Y")){
                    DialogUtil dialogUtil=new DialogUtil(context,"通知",
                            appInfo.getNotice(),"取消","知道了");
                    dialogUtil.show();

                }

            }
        }else {

            if(isDetection){
                isDetection=false;
                detectionUpdate(context);
            }

        }
    }







    public static void about(Context context){
        DialogUtil dialogUtil=new DialogUtil(context,"关于",
               "app名称:图片转字符图\n版本:"+getVsrsionInfo(context),"取消","知道了");
        dialogUtil.show();

    }


    /**
     * 备用检查更新
     * @param context
     */
    public void  detection(Context context){
        Call msg = HttpUtil.getMsg("https://blog.csdn.net/qq_36880660/article/details/88044542");
        msg.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                res=StringUtil.getMiddleStr(res,"[[[","]]]");
                appInfo=JSON.parseObject(res,AppInfo.class);
                detectionUpdate(context);
            }
        });
    }

    /**
     * 复制功能
     * @param text
     * @param context
     */
    public static void copy(String text,Context context){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtil.showLongToast(context,"复制成功");

    }




}
