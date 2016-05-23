import java.util.ArrayList;
import java.util.List;

public class State {
    private int[][] boardState = new int[3][3];
    
    //initial state (start of the game)
    public State() {
        this.reset();
    }
    //initialization with parameters to copy
    public State(State state) {
        int[][] boardState = state.getBoardState();
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.boardState[i][j] = boardState[i][j];
            }
        }
    }
    
    //returns the current board state
    public int[][] getBoardState() {
        return this.boardState;
    }

    //initialize the board to 0
    public void printBoardState() {
        System.out.println();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print(this.boardState[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //fill the move of the player
    public void playerMove(int x, int y, int player) {
        if(this.boardState[x][y] == 0 ){
            this.boardState[x][y] = player;
        }
    }
    
    //initialize the board to 0
    public void reset() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.boardState[i][j] = 0;
            }
        }
    }
    
    //get the new states for the possible moves
    public List<State> getNextStates(boolean isAI) {
        List<State> states = new ArrayList<State>();
        int player = 2;
        if(isAI) player = 1;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(this.boardState[i][j] == 0) {
                    State newState = new State(this);
                    
                    newState.playerMove(i, j, player);
                    
                    states.add(newState);
                }
            }
        }
        return states;
    }
    
    public String toString() {
        String out = "";
        
        out += "\n\n";
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                out += "[" + (this.boardState[i][j] > 0? this.boardState[i][j]: " ") + "]";
            }
            out += "\n";
        }
        
        out += "\n";
        
        return out;
    }

    public boolean isGameOver(){
        return (this.winnerAI() || this.winnerUser() || this.isDraw());
    }

    public boolean isDraw(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(this.boardState[i][j]==0){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean winnerAI(){
        if ((this.boardState[0][0] == this.boardState[1][1] && this.boardState[0][0] == this.boardState[2][2] && this.boardState[0][0] == 1) 
            || (this.boardState[0][2] == this.boardState[1][1] && this.boardState[0][2] == this.boardState[2][0] && this.boardState[0][2] == 1)) {
            return true;
        }
        for (int i = 0; i < 3; ++i){
            if (((this.boardState[i][0] == this.boardState[i][1] && this.boardState[i][0] == this.boardState[i][2] && this.boardState[i][0] == 1)
                    || (this.boardState[0][i] == this.boardState[1][i] && this.boardState[0][i] == this.boardState[2][i] && this.boardState[0][i] == 1))) {
                return true;
            }
        }
        return false;
    }

    public boolean winnerUser(){
        if ((this.boardState[0][0] == this.boardState[1][1] && this.boardState[0][0] == this.boardState[2][2] && this.boardState[0][0] == 2) 
            || (this.boardState[0][2] == this.boardState[1][1] && this.boardState[0][2] == this.boardState[2][0] && this.boardState[0][2] == 2)) {
            return true;
        }
        for (int i = 0; i < 3; ++i){
            if ((this.boardState[i][0] == this.boardState[i][1] && this.boardState[i][0] == this.boardState[i][2] && this.boardState[i][0] == 2)
                    || (this.boardState[0][i] == this.boardState[1][i] && this.boardState[0][i] == this.boardState[2][i] && this.boardState[0][i] == 2)) {
                return true;
            }
        }
        return false;
    }
}