package yanyan.com.tochar.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void showShotToast(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}
