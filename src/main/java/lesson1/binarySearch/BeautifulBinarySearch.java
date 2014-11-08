package lesson1.binarySearch;

/**
 * First binary search published in 1946; first bug-free one in 1962.
 * Bug in Java's Arrays.binarySearch() discovered in 2006.
 */

public class BeautifulBinarySearch {
        private BeautifulBinarySearch() {
            throw new UnsupportedOperationException();
        }

        public static int binarySearch(int[] a, int key)
        {
            int lo = 0, hi = a.length-1;
            while (lo <= hi)
            {
                int mid = lo + (hi - lo) / 2;
                if (key < a[mid]) hi = mid - 1;
                else if (key > a[mid]) lo = mid + 1;
                else return mid;
            }
            return -1;
        }
}
