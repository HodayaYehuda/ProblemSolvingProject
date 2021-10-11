import java.util.*;


public class aStar {



    public static node aStar (){

        Queue<node> L = new PriorityQueue<node>();
        Map<String,node> closedList = new HashMap<String,node>();

        node start_ = new node(helperFuction._startState);
        node goal_ = new node(helperFuction._goalState);
        LinkedList<node> allowedOperators;

        //put in the open list
        helperFuction.nodeHASHMAP.put(helperFuction._startState.toString(),start_.toString());
        L.add(start_);

        //main wile
        while (!L.isEmpty()){
            node state = L.remove();

            if (state.toString().equals(goal_.toString())){
                return state;
            }

           closedList.put(state.toString(),state);

            allowedOperators = helperFuction.ALLallowedOperators(state);

            //check for all allowed operators
            for (int i = 0; i < allowedOperators.size() ; i++) {
                node g = allowedOperators.get(i);
                if (!closedList.containsKey(g.toString()) && !L.contains(g)){
                        L.add(g);
                }

                else if(closedList.containsKey(g.toString())){
                    node similar = closedList.get(g.toString());
                    //check the huristic value
                    if ((node.Dis(similar)) > (node.Dis(g))  ){
                        L.add(g);
                        L.remove(similar);
                        closedList.put(g.toString(),g);
                    }
                }
            }
        }
        return null;
    }

    public static node aStar_withOpen(){
        Queue<node> L = new PriorityQueue<node>();
        Map<String,node> closedList = new HashMap<String,node>();
        node start_ = new node(helperFuction._startState);
        node goal_ = new node(helperFuction._goalState);
        LinkedList<node> allowedOperators;

        helperFuction.nodeHASHMAP.put(helperFuction._startState.toString(),start_.toString());
        L.add(start_);

        while (!L.isEmpty()){

            node state = L.remove();
            // print - with open
            System.out.println("___________________");
            System.out.println("state: ");
            helperFuction.print(state.state);
            System.out.println("List of Allowed Operators: ");
            //
            if (state.toString().equals(goal_.toString())){
                return state;
            }
            closedList.put(state.toString(),state);
            allowedOperators = helperFuction.ALLallowedOperators(state);
            for (int i = 0; i < allowedOperators.size() ; i++) {
                node g = allowedOperators.get(i);
                helperFuction.print(g.state);

                if (!closedList.containsKey(g.toString()) && !L.contains(g)){
                    L.add(g);
                }

                else if(closedList.containsKey(g.toString())){
                    node similar = closedList.get(g.toString());
                    if ((node.Dis(similar)) > (node.Dis(g))){
                        L.add(g);
                        L.remove(similar);
                        closedList.put(g.toString(),g);
                    }
                }
            }
        }

        return null;
    }


}
