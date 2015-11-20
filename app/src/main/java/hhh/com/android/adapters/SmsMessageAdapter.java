package hhh.com.android.adapters;

import android.telephony.SmsMessage;

import java.util.Arrays;
import java.util.List;

/**
 * Created by konstantin.bogdanov on 19.11.2015.
 */
public class SmsMessageAdapter {
    private List<SmsMessage> messages;
    private com.hhh.protocol.message.SmsMessage newSmsMessage;

    public SmsMessageAdapter(SmsMessage[] messages) {
        this.messages = Arrays.asList(messages);
    }

    public com.hhh.protocol.message.SmsMessage getMessages() {
        if (messages != null) {
            newSmsMessage = new com.hhh.protocol.message.SmsMessage();
            newSmsMessage.setPhoneNumber(messages.get(0).getDisplayOriginatingAddress());
            if (messages.size() == 1) {
                SmsMessage message = messages.get(0);
                com.hhh.protocol.message.SmsMessage newMessage = new com.hhh.protocol.message.SmsMessage();
                newMessage.setText(message.getDisplayMessageBody());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (SmsMessage message : this.messages) {
                    stringBuilder.append(message.getDisplayMessageBody());
                }
                newSmsMessage.setText(stringBuilder.toString());
            }
            messages = null;
        }
        return newSmsMessage;
    }

}
