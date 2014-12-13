package algoCourse2.lesson1.assignment.worldnet;

import princeton.algs4.Digraph;
import princeton.algs4.DirectedCycle;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 12/12/2014
 * Time: 16:18
 */
public class WordNet {
    private Map<Integer, String> synsets = new HashMap<Integer, String>();
    private Map<String, Set<Integer>> words = new HashMap<String,Set<Integer>>();
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsetsFile, String hypernymsFile) {
        if (synsetsFile == null ) throw new NullPointerException("Expected synset file");
        if (hypernymsFile == null) throw new NullPointerException("Expected hypernyms file");

        parseSynsets(synsetsFile);
        Digraph G= parseHypernyms(hypernymsFile);
        if (! isRootedDAG(G))
            throw new IllegalArgumentException("Digraph resulting from input files is not a rooted DAG");
        sap = new SAP(G);
    }

    private boolean isRootedDAG(Digraph g) {
        boolean validity = false;
        if(!isAcyclic(g)) return validity;
        int rootCount = rootCount(g);
        if (rootCount == 1 )
            validity = true;
        return validity;
    }

    private boolean isAcyclic(Digraph dg) {
        DirectedCycle cycleFinder = new DirectedCycle(dg);
        return ! cycleFinder.hasCycle();
    }

    private int rootCount(Digraph dig) {
        boolean[] marked = new boolean[dig.V()];
        Set<Integer> roots = new TreeSet< Integer >();
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
        return roots.size();
    }

    private Digraph parseHypernyms(String fileName) {
        int lineCount=0;
        Path filePath = Paths.get(fileName);
        Digraph g = new Digraph(synsets.size());
        try( BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8) ) {
            String line;
            while((line = reader.readLine()) != null ) {
                lineCount++;
                processHyperLine(line, g);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Processed " + fileName + ": " + lineCount + " lines");
        return g;
    }

    private void processHyperLine(String line, Digraph g) {
        String[] vertexes = line.split(",");
        int v = Integer.valueOf(vertexes[0]);
        for (int i = 1; i < vertexes.length; i++) {
            int w = Integer.valueOf(vertexes[i]);
            g.addEdge(v, w);
        }
    }

    private void parseSynsets(String fileName) {        
        Path filePath = Paths.get(fileName);
        int lineCount=0;
        try( BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8) ) {
            String line;
            while((line = reader.readLine()) != null ) {
                lineCount++;
                processSynsetLine(line);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Processed " + fileName + ": " + lineCount + " lines.");
    }

    private void processSynsetLine(String line) {
        String[] columns = line.split(",");
        Integer code = Integer.valueOf(columns[0]);
        String synset = columns[1];
        if (synsets.containsKey(code)) {
            throw new IllegalStateException("Found duplicate entry for entry: " + line);
        }
        synsets.put(code, synset);
        String[] nouns = synset.split("\\s");
        for (String noun : nouns) {
            if (words.containsKey(noun)) {
                Set<Integer> codes = words.get(noun);
                if (codes.contains(code) ) continue;
                codes.add(code);
            } else {
                Set<Integer> codes = new TreeSet<Integer>();
                codes.add(code);
                words.put(noun, codes);
            }
        }    
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
          return words.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new NullPointerException("unexpected null parameter");
        return words.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if( nounA == null || nounB == null) throw new NullPointerException("unexpected null parameter");
        if (!words.containsKey(nounA) || ! words.containsKey(nounB)) throw new IllegalArgumentException("received a non WorldNet world");

        Set synSetsFrom = words.get(nounA);
        Set synSetsTo = words.get(nounB);
        int distance = sap.length(synSetsFrom, synSetsTo);
        return distance;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if( nounA == null || nounB == null) throw new NullPointerException("unexpected null parameter");
        if (!words.containsKey(nounA) || ! words.containsKey(nounB)) throw new IllegalArgumentException("received a non WorldNet world");

        Set source = words.get(nounA);
        Set destination = words.get(nounB);
        int ancestorCode = sap.ancestor(source, destination);
        String ancestorSynset = synsets.get(ancestorCode);
        return ancestorSynset;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String hypernymsFile = "src/main/resources/algo2/lesson1/hypernyms.txt";
        String synsetsFile = "src/main/resources/algo2/lesson1/synsets.txt";
        WordNet wn = new WordNet(synsetsFile, hypernymsFile);
        System.out.println(wn.isNoun("bird"));
        System.out.println( wn.sap("bird","plane"));
        System.out.println(wn.distance("bird", "sex"));
    }
}
