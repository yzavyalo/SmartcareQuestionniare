package com.petrsu.cardiacare.smartcare;

/**
 * Created by Iuliia Zavialova on 16.05.16.
 */
public class SmartCareLibrary {

    // Native code part begin
    static {
        System.loadLibrary("smartcare_native");
    }

    public native long connectSmartSpace(String name, String ip, int port);

    public native void disconnectSmartSpace(long nodeDescriptor);

    public native Questionnaire getQuestionnaire(long nodeDescriptor);
}
