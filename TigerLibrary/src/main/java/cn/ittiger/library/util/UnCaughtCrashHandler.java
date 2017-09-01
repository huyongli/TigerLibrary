package cn.ittiger.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;

public class UnCaughtCrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    public static final boolean DEBUG = true;
    private static UnCaughtCrashHandler INSTANCE;
    private UncaughtExceptionHandler mDefaultHandler;
    private CrashListener mCrashListener;
    private Context mContext;
    private String mLogPath;

    private UnCaughtCrashHandler() {

    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static UnCaughtCrashHandler getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new UnCaughtCrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {

        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mLogPath = SdCardUtil.getCacheDir(mContext) + File.separator + "log";
    }

    public void setCrashListener(CrashListener crashListener) {

        mCrashListener = crashListener;
    }

    /**
     * 设置系统崩溃日志记录目录，请在init()方法之后执行
     *
     * @param logPath
     */
    public void setLogPath(String logPath) {

        this.mLogPath = logPath;
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */

    @SuppressLint("SimpleDateFormat")
    public void uncaughtException(Thread thread, Throwable ex) {

        ThrowableLogHelper.logThrowable(mLogPath, ex);

        if(mCrashListener != null) {
            mCrashListener.onCrash(ex);
        }
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error : ", e);
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {

        if (ex == null) {
            return true;
        }
        final String msg = ex.getLocalizedMessage();
        new Thread() {
            public void run() {

                Looper.prepare();
                Toast.makeText(mContext, "程序出错啦:" + msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        return true;
    }

    public interface CrashListener {

        void onCrash(Throwable throwable);
    }
}
