import java.util.LinkedList;




public class idaStar {

   public static node IDAstar(){
       LinkedList<node> L = new LinkedList<node>();
       node start_ = new node(helperFuction._startState);
       node goal_ = new node(helperFuction._goalState);
       node g = null;
       int t = node.Dis(start_);
       int minF;

       while ((t!=0)){

           minF = Integer.MAX_VALUE;
           L.push(start_);
           helperFuction.H.put(start_.toString(),start_);
           while (!L.isEmpty()){
               node n = L.removeLast();
               if (n.mark.equals("out")){
                   helperFuction.H.remove(n.toString());
               }
               else {
                   n.mark = "out";
                   L.add(n);
                   LinkedList<node> N = helperFuction.ALLallowedOperators(n);
                   for (int i = 0; i < N.size(); i++) {
                       g = N.get(i);
                       if (node.Dis(g) > t + 2 ) {
                           minF = Math.min(minF, node.Dis(g));
                           continue;
                       }
                       //2
                       if (helperFuction.H.containsKey(g.toString()) && helperFuction.H.get(g.toString()).mark.equals("out")) {
                           continue;
                       }
                       //3
                       if (helperFuction.H.containsKey(g.toString()) && !helperFuction.H.get(g.toString()).mark.equals("out")) {
                           if (node.Dis(helperFuction.H.get(g.toString())) > node.Dis(g)) {
                               L.remove(helperFuction.H.get(g.toString()));
                               helperFuction.H.remove(helperFuction.H.get(g.toString()));
                           } else {
                               continue;
                           }
                       }
                   if(g.toString().equals(goal_.toString())){
                       return g;
                   }
                   L.add(g);
                       helperFuction.H.put(g.toString(), g);
                   }
               }
           }
           t=minF;
       }
       return null;
       }


    public static node IDAstar_withOpen(){
        LinkedList<node> L = new LinkedList<node>();
        node start_ = new node(helperFuction._startState);
        node goal_ = new node(helperFuction._goalState);
        node g = null;
        int t = node.Dis(start_);
        int minF;

        while ((t!=0)){
            minF = Integer.MAX_VALUE;
            L.push(start_);
            helperFuction.H.put(start_.toString(),start_);
            while (!L.isEmpty()){

                node n = L.removeLast();
                System.out.println("----------state:---------");
                helperFuction.print(n.state);
                System.out.println("List of Allowed Operators: ");

                if (n.mark.equals("out")){
                    helperFuction.H.remove(n.toString());
                }
                else {
                    n.mark = "out";
                    L.add(n);

                    LinkedList<node> N = helperFuction.ALLallowedOperators(n);
                    for (int i = 0; i < N.size(); i++) {
                        g = N.get(i);
                        helperFuction.print(g.state);
                        if (node.Dis(g) > t + 2 ) {
                            minF = Math.min(minF, node.Dis(g));
                            continue;
                        }
                        if (helperFuction.H.containsKey(g.toString()) && helperFuction.H.get(g.toString()).mark.equals("out")) {
                            continue;
                        }
                        if (helperFuction.H.containsKey(g.toString()) && !helperFuction.H.get(g.toString()).mark.equals("out")) {
                            if (node.Dis(helperFuction.H.get(g.toString())) > node.Dis(g)) {
                                L.remove(helperFuction.H.get(g.toString()));
                                helperFuction.H.remove(helperFuction.H.get(g.toString()));
                            }
                            else {
                                continue;
                            }
                        }

                        if(g.toString().equals(goal_.toString())){
                            return g;
                        }
                        L.add(g);
                        helperFuction.H.put(g.toString(), g);
                    }
                }
            }
            t=minF;
        }
        return null;
    }
}
