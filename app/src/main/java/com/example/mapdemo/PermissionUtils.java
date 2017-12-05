package com.example.mapdemo;

/**
 * Created by admin on 12/5/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.support.v4.util.SimpleArrayMap;

public final class PermissionUtils {
    private static final SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS = new SimpleArrayMap(8);

    private PermissionUtils() {
    }

    public static boolean verifyPermissions(int... grantResults) {
        if(grantResults.length == 0) {
            return false;
        } else {
            int[] var1 = grantResults;
            int var2 = grantResults.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                int result = var1[var3];
                if(result != 0) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean permissionExists(String permission) {
        Integer minVersion = (Integer)MIN_SDK_PERMISSIONS.get(permission);
        return minVersion == null || VERSION.SDK_INT >= minVersion.intValue();
    }

    public static boolean hasSelfPermissions(Context context, String... permissions) {
        String[] var2 = permissions;
        int var3 = permissions.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String permission = var2[var4];
            if(permissionExists(permission) && !hasSelfPermission(context, permission)) {
                return false;
            }
        }

        return true;
    }

    private static boolean hasSelfPermission(Context context, String permission) {
        if(VERSION.SDK_INT >= 23 && "Xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
            return hasSelfPermissionForXiaomi(context, permission);
        } else {
            try {
                return PermissionChecker.checkSelfPermission(context, permission) == 0;
            } catch (RuntimeException var3) {
                return false;
            }
        }
    }

    private static boolean hasSelfPermissionForXiaomi(Context context, String permission) {
        String permissionToOp = AppOpsManagerCompat.permissionToOp(permission);
        if(permissionToOp == null) {
            return true;
        } else {
            int noteOp = AppOpsManagerCompat.noteOp(context, permissionToOp, Process.myUid(), context.getPackageName());
            return noteOp == 0 && PermissionChecker.checkSelfPermission(context, permission) == 0;
        }
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        String[] var2 = permissions;
        int var3 = permissions.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String permission = var2[var4];
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }

        return false;
    }

    public static boolean shouldShowRequestPermissionRationale(Fragment fragment, String... permissions) {
        String[] var2 = permissions;
        int var3 = permissions.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String permission = var2[var4];
            if(fragment.shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }

        return false;
    }

    public static boolean shouldShowRequestPermissionRationale(android.app.Fragment fragment, String... permissions) {
        String[] var2 = permissions;
        int var3 = permissions.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String permission = var2[var4];
         /*   if(FragmentCompat.shouldShowRequestPermissionRationale(fragment, permission)) {
                return true;
            }*/
        }

        return false;
    }

    public static void requestPermissions(android.app.Fragment fragment, String[] permissions, int requestCode) {
/*
        FragmentCompat.requestPermissions(fragment, permissions, requestCode);
*/
    }

    static {
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", Integer.valueOf(14));
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", Integer.valueOf(20));
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", Integer.valueOf(16));
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", Integer.valueOf(16));
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", Integer.valueOf(9));
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", Integer.valueOf(16));
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", Integer.valueOf(23));
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", Integer.valueOf(23));
    }
}
