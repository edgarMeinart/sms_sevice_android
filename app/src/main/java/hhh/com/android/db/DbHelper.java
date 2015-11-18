package hhh.com.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hhh.com.android.db.SmsMessageEntryContract.SmsMessageEntry;

import static hhh.com.android.db.PacketEntryContract.*;

/**
 * Created by konstantin.bogdanov on 11.11.2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "SmsMessages.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DATE_TYPE = " DATE";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TABLE_SMS_MESSAGES =
            "CREATE TABLE " + SmsMessageEntry.TABLE_NAME + " (" +
                    SmsMessageEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    SmsMessageEntry.COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + COMMA_SEP +
                    SmsMessageEntry.COLUMN_NAME_PHONE_NUMBER + INTEGER_TYPE + COMMA_SEP +
                    SmsMessageEntry.COLUMN_NAME_MESSAGE_TEXT + TEXT_TYPE + COMMA_SEP +
                    SmsMessageEntry.COLUMN_NAME_MESSAGE_SENT + INTEGER_TYPE + COMMA_SEP +
                    SmsMessageEntry.COLUMN_NAME_PACKET_ID + INTEGER_TYPE +
            ")";

    private static final String SQL_DELETE_TABLE_SMS_MESSAGES =
            "DROP TABLE IF EXISTS " + SmsMessageEntry.TABLE_NAME;

    private static final String SQL_DELETE_TABLE_PACKETS =
            "DROP TABLE IF EXISTS " + PacketEntrty.TABLE_NAME;

    private static final String SQL_CREATE_TABLE_PACKETS =
            "CREATE TABLE " + PacketEntrty.TABLE_NAME + " (" +
                    PacketEntrty._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    PacketEntrty.COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + COMMA_SEP +
                    PacketEntrty.COLUMN_NAME_PACKET_TYPE + INTEGER_TYPE + COMMA_SEP +
                    PacketEntrty.COLUMN_NAME_DATE + TEXT_TYPE +
                    ")";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PACKETS);
        db.execSQL(SQL_CREATE_TABLE_SMS_MESSAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_PACKETS);
        db.execSQL(SQL_DELETE_TABLE_SMS_MESSAGES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
