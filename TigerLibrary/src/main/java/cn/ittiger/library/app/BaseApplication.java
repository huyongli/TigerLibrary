package cn.ittiger.library.app;

import cn.ittiger.library.helper.AppVersionHelper;
import cn.ittiger.library.util.AppPathHelper;
import cn.ittiger.library.util.LogHelper;
import cn.ittiger.library.util.UnCaughtCrashHandler;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;

/**
 * @author: laohu on 2017/5/11
 * @site: http://ittiger.cn
 */
public abstract class BaseApplication extends Application  {
    private static final String TAG = "BaseApplication";

    private static int sCreatedCount = 0;//已经创建存在的Activity
    private static int sResumedCount = 0;
    private static int sRunningCount = 0;//正在运行的Activity
    private static long mEnterBackgroundTime;// 应用进入后台的时间

    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(base);
        AppContext.init(base);
        initLibraryShowAbleDebugLog();
    }

    @Override
    public void onCreate() {

        super.onCreate();
        registerLifecycleCallbacks();
        if(AppContext.getInstance().isDebug() && isDebugCatchUnCaughtCrash()) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().build());

            UnCaughtCrashHandler crashHandler = UnCaughtCrashHandler.getInstance();
            crashHandler.init(this);
            crashHandler.setLogPath(AppPathHelper.getCrashDir());
            crashHandler.setCrashListener(new UnCaughtCrashHandler.CrashListener() {
                @Override
                public void onCrash(Throwable throwable) {

                    LogHelper.e(throwable);
                }
            });
        }
    }

    /**
     * library库中是否显示Log日志，如果需要显示在实现此方法，调用如下代码：
     * AppContext.getInstance().debug(BuildConfig.DEBUG);
     */
    public abstract void initLibraryShowAbleDebugLog();

    protected boolean isDebugCatchUnCaughtCrash() {

        return true;
    }

    private void registerLifecycleCallbacks() {

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityStopped(Activity activity) {

                sRunningCount--;
                if(sRunningCount == 0) {//当前应用处于后台
                    mEnterBackgroundTime = System.currentTimeMillis();
                    LogHelper.d(TAG, "app enter in background");
                    enterBackground();
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

                sRunningCount++;
                if(isFromBackgroundToForeground()) {
                    startFromBackgroundToFront(activity, mEnterBackgroundTime);
                }
                mEnterBackgroundTime = 0;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityResumed(Activity activity) {

                sResumedCount++;
                LogHelper.d(TAG, "%s resumed", activity.toString());
            }

            @Override
            public void onActivityPaused(Activity activity) {

                sResumedCount--;
                LogHelper.d(TAG, "%s paused", activity.toString());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {

                sCreatedCount--;
                LogHelper.d(TAG, "%s destroyed", activity.toString());
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                sCreatedCount++;
                String uriString = "";
                Intent intent = activity.getIntent();
                if (intent != null) {
                    Uri uri = intent.getData();
                    if (uri != null) {
                        uriString = uri.toString();
                    }
                }
                LogHelper.d(TAG, "%s created: %s", activity.toString(), uriString);
            }
        });

        this.registerComponentCallbacks(new ComponentCallbacks() {

            @Override
            public void onLowMemory() {

                LogHelper.d(TAG, "lowMemory");
            }

            @Override
            public void onConfigurationChanged(android.content.res.Configuration newConfig) {

                LogHelper.d(TAG, "configurationChanged %s", newConfig == null ? "" : newConfig.toString());
            }
        });
    }

    /**
     * 是否从后台到前台
     * @return
     */
    private boolean isFromBackgroundToForeground() {

        // 如果在执行Activity的onStart时只有一个活动状态的Activity，则说明应用刚启动或从后台切换到前台。
        return sRunningCount == 1 && mEnterBackgroundTime > 0;
    }

    /**
     * 通过resumedCount是否大于0判断应用是否在前台<br/>
     * 当Activity onResume时加1，onPause时减1，因此如果resumedCount>0说明应用在前台
     *
     * @return
     */
    public static boolean isRunningForeground() {

        return sResumedCount > 0;
    }

    /**
     * 当前应用是否处于后台
     * @return
     */
    public static boolean isRunningBackground() {

        return sRunningCount == 0 && mEnterBackgroundTime > 0;
    }

    /**
     * 从后台重新回到前台时调用，只会触发一次
     * @param activity              进入前台后展示的Activity
     * @param enterBackgroundTime   应用进入后台的时间
     */
    protected void startFromBackgroundToFront(Activity activity, long enterBackgroundTime) {

    }

    /**
     * 从前台进入后台时调用，只会触发一次
     */
    protected void enterBackground() {

    }

    /**
     * 应用升级相关逻辑处理
     * {@link AppVersionHelper#checkUpdate()}
     *
     * @param cachedVersionCode
     * @param currentVersionCode
     */
    public void onAppUpgrade(int cachedVersionCode, int currentVersionCode) {

    }
}
