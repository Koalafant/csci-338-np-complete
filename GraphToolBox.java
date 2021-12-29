/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

/**
 *
 * @author Reyher
 * methods to calculate optimal and inoptimal independent sets and vertex covers.
 */
public class GraphToolBox {
    /**
     *
     * @param inputGraph input graph.
     * @return array of integers such that this set is the smallest VC of inputGraph.
     */
    // return an array containing the vertex numbers of an optimal VC.
    public static int[] exactVC(Graph inputGraph) {
        //begin timing
        long startTime = System.currentTimeMillis();
        int[][] graph = inputGraph.getGraph();
        //create every vertex cover, check if it's valid, add to vc set
        int[] vertices = new int[graph.length];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = i;
        }
        //algorithm that returns all possible vertex covers
        All_Possible_Combinations a = new All_Possible_Combinations();
        ArrayList<int[]> finalVC = new ArrayList<>();
        ArrayList<int[]> temp;
        for(int i = 0; i < graph.length; i++){
            temp = a.generate(graph.length, i);
                //check to make sure they're vertex covers
                for (int k = 0; k < temp.size(); k++) {
                    boolean correct;
                    correct = correctVC(inputGraph, temp.get(k));
                    if (correct) {
                        finalVC.add(temp.get(k));

                }
            }
                temp.clear();
        }
        //return smallest vc
        //catch if finalVC.get(0) doesn't work
        try {
            int[] min = finalVC.get(0);
            for (int i = 0; i < finalVC.size(); i++) {
                //if length is smaller, set new min
                if (finalVC.get(i).length < min.length) {
                    min = finalVC.get(i);
                }
            }
            return min;
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("No vertex cover possible.");
            return null;
        }
    }

    /**
     *
     * @param inputGraph input graph.
     * @return an array of integers such that this set is a vertex cover of inputGraph.
     */
    // return (in polynomial time) an array of edges that are a VC
    public static int[] inexactVC(Graph inputGraph) {
        /*create largest (worst) VC.
        * create random value to iterate through,
        * popping values from the largest VC to see if anything works.
         */
        int[][] graph = inputGraph.getGraph();
        int[] bigV = new int[graph.length];
        for(int i = 0; i < bigV.length; i++){
            bigV[i] = i;
        }
        Random random = new Random();
        int iteration = random.nextInt(bigV.length + (random.nextInt(100) * random.nextInt(100) * (100 % bigV.length)));
        //create new VCs and check them
        for(int i = 0; i < iteration; i++){
            int length = random.nextInt(bigV.length);
            if(length == 0){
                length = 1;
            }else if(length == bigV.length){
                length --;
            }
            int[] testVC = new int[length];
            //create new vertices
            for(int j = 0; j < testVC.length; j++){
                int value = random.nextInt(graph.length);
                boolean dupe = false;
                //check for duplicates

                    for (int k = 0; k < testVC.length; k++) {
                        if(testVC[k] == value){
                            dupe = true;
                        }
                        if(dupe){
                            if(value == testVC.length - 1){
                                value = 0;
                                dupe = false;
                            }else{
                                value++;
                                dupe = false;
                            }
                        }
                    }

                testVC[j] = value;
            }
            //test for correctVC
            try {
                boolean correct = correctVC(inputGraph, testVC);

            if(correct){
                if(testVC.length < bigV.length){
                    bigV = testVC;
                }
            }
            }catch(ArrayIndexOutOfBoundsException e) {}
        }
        System.out.println("Inexact VC of size " + graph.length + " finished.");

        return bigV;
    }

    /**
     *
     * @param inputGraph graph sent in. Finds independent set based on this
     * @return array of vertices such that this set is the best independent set.
     */
    // return an array containing the vertex numbers of an optimal IS.
    public static int[] optimalIS(Graph inputGraph) {

        int[][] graph = inputGraph.getGraph();
        //create every independent set, check if it's valid, add to IS set
        int[] vertices = new int[graph.length];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = i;
        }
        //algorithm that returns all possible independent sets
        All_Possible_Combinations a = new All_Possible_Combinations();
        ArrayList<int[]> temp;
        int[] max = new int [0];
        for(int i = 0; i < graph.length; i++){
            temp = a.generate(graph.length, i);
            //check to make sure they're independent sets
            for (int k = 0; k < temp.size(); k++) {
                boolean correct;
                correct = correctIS(inputGraph, temp.get(k));
                if (correct) {
                    if(temp.get(k).length > max.length){
                        max = temp.get(k);
                    }
                }
            }
            temp.clear();
        }
        //return smallest IS
        try {
            return max;
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("No independent set possible.");
            return null;
        }
    }

    /**
     *
     * @param inputGraph input graph.
     * @return array of integers such that this set is an indepdent set.
     */
    // return (in polynomial time) an array containing the vertex numbers of an IS.
    public static int[] inexactIS(Graph inputGraph) {
        /*create smallest (worst) IS.
         * create random value to iterate through,
         * adding values to the smallest IS to see if anything works.
         */
        int[][] graph = inputGraph.getGraph();
        int[] bigV = new int[1];
        for(int i = 0; i < bigV.length; i++){
            bigV[i] = i;
        }
        Random random = new Random();
        //this iterator needed this number to return more than [0]
        int iteration = 100000;
        //create new ISs and check them
        for(int i = 0; i < iteration; i++){
            int length = random.nextInt(graph.length);
            if(length == 0){
                length = 1;
            }else if(length == bigV.length){
                length --;
            }
            int[] testIS = new int[length];
            ArrayList<Integer> collection = new ArrayList();
            for(int j = 0; j < graph.length; j++){
                collection.add(j);
            }
            Collections.shuffle(collection);
            //add random values
            for(int j = 0; j < testIS.length; j++){
                testIS[j] = collection.get(j);
            }
            //test for correctIS
                boolean correct = correctIS(inputGraph, testIS);
                if(correct){
                    if(testIS.length > bigV.length){
                        bigV = testIS;
                    }
                }
        }
        return bigV;
    }

    /**
     *
     * @param g input graph.
     * @param vc possible vertex cover.
     * @return true if a VC, false if not.
     */
    //returns true if given int[] is a correct vc.
    //build the edge set of the graph and check if length is accurate
    public static boolean correctVC(Graph g, int[] vc) {
        //create arraylist of edge pairs
        //Arraylists of the form <edge1, edge2, marked (0 or 1)>
        ArrayList<ArrayList<Integer>> edgeSets = new ArrayList<>();
        int[][] graph = g.getGraph();
        //traverse each vertex
        for(int i = 0; i < graph.length; i++) {
            //traverse each vertex edge
            for (int j = 0; j < graph[i].length; j++) {
                //traverse edgeSet and add new ones
                boolean exists = false;
                for (int k = 0; k < edgeSets.size(); k++) {
                    //if there is an edgeset (edge1 - edge2 or edge2 - edge1)
                    if ((edgeSets.get(k).get(0) == i && edgeSets.get(k).get(1) == graph[i][j]) || (edgeSets.get(k).get(0) == graph[i][j] && edgeSets.get(k).get(1) == i)) {
                        exists = true;
                        break;
                    }
                }
                //check to see if that edge set exists, if not, create it.
                if (!exists) {
                    edgeSets.add(new ArrayList<>());
                    edgeSets.get(edgeSets.size() - 1).add(i);
                    edgeSets.get(edgeSets.size() - 1).add(graph[i][j]);
                    //edgeset[2] is a boolean -> marked or not (0 or 1)
                    edgeSets.get(edgeSets.size() - 1).add(0);
                }
            }
        }
        //edgeSet built, traverse potential vertex cover and mark edges
        for(int i = 0; i < vc.length; i++){
            for(int j = 0; j < graph[vc[i]].length; j++){
                int edge1 = vc[i];
                int edge2 = graph[vc[i]][j];
                //traverse edgeset and find corresponding edge
                for(int k = 0; k < edgeSets.size(); k++){
                    if((edgeSets.get(k).get(0) == edge1 && edgeSets.get(k).get(1) == edge2) || (edgeSets.get(k).get(1) == edge1 && edgeSets.get(k).get(0) == edge2)){
                        edgeSets.get(k).set(2, 1);
                    }
                }
            }
        }
        //traverse edgeSets, if all are marked ([2] == 1) return true
        boolean correct = true;
        for(int i = 0; i < edgeSets.size(); i++){
            if(edgeSets.get(i).get(2) == 0){
                correct = false;
            }
        }
        return correct;
    }

    /**
     *
     * @param g input graph.
     * @param is potential independent set.
     * @return true if IS, false if not.
     */
    //returns boolean whether the given int[] is a correct Independent Set.
      public static boolean correctIS(Graph g, int[] is){
        int[][] graph = g.getGraph();
        int[] vertices = new int[graph.length];
        //marked unmarked values
        for(int i = 0; i < vertices.length; i++){
            vertices[i] = 0;
        }
        //mark all vertices in is[]
        for(int i = 0; i < is.length; i++){
            vertices[is[i]] = 1;
        }
        //if a marked vertex connects to another marked vertex, not valid
          boolean valid = true;
        for(int i = 0; i < is.length; i++){
            for(int j = 0; j < graph[is[i]].length; j++){
                if(vertices[graph[is[i]][j]] == 1){
                    valid = false;
                }
            }
        }
        return valid;
      }
}