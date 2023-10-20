package signals;

/**
 * Every time you press Enter, these two lines should print:
 * 
 * <pre>
 * addListener received a message<br>awaitThread received a message
 * </pre>
 * 
 * If so, {@link Signal0} is working as intended. The order of the lines is
 * deterministic due to the implementation of {@link Signal0#emit}.
 * <p>
 * Pressing Ctrl+D should cause the main thread to exit the loop, and terminate
 * normally. Prefer this method over Ctrl+C.
 */
final class Signal0Test {
	private static final Signal0 msgReceived = new Signal0();

	private static void awaitThread() {
		while (true) {
			try {
				msgReceived.await();
				System.out.println("awaitThread received a message");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		msgReceived.addListener(() -> System.out.println("addListener received a message"));
		Thread.ofVirtual().start(Signal0Test::awaitThread);
		System.out.println("Press Ctrl+D to stop the test");
		final var console = System.console();
		while (console.readLine() != null) {
			msgReceived.emit();
		}
		System.out.println("Test passed");
	}

	private Signal0Test() {
	}
}
