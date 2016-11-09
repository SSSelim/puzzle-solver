package com.selimssevgi.puzzlesolver;

import java.util.ArrayList;
import java.util.Random;

public final class Util {
	
	public static int[][] getGoalState(){
		final int[][] goalState = {
			    {1,2,3,4},
			    {5,6,7,8},
			    {9,10,11,12},
			    {13,14,15,0}
			};
		
		return goalState;
	}

  /**
   * It uses goal state and from there taking steps backwards
   * as many as depth in order to create a random starting state
   * */
  public static int[][] createStartState(int depth) {
    int[][] goalState = getGoalState();
    int nexti;
    int nextj;
    int stari = 3;
    int starj = 3;
    int temp  = 0;
    int d     = 0;
    Random rand = new Random();
    ArrayList<int[][]> uniqueMatrixesList = new ArrayList<>();
    ArrayList<int[][]> validMovesList = Util.getValidMoves(stari,starj);
    int[][] move;
    int[][] tempState;
    int[][] currentState;

    currentState = Util.copyMatrix(goalState);
    uniqueMatrixesList.add(goalState);

    int nextMove = rand.nextInt(validMovesList.size());

    while( depth>d ){
      move = validMovesList.get(nextMove);
      nexti = move[0][0];
      nextj = move[0][1];

      tempState = Util.copyMatrix(currentState);

      // next possible state
      temp = tempState[stari][starj];
      tempState[stari][starj] = tempState[nexti][nextj];
      tempState[nexti][nextj] = temp;

      if( isUnique(uniqueMatrixesList, tempState) ){
        validMovesList.remove(nextMove);
        nextMove = rand.nextInt(validMovesList.size());
        continue;
      }

      uniqueMatrixesList.add(tempState);
      currentState = copyMatrix(uniqueMatrixesList.get(uniqueMatrixesList.size()-1));

      stari = nexti;
      starj = nextj;
      validMovesList = Util.getValidMoves(stari,starj);
      nextMove = rand.nextInt(validMovesList.size());
      d++;
    }

    return currentState;
  }

  private static boolean isUnique(ArrayList<int[][]> uniqueMatrixesList, int[][] nextState) {
    for (int[][] m : uniqueMatrixesList) {
      if( isSameMatrix(m, nextState) ){
        return true;
      }
    }
    return false;
  }

	public static ArrayList<int[][]> getValidMoves(int stari, int starj){
		ArrayList<int[][]> validMoves = new ArrayList<>();
		int[][] possibleMoves = { {1,0}, {0,1}, {-1,0}, {0,-1}, };
		int nexti;
		int nextj;
		
		for( int i=0; i<4; i++ ){ // check all possible moves!
			nexti = possibleMoves[i][0]+stari;
			nextj = possibleMoves[i][1]+starj;
			
			if( Util.isValidMove(nexti,nextj) ){
				int[][] move = {{nexti,nextj}};
				validMoves.add(move);
			}
		}

		return validMoves;
	}
	
	public static int[] locateElement(int[][] matrix, int element){
		int i,j;
		int rows = matrix.length;
		int cols = matrix[0].length;
		int[] indeces = new int[2]; // (indeces[0],indeces[1])
		
		for( i=0; i<rows; i++ ){
			for( j=0; j<cols; j++ ){
				if( matrix[i][j] == element ){
					indeces[0] = i;
					indeces[1] = j;
				}
			}
		}
		
		return indeces;
	}
	
	public static boolean isValidMove(int i, int j){
    return !(i < 0 || i > 3 || j < 0 || j > 3);
  }

  public static void printMatrix(int[][] m, String msg){
    int i,j;
    int rows = m.length;
    int cols = m[0].length;

    System.out.printf("\n%s : \n", msg);
    for( i=0; i<rows; i++ ){
      for( j=0; j<cols; j++ ){
        System.out.printf("%2d ", m[i][j]);
      }
      System.out.printf("\n");
    }
  }

  public static int[][] copyMatrix(int[][] m) {
		int[][] state = new int[4][4];
		int i,j;
		int rows = m.length;
		int cols = m[0].length;
		
		for( i=0; i<rows; i++ ){
			for( j=0; j<cols; j++ ){
				state[i][j] = m[i][j];
			}
		}
		
		return state;
	}
	
	public static boolean isSameMatrix(int[][] m, int[][] n) {
		// m and n have to have same rows and cols
		int rows = m.length;
		int cols = m[0].length;
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if( m[i][j] != n[i][j] ){
					return false;
				}
			}
		}
		return true;
	}
}
