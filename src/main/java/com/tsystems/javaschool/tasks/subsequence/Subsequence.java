package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.Objects;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Null list has been found");
        }

        int k = 0;

        outerLoop:
        for (int i = 0; i < x.size(); i++) {
            for (int j = k; j < y.size(); j++) {
                if (containsFromIndex(i, y.get(j), x)) {
                    if (Objects.equals(x.get(i), y.get(j))) {
                        k = j + 1;
                        continue outerLoop;
                    } else {
                        return false;
                    }
                }
            }

            return false;
        }

        return true;
    }

    @SuppressWarnings("rawtypes")
    private boolean containsFromIndex(int startIndex, Object yElement, List x) {
        for (int i = startIndex; i < x.size(); i++) {
            if (Objects.equals(yElement, x.get(i))) {
                return true;
            }
        }

        return false;
    }
}
