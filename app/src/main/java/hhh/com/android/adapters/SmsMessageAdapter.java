package hhh.com.android.adapters;

import android.telephony.SmsMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by konstantin.bogdanov on 19.11.2015.
 */
public class SmsMessageAdapter {
    private List<SmsMessage> messages;
    private List<com.hhh.protocol.message.SmsMessage> adaptedMessages;

    public SmsMessageAdapter(SmsMessage[] messages) {
        this.messages = Arrays.asList(messages);
    }

    public List<com.hhh.protocol.message.SmsMessage> getMessages() {
        if (messages != null) {
            adaptedMessages = new ArrayList<>();
            for (SmsMessage message : this.messages) {
                com.hhh.protocol.message.SmsMessage newMessage = new com.hhh.protocol.message.SmsMessage();
                newMessage.phoneNumber = message.getDisplayOriginatingAddress();
                newMessage.text = message.getDisplayMessageBody();
                adaptedMessages.add(newMessage);
            }
        }

        return adaptedMessages;
    }

}
