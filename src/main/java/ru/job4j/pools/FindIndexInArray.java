package ru.job4j.pools;

import java.util.concurrent.RecursiveTask;

public class FindIndexInArray<T>  extends RecursiveTask<Integer> {
    private final T[] array;
    private final T searchObject;
    int startIndex, endIndex;

    public FindIndexInArray(T[] array, T searchObject, int startIndex, int endIndex) {
        this.array = array;
        this.searchObject = searchObject;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    protected Integer compute() {
        if (endIndex - startIndex > 10) {
            int middleIndex = startIndex + (endIndex - startIndex)  / 2;
            FindIndexInArray<T> first = new FindIndexInArray<>(array, searchObject, startIndex, middleIndex);
            FindIndexInArray<T> second = new FindIndexInArray<>(array, searchObject, middleIndex, endIndex);
            first.fork();
            second.fork();
            return merge(first.join(), second.join());
        }
        return linearSearch();
    }

    private int linearSearch() {
        for (int i = startIndex; i <= endIndex; i++) {
            if (array[i] == searchObject) {
                return i;
            }
        }
        return -1;
    }

    private int merge(int first, int second) {
        if (first == -1 && second == -1) {
            return -1;
        } else if (first == -1) {
            return second;
        } else {
            return first;
        }
    }
}
