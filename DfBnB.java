import java.util.Comparator;
import java.util.LinkedList;

public class DfBnB {



    public static node DFBnB (){
    LinkedList<node> L = new LinkedList<node>();
    node start_ = new node(helperFuction._startState);
    node goal_ = new node(helperFuction._goalState);
        L.add(start_);
    node result = null;


    int t = Integer.MAX_VALUE;

    //main loop
        while (!L.isEmpty()){

            node n = L.remove();
            if(n.mark.equals("out") && L.contains(n)){
                helperFuction.H.remove(n.toString());
            }
          else{
              n.mark = "out";
                //lost of ALL allowed Operators
                LinkedList<node> N = new LinkedList<node>();
		N = helperFuction.ALLallowedOperators(n);

                N.sort(new Comparator<node>() {
                    @Override
                    public int compare(node o1, node o2) {
                        if(o1.cost < o2.cost){
                            return 1;
                        }
                        if (o1.cost > o2.cost){
                            return  -1;
                        }
                        return 0;
                    }
                });

                // for every allowed operater
                for (int i = 0; i < N.size() ; i++) {
                    node g = N.get(i);
                    if (g.cost >= t) {
                        N = subList(N, g.cost);

                    }
                    else if(helperFuction.H.containsKey(g.toString()) && g.mark.equals("out")){
                        N.remove(g);
                    }
                    else  if (helperFuction.H.containsKey(g.toString()) && !g.mark.equals("out")){
                        if (helperFuction.H.get(g.toString()).cost <= g.cost ){
                            N.remove(g);
                        }
                        else{
                            L.remove(helperFuction.H.get(g));
                        }
                    }
                    else if(g.toString().equals(goal_.toString())){
                        t = g.cost;
                        result = g;
                        N = subList(N, g.cost);
                    }
                }
                L.addAll(N);
                L.addAll(helperFuction.H.values());
            }
        }
        return result;

        }



    public static node DFBnB_withOpen (){
        LinkedList<node> L = new LinkedList<node>();
        node start_ = new node(helperFuction._startState);
        node goal_ = new node(helperFuction._goalState);
        L.add(start_);
        node result = null;
        int counter = 0;
        int t = Integer.MAX_VALUE;

        while (!L.isEmpty()){

            node n = L.remove();


            System.out.println("___________________");
            System.out.println("state: ");
            helperFuction.print(n.state);
            System.out.println("List of Allowed Operators: ");


            if(n.mark.equals("out") && L.contains(n)){
                helperFuction.H.remove(n.toString());
            }
            else{
                n.mark = "out";
                //order them by cost value (small do big)
                LinkedList<node> N = helperFuction.ALLallowedOperators(n);
                counter += N.size();
                N.sort(new Comparator<node>() {
                    @Override
                    public int compare(node o1, node o2) {
                        if(o1.cost < o2.cost){
                            return 1;
                        }
                        if (o1.cost > o2.cost){
                            return  -1;
                        }
                        return 0;

                    }
                });

                for (int i = 0; i < N.size() ; i++) {
                    node g = N.get(i);

                    helperFuction.print(g.state);

                    if (g.cost >= t) {
                        N = subList(N, g.cost);
                    }
                    else if(helperFuction.H.containsKey(g.toString()) && g.mark.equals("out")){
                        N.remove(g);
                    }
                    else  if (helperFuction.H.containsKey(g.toString()) && !g.mark.equals("out")){
                        if (helperFuction.H.get(g.toString()).cost <= g.cost ){
                            N.remove(g);
                        }
                        else{
                            L.remove(helperFuction.H.get(g));
                        }
                    }
                    else if(g.toString().equals(goal_.toString())){
                        t = g.cost;
                        result = g;
                        N = subList(N, g.cost);

                    }

                }
                L.addAll(N);
                L.addAll(helperFuction.H.values());
            }
        }
        System.out.println(counter);

        return result;

    }






    public static LinkedList subList(LinkedList<node> N , int t) {
        LinkedList<node> ans = new LinkedList<node>();
        for (node n: N) {
            System.out.println("n.cost: " + n.cost);
            if (n.cost < t){
                ans.add(n);
            }
        }
        return ans;
    }

}
