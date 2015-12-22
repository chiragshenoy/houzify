package com.visualizedsort.chiragshenoy.houzify;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Chirag Shenoy on 21-Dec-15.
 */
public class HeapSorter implements Sorter {

    NumberList n;
    private static int N;
    public Context context;
    private int[] arr;
    Timer noMovementTimer;
    SortObserver sortObserver;
    int i, j;
    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();

    public HeapSorter(SortObserver s, Context context, int[] arr) {
        this.context = context;
        this.arr = arr;
        this.sortObserver = s;
    }

    public void sort() {
        heapify(arr);

        i = N;
        noMovementTimer = new Timer();
        noMovementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sortObserver.callback(0, i);
                swap(arr, 0, i);
                N = N - 1;
                maxheap(arr, 0);
                i--;

                if (i == 0)
                    noMovementTimer.cancel();

            }
        }, 0, 1000);//put here time 1000 milliseconds=1 second


//        for (int i = N; i > 0; i--) {
//            sortObserver.callback(0, i);
//            swap(arr, 0, i);
//            N = N - 1;
//            maxheap(arr, 0);
//        }


    }

    public void heapify(int arr[]) {
        N = arr.length - 1;
        for (int i = N / 2; i >= 0; i--)
            this.maxheap(arr, i);
    }

    /* Function to swap largest element in heap */
    public void maxheap(int arr[], int i) {
        int left = 2 * i;
        int right = 2 * i + 1;
        int max = i;
        if (left <= N && arr[left] > arr[i])
            max = left;
        if (right <= N && arr[right] > arr[max])
            max = right;

        if (max != i) {
            sortObserver.callback(i, max);
            this.swap(arr, i, max);
            maxheap(arr, max);
        }
    }

    /* Function to swap two numbers in an array */
    public void swap(int arr[], int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//
//                sortObserver.callback(i, j);
//
//            }
//        }, 1000);


//        Log.e("Swap being called", "" + i + " and " + j);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // this code will be executed after 2 seconds
//                sortObserver.callback(i, j);
//
//            }
//        }, 2000);
    }

    public int[] getSortedArray() {
        sort();
        return arr;
    }

}
