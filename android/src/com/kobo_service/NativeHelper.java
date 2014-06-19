package com.kobo_service;

import android.util.Log;

public class NativeHelper {

    static {
        try {
            System.loadLibrary("kobo_service");
        } catch (Exception ex) {
            Log.w("Cannot open kobo_service library.", ex);
        }
    }

    //
    // IOCTL codes
    //

    // eINK update mode
    // 0 - normal
    // 1 - ebook mode (often full screen refreshes)
    // 2 - fast monochrome
    // 3 - fast monochrome with dithering
    public static final int MXCFB_SET_UPDATE_MODE = 1074021939;
    public static final int MXCFB_GET_UPDATE_MODE = -2147203532;

    public static native int sendEvent(String device, short type, short code, int value);

    public static native int ioctlSetInteger(String device, int code, int value);
    public static native int ioctlGetInteger(String device, int code);

    public static void OneTimeRefresh() {
        try {
            final int oldMode = NativeHelper.ioctlGetInteger("/dev/graphics/fb0", NativeHelper.MXCFB_GET_UPDATE_MODE);
            NativeHelper.ioctlSetInteger("/dev/graphics/fb0", NativeHelper.MXCFB_SET_UPDATE_MODE, 1);

            final android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        NativeHelper.ioctlSetInteger("/dev/graphics/fb0", NativeHelper.MXCFB_SET_UPDATE_MODE, oldMode);
                    } catch (Exception ex) {
                        Log.w("Cannot refresh e-Ink screen.", ex);
                    }
                }
            }, 100);
        } catch (Exception ex) {
            Log.w("Cannot refresh e-Ink screen.", ex);
        }
    }
}
