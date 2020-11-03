package com.snmi.util;

/**
 * Util class - CapacityHandler
 * It returns a capacity that will be used when a new collection has been initialized.
 * Makes compatible list size with default collection load factor.
 * Ensures that the size of the list will be close to what it is expected to be
 * and will not be automatically expanded.
 * @version 0.0.1
 */
public class CapacityHandler {

    private CapacityHandler() {

    }

    public static int getCapacity(int expectedCapacity) {
        return (int) (expectedCapacity + (expectedCapacity * 0.35));
    }

}
