import java.util.ArrayList;
import java.util.List;

public class Solver {
	int[][] initialState = new int[3][3];
	List<WinMove> winningMoveList = new ArrayList<WinMove>();
	int depthLimit = 3;//Hard coded depth limit.
	
	public Solver(int[][] initialState)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				this.initialState[i][j] = initialState[i][j];
			}
		}
	}
	
	/*
	 * Gets the best move using minimax.
	 * */
	public Coordinate solve()
	{
		minimax(this.initialState,true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);//First recursive call.	
		return getBestWinningMove(winningMoveList);//Returns the coordinate of the best move.
	}
	/*
	 * Gets the best winning move from the list.
	 * */
	public Coordinate getBestWinningMove(List<WinMove> winningMoveList)
	{
		Coordinate bestWinningMove = new Coordinate(0,0);//initialize the Coordinate
		int best = Integer.MIN_VALUE;//initialize best value.
		
		for(WinMove Possible : winningMoveList)//for each winning move, get the best move.
		{
			if(best < Possible.utility)
			{
				best = Possible.utility;
				bestWinningMove = Possible.coordinate;
			}
		}
		return bestWinningMove;
		
	}
	/*
	 * Gets the possible coordinates to move.
	 * */
	public List<Coordinate> getPossibleMoves(int[][] currentState)
	{
		List<Coordinate> coordinateList = new ArrayList<>();
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(currentState[i][j] == 0)
					coordinateList.add(new Coordinate(i,j));
			}
		}
		
		return coordinateList;
	}
	/*
	 * Execute move.
	 * */
	
	public int[][] move(Coordinate coordinate, int player, int[][] currentState)
	{
		int[][] newState = new int[3][3];
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				newState[i][j] = currentState[i][j];
			}
		}
		newState[coordinate.x][coordinate.y] = player;
		return newState;
	}
	/*
	 * Generates next states.
	 * */
	public List<int[][]> generateNextStates(int [][] currentState, boolean ai)
	{
		List<int[][]> nextStates = new ArrayList<int[][]>();
		int number;
		if(ai)
			number = 2;
		else
			number = 1;
		
		for(Coordinate coor: getPossibleMoves(currentState))
		{
			nextStates.add(move(coor,number,currentState));
		}
		
		return nextStates;
	}
	/*
	 * Converts the state to a coordinate with its utility.
	 * */

	public WinMove convertToMove(int[][] currentState, int[][] nextState, int currentUtility)
	{
		Coordinate winningCoordinate = new Coordinate(0,0);
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(currentState[i][j] != nextState[i][j])
				{
					winningCoordinate = new Coordinate(i,j);
				}
			}
		}
		return new WinMove(winningCoordinate, currentUtility);
		
	}
	/*
	 * Checks if the game is at an end state.
	 * */
	public boolean weHaveAWinner(int[][] currentState)
	{
		if(aiWin(currentState))
			return true;
		if(playerWin(currentState))
			return true;
		
		return false;
	}
	/*
	 * Function for getting the heuristic value each line.
	 * */
	public int heuristic(int X, int O)
	{
		if(O == 3)
			return 100;
		else if(O == 2 && X == 0)
			return 10;
		else if(O == 1 && X == 0)
			return 1;
		else if(X == 3)
			return -100;
		else if(O == 0 && X == 2)
			return -10;
		else if(O == 0 && X == 1)
			return -1;
		else
			return 0;
	}
	/*
	 * Gets the total heuristic value of the board.
	 * */
	public int stateValue(int[][] currentState)
	{
		int stateValue = 0;
		int X = 0;
		int O = 0;
		
		for(int i = 0; i < 3; i++)
		{
			X = 0;
			O = 0;
			for(int j = 0; j < 3; j++)
			{
				if(currentState[i][j] == 1)
					X++;
				else if(currentState[i][j] == 2)
					O++;
			}
			stateValue += heuristic(X,O);
		}
			
		for(int i = 0; i < 3; i++)
		{
			X = 0;
			O = 0;
			for(int j = 0; j < 3; j++)
			{
				if(currentState[j][i] == 1)
					X++;
				else if(currentState[j][i] == 2)
					O++;
			}
			stateValue += heuristic(X,O);
		}
		
		X = 0;
		O = 0;
		
		for(int i = 0; i < 3; i++)
		{
			if(currentState[i][i] == 1)
				X++;
			else if(currentState[i][i] == 2)
				O++;
		}
		stateValue += heuristic(X,O);
		
		X = 0;
		O = 0;
		
		
		for(int i = 0, j = 2; i < 3; i++, j--)
		{
			if(currentState[i][j] == 1)
				X++;
			else if(currentState[i][j] == 2)
				O++;
		}
		stateValue += heuristic(X,O);
		

		
		return stateValue;
		
		
	}
	/*
	 * Minimax function.
	 * */
	public int minimax(int[][] currentState, boolean ai, int depth, int alpha, int beta)
	{	
	
		if(beta<=alpha){ System.out.println("Pruning at depth = "+depth);if(ai) return Integer.MAX_VALUE; else return Integer.MIN_VALUE; }
        
		
		if(weHaveAWinner(currentState) || depth == depthLimit) // checks if the board is an end state or the depth limit defined has been reached
			return stateValue(currentState);//returns the heuristic value of the board
		
		
		if(draw(currentState))//if the board is at a draw state, returns 0.
			return 0;
		
		
		
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE; // variable holders for min max
		
		if(depth == 0)//if its the root
			winningMoveList.clear();//clears winningMoveLists to refresh.
		
		for(int[][] nextState : generateNextStates(currentState, ai))//for each next possible moves by the AI
		{
			int currentUtility = 0;
			if(ai)// MAX
			{
				currentUtility = minimax(nextState, !ai, depth + 1, alpha, beta);//recursive call.
				
				max = Integer.max(max,currentUtility);//maximization node.
				alpha = Integer.max(alpha,currentUtility);//alpha gets the max utility.
			
				if(depth == 0)//if root node.
					winningMoveList.add(convertToMove(currentState,nextState, currentUtility));//adds each move to determine the winning move.
			}
			else// MIN
			{
				currentUtility = minimax(nextState, !ai, depth + 1, alpha, beta);//recursive call.
				
				min = Integer.min(min, currentUtility);//minimization node.
				beta = Integer.min(beta, currentUtility);//beta gets the min utility.
			}
			
			if(currentUtility == Integer.MAX_VALUE || currentUtility == Integer.MIN_VALUE)//pruning check.
				break;//Do not go to siblings.
		}
		
		if(ai)//if maximization node
			return max;//return the max value
		else
			return min;//return the min value
		
		

	}
	/*
	 * Checks if the board is in a draw state.
	 * */
	public boolean draw(int[][] currentState)
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(currentState[i][j] == 0)
					return false;
			}
		}
		return true;
	}
	/*
	 * Checks if the player is already a winner, which will not happen.
	 * */
	public boolean playerWin(int[][] currentState)
	{
		 if ((currentState[0][0] == currentState[1][1] && currentState[0][0] == currentState[2][2] && currentState[0][0] == 1) || (currentState[0][2] == currentState[1][1] && currentState[0][2] == currentState[2][0] && currentState[0][2] == 1)) {
	            return true;
	        }
		 
		 for(int i = 0; i < 3; i++)
		 {
			 if((currentState[i][0] == currentState[i][1] && currentState[i][0] == currentState[i][2] && currentState[i][0] == 1)||(currentState[0][i] == currentState[1][i] && currentState[0][i] == currentState[2][i] && currentState[0][i] == 1))
				 return true;
		 }
		 return false;
	}
	/*
	 * Checks if the AI is already a winner. 
	 * */
	public boolean aiWin(int[][] currentState) 
	{
		 if ((currentState[0][0] == currentState[1][1] && currentState[0][0] == currentState[2][2] && currentState[0][0] == 2) || (currentState[0][2] == currentState[1][1] && currentState[0][2] == currentState[2][0] && currentState[0][2] == 2)) {
	            return true;
	        }
		 
		 for(int i = 0; i < 3; i++)
		 {
			 if((currentState[i][0] == currentState[i][1] && currentState[i][0] == currentState[i][2] && currentState[i][0] == 2)||(currentState[0][i] == currentState[1][i] && currentState[0][i] == currentState[2][i] && currentState[0][i] == 2))
				 return true;
		 }
		 return false;
	}

}
