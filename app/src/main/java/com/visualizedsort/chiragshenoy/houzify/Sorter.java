package com.visualizedsort.chiragshenoy.houzify;

/**
 * Created by Chirag Shenoy on 21-Dec-15.
 */
public interface Sorter {

    int[] getSortedArray();

    public void registerObserver(SortObserver observer);

    public Position doSomethingWithSortPosition();
}
