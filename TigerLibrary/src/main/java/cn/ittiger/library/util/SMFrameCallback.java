package cn.ittiger.library.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.Choreographer;

/**
 * 流畅度分析，16ms
 * 在需要统计的Activity的onCreate方法中进行使用@{link SMFrameCallback.getInstance.start()}
 *
 * Created by ylhu on 17-2-15.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SMFrameCallback implements Choreographer.FrameCallback {

    public static  SMFrameCallback sInstance;

    private String TAG="SMFrameCallback";

    public static final float deviceRefreshRateMs=16.6f;

    public static  long lastFrameTimeNanos=0;//纳秒为单位

    public static  long currentFrameTimeNanos=0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void start() {
        Choreographer.getInstance().postFrameCallback(SMFrameCallback.getInstance());
    }

    public static SMFrameCallback getInstance() {
        if (sInstance == null) {
            sInstance = new SMFrameCallback();
        }
        return sInstance;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void doFrame(long frameTimeNanos) {
        if(lastFrameTimeNanos==0){
            lastFrameTimeNanos=frameTimeNanos;
            Choreographer.getInstance().postFrameCallback(this);
            return;
        }
        currentFrameTimeNanos=frameTimeNanos;
        float value=(currentFrameTimeNanos-lastFrameTimeNanos)/1000000.0f;

        final int skipFrameCount = skipFrameCount(lastFrameTimeNanos, currentFrameTimeNanos, deviceRefreshRateMs);
        Log.e(TAG,"两次绘制时间间隔value="+value+"  frameTimeNanos="+frameTimeNanos+"  currentFrameTimeNanos="+currentFrameTimeNanos+"  skipFrameCount="+skipFrameCount+"");
        lastFrameTimeNanos=currentFrameTimeNanos;
        Choreographer.getInstance().postFrameCallback(this);
    }


    /**
     *
     *计算跳过多少帧
     * @param start
     * @param end
     * @param devicefreshRate
     * @return
     */
    private  int skipFrameCount(long start,long end,float devicefreshRate){
        int count =0;
        long diffNs=end-start;
        long diffMs = Math.round(diffNs / 1000000.0f);
        long dev= Math.round(devicefreshRate);
        if(diffMs>dev){
            long skipCount=diffMs/dev;
            count=(int)skipCount;
        }
        return  count;
    }
}
