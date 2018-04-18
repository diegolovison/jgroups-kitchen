package com.github.diegolovison.jgroupskitchen.simplechat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

public class ChatReceiverAdapter extends ReceiverAdapter {

   private List<String> state = new LinkedList<String>();

   @Override
   public void viewAccepted(View newView) {
      System.out.println("** view: " + newView);
   }

   @Override
   public void receive(Message msg) {
      String line = msg.getSrc() + ": " + msg.getObject();
      System.out.println(line);
      synchronized(state) {
         state.add(line);
      }
   }

   @Override
   public void getState(OutputStream output) throws Exception {
      synchronized(state) {
         Util.objectToStream(state, new DataOutputStream(output));
      }
   }

   @Override
   public void setState(InputStream input) throws Exception {
      List<String> list = Util.objectFromStream(new DataInputStream(input));
      synchronized(state) {
         state.clear();
         state.addAll(list);
      }
      System.out.println(list.size() + " messages in chat history):");
      for(String str: list) {
         System.out.println(str);
      }
   }
}
