package hhh.com.android.db;

import android.provider.BaseColumns;

/**
 * Created by konstantin.bogdanov on 11.11.2015.
 */
public class ReceivedSmsMessageEntryContract {
    public ReceivedSmsMessageEntryContract() {  }

    /* Inner class that defines the table contents */
    public static abstract class ReceivedSmsMessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "received_sms_messages";
        public static final String COLUMN_NAME_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_NAME_MESSAGE_TEXT = "message_text";
        public static final String COLUMN_NAME_MESSAGE_SENT = "message_sent";
        public static final String COLUMN_NAME_PACKET_ID = "packet_id";
    }
}

