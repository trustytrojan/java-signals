package signals;

import java.util.HashSet;
import java.util.function.BiConsumer;

public class Signal2<T, U> {
	public final class Pair {
		public final T first;
		public final U second;

		private Pair(T t, U u) {
			this.first = t;
			this.second = u;
		}
	}

	private final HashSet<BiConsumer<T, U>> listeners = new HashSet<>();
	private Pair awaitedPair;

	public void addListener(BiConsumer<T, U> listener) { listeners.add(listener); }
	public void removeListener(BiConsumer<T, U> listener) { listeners.remove(listener); }
	public void clearListeners() { listeners.clear(); }

	public void emit(T t, U u) {
		listeners.forEach(listener -> listener.accept(t, u));
		synchronized (this) {
			awaitedPair = new Pair(t, u);
			notify();
		}
	}

	public Pair await() throws InterruptedException {
		synchronized (this) { wait(); }
		return awaitedPair;
	}
}
