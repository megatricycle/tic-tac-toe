import java.util.ArrayList;
import java.util.List;

public class TicTacToeAI {
    int[][] state = new int[3][3];
    List<PointsUtility> movesList = new ArrayList<PointsUtility>();
    int depthCut = 3;
    
    public TicTacToeAI(int[][] state){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.state[i][j] = state[i][j];
            }
        }
    }

    public Coordinates getAImove(){
        // check corners
        if(this.state[1][0] == 2 && this.state[2][1] == 2 && this.state[2][0] == 0) {
            return new Coordinates(2, 0);
        }
        else if(this.state[1][0] == 2 && this.state[0][1] == 2 && this.state[0][0] == 0) {
            return new Coordinates(0, 0);
        }
        else if(this.state[1][2] == 2 && this.state[0][1] == 2 && this.state[0][2] == 0) {
            return new Coordinates(0, 2);
        }
        else if(this.state[1][2] == 2 && this.state[2][1] == 2 && this.state[2][2] == 0) {
            return new Coordinates(2, 2);
        }
        
        // do normal minmax
        alphaBetaMinimax(this.state, true, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        return bestMove(movesList);//Returns the coordinate of the best move.
    }
    
    private Coordinates bestMove(List<PointsUtility> movesToWin){
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

    private int alphaBetaMinimax(int[][] currentState, boolean isAI, int alpha, int beta, int depth){
        if(beta<=alpha){ if(isAI) return Integer.MAX_VALUE; else return Integer.MIN_VALUE; }
        if(isGameOver(currentState) || depth == depthCut) return evaluateBoard(currentState);
        
        if(isDraw(currentState)) return 0;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE; 
        
        if(depth == 0) movesList.clear();
        
        for(int[][] child : getNextStates(currentState, isAI)){
            int utility = 0;
            if(isAI){
                utility = alphaBetaMinimax(child, !isAI, alpha, beta, depth + 1);
                max = Integer.max(max,utility);
                alpha = Integer.max(alpha,utility);
                if(depth == 0) movesList.add(new PointsUtility(getPoints(currentState,child), utility));
            }else{
                utility = alphaBetaMinimax(child, !isAI, alpha, beta, depth + 1);
                min = Integer.min(min, utility);
                beta = Integer.min(beta, utility);
            }
            
            if(utility == Integer.MAX_VALUE || utility == Integer.MIN_VALUE) break;
        }
        
        if(isAI) return max;
        else return min;

    }

    private Coordinates getPoints(int[][] currentState, int[][] childState){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(currentState[i][j]!=childState[i][j]){
                    return new Coordinates(i,j);
                }
            }
        }
        return new Coordinates(0,0);
    }

    private List<int [][]> getNextStates(int[][] currentState, boolean isAI){
        List<int[][]> nextStates = new ArrayList<int[][]>();
        int player = 2;
        if(isAI) player = 1;
        for(Coordinates move: getPossibleMoves(currentState)){
            nextStates.add(getNewState(move,player,currentState));
        }
        return nextStates;
    }

    private List<Coordinates> getPossibleMoves(int[][] currentState){
        List<Coordinates> points = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(currentState[i][j] == 0) points.add(new Coordinates(i,j));
            }
        }
        return points;
    }

    private int[][] getNewState(Coordinates point, int player, int[][] currentState){
        int[][] newState = new int[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                newState[i][j] = currentState[i][j];
            }
        }
        newState[point.getX()][point.getY()] = player;
        return newState;
    }

    private PointsUtility convertToMove(int[][] currentState, int[][] nextState, int utility){
        Coordinates winningCoordinate = new Coordinates(0,0);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(currentState[i][j] != nextState[i][j]){
                    winningCoordinate = new Coordinates(i,j);
                }
            }
        }
        return new PointsUtility(winningCoordinate, utility);
    }

    private int changeInUtility(int X, int O){
        if(X == 3)
            return 100;
        else if(X == 2 && O == 0)
            return 10;
        else if(X == 1 && O == 0)
            return 1;
        else if(O == 3)
            return -100;
        else if(X == 0 && O == 2)
            return -10;
        else if(X == 0 && O == 1)
            return -1;
        else
            return 0;
    }

    private int evaluateBoard(int[][] currentState){
        int evaluateBoard = 0;
        int X = 0;
        int O = 0;
        
        for(int i = 0; i < 3; i++){
            X = 0;
            O = 0;
            for(int j = 0; j < 3; j++){
                if(currentState[i][j] == 1)
                    X++;
                else if(currentState[i][j] == 2)
                    O++;
            }
            evaluateBoard += changeInUtility(X,O);
        }
            
        for(int i = 0; i < 3; i++){
            X = 0;
            O = 0;
            for(int j = 0; j < 3; j++){
                if(currentState[j][i] == 1)
                    X++;
                else if(currentState[j][i] == 2)
                    O++;
            }
            evaluateBoard += changeInUtility(X,O);
        }
        
        X = 0;
        O = 0;
        
        for(int i = 0; i < 3; i++){
            if(currentState[i][i] == 1)
                X++;
            else if(currentState[i][i] == 2)
                O++;
        }
        evaluateBoard += changeInUtility(X,O);
        
        X = 0;
        O = 0;
        
        for(int i = 0, j = 2; i < 3; i++, j--){
            if(currentState[i][j] == 1)
                X++;
            else if(currentState[i][j] == 2)
                O++;
        }
        evaluateBoard += changeInUtility(X,O);
        return evaluateBoard;        
    }
    
    private boolean isGameOver(int[][] state){
        return (this.winnerAI(state) || this.winnerUser(state));
    }

    private boolean isDraw(int[][] state){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(state[i][j]==0){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean winnerAI(int[][] state){
        if ((state[0][0] == state[1][1] && state[0][0] == state[2][2] && state[0][0] == 1) 
            || (state[0][2] == state[1][1] && state[0][2] == state[2][0] && state[0][2] == 1)) {
            return true;
        }
        for (int i = 0; i < 3; ++i){
            if (((state[i][0] == state[i][1] && state[i][0] == state[i][2] && state[i][0] == 1)
                    || (state[0][i] == state[1][i] && state[0][i] == state[2][i] && state[0][i] == 1))) {
                return true;
            }
        }
        return false;
    }

    private boolean winnerUser(int[][] state){
        if ((state[0][0] == state[1][1] && state[0][0] == state[2][2] && state[0][0] == 2) 
            || (state[0][2] == state[1][1] && state[0][2] == state[2][0] && state[0][2] == 2)) {
            return true;
        }
        for (int i = 0; i < 3; ++i){
            if ((state[i][0] == state[i][1] && state[i][0] == state[i][2] && state[i][0] == 2)
                    || (state[0][i] == state[1][i] && state[0][i] == state[2][i] && state[0][i] == 2)) {
                return true;
            }
        }
        return false;
    }

}
