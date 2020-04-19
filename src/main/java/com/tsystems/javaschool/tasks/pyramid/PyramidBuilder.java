package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        verifyPyramidBuildingPossibility(inputNumbers);
        Collections.sort(inputNumbers);
        ListIterator<Integer> iterator = inputNumbers.listIterator(inputNumbers.size());

        int[][] pyramid = createEmptyMatrix(inputNumbers.size());

        for (int i = pyramid.length - 1; i >= 0; i--) {
            int j = i + 1;
            int k = i + pyramid.length - 1;

            while (j > 0) {
                pyramid[i][k] = iterator.previous();

                j--;
                k = k - 2;
            }
        }

        return pyramid;
    }

    private <T> void verifyPyramidBuildingPossibility(List<T> list) {
        if (list.contains(null)) {
            throw new CannotBuildPyramidException();
        }

        double root = (-1 + Math.sqrt(1 + 8 * list.size())) / 2.0;

        if (root % 1 != 0) {
            throw new CannotBuildPyramidException();
        }
    }

    private int[][] createEmptyMatrix(int elementsCount) {
        int columnsCount = (int) ((-1 + Math.sqrt(1 + 8 * elementsCount)) / 2.0);
        int rowsCount = 2 * columnsCount - 1;

        return new int[columnsCount][rowsCount];
    }
}
