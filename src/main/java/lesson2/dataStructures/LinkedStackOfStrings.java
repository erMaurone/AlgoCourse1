package lesson2.dataStructures;
/**
*Proposition.
*
*   Every operation takes constant time in the worst case.
*Proposition.
*  A stack with N items uses ~40N bytes.bytes
* 16 bytes class overhead + 8 bytes inner class overhead + 8 bytes String ref. + 8 bytes Node ref = ~40bytes per element
*
* Created with IntelliJ IDEA.
* User: mauro
* Date: 07/11/2014
* Time: 22:44
* LIFO - Adding at the top and removing from the top
*/


public class LinkedStackOfStrings {

    private Node first = null;

    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(String item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public String pop() {
        String item = first.item;
        first = first.next;
        return item;
    }
}
