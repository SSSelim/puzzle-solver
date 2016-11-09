package com.selimssevgi.puzzlesolver;

import java.util.ArrayList;

public class Node {
  private static int[][] goalState = Util.getGoalState();
	private int[][] state; // matrix to store the state of the puzzle
	private int stari; // location of the star element in the matrix (stari,starj)
	private int starj;
	private Node parent; // from which state this node is created
	private int fn = 0;
	private int gn = 0;
	private int hn = 0;

	
	public Node(int[][] s, Node p){
		state = s;
		parent = p;
		setStarIndeces(); // sets (stari,starj)
	}

	public ArrayList<Node> findSuccessors(){
		ArrayList<Node> successorList = new ArrayList<>();
		
		int[][] Sstate;
		int temp;
		
		ArrayList<int[][]> validMoves = Util.getValidMoves(stari, starj);
		
		for (int[][] move : validMoves) {
			Sstate = Util.copyMatrix(state);
			temp = Sstate[stari][starj];
			Sstate[stari][starj] = Sstate[move[0][0]][move[0][1]];
			Sstate[move[0][0]][move[0][1]] = temp;

			successorList.add(new Node(Sstate,this));
		}
		
		return successorList;
	}

	public boolean isGoal(){
		return Util.isSameMatrix(state, goalState);
	}
	
	// set methods
	private void setStarIndeces(){
		int[] indices; // (indices[0],indices[1])
		indices = Util.locateElement(state,0); // defines star as 0
		stari = indices[0];
		starj = indices[1];
	}
	
	public void sethn(){
		// Manhattan distance
		int hn = 0;
		int[] indices;
		
		for (int i = 0; i < goalState.length; i++) {
			for (int j = 0; j < goalState[0].length; j++) {
				if( state[i][j] != goalState[i][j] ){
					indices = Util.locateElement(goalState,state[i][j]);
					hn += Math.abs(i-indices[0]) + Math.abs(j-indices[1]);
				}
			}
		}
		
		this.hn = hn;
	}
	
	public void setgn(){
		gn = parent.getgn() + 1;
	}
	
	public void setfn(){
		fn = gn + hn;
	}
	
	// get methods
	public int getgn(){
		return gn;
	}
	
	public int gethn(){
		return hn;
	}
	
	public int getfn(){
		return fn;
	}
	
	public int getStari(){
		return stari;
	}
	
	public int getStarj(){
		return starj;
	}
	
	public int[][] getState(){
		return state;
	}
	
	public Node getParent(){
		return parent;
	}

  public static void printNode(Node n) {
    System.out.printf("\nNode debug start\n");
    Util.printMatrix(n.getState(), "Node state matris");
    System.out.printf("\nNode fn : %d\n", n.getfn());
    System.out.printf("\nNode gn : %d\n", n.getgn());
    System.out.printf("\nNode hn : %d\n", n.gethn());
    System.out.printf("\nStar is at (%d,%d)\n", n.getStari(), n.getStarj());
    System.out.printf("\nNode debug end\n");
  }
}
