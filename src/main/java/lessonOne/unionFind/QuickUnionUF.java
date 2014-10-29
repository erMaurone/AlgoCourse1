package lessonOne.unionFind;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 29/10/2014
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public class QuickUnionUF {

    private int[] id;

    public QuickUnionUF(final int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) id[i] = i;
    }

    private int root(int i) {
        while (i != id[i]) {
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        id[i] = j;
    }

    public void publishStack() {
        for (int i = 0; i < id.length; i++) {
            System.out.println("id = " + i + " key = " + id[i]);
        }
    }

    public static void main(String... args) {
        int elementsHalfSize = 20;
        QuickUnionUF quickUnionUF = new QuickUnionUF(elementsHalfSize * 2);
        for (int i = 0; i < elementsHalfSize; i++) {
            quickUnionUF.union(i, elementsHalfSize - i);
        }
        int connectedCount = 0;
        for (int i = 0; i < elementsHalfSize * 2; i++) {
            for (int j = i + 1; j < elementsHalfSize * 2; j++)
                if (quickUnionUF.connected(i, j))
                    connectedCount++;
        }
        quickUnionUF.publishStack();
        System.out.println("connected pairs " + connectedCount);
    }
}
