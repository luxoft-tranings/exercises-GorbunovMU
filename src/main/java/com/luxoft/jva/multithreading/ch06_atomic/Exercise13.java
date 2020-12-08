package com.luxoft.jva.multithreading.ch06_atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * In this exercise we will play atomic ping-pong again:
 * <ul>
 * <li>Create classes {@link Ping} and {@link Pong} that implements {@link Runnable}.</li>
 * <li>Create class {@link Ball} that has two {@link AtomicInteger} fields ping and pong.</li>
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
public class Exercise13 {

	public static int KOL_ITERATIONS = 100;

	public static void main(String[] args) throws Exception {
		System.out.println("Game starting...");
		// your code goes here
		BallAtomic ballAtomic = new BallAtomic(0, 1);
		Thread pingThread = new Thread(new PingAtomic(ballAtomic));
		Thread pongThread = new Thread(new PongAtomic(ballAtomic));

		pingThread.start();
		pongThread.start();

		pingThread.join();
		pongThread.join();

		System.out.println(ballAtomic);
	}

}

class BallAtomic {
	AtomicInteger ping;
	AtomicInteger pong;

	public BallAtomic(int ping, int pong) {
		this.ping = new AtomicInteger();
		this.pong = new AtomicInteger();
		this.ping.set(ping);
		this.pong.set(pong);
	}

	@Override
	public String toString() {
		return "Ball{" +
				"ping=" + ping +
				", pong=" + pong +
				'}';
	}
}

class PongAtomic implements Runnable {
	private BallAtomic ball;

	PongAtomic(BallAtomic ball) {
		this.ball = ball;
	}

	@Override
	public void run() {
		for (int i = 1; i < Exercise13.KOL_ITERATIONS + 1; i++) {
			while (ball.ping.get() != i) {}
			ball.pong.addAndGet(1);
		}

	}
}


class PingAtomic implements Runnable {
	private BallAtomic ball;

	public PingAtomic(BallAtomic ball) {
		this.ball = ball;
	}

	@Override
	public void run() {
		for (int i = 1; i < Exercise13.KOL_ITERATIONS + 1; i++) {
			while (ball.pong.get() != i) {
			}
			ball.ping.addAndGet(1);
		}
	}
}
