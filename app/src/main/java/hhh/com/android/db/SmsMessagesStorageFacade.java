package hhh.com.android.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.hhh.protocol.message.SmsMessage;

import hhh.com.android.Application;
import hhh.com.android.db.SmsMessageEntryContract.SmsMessageEntry;

/**
 * Created by konstantin.bogdanov on 11.11.2015.
 */
public class SmsMessagesStorageFacade {
    private static class LazyLoader {
        private static final SmsMessagesStorageFacade INSTANCE = new SmsMessagesStorageFacade();
    }

    private SmsMessagesStorageFacade() {}

    public static SmsMessagesStorageFacade getInstance() {
        return LazyLoader.INSTANCE;
    }

    private SQLiteDatabase getDatabase() {
        return Application.getDatabase(Application.getContext());
    }

    public void putMessage(SmsMessage message, long packetId, Long date) {
        ContentValues values = new ContentValues();
        values.put(SmsMessageEntry.COLUMN_NAME_ENTRY_ID, message.id);
        values.put(SmsMessageEntry.COLUMN_NAME_PHONE_NUMBER, message.phoneNumber);
        values.put(SmsMessageEntry.COLUMN_NAME_MESSAGE_SENT, 0);
        values.put(SmsMessageEntry.COLUMN_NAME_MESSAGE_TEXT, message.text);
        values.put(SmsMessageEntry.COLUMN_NAME_PACKET_ID, packetId);
        getDatabase().insert(
                SmsMessageEntry.TABLE_NAME,
                null,
                values
        );
    }
}
