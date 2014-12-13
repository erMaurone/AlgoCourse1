package algoCourse2.lesson1.assignment.worldnet;

import org.junit.Assert;
import org.junit.Test;
import princeton.algs4.Digraph;
import princeton.stdlib.In;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 09/12/2014
 * Time: 16:16
 */

public class SAPTest {

    @Test(expected = NullPointerException.class )
    public void testNullArgToConstructor() { SAP sap = new SAP(null); }

    @Test
    public void testImmutability() {
        In inputFile= new In("src/main/resources/algo2/lesson1/digraph2.txt");
        Digraph directedGraph = new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        int l = sap.length(2,3);
        int a = sap.ancestor(2, 3);
        directedGraph.addEdge(3,5);
        Assert.assertEquals("length",l, sap.length(2,3));
        Assert.assertEquals("ancestor",a,sap.ancestor(2,3));
    }

    @Test(expected = NullPointerException.class) public void testNullArgsAncestors() {
        In inputFile= new In("src/main/resources/algo2/lesson1/digraph1.txt");
        Digraph directedGraph = new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        sap.ancestor(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testNullArgsLengths() {
        In inputFile= new In("src/main/resources/algo2/lesson1/digraph1.txt");
        Digraph directedGraph = new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        sap.length(null, null);
    }

    @Test public void testLengthsAncestor() throws Exception {
        In inputFile= new In("src/main/resources/algo2/lesson1/digraph1.txt");
        Digraph directedGraph = new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        Integer[]v = {7,4,3,9};
        Integer[]w = {8,9,10,12};
        Iterable<Integer> V = Arrays.asList(v);
        Iterable<Integer> W = Arrays.asList(w);
        int l = sap.length(V,W);
        int a = sap.ancestor(V,W);

        Assert.assertEquals("length",0, l);
        Assert.assertEquals("ancestor",9, a);
    }


    @Test
    public void testLengthAncestor1() throws Exception {
        In inputFile= new In("src/main/resources/algo2/lesson1/digraph1.txt");
        Digraph directedGraph = new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        int l = sap.length(3,11);
        int a = sap.ancestor(3,11);
        Assert.assertEquals("ancestor",1, a);
        Assert.assertEquals("length",4, l);
    }

    @Test
    public void testLengthAncestor2() throws Exception {
        In inputFile= new In("src/main/resources/algo2/lesson1/digraph2.txt");
        Digraph directedGraph = new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        int l = sap.length(5,1);
        int a = sap.ancestor(5,1);
        Assert.assertEquals("ancestor",0, a);
        Assert.assertEquals("length",2, l);
    }

    @Test
    public void testLengthAncestor3() throws Exception {
        In inputFile= new In("src/main/resources/algo2/lesson1/digraph3.txt");
        Digraph directedGraph = new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        int l = sap.length(5,1);
        int a = sap.ancestor(5,1);
        Assert.assertEquals("ancestor",-1, a);
        Assert.assertEquals("length",-1, l);
    }

    @Test
    public void testLengthAncestor4() throws Exception {

        In inputFile= new In("src/main/resources/algo2/lesson1/digraph4.txt");
        Digraph directedGraph = new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        int l = sap.length(1,0);
        int a = sap.ancestor(1,0);
        Assert.assertEquals("ancestor",8, a);
        Assert.assertEquals("length",3, l);
    }

    @Test
    public void testLengthAncestor5() throws Exception {
        In inputFile= new In("src/main/resources/algo2/lesson1/digraph5.txt");
        Digraph directedGraph= new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        int l = sap.length(7,21);
        int a = sap.ancestor(7,21);
        Assert.assertEquals("ancestor",9, a);
        Assert.assertEquals("length",4, l);
    }

    @Test
    public void testLengthAncestor6() throws Exception {
        In inputFile= new In("src/main/resources/algo2/lesson1/digraph6.txt");
        Digraph directedGraph = new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        int l = sap.length(5,2);
        int a = sap.ancestor(5,2);
        Assert.assertEquals("ancestor",4, a);
        Assert.assertEquals("length",3, l);
    }

    @Test
    public void testLengthAncestor9() throws Exception {
        In inputFile= new In("src/main/resources/algo2/lesson1/digraph9.txt");
        Digraph directedGraph = new Digraph(inputFile);
        SAP sap = new SAP(directedGraph);
        int l = sap.length(7,4);
        int a = sap.ancestor(7,4);
        Assert.assertEquals("ancestor",-1, a);
        Assert.assertEquals("length",-1, l);
    }
}
