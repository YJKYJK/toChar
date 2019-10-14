package yanyan.com.tochar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import yanyan.com.tochar.beans.ThreadInfo;

public class PictureSelectUtil {



    /**
     * 图片种类
     */
    public static final String IMAGE_TYPE = ".png";

    /**
     * 默认选择图片
     * @param activity
     * @param requestCode
     */
    public static void selectPohto(Activity activity, int requestCode){
        selectPohto(activity,PictureMimeType.ofImage(),requestCode);
    }

    /**
     * 根据自定义类型打开图片选择器
     * @param activity
     * @param type
     * @param requestCode
     */
    public static void selectPohto(Activity activity,int type, int requestCode){
        selectPohto(activity,type,requestCode,false,1,false);
    }

    /**
     * 选择多图
     * @param activity
     * @param maxNum
     * @param requestCode
     */
    public static void selectPohtoMaxNum(Activity activity,int maxNum, int requestCode){
        selectPohto(activity,PictureMimeType.ofImage(),requestCode,false,maxNum,false);
    }

    /**
     * 是否可以选择gif
     * @param activity
     * @param isSelectGif
     * @param requestCode
     */
    public static void selectImageAndGif(Activity activity,boolean isSelectGif,int requestCode){
        selectPohto(activity,PictureMimeType.ofImage(),requestCode,false,1,isSelectGif);
    }

    /**
     * 图片选择器
     * @param activity
     * @param type
     * @param requestCode
     * @param isCamera
     */
    public static void selectPohto(Activity activity,int type, int requestCode,boolean isCamera,int MaxNum,boolean isSelectGif){
        PictureSelector pictureSelector = PictureSelector.create(activity);
        PictureSelectionModel model = pictureSelector.openGallery(type);//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
        // pictureSelector.themeStyle();//主题样式(不设置为默认样式)
        model.maxSelectNum(MaxNum);// 最大图片选择数量 int
        model.imageSpanCount(3);// 每行显示个数 int
        if(MaxNum>1){

            model.selectionMode(PictureConfig.MULTIPLE);
        }else {
            model.selectionMode(PictureConfig.SINGLE);// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
        }

        // model.previewImage(true);// 是否可预览图片 true or false
        // model.previewVideo()// 是否可预览视频 true or false
        //model .enablePreviewAudio() // 是否可播放音频 true or false
        model.isCamera(isCamera);//是否显示拍照按钮 true or false
        model.imageFormat(PictureMimeType.PNG);// 拍照保存图片格式后缀,默认jpeg
        model.isZoomAnim(true);// 图片列表点击 缩放效果 默认true
        model.sizeMultiplier(0.5f);// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
        model.setOutputCameraPath("/qezlProPic'");// 自定义拍照保存路径,可不填
        model.enableCrop(false);// 是否裁剪 true or false
        model.compress(false);// 是否压缩 true or false
        //model.glideOverride();// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
        model.withAspectRatio(1, 1);// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
        //model .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
        model.isGif(isSelectGif);// 是否显示gif图片 true or false
        model.compressSavePath(activity.getFilesDir().getAbsolutePath());//压缩图片保存地址
        model.freeStyleCropEnabled(false);// 裁剪框是否可拖拽 true or false
        model .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
//                .previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(50)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(50)// 小于100kb的图片不压缩
//                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                .rotateEnabled() // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(requestCode);//结果回调onActivityResult code
    }









    /**
     * 把原图按1/10的比例压缩
     *
     * @param path 原图的路径
     * @return 压缩后的图片
     */
    public static Bitmap getCompressPhoto(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 10; // 图片的大小设置为原来的十分之一
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        options = null;
        return bmp;
    }





    /**
     * 保存Bitmap图片在SD卡中
     * 如果没有SD卡则存在手机中
     *
     * @param mbitmap 需要保存的Bitmap图片
     * @return 保存成功时返回图片的路径，失败时返回null
     */
    public static String savePhotoToSD(Bitmap mbitmap, Context context) {
        FileOutputStream outStream = null;
        String fileName = getPhotoFileName(context);
        try {
            File file=new File(fileName);
            file.createNewFile();
            outStream = new FileOutputStream(fileName);
            // 把数据写入文件，100表示不压缩
            mbitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
            return fileName;
        } catch (Exception e) {
            ToastUtil.showLongToast(context,"保存失败，请重新生成");
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (outStream != null) {
                    // 记得要关闭流！
                    outStream.close();
                }
                if (mbitmap != null) {
                   // mbitmap.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 缓存
     * @param
     * @param context
     * @return
     */
    public static String cacheBitmap(Bitmap mbitmap,String fileName, Context context) {
        File filePath = new File(getCachePath(context));
        // 判断文件是否已经存在，不存在则创建
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        FileOutputStream fos=null;
        String bitmapName=filePath+"/"+fileName;
        try {
            File bitmapFile=new File(bitmapName);
            bitmapFile.createNewFile();
            fos=new FileOutputStream(bitmapName);
            mbitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (mbitmap != null) {
                    mbitmap.recycle();
                }

            }catch (Exception e){

            }


        }
     return bitmapName;
    }




    /**
     * 保存gif
     * @param context
     * @return
     */
    public static boolean saveGif(Context context){
        boolean res=false;
        FileOutputStream outStream = null;
        String fileName=getPhotoFileName(context,".gif");
        return res;
    }



    /**
     * 使用当前系统时间作为上传图片的名称  png
     *
     * @return 存储的根路径+图片名称
     */
    public static String getPhotoFileName(Context context) {
       return getPhotoFileName(context,IMAGE_TYPE);
    }

    /**
     * 使用当前系统时间作为上传图片的名称
     *
     * @return 存储的根路径+图片名称
     */
    public static String getPhotoFileName(Context context,String type) {
        File file = new File(getPhoneRootPath(context));
        // 判断文件是否已经存在，不存在则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        // 设置图片文件名称
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        String photoName = "/" + time + type;
        return file + photoName;
    }



    /**
     * 获取手机可存储路径
     *
     * @param context 上下文
     * @return 手机可存储路径
     */
    public static String getPhoneRootPath(Context context) {
      String savePath="";
      String sdPath=Environment.getExternalStorageDirectory().getPath()+"/QezlPro/";
      File file=new File(sdPath);
        if(!file.exists()) {
            file.mkdir();
        }
        else if(!file.isDirectory()) {
            file.delete();
            file.mkdir();
        }

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = sdPath;
        } else {
            Toast.makeText(context, "保存失败！", Toast.LENGTH_SHORT).show();
            return null;
        }


        return savePath;
    }


    /**
     * 缓存路径
     * @param context
     * @return
     */
    public static String getCachePath(Context context) {
        String savePath="";
        String sdPath=Environment.getExternalStorageDirectory().getPath()+"/QezlPro/cache/";
        File file=new File(sdPath);
        if(!file.exists()) {
            file.mkdir();
        }
        else if(!file.isDirectory()) {
            file.delete();
            file.mkdir();
        }

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = sdPath;
        } else {
            Toast.makeText(context, "保存失败！", Toast.LENGTH_SHORT).show();
            return null;
        }


        return savePath;
    }



    /**
     * 根据图片本地地址返回bitmap
     * @param filePath
     * @return
     */
    public static Bitmap getBitmap(String filePath){
        Bitmap bitmap=null;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            bitmap  = BitmapFactory.decodeStream(fis);
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
