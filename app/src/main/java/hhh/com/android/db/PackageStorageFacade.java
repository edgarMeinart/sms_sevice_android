package hhh.com.android.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.hhh.protocol.message.SmsMessage;

import hhh.com.android.Application;
import hhh.com.android.db.SmsMessageEntryContract.SmsMessageEntry;

/**
 * Created by konstantin.bogdanov on 11.11.2015.
 */
public class PackageStorageFacade {
    private static class LazyLoader {
        private static final PackageStorageFacade INSTANCE = new PackageStorageFacade();
    }

    private PackageStorageFacade() {}

    public static PackageStorageFacade getInstance() {
        return LazyLoader.INSTANCE;
    }

    private SQLiteDatabase getDatabase() {
        return Application.getDatabase();
    }
}
