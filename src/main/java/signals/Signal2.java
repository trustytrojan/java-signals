package signals;

import java.util.HashSet;
import java.util.function.BiConsumer;

public class Signal2<T, U> {
	public final class Values {
		public final T first;
		public final U second;

		private Values(final T t, final U u) {
			this.first = t;
			this.second = u;
		}
	}

	private final HashSet<BiConsumer<T, U>> listeners = new HashSet<>();
	private Values awaitedValues;

	public void addListener(final BiConsumer<T, U> listener) { listeners.add(listener); }
	public void removeListener(final BiConsumer<T, U> listener) { listeners.remove(listener); }
	public void clearListeners() { listeners.clear(); }

	public void emit(final T t, final U u) {
		listeners.forEach(listener -> listener.accept(t, u));
		synchronized (this) {
			awaitedValues = new Values(t, u);
			notify();
		}
	}

	public Values await() throws InterruptedException {
		synchronized (this) { wait(); }
		return awaitedValues;
	}
}
