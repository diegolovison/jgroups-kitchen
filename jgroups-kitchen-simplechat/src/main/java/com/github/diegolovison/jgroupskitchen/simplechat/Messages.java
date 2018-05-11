package com.github.diegolovison.jgroupskitchen.simplechat;

import fundamentals.Queue;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;

public class Messages implements Serializable {

    private final Queue<String> messages;
    private final int maxMessagesToStore;

    public Messages(int maxMessagesToStore) {
        this.maxMessagesToStore = maxMessagesToStore;
        this.messages = new Queue<>();
    }

    public void add(String message) {
        if (maxMessagesToStore == messages.size()) {
            messages.dequeue();
        }
        messages.enqueue(message);
    }

    public void clear() {
        for (int i = 0; i < this.messages.size(); i++) {
            this.messages.dequeue();
        }
    }

    public void addAll(Messages messages) {
        for (String item : messages.messages) {
            this.messages.enqueue(item);
        }
    }

    public int size() {
        return this.messages.size();
    }

    public void forEach(Consumer<String> action) {
        Objects.requireNonNull(action);
        for (String t : this.messages) {
            action.accept(t);
        }
    }
}
