package lessonOne.unionFind;


public class QuickFindUF {
    private int[] id;
    public QuickFindUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    public boolean connected(int p, int q)
    { return id[p] == id[q]; }

    public void union(int p, int q)
    {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++)
            if (id[i] == pid) id[i] = qid;
    }

    public void publishStack(){
        for (int i =0; i< id.length; i++) {
            System.out.println("id = " + i);
            System.out.println("key = " + id[i]);
        }
    }

    public static void main(String... args) {
        int elementsHalfSize = 20;
        QuickFindUF quickFind = new QuickFindUF(elementsHalfSize * 2);
        for(int i = 0; i < elementsHalfSize; i++) {
            quickFind.union(i, elementsHalfSize-i);
        }
        int connectedCount = 0;
        for(int i = 0;i < elementsHalfSize*2;i++) {
            for(int j=i+1; j< elementsHalfSize*2;j++)
                if ( quickFind.connected(i, j))
                    connectedCount++;
        }
        quickFind.publishStack();
        System.out.println("connected pairs " + connectedCount);
    }
}
