import java.util.Comparator;
import java.util.LinkedList;


//import static helperFuction._goalState;


public class node implements Comparable<node> , Comparator<node> {
        public String[][] state = null;
        public LinkedList<node> path = new  LinkedList<node>();
        public String change = "";
        public  int cost = 0;
        public static int _nodesNum = 0;
        public String mark = "";
        public static int _counter=0;

        public node(String[][] n) {
            this.state =n;
            LinkedList<String[][]> path;
            _nodesNum++;
            _counter++;

        }
        public node() {
            this.state =null;
            LinkedList<char[][]> path;

            _nodesNum++;
            _counter++;


        }

        @Override
        public int compare(node o1, node o2)
        {     if(Dis(o1) > Dis(o2)){
            return 1;
        }
            if (Dis(o1) < Dis(o2)){
                return  -1;
            }
            if (o1.path.contains("&") && !o2.path.contains("&")){
                return 1;
            }
            if (!o1.path.contains("&") && o2.path.contains("&")){
                return -1;
            }


            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj.getClass() != this.getClass()) {
                return false;
            }
            final node other = (node) obj;

            if (!this.toString().equals(other.toString())){
                return false;
            }
            return true;
        }

        // order to nodes in the priority q
        @Override
        public int compareTo(node o) {
            if (Dis(this) > Dis(o))
                return 1;
            else if (Dis(this) < Dis(o))
                return -1;

            if (this.path.contains("&") && !o.path.contains("&")){
                return 1;
            }
            if (!this.path.contains("&") && o.path.contains("&")){
                return -1;
            }
            return 0;

        }




        @Override
        public String toString(){
            String hash = "";
            for (int i = 0; i < this.state.length; i++) {
                for (int j = 0; j < this.state[0].length; j++) {
                    if (this.state[i][j].equals("_")){
                        hash += "0";
                    }
                    else{
                        hash += this.state[i][j];
                    }
                }
            }
            return hash;
        }

    public static int Dis(node n){
        int ans = 0;
        for (int i = 0; i < n.state.length ; i++) {
            for (int j = 0; j < n.state[0].length; j++) {
                ans += Math.abs(i+j - findDestinationPlace(n.state[i][j]));
            }
        }
        return ans;
    }

    // find the place where this char supposed to be
    public static int findDestinationPlace(String x){
        for (int i = 0; i < helperFuction._goalState.length ; i++) {
            for (int j = 0; j < helperFuction._goalState[0].length; j++) {
                if (helperFuction._goalState[i][j].equals(x)){
                    return (i+j);
                }

            }
        }
        return 0;
    }


}

