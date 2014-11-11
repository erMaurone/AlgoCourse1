package lesson2.mergeSort;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 10/11/2014
 * Time: 08:49
 * To change this template use File | Settings | File Templates.
 */
public class MergeSortSimple {
    private static final int CUTOFF = 7;

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        assert isSorted(a, lo, mid);
        // precondition: a[lo..mid] sorted
        assert isSorted(a, mid+1, hi);
        // precondition: a[mid+1..hi] sorted
        for (int k = lo; k <= hi; k++)      // copy array
            aux[k] = a[k];
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++)
        {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
        assert isSorted(a, lo, hi);
        // postcondition: a[lo..hi] sorted
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
    {
        if (hi <= lo + CUTOFF - 1)
        {
            insertionSort(a, lo, hi);
            return;
        }
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid+1, hi);
        if (!less(a[mid+1], a[mid]))   // OPTIMIZATION - the two halves are
            return;                     // already in sorted order no need to merge
        merge(a, aux, lo, mid, hi);
    }
    public static void sort(Comparable[] a)
    {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static boolean less(Comparable v, Comparable w)
    { return v.compareTo(w) < 0; }

    private static boolean isSorted(Comparable[] a, int lo, int hi)
    {
        for (int i = lo; i < hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    public static void insertionSort(Comparable[] a, int lo, int hi) {
        int N = a.length;
        for (int i = lo; i < hi; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
            assert isSorted(a, 0, i);
        }
        assert isSorted(a);
    }

    private static boolean isSorted(Comparable[] a)
    {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
