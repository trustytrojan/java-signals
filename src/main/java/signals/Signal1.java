package signals;

import java.util.HashSet;
import java.util.function.Consumer;

public final class Signal1<T> {
	private final HashSet<Consumer<T>> listeners = new HashSet<>();
	private T awaitedValue;

	public void addListener(Consumer<T> listener) { listeners.add(listener); }
	public void removeListener(Consumer<T> listener) { listeners.remove(listener); }
	public void clearListeners() { listeners.clear(); }

	public void emit(T t) {
		listeners.forEach(listener -> listener.accept(t));
		synchronized (this) {
			awaitedValue = t;
			notify();
		}
	}

	public T await() throws InterruptedException {
		synchronized (this) { wait(); }
		return awaitedValue;
	}
}
