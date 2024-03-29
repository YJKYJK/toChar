package yanyan.com.tochar.gif;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by chenliu on 2016/7/1.<br/>
 * 描述：
 * </br>
 */
public class GifMakeUtil {

    public static String createGif(String filename, List<String> paths, int fps, int width, int height) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
            localAnimatedGifEncoder.start(baos);//start
            localAnimatedGifEncoder.setRepeat(0);//设置生成gif的开始播放时间。0为立即开始播放
            localAnimatedGifEncoder.setDelay(fps);
            if (paths.size() > 0) {
                for (int i = 0; i < paths.size(); i++) {
                    Bitmap bitmap = BitmapFactory.decodeFile(paths.get(i));
                    Bitmap resizeBm = ImageUtil.resizeImage(bitmap, width, height);
                    localAnimatedGifEncoder.addFrame(resizeBm);
                }
            }
            localAnimatedGifEncoder.finish();//finish

//            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/LiliNote");
//            if (!file.exists()) file.mkdir();
//            String path = Environment.getExternalStorageDirectory().getPath() + "/LiliNote/" + filename + ".gif";
        try {


            FileOutputStream fos = new FileOutputStream(filename);
            baos.writeTo(fos);
            baos.flush();
            fos.flush();
            baos.close();
            fos.close();
        }catch (Exception e){

        }

        return filename;
    }


    public static String createGifByBitmap(String filename, List<Bitmap> bitmaps, int fps, int width, int height) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
        localAnimatedGifEncoder.start(baos);//start
        localAnimatedGifEncoder.setRepeat(0);//设置生成gif的开始播放时间。0为立即开始播放
        localAnimatedGifEncoder.setDelay(fps);
        if (bitmaps.size() > 0) {
            for (int i = 0; i < bitmaps.size(); i++) {
                Bitmap bitmap = bitmaps.get(i);
                Bitmap resizeBm = ImageUtil.resizeImage(bitmap, width, height);
                localAnimatedGifEncoder.addFrame(resizeBm);
            }
        }
        localAnimatedGifEncoder.finish();//finish

        try {


            FileOutputStream fos = new FileOutputStream(filename);
            baos.writeTo(fos);
            baos.flush();
            fos.flush();
            baos.close();
            fos.close();
        }catch (Exception e){

        }

        return filename;
    }





}
