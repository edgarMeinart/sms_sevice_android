package hhh.com.android.db;

import android.provider.BaseColumns;

/**
 * Created by konstantin.bogdanov on 11.11.2015.
 */
public class SmsMessageEntryContract {
    public SmsMessageEntryContract() {  }

    /* Inner class that defines the table contents */
    public static abstract class SmsMessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "sms_messages";
        public static final String COLUMN_NAME_ENTRY_ID = "message_id";
        public static final String COLUMN_NAME_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_NAME_MESSAGE_TEXT = "message_text";
        public static final String COLUMN_NAME_MESSAGE_SENT = "message_text";
        public static final String COLUMN_NAME_PACKET_ID = "packet_id";
    }
}

