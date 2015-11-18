package hhh.com.android.db;

import android.provider.BaseColumns;

/**
 * Created by konstantin.bogdanov on 11.11.2015.
 */
public class PacketEntryContract {
    public PacketEntryContract() {  }

    /* Inner class that defines the table contents */
    public static abstract class PacketEntrty implements BaseColumns {
        public static final String TABLE_NAME = "packets";
        public static final String COLUMN_NAME_ENTRY_ID = "packet_id";
        public static final String COLUMN_NAME_DATE = "got_date";
        public static final String COLUMN_NAME_PACKET_TYPE = "packet_type";
    }
}

