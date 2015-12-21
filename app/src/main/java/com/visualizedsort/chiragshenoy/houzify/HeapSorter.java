package com.visualizedsort.chiragshenoy.houzify;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Chirag Shenoy on 21-Dec-15.
 */
public class HeapSorter implements Sorter {

    NumberList n;
    private static int N;
    public Context context;
    int[] arr;

    private ArrayList<SortObserver> observers = new ArrayList<SortObserver>();
    private Position ballPosition = new Position();

    public void registerObserver(SortObserver observer) {
        observers.add(observer);
    }

    public void notifyListeners() {
        for (SortObserver observer : observers) {
            observer.funcX(ballPosition);
        }
    }

    public Position doSomethingWithSortPosition() {
        //bounce etc
        notifyListeners();
        return ballPosition;
    }


    public HeapSorter(Context context, int[] arr) {
        this.context = context;
        this.arr = arr;
    }

    public void sort() {
        heapify(arr);
        for (int i = N; i > 0; i--) {
            swap(arr, 0, i);
            N = N - 1;
            maxheap(arr, 0);
        }
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
            this.swap(arr, i, max);
            maxheap(arr, max);
        }
    }

    /* Function to swap two numbers in an array */
    public void swap(int arr[], int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        int[] temp = {i, j};
        ballPosition.setPos(temp);
    }

    public int[] getSortedArray() {
        sort();
        return arr;
    }


}
