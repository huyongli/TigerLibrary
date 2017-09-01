package cn.ittiger.library.helper;

import cn.ittiger.library.app.AppContext;
import cn.ittiger.library.util.AppUtil;
import cn.ittiger.library.util.PreferenceHelper;
import cn.ittiger.library.util.SecurityUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;

public class Configuration {
    private static final String KEY_UUID = "key_uuid";

    private static Configuration sInstance = new Configuration();

    private Context mContext;
    private String mAndroidId;
    private String mAndroidIdHash;
    private int mVersionCode;
    private String mVersionName;
    private String mPackageName;

    public static Configuration getInstance() {

        return sInstance;
    }

    private Configuration() {

        mContext = AppContext.getInstance();
        mPackageName = AppUtil.getAppPackageName(mContext);
        mVersionCode = AppUtil.getAppVersionCode(mContext);
        mVersionName = AppUtil.getAppVersionName(mContext);
    }

    public int getVersionCode() {

        return mVersionCode;
    }

    public String getVersionName() {

        return mVersionName;
    }

    public String getPackageName() {

        return mPackageName;
    }

    public String getClientId() {

        String id = getAndroidIdHash();
        if (TextUtils.isEmpty(id)) {
            id = getClientGUID();
        }
        if (TextUtils.isEmpty(id)) {
            id = "Unknown";
        }
        return id;
    }

    public String getClientGUID() {

        final SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        String guid = preferences.getString(KEY_UUID, "");
        if (TextUtils.isEmpty(guid)) {
            final String defaultId = SecurityUtil.generateGUID();
            PreferenceHelper.putString(KEY_UUID, defaultId);
            guid = defaultId;
        }
        return guid;
    }

    public String getAndroidId() {

        if (TextUtils.isEmpty(mAndroidId)) {
            mAndroidId = Settings.Secure.getString(
                    mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return mAndroidId;
    }

    public String getAndroidIdHash() {

        if (TextUtils.isEmpty(mAndroidIdHash)) {
            String androidId = getAndroidId();
            if (TextUtils.isEmpty(androidId)) {
                // generate a uid.
                androidId = getClientGUID();
            }
            mAndroidIdHash = SecurityUtil.md5Hash(androidId);
        }
        return mAndroidIdHash;
    }
}
