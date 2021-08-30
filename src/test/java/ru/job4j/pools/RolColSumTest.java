package ru.job4j.pools;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RolColSumTest {

    public static int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int count = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = count++;
            }
        }
        return matrix;
    }

    @Test
    public void whenSerialSum() {
        int[][] data = generateMatrix(2);
        RolColSum.Sums[] sums = RolColSum.sum(data);
        int rowSum1 = 3;
        int rowSum2 = 7;
        int colSum1 = 4;
        int colSum2 = 6;
        assertThat(rowSum1, is(sums[0].getRowSum()));
        assertThat(rowSum2, is(sums[1].getRowSum()));
        assertThat(colSum1, is(sums[0].getColSum()));
        assertThat(colSum2, is(sums[1].getColSum()));
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] data = generateMatrix(2);
        RolColSum.Sums[] sums = RolColSum.asyncSum(data);
        int rowSum1 = 3;
        int rowSum2 = 7;
        int colSum1 = 4;
        int colSum2 = 6;
        assertThat(rowSum1, is(sums[0].getRowSum()));
        assertThat(rowSum2, is(sums[1].getRowSum()));
        assertThat(colSum1, is(sums[0].getColSum()));
        assertThat(colSum2, is(sums[1].getColSum()));
    }

    @Test
    public void whenAsyncAndSum() throws ExecutionException, InterruptedException {
        int[][] data = generateMatrix(2);
        RolColSum.Sums[] asyncSums = RolColSum.asyncSum(data);
        RolColSum.Sums[] sums = RolColSum.sum(data);
        assertThat(asyncSums[0].getRowSum(), is(sums[0].getRowSum()));
        assertThat(asyncSums[1].getRowSum(), is(sums[1].getRowSum()));
        assertThat(asyncSums[0].getColSum(), is(sums[0].getColSum()));
        assertThat(asyncSums[1].getColSum(), is(sums[1].getColSum()));
    }

    @Test
    public void whenMatrixWithoutEl() throws ExecutionException, InterruptedException {
        int[][] data = generateMatrix(0);
        RolColSum.Sums[] sums = RolColSum.asyncSum(data);
        assertThat(0, is(sums.length));
    }
}
