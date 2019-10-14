package yanyan.com.tochar.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import yanyan.com.tochar.beans.ToChanrBean;

public class ToCharUtil {

    /**
     * 有颜色
     * @param
     * @param context
     * @return
     */
    public static ToChanrBean createAsciiPicColor(Bitmap img,String str, Context context) {
        //final String base = "";// 字符串由复杂到简单
        if(str.length()<5){
            str="E8XOHLTIKi=+;:,.";
        }
        StringBuilder text = new StringBuilder();
        List<Integer> colors = new ArrayList<>();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        // Bitmap image = BitmapFactory.decodeFile(path);  //读取图片
        Bitmap image = img;  //读取图片
        int width0 = image.getWidth();
        int height0 = image.getHeight();
        int width1, height1;
        int scale = 7;
        if (width0 <= width / scale) {
            width1 = width0;
            height1 = height0;
        } else {
            width1 = width / scale;
            height1 = width1 * height0 / width0;
        }
        image = scale(image, width1, height1);  //读取图片
        //输出到指定文件中
        for (int y = 0; y < image.getHeight(); y += 2) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = image.getPixel(x, y);
                final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                final int index = Math.round(gray * (str.length() + 1) / 255);
                String s = index >= str.length() ? " " : String.valueOf(str.charAt(index));
                colors.add(pixel) ;
                text.append(s);
            }
            text.append("\n");
            colors.add(0) ;
        }
        Bitmap bitmap = textAsBitmapColor(text, colors, context);
        ToChanrBean toChanrBean=new ToChanrBean(text.toString(),bitmap);
        return toChanrBean;
    }
    /**
     * 颜色字符创建
     * @param text
     * @param colors
     * @param context
     * @return
     */
    public static Bitmap textAsBitmapColor(StringBuilder text, List<Integer> colors, Context context) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.TRANSPARENT);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setTextSize(12);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         //
        SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
        ForegroundColorSpan colorSpan;
        for (int i = 0; i <colors.size() ; i++) {
            colorSpan = new ForegroundColorSpan(colors.get(i));
            spannableString.setSpan(colorSpan, i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        StaticLayout layout = new StaticLayout(spannableString, textPaint, width,

                Layout.Alignment.ALIGN_CENTER, 1f, 0.0f, true);

        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth() + 20,

                layout.getHeight() + 20, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        canvas.translate(10, 10);

        canvas.drawColor(Color.WHITE);

//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
        layout.draw(canvas);
        return bitmap;
    }




    /**
     * 无颜色
     * @param img
     * @param context
     * @return
     */
    public static ToChanrBean createAsciiPic( Bitmap img, String str,Context context) {
        if(str.length()<5){
            str="E8XOHLTIKi=+;:,.";
        }
        StringBuilder text = new StringBuilder();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Bitmap image = img;  //读取图片
        int width0 = image.getWidth();
        int height0 = image.getHeight();
        int width1, height1;
        int scale = 7;
        if (width0 <= width / scale) {
            width1 = width0;
            height1 = height0;
        } else {
            width1 = width / scale;
            height1 = width1 * height0 / width0;
        }
        image = scale(img, width1, height1);  //读取图片
        //输出到指定文件中
        for (int y = 0; y < image.getHeight(); y += 2) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = image.getPixel(x, y);
                final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                final int index = Math.round(gray * (str.length() + 1) / 255);
                String s = index >= str.length() ? " " : String.valueOf(str.charAt(index));
                text.append(s);
            }
            text.append("\n");
        }
        Bitmap bitmap = textAsBitmap(text, context);
        ToChanrBean toChanrBean=new ToChanrBean(text.toString(),bitmap);
        return toChanrBean;
    }

    /**
     * 无颜色生成
     * @param text
     * @param context
     * @return
     */
    public static Bitmap textAsBitmap(StringBuilder text, Context context) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.GRAY);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setTextSize(12);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         //

        StaticLayout layout = new StaticLayout(text, textPaint, width,

                Layout.Alignment.ALIGN_CENTER, 1f, 0.0f, true);

        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth() + 20,

                layout.getHeight() + 20, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        canvas.translate(10, 10);

        canvas.drawColor(Color.WHITE);

//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
        layout.draw(canvas);
        return bitmap;
    }


    /**
     * 从当前存在的位图，按一定的比例创建一个新的位图
     * @param src
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scale(Bitmap src, int newWidth, int newHeight) {
        Bitmap ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true);
        return ret;
    }

}
