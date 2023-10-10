package signals;

import java.util.HashSet;
import java.util.function.Consumer;

public final class Signal1<T> {
	private final HashSet<Consumer<T>> listeners = new HashSet<>();
	private T awaitedObject;

	public void addListener(Consumer<T> listener) { listeners.add(listener); }
	public void removeListener(Consumer<T> listener) { listeners.remove(listener); }
	public void clearListeners() { listeners.clear(); }

	public void emit(T t) {
		listeners.forEach(listener -> listener.accept(t));
		synchronized (this) {
			awaitedObject = t;
			notify();
		}
	}

	public T await() throws InterruptedException {
		synchronized (this) { wait(); }
		return awaitedObject;
	}
}
