package com.github.diegolovison.jgroupskitchen.simplechat;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ChatReceiverAdapter extends ReceiverAdapter {

    private final Messages state;

    public ChatReceiverAdapter(int maxMessagesToStore) {
        state = new Messages(maxMessagesToStore);
    }

    @Override
    public void viewAccepted(View newView) {
        System.out.println("** view: " + newView);
    }

    @Override
    public void receive(Message msg) {
        String line = msg.getSrc() + ": " + msg.getObject();
        System.out.println(line);
        synchronized (state) {
            state.add(line);
        }
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        synchronized (state) {
            Util.objectToStream(state, new DataOutputStream(output));
        }
    }

    @Override
    public void setState(InputStream input) throws Exception {
        Messages messages = Util.objectFromStream(new DataInputStream(input));
        synchronized (state) {
            state.clear();
            state.addAll(messages);
        }
        System.out.println(messages.size() + " messages in chat history):");
        messages.forEach((str) -> System.out.println(str));
    }
}
