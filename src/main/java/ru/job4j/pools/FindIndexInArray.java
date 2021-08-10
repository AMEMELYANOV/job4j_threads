package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class FindIndexInArray<T>  extends RecursiveTask<Integer> {
    private final T[] array;
    private final T searchObject;

    public FindIndexInArray(T[] array, T searchObject) {
        this.array = array;
        this.searchObject = searchObject;
    }

    @Override
    protected Integer compute() {
        if (array.length > 10) {
            int mid = array.length / 2;
            FindIndexInArray<T> first = new FindIndexInArray<>(
                Arrays.copyOfRange(array, 0, mid), searchObject);
            FindIndexInArray<T> second = new FindIndexInArray<>(
                Arrays.copyOfRange(array, mid, array.length), searchObject);
            first.fork();
            second.fork();
            return merge(first.join(), second.join(), mid);
        }
        return linearSearch();
    }

    private int linearSearch() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == searchObject) {
                return i;
            }
        }
        return -1;
    }

    private int merge(int first, int second, int mid) {
        if (first == -1 && second == -1) {
            return -1;
        } else if (first == -1) {
            return second + mid;
        } else {
            return first;
        }
    }
}
