package io.kzw.advance.main;

import java.util.Arrays;

public class Sorts {

    public static void main(String[] args) {
        int[] arr = new int[]{5, 4, 3, 2, 1};
        mergeSort(arr);
    }

    private static void bubbleSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println("bubble sort = " + Arrays.toString(arr));
    }

    private static void selectSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            int minIndex = i;
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
            }
        }
        System.out.println("select sort = " + Arrays.toString(arr));
    }

    private static void insertSort(int[] arr) {
        int len = arr.length;
        int temp;
        for (int i = 1; i < len; i++) {
            if (arr[i] < arr[i - 1]) {
                temp = arr[i];
                int j = i - 1;
                for (; j >= 0 && arr[j] > temp; j--) {
                    arr[j + 1] = arr[j];
                }
                arr[j + 1] = temp;
            }
        }
        System.out.println("insert sort = " + Arrays.toString(arr));
    }

    private static void shellSort(int[] arr) {
        int len = arr.length;
        int temp;
        for (int gap = len / 2; gap > 0; gap /= 2) {
            for (int i = 0; i < len; i += gap) {
                temp = arr[i];
                int j = i;
                for (; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
        System.out.println("shell sort = " + Arrays.toString(arr));
    }

    private static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        System.out.println("quick sort = " + Arrays.toString(arr));
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int p = partation(arr, left, right);
            quickSort(arr, left, p - 1);
            quickSort(arr, p + 1, right);
        }
    }

    private static int partation(int array[], int left, int right) {
        int pv = array[left];
        int p = left;
        for (int i = p + 1; i <= right; i++) {
            if (array[i] < pv) {
                p++;
                if (p != i) {
                    int temp = array[p];
                    array[p] = array[i];
                    array[i] = temp;
                }
            }
        }
        array[left] = array[p];
        array[p] = pv;
        return p;
    }

    private static void mergeSort(int array[]) {
        int len = array.length;
        int temp[] = new int[len];
        mergeSort(array, temp, 0, len - 1);
        System.out.println("merge sort = " + Arrays.toString(array));
    }

    private static void mergeSort(int array[], int temp[], int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, temp, left, mid);
            mergeSort(array, temp, mid + 1, right);
            merge(array, temp, left, mid, right);
        }
    }

    private static void merge(int array[], int temp[], int left, int mid, int right) {
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
        }
        int pa = left;
        int pb = mid + 1;
        int index = left;

        while (pa <= mid && pb <= right) {
            if (temp[pa] <= temp[pb]) {
                array[index++] = temp[pa++];
            } else {
                array[index++] = temp[pb++];
            }
        }
        while (pa <= mid) {
            array[index++] = temp[pa++];
        }
        while (pb <= right) {
            array[index++] = temp[pb++];
        }
    }
}
