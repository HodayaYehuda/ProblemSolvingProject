import java.util.HashMap;
import java.util.LinkedList;

//import static helperFuction.*;

public class DFID {


    public static int counter = 0;
    public static node _ans = null;

    public static boolean DFID_f() {

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            HashMap h_ = new HashMap();
            //send to the the recursive function with i
            String result = Limited_DFS(new node(helperFuction._startState), new node(helperFuction._goalState), i, h_);
            if (!result.equals("cutoff")) {
                return true;
            }
        }
        return false;
    }


    //recursive function
    public static String Limited_DFS(node n, node goal, int limit, HashMap h_) {
        if (n.toString().equals(goal.toString())) {
            _ans = n;
            return "";
        }

        //arrived to the cutoff

        else if (limit == 0) {
            return "cutoff";
        }

        else {

            h_.put(n.toString(), n);
            boolean isCutoff = false;


            LinkedList<node> N = helperFuction.allowedOperators(n);

            //send to the recursive for every "son"
            for (int i = 0; i < N.size(); i++) {
                node g = N.get(i);
                counter++;
                if (h_.containsKey(g.toString())) continue;
                String result = Limited_DFS(g, goal, limit - 1, h_);

                if (result.equals("cutoff")) {
                    isCutoff = true;
                }

                else if (!result.equals("fail")) {
                    return result;
                }
            }
            helperFuction.H.remove(n);
            if (isCutoff) {
                return "cutoff";
            } else {

                return "fail";
            }

        }
    }

    public static boolean DFID_f_withOpen() {

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            HashMap <String, node> h_ = new HashMap<String, node>();
            String result = Limited_DFS_withOpen(new node(helperFuction._startState), new node(helperFuction._goalState), i, h_);
            if (!result.equals("cutoff")) {
                return true;
            }
        }
        return false;
    }


    public static String Limited_DFS_withOpen(node n, node goal, int limit, HashMap h_) {

        System.out.println("___________________");
        System.out.println("state: ");
        helperFuction.print(n.state);
        System.out.println("List of Allowed Operators: ");


        if (n.toString().equals(goal.toString())) {
            _ans = n;
            return "";
        }

        else if (limit == 0) {
            //   System.out.println("limit is 0");
            return "cutoff";
        }

        else {
            h_.put(n.toString(), n);
            boolean isCutoff = false;

            LinkedList<node> N = helperFuction.allowedOperators(n);

            for (int i = 0; i < N.size(); i++) {
                helperFuction.print(n.state);
                node g = N.get(i);
                counter++;
                System.out.println("counter: " + counter );

                if (h_.containsKey(g.toString())) continue;
                String result = Limited_DFS(g, goal, limit - 1, h_);

                if (result.equals("cutoff")) {
                    isCutoff = true;
                }

                else if (!result.equals("fail")) {
                    return result;
                }
            }
            helperFuction.H.remove(n);
            if (isCutoff) {
                return "cutoff";
            } else {
                return "fail";
            }
        }
    }

}

