

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;


public class Ex1 {

static String READERwhichAlgo = "";
static String READERtime = "";
static String READERifOpen = "";
    private static int _cost;

/////////////////////////////read file//////////////////////////////////////


    //read from file
 public static void convertToNodes(){
     try {
         File myObj = new File("input.txt");
         Scanner myReader = new Scanner(myObj);
         if (myReader.hasNextLine()) {
             READERwhichAlgo = myReader.nextLine();
         }
         if (myReader.hasNextLine()) {
             READERtime = myReader.nextLine();
         }
         if (myReader.hasNextLine()) {
             READERifOpen = myReader.nextLine();
         }
         String size="";
         if (myReader.hasNextLine()) {
             size = myReader.nextLine();
         }
         int n = Integer.parseInt(String.valueOf(size.charAt(0)));
         int m = Integer.parseInt(String.valueOf(size.charAt(2)));

        //mat of start state
         helperFuction._startState = new String[n][m];
         int rows = 0;
         while (myReader.hasNextLine()) {
             String line = myReader.nextLine();
             String[] arr = line.split(",");

             for (int i = 0; i < m ; i++) {
                 helperFuction._startState[rows][i] = arr[i];
             }
             rows ++;

            if (rows == n)
                break;
         }
         if (myReader.hasNextLine()) {
             // "Goal state:"
             String line = myReader.nextLine();
         }
        //mat of goal state
         helperFuction._goalState = new String[n][m];
         rows = 0;
         while (myReader.hasNextLine()) {
              String line = myReader.nextLine();
             String[] arr = line.split(",");
             for (int i = 0; i < m ; i++) {
                 helperFuction._goalState[rows][i] = arr[i];
             }
             rows ++;
             if (rows == n) break;
         }
         myReader.close();
     } catch (FileNotFoundException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
     }
 }

    public static void main (String args[]){
     convertToNodes();

    long before = System.currentTimeMillis();
   node ans = null;

   if (READERwhichAlgo.equals("BFS") && READERifOpen.equals("with open")){
       ans = bfs.BFS_withOpen();
   }
    else if (READERwhichAlgo.equals("BFS") && READERifOpen.equals("no open")){
        ans = bfs.BFS();
    }

    else if (READERwhichAlgo.equals("DFID") && READERifOpen.equals("with open") ){
        boolean an = DFID.DFID_f_withOpen();
        ans = DFID._ans;
    }

    else if (READERwhichAlgo.equals("DFID") && READERifOpen.equals("no open")){
        boolean an = DFID.DFID_f();
        ans = DFID._ans;
    }

    else if (READERwhichAlgo.equals("A*") && READERifOpen.equals("with open") ){
        ans = aStar.aStar_withOpen();
    }

    else if (READERwhichAlgo.equals("A*") && READERifOpen.equals("no open") ){
        ans = aStar.aStar();
    }


    else if (READERwhichAlgo.equals("IDA*") && READERifOpen.equals("with open") ){
        ans = idaStar.IDAstar_withOpen();
    }

    else if (READERwhichAlgo.equals("IDA*") && READERifOpen.equals("no open") ){
        ans = idaStar.IDAstar();
    }

    else if (READERwhichAlgo.equals("DFBnB") && READERifOpen.equals("with open")){
        ans = DfBnB.DFBnB_withOpen();
    }

    else if (READERwhichAlgo.equals("DFBnB") && READERifOpen.equals("no open")){
        ans = DfBnB.DFBnB();
    }

     long after = System.currentTimeMillis();
     double _time = (after-before)/1000.0;

    LinkedList<node> ans2 = helperFuction.convertToLL(ans);
    _cost = ans.cost;

        try {
            FileWriter myWriter = new FileWriter(" output.txt");
            myWriter.write(helperFuction._path+ " \n");
            myWriter.write("Num: " + node._counter + " \n");
            myWriter.write("cost: "+ _cost + " \n");
            if (READERtime.equals("with time")){
                myWriter.write(_time +" seconds \n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
 }
