package com.kobo_service;

public class NativeHelper {

    static {
        System.loadLibrary("kobo_service");
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
    public static native int ioctlGetInteger(String device, int  code);
}
