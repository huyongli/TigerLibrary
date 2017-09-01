package cn.ittiger.library.helper;

import cn.ittiger.library.app.AppContext;
import cn.ittiger.library.app.IDbApplication;
import cn.ittiger.database.SQLiteDB;
import cn.ittiger.database.SQLiteDBConfig;
import cn.ittiger.database.SQLiteDBFactory;

/**
 * 本地数据库管理类
 */
public class DBHelper {
    /**
     * 全局数据库
     */
    private static SQLiteDB sDB;

    /**
     * 获取全局数据库操作对象
     * @return
     */
    public static SQLiteDB getSQLiteDB() {

        if(sDB == null) {
            synchronized (DBHelper.class) {
                if(sDB == null) {
                    try {
                        IDbApplication dbApplication = (IDbApplication) AppContext.getInstance().getApplicationContext();
                        SQLiteDBConfig config = dbApplication.getDbConfig();
                        sDB = SQLiteDBFactory.createSQLiteDB(config);
                    } catch(Exception e) {
                        throw new IllegalStateException("Application must be implements IDBApplication");
                    }
                }
            }
        }
        return sDB;
    }

    /**
     * 关闭数据库
     */
    public static void closeSQLiteDB() {
        if(sDB != null) {
            sDB.close();
        }
        sDB = null;
    }
}
