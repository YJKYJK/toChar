package yanyan.com.tochar.utils;

import android.app.Activity;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class PermissionsUtil {

    /**
     * 授权
     * @param permissions
     * @param context
     * @return
     */
    public static boolean authorization(String [] permissions, Activity context){
        final boolean[] res = {false};
        //判断权限是否存在
       if(XXPermissions.isHasPermission(context,permissions)){
           return true;
       }

        XXPermissions permission = XXPermissions.with(context).permission(permissions);
        permission.request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if(isAll){
                    res[0] =true;
                }else {
                    ToastUtil.showShotToast(context,"获取权限成功，部分权限未正常授予");
                }

            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {
                if(quick) {
                    ToastUtil.showShotToast(context,"被永久拒绝授权，请手动授予权限");
                    //如果是被永久拒绝就跳转到应用权限系统设置页面
                    XXPermissions.gotoPermissionSettings(context);
                }else {
                    ToastUtil.showShotToast(context,"获取权限失败");
                }
                res[0] =false;
            }
        });

        return res[0];
    }
}
