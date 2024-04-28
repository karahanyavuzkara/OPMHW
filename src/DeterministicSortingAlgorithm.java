public class DeterministicSortingAlgorithm {

    public static int[] merge(int[] left, int[] right) {
        int[] merged = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;

        // Compare and merge elements from left and right arrays
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                merged[k] = left[i];
                i++;
            } else {
                merged[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < left.length) {
            merged[k] = left[i];
            i++;
            k++;
        }

        while (j < right.length) {
            merged[k] = right[j];
            j++;
            k++;
        }

        return merged;
    }

    public static int[] sort(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }


        int mid = arr.length / 2;


        int[] left = new int[mid];
        int[] right = new int[arr.length - mid];

        System.arraycopy(arr, 0, left, 0, mid);
        System.arraycopy(arr, mid, right, 0, right.length);


        left = sort(left);
        right = sort(right);


        return merge(left, right);
    }

    public static void main(String[] args) {

        int[][] testCases = {
                {5, 2, 9, 1, 5, 6},  // Case 1
                {3, 8, 7, 6, 4},     // Case 2
                {10, 5, 3, 7, 9, 2, 4}, // Case 3
                {1, 1, 1, 1, 1},     // Case 4
                {}                   // Case 5 (empty array)
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] sortedArray = sort(testCases[i]);
            System.out.print("Case " + (i + 1) + ": ");
            for (int num : sortedArray) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
