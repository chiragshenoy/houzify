package com.visualizedsort.chiragshenoy.houzify;

import java.util.Observer;

/**
 * Created by Chirag Shenoy on 21-Dec-15.
 */
public interface SortObserver extends Observer {

    void onArrayUpdate();
}
