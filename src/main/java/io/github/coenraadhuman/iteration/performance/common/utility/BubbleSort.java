package io.github.coenraadhuman.iteration.performance.common.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class BubbleSort {

    public static void sort(char[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 1; j < array.length - i; j++) {
                if (array[j - 1] > array[j]) {
                    swap(array, j, j - 1);
                }
            }
        }
    }

    private static void swap(char[] array, int a, int b) {
        var temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

}