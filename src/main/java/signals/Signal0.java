package signals;

import java.util.HashSet;

public final class Signal0 {
	private final HashSet<Runnable> listeners = new HashSet<>();

	public void addListener(Runnable listener) { listeners.add(listener); }
	public void removeListener(Runnable listener) { listeners.remove(listener); }
	public void clearListeners() { listeners.clear(); }

	public void emit() {
		listeners.forEach(Runnable::run);
		synchronized (this) { notify(); }
	}

	public void await() throws InterruptedException {
		synchronized (this) { wait(); }
	}
}
