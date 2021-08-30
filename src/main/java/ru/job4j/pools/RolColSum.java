package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
            }
            int colSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                colSum += matrix[j][i];
            }
            Sums sums = new Sums();
            sums.setRowSum(rowSum);
            sums.setColSum(colSum);
            rsl[i] = sums;
        }
        return rsl;
    }

        public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
            Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
            for (int i = 0; i < matrix.length; i++) {
                futures.put(i, getTask(matrix, i));
            }
            for (Integer key : futures.keySet()) {
                rsl[key] = futures.get(key).get();
            }
        return rsl;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sum = new Sums();
            int rowSum = 0;
            for (int i = 0; i < matrix[index].length; i++) {
                rowSum += matrix[index][i];
            }
            int colSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                colSum += matrix[i][index];
            }
            sum.setRowSum(rowSum);
            sum.setColSum(colSum);
            return sum;
        });
    }
}