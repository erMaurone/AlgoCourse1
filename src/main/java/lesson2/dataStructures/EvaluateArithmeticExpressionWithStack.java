package lesson2.dataStructures;

import princeton.stdlib.StdIn;
import princeton.stdlib.StdOut;

/**
 * Created with IntelliJ IDEA.
 * User: mauro
 * Date: 07/11/2014
 * Time: 23:25
 * To change this template use File | Settings | File Templates.
 */
public class EvaluateArithmeticExpressionWithStack {

    // can evaluate expression such as:
    // ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )
        public static void main(String[] args)
        {
            // ArrauStackGenerics implementation is a fixed stack one
            // you need to either guess the size or make it resizable
            ArrayStackGenerics<String> ops = new ArrayStackGenerics<String>(40);
            ArrayStackGenerics<Double> vals = new ArrayStackGenerics<Double>(40);
            while (!StdIn.isEmpty()) {
                String s = StdIn.readString();
                if (s.equals("(")) ;
                else if (s.equals("+")) ops.push(s);
                else if (s.equals("*")) ops.push(s);
                else if (s.equals(")"))
                {
                    String op = ops.pop();
                    if (op.equals("+")) vals.push(vals.pop() + vals.pop());
                    else if (op.equals("*")) vals.push(vals.pop() * vals.pop());
                }
                else vals.push(Double.parseDouble(s));
            }
            StdOut.println(vals.pop());
        }
}
