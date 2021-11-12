package com.newpathfly.flight.search.webapp;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Registry<E> {
    private final Set<Consumer<E>> listeners = ConcurrentHashMap.newKeySet();

    public IRegistry register(Consumer<E> listener) {
      listeners.add(listener);
      return () -> listeners.remove(listener);
    }
  
    public void sentEvent(E event) {
      listeners.forEach(listener -> listener.accept(event));
    }
}
