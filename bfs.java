import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class bfs {


    public static node BFS (){
 
        LinkedList<node> L = new LinkedList<node>();


        Set<String> ClosedList = new HashSet<>();
        Set<String>  OpenList = new HashSet<>();
        node start_ = new node(helperFuction._startState);
        node goal_ = new node(helperFuction._goalState);


        L.addLast(start_);
        OpenList.add(start_.toString());
        helperFuction.nodeHASHMAP.put(start_.toString(),start_.toString());

        while (!L.isEmpty()){
            node state_ = L.removeFirst();

            OpenList.remove(state_.toString());
            ClosedList.add(state_.toString());

            // for all operator allowed on state
            LinkedList<node> allowedOperators = helperFuction.allowedOperators(state_);
            for (int i = 0; i < allowedOperators.size() ; i++) {
              

                node g = allowedOperators.get(i);

                helperFuction.nodeHASHMAP.put(g.toString(),g.toString());
                if (ClosedList.contains((g).toString())) {
                    continue;
                }

                if (OpenList.contains((g).toString())) {
                    continue;
                }

                if ((g).toString().equals(goal_.toString())){
                    return g;
                }
                L.addLast(g);

                OpenList.add(g.toString());
            }
        }
        return null;
    }


    public static node BFS_withOpen (){
       LinkedList<node> L = new LinkedList<node>();
        node start_ = new node(helperFuction._startState);
        node goal_ = new node(helperFuction._goalState);
        L.addLast(start_);

        helperFuction.nodeHASHMAP.put(start_.toString(),start_.toString());

        while (!L.isEmpty()){
            node state_ = L.removeFirst();

            //print - with open
            System.out.println("----------state:---------");
            print(state_.state);
            System.out.println("List of Allowed Operators: ");

            //insert to close list
            helperFuction.C.put(helperFuction.matToString(state_.state),helperFuction.matToString(state_.state));
            LinkedList<node> allowedOperators = helperFuction.allowedOperators(state_);
            for (int i = 0; i < allowedOperators.size() ; i++) {
                node g = allowedOperators.get(i);
                 print(g.state);
                helperFuction.nodeHASHMAP.put(g.toString(),g.toString());
                if (!helperFuction.C.containsKey((g).toString()) && !L.contains(g)){
                    if ((g).toString().equals(goal_.toString())){
                        return g;
                    }
                    L.addLast(g);
                }
            }
        }
        return null;
    }


    public static void print(String[][] state){
        String ans="";
        System.out.println("--------");
        for (int i = 0; i < state.length ; i++) {
            for (int j = 0; j < state[0].length; j++) {
               ans = ans + state[i][j] + " ";
            }
            System.out.println(ans);
            ans = "";
            System.out.println("");
        }



    }
}
