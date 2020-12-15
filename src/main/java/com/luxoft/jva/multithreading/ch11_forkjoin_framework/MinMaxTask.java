package com.luxoft.jva.multithreading.ch11_forkjoin_framework;

import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MinMaxTask extends RecursiveTask<MinMax> {
    private static final int MINIMAL_COUNT = 30_000;

    private MinMax minMax;
    private int low;
    private int high;

    private int[] data;

    public MinMaxTask(MinMax minMax, int low, int high, int[] data) {
        this.minMax = minMax;
        this.low = low;
        this.high = high;
        this.data = data;
    }

    public MinMaxTask(MinMax minMax, int[] data) {
        this(minMax, 0, data.length, data);
    }

    @Override
    protected MinMax compute() {
        int delta = high - low;

        if (delta <= MINIMAL_COUNT) {
            calculate();
        } else {
            forkAndCalculate(delta);
        }
        return minMax;
    }

    private void forkAndCalculate(int delta) {
        System.out.println("Fork and calculating... " +  Thread.currentThread().getName());
        int mid = low + (delta) / 2;

        MinMaxTask left = new MinMaxTask(minMax, low, mid, data);
        MinMaxTask right = new MinMaxTask(minMax, mid, high, data);

        ForkJoinTask.invokeAll(left,right);
        right.join();
        left.join();
    }

    private void calculate() {
        System.out.println("Calculating... " + Thread.currentThread().getName());
        for (int i = 0; i < data.length; i++) {
            if (data[i] > minMax.getMax()) {
                minMax.setMax(data[i]);
            }
            if (data[i] < minMax.getMin()) {
                minMax.setMin(data[i]);
            }
        }

    }


}

class MinMax {
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "MinMax{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
