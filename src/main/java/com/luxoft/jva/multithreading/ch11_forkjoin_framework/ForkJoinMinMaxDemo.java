package com.luxoft.jva.multithreading.ch11_forkjoin_framework;

import org.junit.Test;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ForkJoinMinMaxDemo {
    private static final int LOTS_OF_OBJECTS = 100_000;

//    private int[] DATA = {0,1,2,3,4,5,6,7,8,9};

    private static final int[] DATA = IntStream
            .range(0, LOTS_OF_OBJECTS)
            .toArray();

    MinMax minMax = new MinMax();



    @Test
    public void runUsingForkJoinPool() throws Exception
    {

        new ForkJoinPool().invoke(new MinMaxTask(minMax, DATA));

        System.out.println(minMax);
    }



}
