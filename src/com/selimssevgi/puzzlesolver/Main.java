package com.selimssevgi.puzzlesolver;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int[][] startState;

    System.out.printf("Depth > ");
    int depth = in.nextInt();

    startState = Util.createStartState(depth);

    Util.printMatrix(startState, "Starting matrix according to given depth: ");

    solve(startState);
    in.close();
  }

  private static void solve(int[][] startState){
    // it holds the nodes that will be check
    ArrayList<Node> openList = new ArrayList<Node>();
    // it holds the nodes that already checked
    ArrayList<Node> closeList = new ArrayList<Node>();
    ArrayList<Node> mapList = null;
    Node startNode = new Node(startState, null);

    openList.add(startNode);

    boolean reached = false;
    int step = 0;

    long startTime = System.nanoTime();

    while( !openList.isEmpty() && !reached ){
      step++;
      for (Node n : findNodeWithLeastfn(openList).findSuccessors() ) {

        if( n.isGoal() ) {
          mapList = createMap(n);
          reached = true;
          continue;
        }

        n.setgn();
        n.sethn();
        n.setfn();

        if( isInList(openList,n) ){
          continue; // there is a better one in the list
        }

        if( isInList(closeList,n) ){
          continue; // same one already opened
        }

        openList.add(n);
      }
    }

    long endTime = System.nanoTime();
    long duration = (endTime - startTime);

    printMap(mapList);

    System.out.printf("\nIt took %d steps to solve it.\n", step);
    System.out.println("Timing: " + duration + " ns");
  }

  private static boolean isInList(ArrayList<Node> list, Node n) {
    for(Node node : list) {
      if( Util.isSameMatrix(node.getState(), n.getState()) ){
        if( node.getfn() < n.getfn() ){
          return true;
        }
      }
    }

    return false;
  }

  private static Node findNodeWithLeastfn(ArrayList<Node> openList) {
    int minIndex = 0;

    for (int i = 1; i < openList.size(); i++) {
      if( openList.get(minIndex).getfn() > openList.get(i).getfn() ){
        minIndex = i;
      }
    }

    return openList.remove(minIndex); // returns the element removed
  }

  private static ArrayList<Node> createMap(Node n) {
    ArrayList<Node> mapList = new ArrayList<>();

    while( n != null){
      mapList.add(0, n);
      n = n.getParent();
    }

    return mapList;
  }

  private static void printMap(ArrayList<Node> mapList) {
    int i=0;
    String msg = "Starting state";
    for (Node node : mapList) {

      Util.printMatrix(node.getState(), msg);
      i++;
      msg = i + ".state";
    }

  }
}
