package hhh.com.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import hhh.com.android.db.DbHelper;

/**
 * Created by konstantin.bogdanov on 11.11.2015.
 */
public class Application extends android.app.Application {
    private volatile DbHelper dbHelper;
    private static Context context;

    private void initDatabase() {
        dbHelper = new DbHelper(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
        context = this;
    }

    public static @Nullable
    SQLiteDatabase getDatabase(Context context) {
        return ((Application)context.getApplicationContext()).getDatabase();
    }

    private @Nullable SQLiteDatabase getDatabase() {
        try {
            return getDBHelper().getWritableDatabase();
        } catch (Exception e) {
        }

        return null;
    }

    private SQLiteOpenHelper getDBHelper() {
        return dbHelper;
    }

    public static Context getContext() {
        return context;
    }
}
