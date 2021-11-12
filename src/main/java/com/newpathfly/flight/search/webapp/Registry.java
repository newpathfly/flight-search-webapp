package com.newpathfly.flight.search.webapp;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Registry<E> {
    private final Set<Consumer<E>> _listeners = ConcurrentHashMap.newKeySet();

    public IRegistry register(Consumer<E> listener) {
      _listeners.add(listener);
      return () -> _listeners.remove(listener);
    }
  
    public void sentEvent(E event) {
      _listeners.forEach(listener -> listener.accept(event));
    }
}
