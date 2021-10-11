
import java.util.HashMap;
import java.util.LinkedList;

public class helperFuction {

    public static String[][] _startState = null;
    public static String[][] _goalState = null;

    public static HashMap<String, String> nodeHASHMAP = new HashMap<String, String>();
    public static HashMap<String, String> C = new HashMap<String, String>();
    public static String _path;
    public static HashMap<String, node> H = new HashMap<>();


    //check how many bottom line in the greed
    public static int[] howMany_(node state) {
        //firstX, firstY, secX, secY
        int[] ans = {-1, -1, -1, -1};
        boolean flag = false;
        for (int i = 0; i < state.state.length; i++) {
            for (int j = 0; j < state.state[0].length; j++) {
                if (state.state[i][j].equals("_") && flag) {
                    ans[2] = i;
                    ans[3] = j;
                }
                if (state.state[i][j].equals("_") && !flag) {
                    ans[0] = i;
                    ans[1] = j;
                    flag = true;
                }
            }
        }
        return ans;
    }

//replay list of the allowed Operators for 1 _ or 2 __
    public static LinkedList<node> allowedOperators(node state) {
        LinkedList<node> oper = new LinkedList<>();
        int[] lineIndex = howMany_(state);
        if (lineIndex[2] == -1) {
            oper.addAll(allowedOperators_(state, lineIndex[0], lineIndex[1]));
        } else {
            oper.addAll(allowedOperators__(state, lineIndex[0], lineIndex[1], lineIndex[2], lineIndex[3]));
        }
        return oper;
    }

    // allowed operators USE OPEN LIST
    public static LinkedList<node> allowedOperators_(node state, int x, int y) {
        LinkedList<node> operators = new LinkedList<>();
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (i >= 0 && i < state.state.length
                        && j < state.state[0].length && j >= 0
                        && !(i == x && j == y)
                        && !(i == x - 1 && j == y - 1)
                        && !(i == x + 1 && j == y + 1)
                        && !(i == x - 1 && j == y + 1)
                        && !(i == x + 1 && j == y - 1)
                ) {
                    String tempMat[][] = copyMat(state.state);
                    tempMat[x][y] = tempMat[i][j];
                    tempMat[i][j] = "_";
                    String hashMat = matToString(tempMat);
                   

                    if (!nodeHASHMAP.containsValue(hashMat)) {


                        tempMat[i][j] = tempMat[x][y];
                        tempMat[x][y] = "_";

                        String tempChange = wichChange(tempMat, x, y, i, j);

                        if (!ifIsTheRevers(state.change, tempChange)){
                            node temp = new node();
                            temp.cost = state.cost + 5;
                            temp.state = tempMat;
                            temp.change = tempChange;
                            tempMat[x][y] = tempMat[i][j];
                            tempMat[i][j] = "_";
                            temp.path.add(state);
                            operators.add(temp);
                        }
                    }
                }
            }
        }
        return operators;
    }

// allowed operators USE OPEN LIST
    public static LinkedList<node> allowedOperators__(node state, int x1, int y1, int x2, int y2) {
        LinkedList<node> operators = new LinkedList<>();

        // __
        if (x1 + 1 == x2 && y1 == y2 ||
                x1 == x2 && y1 + 1 == y2 ||
                x1 - 1 == x2 && y1 == y2 ||
                x1 == x2 && y1 - 1 == y2) {

            int sX = Math.min(x1, x2);
            int sY = Math.min(y1, y2);
            int lX = Math.max(x1, x2);
            int lY = Math.max(y1, y2);

            // __ lines go up
            if (sX == lX && sY + 1 == lY) {
                if (sX - 1 >= 0) {
                    String[][] tempMat = copyMat(state.state);
                    tempMat[sX][sY] = state.state[sX - 1][sY];
                    tempMat[lX][lY] = state.state[lX - 1][lY];
                    tempMat[sX - 1][sY] = "_";
                    tempMat[lX - 1][lY] = "_";
                    String hashMat = matToString(tempMat);
                    if (!nodeHASHMAP.containsValue(hashMat)) {
                        node temp = new node();
                        temp.cost =state.cost + 6;
                        temp.state = tempMat;
                        temp.path.add(state);
                        temp.change = temp.state[sX][sY] + "&" + temp.state[lX][lY] + "U";
                        operators.add(temp);
                    }
                }
            }
            // __ lines go down
            if (sX == lX && sY + 1 == lY) {
                if (sX + 1 <= state.state.length - 1) {
                    String tempMat[][] = copyMat(state.state);
                    tempMat[sX][sY] = state.state[sX + 1][sY];
                    tempMat[lX][lY] = state.state[lX + 1][lY];
                    tempMat[sX + 1][sY] = "_";
                    tempMat[lX + 1][lY] = "_";
                    String hashMat = matToString(tempMat);
                    if (!nodeHASHMAP.containsValue(hashMat)) {
                        node temp = new node();
                        temp.cost = state.cost + 6;
                        temp.state = tempMat;
                        temp.path.add(state);
                        temp.change = temp.state[sX][sY] + "&" + temp.state[lX][lY] + "D";
                        operators.add(temp);

                    }
                }
            }
            //-
            //- lines go right
            if (sX + 1 == lX && sY == lY) {
                if (sY + 1 <= state.state[0].length - 1 ) {
                    String tempMat[][] = copyMat(state.state);
                    tempMat[sX][sY] = state.state[sX][sY + 1];
                    tempMat[lX][lY] = state.state[lX][lY + 1];
                    tempMat[sX][sY + 1] = "_";
                    tempMat[lX][lY + 1] = "_";
                    String hashMat = matToString(tempMat);
                    if (!nodeHASHMAP.containsValue(hashMat)) {
                        node temp = new node();
                        temp.cost = state.cost + 6;
                        temp.state = tempMat;
                        temp.path.add(state);
                        temp.change = temp.state[sX][sY] + "&" + temp.state[lX][lY] + "R";
                        operators.add(temp);

                    }
                }
            }

            //-
            //- lines go left
            if (sX + 1 == lX && sY == lY) {
                if (sY - 1 >= 0) {
                    String tempMat[][] = copyMat(state.state);
                    tempMat[sX][sY] = state.state[sX][sY - 1];
                    tempMat[lX][lY] = state.state[lX][lY - 1];
                    tempMat[sX][sY - 1] = "_";
                    tempMat[lX][lY - 1] = "_";
                    String hashMat = matToString(tempMat);
                    if (!nodeHASHMAP.containsValue(hashMat)) {
                        node temp = new node();
                        temp.cost = state.cost + 6;
                        temp.state = tempMat;
                        temp.path.add(state);
                        temp.change = temp.state[sX][sY] + "&" + temp.state[lX][lY] + "L";

                        operators.add(temp);

                    }
                }
            }
        }

        for (int i = x1 - 1; i < x1 + 2; i++) {
            for (int j = y1 - 1; j < y1 + 2; j++) {
                if (i >= 0 && i < state.state.length
                        && j < state.state[0].length && j >= 0
                        && !(i == x1 && j == y1)
                        && !(i == x1 - 1 && j == y1 - 1)
                        && !(i == x1 + 1 && j == y1 + 1)
                        && !(i == x1 - 1 && j == y1 + 1)
                        && !(i == x1 + 1 && j == y1 - 1)
                        && !(state.state[i][j].equals("_"))
                ) {
                    //copy mat
                    String tempMat[][] = copyMat(state.state);
                    tempMat[x1][y1] = tempMat[i][j];
                    tempMat[i][j] = "_";
                    String hashMat = matToString(tempMat);

                    if (!nodeHASHMAP.containsValue(hashMat)) {
                        tempMat[i][j] = tempMat[x1][y1];
                        tempMat[x1][y1] = "_";

                        String tempChange = wichChange(tempMat, x1, y1, i, j);
                        if (!ifIsTheRevers(state.change, tempChange)){
                            node temp = new node();
                            temp.cost = state.cost + 5;
                            temp.state = tempMat;
                            temp.change = tempChange;
                            tempMat[x1][y1] = tempMat[i][j];
                            tempMat[i][j] = "_";
                            temp.path.add(state);
                            operators.add(temp);
                        }
                    }
                }
            }
        }
        for (int i = x2 - 1; i < x2 + 2; i++) {
            for (int j = y2 - 1; j < y2 + 2; j++) {
                if (i >= 0 && i < state.state.length
                        && j < state.state[0].length && j >= 0
                        && !(i == x2 && j == y2)
                        && !(i == x2 - 1 && j == y2 - 1)
                        && !(i == x2 + 1 && j == y2 + 1)
                        && !(i == x2 - 1 && j == y2 + 1)
                        && !(i == x2 + 1 && j == y2 - 1)
                        && !state.state[i][j].equals("_")) {
                    //copy mat

                    String tempMat[][] = copyMat(state.state);
                    tempMat[x2][y2] = tempMat[i][j];
                    tempMat[i][j] = "_";
                    String hashMat = matToString(tempMat);

                    if (!nodeHASHMAP.containsValue(hashMat)) {

                        tempMat[i][j] = tempMat[x2][y2];
                        tempMat[x2][y2] = "_";
                        String tempChange = wichChange(tempMat, x2, y2, i, j);
                        if (!ifIsTheRevers(state.change, tempChange)){
                            node temp = new node();
                            temp.cost = state.cost + 5;
                            temp.state = tempMat;
                            temp.change = tempChange;
                            tempMat[x2][y2] = tempMat[i][j];
                            tempMat[i][j] = "_";
                            temp.path.add(state);
                            operators.add(temp);
                        }
                    }
                }
            }
        }


        return operators;
    }

    //copy mat
    public static String[][] copyMat(String[][] mat) {
        String[][] a = new String[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                a[i][j] = mat[i][j];
            }
        }
        return a;
    }

    //convert to list
    public static LinkedList<node> convertToLL(node n) {
        LinkedList<node> ans = new LinkedList<>();
        ans.add(n);
        while (n.path.size() == 1) {

            ans.addAll(n.path);
            _path = n.change + "-" + _path;
            n = n.path.get(0);

        }
        _path = _path.substring(0, _path.length() - 5);

        return ans;
    }

    //check wich change the node do
    public static String wichChange(String [][] n, int x, int y, int i, int j) {
        String change = "";
        if (i == x && j + 1 == y) change = n[i][j] + "R";
        if (i == x + 1 && j == y) change = n[i][j] + "U";
        if (i == x && j == y + 1) change = n[i][j] + "L";
        if (i + 1 == x && j == y) change = n[i][j] + "D";

        return change;

    }

    //mako string from the mat
    public static String matToString(String mat[][]) {
        String hash = "";
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j].equals("_")) {
                    hash += "0";
                } else {
                    hash += mat[i][j];
                }
            }
        }
        return hash;
    }


// allowed operators WITHUOT USE OPEN LIST
    public static LinkedList<node> ALLallowedOperators(node state) {
        LinkedList<node> oper = new LinkedList<>();
        int[] lineIndex = howMany_(state);
        if (lineIndex[2] == -1) {
            oper.addAll(ALLallowedOperators_(state, lineIndex[0], lineIndex[1]));
        } else {
            oper.addAll(ALLallowedOperators__(state, lineIndex[0], lineIndex[1], lineIndex[2], lineIndex[3]));
        }
        return oper;
    }

    // allowed operators WITHUOT USE OPEN LIST_
    public static LinkedList<node> ALLallowedOperators_(node state, int x, int y) {
        LinkedList<node> operators = new LinkedList<>();
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (i >= 0 && i < state.state.length
                        && j < state.state[0].length && j >= 0
                        && !(i == x && j == y)
                        && !(i == x - 1 && j == y - 1)
                        && !(i == x + 1 && j == y + 1)
                        && !(i == x - 1 && j == y + 1)
                        && !(i == x + 1 && j == y - 1)
                ) {
                    String tempMat[][] = copyMat(state.state);
                    tempMat[x][y] = tempMat[i][j];
                    tempMat[i][j] = "_";


                    tempMat[i][j] = tempMat[x][y];
                    tempMat[x][y] = "_";

                    String tempChange = wichChange(tempMat, x, y, i, j);

                    if (!ifIsTheRevers(state.change, tempChange)){
                        node temp = new node();
                        temp.cost = state.cost + 5;
                        temp.state = tempMat;
                        temp.change = tempChange;
                        tempMat[x][y] = tempMat[i][j];
                        tempMat[i][j] = "_";
                        temp.path.add(state);
                        operators.add(temp);
                    }

                }
            }
        }
        return operators;
    }

// allowed operators WITHUOT USE OPEN LIST _ _
    public static LinkedList<node> ALLallowedOperators__(node state, int x1, int y1, int x2, int y2) {
        LinkedList<node> operators = new LinkedList<>();

        // __
        if (x1 + 1 == x2 && y1 == y2 ||
                x1 == x2 && y1 + 1 == y2 ||
                x1 - 1 == x2 && y1 == y2 ||
                x1 == x2 && y1 - 1 == y2) {

            int sX = Math.min(x1, x2);
            int sY = Math.min(y1, y2);
            int lX = Math.max(x1, x2);
            int lY = Math.max(y1, y2);

            // __ lines go up
            if (sX == lX && sY + 1 == lY) {
                if (sX - 1 >= 0) {
                    String[][] tempMat = copyMat(state.state);
                    tempMat[sX][sY] = state.state[sX - 1][sY];
                    tempMat[lX][lY] = state.state[lX - 1][lY];
                    tempMat[sX - 1][sY] = "_";
                    tempMat[lX - 1][lY] = "_";
                    String hashMat = matToString(tempMat);

                    node temp = new node();
                    temp.cost = 6;
                    temp.state = tempMat;
                    temp.path.add(state);
                    temp.change = temp.state[sX][sY] + "&" + temp.state[lX][lY] + "U";
                    operators.add(temp);
                }

            }
            // __ lines go down
            if (sX == lX && sY + 1 == lY) {
                if (sX + 1 <= state.state.length - 1) {
                    String tempMat[][] = copyMat(state.state);
                    tempMat[sX][sY] = state.state[sX + 1][sY];
                    tempMat[lX][lY] = state.state[lX + 1][lY];
                    tempMat[sX + 1][sY] = "_";
                    tempMat[lX + 1][lY] = "_";
                    String hashMat = matToString(tempMat);

                    node temp = new node();
                    temp.cost = 6;
                    temp.state = tempMat;
                    temp.path.add(state);
                    temp.change = temp.state[sX][sY] + "&" + temp.state[lX][lY] + "D";

                }
            }
            //-
            //- lines go right
            if (sX + 1 == lX && sY == lY) {
                if (sY + 1 <= state.state[0].length - 1) {
                    String tempMat[][] = copyMat(state.state);
                    tempMat[sX][sY] = state.state[sX][sY + 1];
                    tempMat[lX][lY] = state.state[lX][lY + 1];
                    tempMat[sX][sY + 1] = "_";
                    tempMat[lX][lY + 1] = "_";
                    String hashMat = matToString(tempMat);

                    node temp = new node();
                    temp.cost = 6;
                    temp.state = tempMat;
                    temp.path.add(state);
                    temp.change = temp.state[sX][sY] + "&" + temp.state[lX][lY] + "R";

                }
            }

            //-
            //- lines go left
            if (sX + 1 == lX && sY == lY) {
                if (sY - 1 > 0) {
                    String tempMat[][] = copyMat(state.state);
                    tempMat[sX][sY] = state.state[sX][sY - 1];
                    tempMat[lX][lY] = state.state[lX][lY - 1];
                    tempMat[sX][sY - 1] = "_";
                    tempMat[lX][lY - 1] = "_";
                    String hashMat = matToString(tempMat);

                    node temp = new node();
                    temp.cost = 6;
                    temp.state = tempMat;
                    temp.path.add(state);
                    temp.change = temp.state[sX][sY] + "&" + temp.state[lX][lY] + "L";
                    ;
                    operators.add(temp);


                }
            }
        }

        for (int i = x1 - 1; i < x1 + 2; i++) {
            for (int j = y1 - 1; j < y1 + 2; j++) {
                if (i >= 0 && i < state.state.length
                        && j < state.state[0].length && j >= 0
                        && !(i == x1 && j == y1)
                        && !(i == x1 - 1 && j == y1 - 1)
                        && !(i == x1 + 1 && j == y1 + 1)
                        && !(i == x1 - 1 && j == y1 + 1)
                        && !(i == x1 + 1 && j == y1 - 1)
                        && !(state.state[i][j].equals("_"))
                ) {
                    //copy mat
                    String tempMat[][] = copyMat(state.state);
                    tempMat[x1][y1] = tempMat[i][j];
                    tempMat[i][j] = "_";
                    String hashMat = matToString(tempMat);


                    tempMat[i][j] = tempMat[x1][y1];
                    tempMat[x1][y1] = "_";

                    String tempChange = wichChange(tempMat, x1, y1, i, j);
                    if (!ifIsTheRevers(state.change, tempChange)){
                        node temp = new node();
                        temp.cost = state.cost + 5;
                        temp.state = tempMat;
                        temp.change = tempChange;
                        tempMat[x1][y1] = tempMat[i][j];
                        tempMat[i][j] = "_";
                        temp.path.add(state);
                        operators.add(temp);
                    }
                }
            }
        }
        for (int i = x2 - 1; i < x2 + 2; i++) {
            for (int j = y2 - 1; j < y2 + 2; j++) {
                if (i >= 0 && i < state.state.length
                        && j < state.state[0].length && j >= 0
                        && !(i == x2 && j == y2)
                        && !(i == x2 - 1 && j == y2 - 1)
                        && !(i == x2 + 1 && j == y2 + 1)
                        && !(i == x2 - 1 && j == y2 + 1)
                        && !(i == x2 + 1 && j == y2 - 1)
                        && !state.state[i][j].equals("_")) {
                    //copy mat

                    String tempMat[][] = copyMat(state.state);
                    tempMat[x2][y2] = tempMat[i][j];
                    tempMat[i][j] = "_";
                    String hashMat = matToString(tempMat);


                    tempMat[i][j] = tempMat[x2][y2];
                    tempMat[x2][y2] = "_";

                    String tempChange = wichChange(tempMat, x2, y2, i, j);
                    if (!ifIsTheRevers(state.change, tempChange)){
                        node temp = new node();
                        temp.cost = state.cost + 5;
                        temp.state = tempMat;
                        temp.change = tempChange;
                        tempMat[x2][y2] = tempMat[i][j];
                        tempMat[i][j] = "_";
                        temp.path.add(state);
                        operators.add(temp);
                    }

                }
            }
        }


        return operators;
    }

    public static void print(String[][] state) {
        String ans = "";
        System.out.println("--------");
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                ans = ans + state[i][j] + " ";
            }
            System.out.println(ans);
            ans = "";
            System.out.println("");
        }
    }


    //check if it revers steps
    public static boolean ifIsTheRevers(String change1, String change2)
    {
        if (change1.equals("") || change2.equals("")){
            return false;
        }

        if (change1.charAt(0) == change2.charAt(0) && (
                change1.charAt(1) == 'L' &&   change2.charAt(1) == 'R' ||
                change1.charAt(1) == 'R' &&   change2.charAt(1) == 'L' ||
                change1.charAt(1) == 'U' &&   change2.charAt(1) == 'D' ||
                change1.charAt(1) == 'D' &&   change2.charAt(1) == 'U'
                )){
       
            return true;
        }
        return false;
    }


}
