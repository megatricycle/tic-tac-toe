import java.util.ArrayList;
import java.util.List;

public class TicTacToeAI{

	State initialState = new State();
	List<PointsUtility> movesToWin = new ArrayList<PointsUtility>();
	int depthCut = -1;	//depth on which the generation of tree will stop

	public TicTacToeAI(State initialState){
		this.initialState = new State(initialState);
	}

	public Coordinates getAImove(){
		alphaBetaMinimax(this.initialState, true, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		return bestMove(movesToWin);
	}

	public Coordinates bestMove(List<PointsUtility> movesToWin){
		int maxUtility = Integer.MIN_VALUE;
		Coordinates bestCoordinate = new Coordinates(0,0);
		for(PointsUtility move: movesToWin){
			if(move.utility > maxUtility){
				maxUtility = move.utility;
				bestCoordinate = move.point;
			}
		}
		return bestCoordinate;
	}

	public int alphaBetaMinimax(State currentState, boolean isAI, int alpha, int beta, int depth){
		if(beta<=alpha){ System.out.println("Pruning at depth = "+depth);if(isAI) return Integer.MAX_VALUE; else return Integer.MIN_VALUE; }
        

		if(depth==depthCut || currentState.isGameOver()) return evaluateBoard(currentState.getBoardState());
		if(currentState.isDraw()) return 0;

		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

		if(depth == 0) movesToWin.clear();

		for(State childState: currentState.getNextStates(isAI)){
			int utility = 0;
			if(isAI){ //maximizer
				utility = alphaBetaMinimax(childState, false, alpha, beta, depth+1);
				max = Math.max(utility, max);
				alpha = Math.max(utility, alpha);

				if(depth == 0) movesToWin.add(new PointsUtility(getPoints(currentState, childState), utility));
			}else{	//minimizer
				utility = alphaBetaMinimax(childState, true, alpha, beta, depth+1);
				min = Math.max(utility, min);
				beta = Math.max(utility, beta);
			}

			if(utility == Integer.MAX_VALUE || utility == Integer.MIN_VALUE) break;
		}

		if(isAI) return max;
		else return min;
        
	}

	public Coordinates getPoints(State currentState, State childState){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(currentState.getBoardState()[i][j]!=childState.getBoardState()[i][j]){
					return new Coordinates(i,j);
				}
			}
		}
		return new Coordinates(0,0);
	}

	public int evaluateBoard(int[][] board) {
        int utility = 0;

        //Check all rows
        for (int i = 0; i < 3; i++) {
            int blank = 0;
            int X = 0;
            int O = 0;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) blank++;
                else if (board[i][j] == 1) X++;
                else O++;
            } 
            utility+=changeInUtility(X, O); 
        }

        //Check all columns
        for (int j = 0; j < 3; j++) {
            int blank = 0;
            int X = 0;
            int O = 0;
            for (int i = 0; i < 3; i++) {
                if (board[i][j] == 0) blank++;
                else if (board[i][j] == 1) X++;
                else O++;
            }
            utility+=changeInUtility(X, O);
        }

        int blank = 0;
        int X = 0;
        int O = 0;

        //Check diagonal (first)
        for (int i = 0, j = 0; i < 3; i++, j++) {
            if (board[i][j] == 1) X++;
            else if (board[i][j] == 2) O++;
            else blank++;
        }

        utility+=changeInUtility(X, O);

        blank = 0;
        X = 0;
        O = 0;

        //Check Diagonal (Second)
        for (int i = 2, j = 0; i > -1; i--, j++) {
            if (board[i][j] == 1) X++;
            else if (board[i][j] == 2) O++;
            else blank++;
        }

        utility+=changeInUtility(X, O);

        return utility;
    }

    private int changeInUtility(int X, int O){
        int change;

        if (X == 3) {
            change = 100;
        } else if (X == 2 && O == 0) {
            change = 10;
        } else if (X == 1 && O == 0) {
            change = 1;
        } else if (O == 3) {
            change = -100;
        } else if (O == 2 && X == 0) {
            change = -10;
        } else if (O == 1 && X == 0) {
            change = -1;
        } else {
            change = 0;
        } 
        return change;
    }
    


}