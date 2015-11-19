package hhh.com.android.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;

import com.hhh.protocol.message.SmsMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hhh.com.android.Application;
import hhh.com.android.db.ReceivedSmsMessageEntryContract.ReceivedSmsMessageEntry;
import hhh.com.android.db.SmsMessageEntryContract.SmsMessageEntry;

/**
 * Created by konstantin.bogdanov on 11.11.2015.
 */
public class ReceivedSmsMessagesStorageFacade {
    private static class LazyLoader {
        private static final ReceivedSmsMessagesStorageFacade INSTANCE = new ReceivedSmsMessagesStorageFacade();
    }

    private ReceivedSmsMessagesStorageFacade() {}

    public static ReceivedSmsMessagesStorageFacade getInstance() {
        return LazyLoader.INSTANCE;
    }

    private SQLiteDatabase getDatabase() {
        return Application.getDatabase();
    }

    public void putMessages(List<SmsMessage> smsMessages) {
        for (SmsMessage message : smsMessages) {
            putMessage(message);
        }
    }

    public void putMessages(List<SmsMessage> smsMessages, long packet) {
        for (SmsMessage message : smsMessages) {
            putMessage(message, packet);
        }
    }

    public void putMessage(SmsMessage message) {
        putMessage(message, -1);
    }

    public void putMessage(SmsMessage message, long packetId) {
        ContentValues values = new ContentValues();
        values.put(ReceivedSmsMessageEntry.COLUMN_NAME_PHONE_NUMBER, message.phoneNumber);
        values.put(ReceivedSmsMessageEntry.COLUMN_NAME_MESSAGE_SENT, 0);
        values.put(ReceivedSmsMessageEntry.COLUMN_NAME_MESSAGE_TEXT, message.text);
        values.put(ReceivedSmsMessageEntry.COLUMN_NAME_PACKET_ID, packetId);
        getDatabase().insert(
                ReceivedSmsMessageEntry.TABLE_NAME,
                null,
                values
        );
    }

    public void setSentStatus(List<SmsMessage> smsMessages) {
        ContentValues values = new ContentValues();
        values.put(ReceivedSmsMessageEntry.COLUMN_NAME_MESSAGE_SENT, 1);

        for (SmsMessage message : smsMessages) {
            getDatabase().update(ReceivedSmsMessageEntry.TABLE_NAME, values, ReceivedSmsMessageEntry._ID + " LIKE ?",
                    new String[]{String.valueOf(message.id)});
        }
    }

    public List<SmsMessage> getNonSentSmsMessages() {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(ReceivedSmsMessageEntry.TABLE_NAME);
        sqLiteQueryBuilder.appendWhere(ReceivedSmsMessageEntry.COLUMN_NAME_MESSAGE_SENT + "=0");

        String[] projection = new String[]{
                ReceivedSmsMessageEntry._ID,
                ReceivedSmsMessageEntry.COLUMN_NAME_MESSAGE_TEXT,
                ReceivedSmsMessageEntry.COLUMN_NAME_PHONE_NUMBER
        };

        Cursor cursor = sqLiteQueryBuilder.query(getDatabase(), projection, null, null, null, null, null);

        List<SmsMessage> smsMessages = new ArrayList<>();

        if (cursor != null) {
            while(cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(ReceivedSmsMessageEntry._ID));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ReceivedSmsMessageEntry.COLUMN_NAME_PHONE_NUMBER));
                String text = cursor.getString(cursor.getColumnIndex(ReceivedSmsMessageEntry.COLUMN_NAME_MESSAGE_TEXT));
                SmsMessage smsMessage = new SmsMessage(id, phoneNumber, text);
                smsMessages.add(smsMessage);
            }
        }

        return smsMessages;
    }
}
