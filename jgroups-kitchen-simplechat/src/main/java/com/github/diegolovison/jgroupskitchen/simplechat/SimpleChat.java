package com.github.diegolovison.jgroupskitchen.simplechat;

import org.jgroups.JChannel;
import org.jgroups.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

public class SimpleChat {

    private JChannel channel;
    private String userName;

    public SimpleChat() {
        this.userName = UUID.randomUUID().toString();
    }

    private void start() throws Exception {
        channel = new JChannel(); // use the default config, udp.xml
        channel.setReceiver(
                new ChatReceiverAdapter(Integer.valueOf(System.getProperty("max_history", "5"))));
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
    }

    private void eventLoop() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.print("> ");
                System.out.flush();
                String line = in.readLine().toLowerCase();
                if (line.equals("q")) break;
                line = "[" + userName + "] " + line;
                Message msg = new Message(null, line);
                channel.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stop() {
        channel.close();
    }

    public static void main(String[] args) throws Exception {
        SimpleChat chat = new SimpleChat();
        chat.start();
        chat.eventLoop();
        chat.stop();
    }
}