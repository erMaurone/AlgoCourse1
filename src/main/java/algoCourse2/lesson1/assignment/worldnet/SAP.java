package algoCourse2.lesson1.assignment.worldnet;

import princeton.algs4.Digraph;
import princeton.algs4.DirectedCycle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 09/12/2014
 * Time: 16:13
 */

public class SAP {
    private static final int ERROR_CODE = -1;
    private static SimpleBFS bfs;
    private Set<Integer> roots = new TreeSet< Integer >();
    private boolean isCyclic;
    Map<Question,Answer> faq = new HashMap<Question,Answer>();
    private Digraph dg;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new NullPointerException("Digraph expected");
        dg = new Digraph(G);
        DirectedCycle cycleFinder = new DirectedCycle(dg);
        isCyclic =cycleFinder.hasCycle();
        if ( isCyclic) return;
        findRoots(G);
        dg = dg.reverse();
    }

    private void findRoots(Digraph dig) {
        boolean[] marked = new boolean[dig.V()];
        outer:
        for (int i = 0; i < marked.length; i++) {
            for (int v : dig.adj(i)) {
                marked[i] = true;
                continue outer;
            }
            if (marked[i] == false ) {
                Digraph test = new Digraph(dig.reverse());
                for (int y: test.adj(i)) {
                    roots.add(i);
                    continue outer;
                }
            }
        }
    }

    // ancestor of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if(isCyclic) return ERROR_CODE;
        Answer a = answer(new Question(v,w));
        return a.length();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if(isCyclic) return ERROR_CODE;
        Answer a = answer(new Question(v,w));
        return a.ancestor();
    }

    private Answer answer(Question q) {
        Answer a;
        if (faq.containsKey(q) ) {
            a = faq.get(q);
        }  else {
            a = new Answer(q);
            faq.put(q, a);
        }
        return a;
    }

    // ancestor of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int  length(Iterable<Integer> v, Iterable<Integer> w) {
        if(v == null || w == null) throw new NullPointerException("arguments cannot be null");
        if(isCyclic) return ERROR_CODE;
        int shortest=ERROR_CODE;
        for (int a : v)
            for(int b : w) {
                int tmp = length(a,b);
                if (tmp != ERROR_CODE && tmp < shortest || shortest == ERROR_CODE )
                    shortest = tmp;
            }
        return shortest;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if(v == null || w == null) throw new NullPointerException("arguments cannot be null");
        if(isCyclic) return ERROR_CODE;
        int shortest=ERROR_CODE, ancestor = ERROR_CODE;
        for (int a : v)
            for(int b : w) {
                int tmp = length(a,b);
                if (tmp != ERROR_CODE && tmp < shortest || shortest == ERROR_CODE) {
                    shortest = tmp;
                    ancestor= ancestor(a,b);
                }
            }
        return ancestor;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        String NEWLINE = System.lineSeparator();
        output.append(dg.reverse() + NEWLINE);
        output.append(bfs + NEWLINE);
        output.append("roots ");
        for (int i : roots) output.append(i + ", ");
        output.append(NEWLINE);
        return output.toString();
    }

    private class Question {
        private final int v, w;
        Question(int v, int w) {this.v =v; this.w = w;}

        @Override
        public boolean equals(Object o) {
            boolean equalityTest = false;
            if (!(o instanceof Question)) return equalityTest;
            Question that = (Question) o;
            if (this.v == that.v && this.w == that.w) equalityTest = true;
            return equalityTest;
        }
    }

    private class Answer {
        private int ancestor, length;
        Answer(Question q) {
            this.ancestor = this.length = ERROR_CODE;
            checkDirectConnection(q.v, q.w);
            if( ! hasDirectConnection() )
                findAnswer(q.v, q.w);
        }
        private int ancestor() { return this.ancestor; }
        private int length() { return this.length; }

        private void checkDirectConnection(int v, int w) {
            if (v == w) {
                this.length = 0;
                this.ancestor = v;
                return;
            }
            for (int x : dg.adj(v))
                if (x == w) {
                    this.length = 1;
                    this.ancestor = v;
                    return;
                }
            for (int x : dg.adj(w))
                if ( x == v) {
                    this.length = 1;
                    this.ancestor = w;
                    return;
                }
        }

        private boolean hasDirectConnection() {
            return this.length() != ERROR_CODE && this.ancestor != ERROR_CODE;
        }

        private void findAnswer(int v, int w) {
            int champDist = ERROR_CODE, champAncestor = ERROR_CODE;
            bfs = new SimpleBFS(dg, roots);
            if (bfs.hasPathTo(v) && bfs.hasPathTo(w)) {
                for (int x : bfs.pathTo(v)) {
                    if (x == v) continue;
                    SimpleBFS b = new SimpleBFS(dg, x);
                    if (!b.hasPathTo(w)) continue;
                    int dist = b.distTo(v) + b.distTo(w);
                    if (champDist == ERROR_CODE || (dist > ERROR_CODE && dist < champDist) ) {
                        champDist = dist;
                        champAncestor = x;
                    }
                }
            }
            if(this.length() == ERROR_CODE || (champDist != ERROR_CODE && champDist < this.length()) ) {
                this.length = champDist;
                this.ancestor = champAncestor;
            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {}
}
