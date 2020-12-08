package com.luxoft.jva.multithreading.ch06_atomic;

/**
 * In this exercise we will play volatile ping-pong:
 * <ul>
 * <li>Create classes {@link Ping} and {@link Pong} that implements {@link Runnable}.</li>
 * <li>Create class {@link Ball} that has two volatile fields ping and pong.</li>
 * </ul>
 * <p>
 * <p>
 * In loop
 * {@link Ping}:
 * <ul>
 * <li>Increase ping value by 1</li>
 * <li>Do nothing while current step != pong</li>
 * </ul>
 * <p>
 * <p>
 * {@link Pong}:
 * <ul>
 * <li>Do nothing while ping != current step</li>
 * <li>Increase pong value by 1</li>
 * </ul>
 *
 * @author BKuczynski.
 */
public class Exercise12 {

	public static int KOL_ITERATIONS = 100;

	public static void main(String[] args) throws Exception {
		System.out.println("Game starting...");
		// your code goes here
		Ball ball = new Ball(0, 1);
		Thread pingThread = new Thread(new Ping(ball));
		Thread pongThread = new Thread(new Pong(ball));

		pingThread.start();
		pongThread.start();

		pingThread.join();
		pongThread.join();

		System.out.println(ball);
	}

}

class Ball {
	volatile int ping;
	volatile int pong;

	public Ball(int ping, int pong) {
		this.ping = ping;
		this.pong = pong;
	}

	@Override
	public String toString() {
		return "Ball{" +
				"ping=" + ping +
				", pong=" + pong +
				'}';
	}
}

class Pong implements Runnable {
	private Ball ball;

	Pong(Ball ball) {
		this.ball = ball;
	}

	@Override
	public void run() {
		for (int i = 1; i < Exercise12.KOL_ITERATIONS + 1; i++) {
			while (ball.ping != i) {}
			ball.pong ++;
		}

	}
}


class Ping implements Runnable {
	private Ball ball;

	public Ping(Ball ball) {
		this.ball = ball;
	}

	@Override
	public void run() {
		for (int i = 1; i < Exercise12.KOL_ITERATIONS + 1; i++) {
			while (ball.pong != i) {}
			ball.ping ++;
		}
	}
}
