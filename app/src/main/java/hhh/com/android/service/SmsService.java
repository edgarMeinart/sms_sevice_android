package hhh.com.android.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;

import com.hhh.protocol.IllegalConversionException;
import com.hhh.protocol.Packet;
import com.hhh.protocol.message.SmsMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import java.util.jar.Pack200;

/**
 * Created by konstantin.bogdanov on 05.11.2015.
 */
public class SmsService extends Service {
    private List<SmsMessage> messages;

    public final static String ADDRESS = "address";
    public static final String PORT = "port";
    Socket socket;
    Thread smsSendThread;
    Thread requestThread;
    Thread sendResponseThread;
    Thread socketConnectionThread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (socket == null) {
            messages = new java.util.concurrent.CopyOnWriteArrayList<>();

            String address = intent.getStringExtra(ADDRESS);
            int port = intent.getIntExtra(PORT, 80);
            socket = new Socket();

            socketConnectionThread = new SocketConnectionThread(address, port);
            socketConnectionThread.start();
            smsSendThread = new SendSmsThread();
            smsSendThread.start();
            requestThread = new RequestThread();
            requestThread.start();

            Notification note = new NotificationCompat.Builder(this)
                    .addAction(android.R.drawable.btn_default, "test", null)
                    .build();
            startForeground(10, note);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket = null;
        socketConnectionThread.interrupt();
        smsSendThread.interrupt();
        requestThread.interrupt();
        sendResponseThread.interrupt();
        messages.clear();
        stopForeground(true);
        stopSelf();
    }

    private class SendSmsThread extends Thread {
        @Override
        public void run() {
            new Runnable() {
                @Override
                public void run() {
                    while (!isInterrupted()) {
                        if (!messages.isEmpty()) {
                            SmsMessage message = messages.get(0);
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(message.phoneNumber, null, message.text, null, null);
                            messages.remove(0);
                        }
                        try {
                            sleep(20000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }
    }

    private class RequestThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Packet<SmsMessage> smsMessagePacket = Packet.read(socket.getInputStream());
                    messages.addAll(smsMessagePacket.getMessages());
                    Packet<SmsMessage> responsePacket = Packet.createResponsePacket(smsMessagePacket);
                    responsePacket.write(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalConversionException e) {
                    e.printStackTrace();
                }
                try {
                    sleep(20000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SocketConnectionThread extends Thread {
        private String host;
        private int port;

        public SocketConnectionThread(String address, int port) {
            this.host = address;
            this.port = port;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                if (!socket.isConnected()) {
                    try {
                        SocketAddress socketAddress = new InetSocketAddress(host, port);
                        socket.connect(socketAddress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
