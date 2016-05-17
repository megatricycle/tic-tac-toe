import java.util.ArrayList;

class State {
    private int[][] boardState = new int[3][3];
    
    public State() {
        this.reset();
    }
    
    public State(State state) {
        int[][] boardState = state.getBoardState();
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.boardState[i][j] = boardState[i][j];
            }
        }
    }
    
    public int[][] getBoardState() {
        return this.boardState;
    }
    
    public void playerMove(int x, int y, int player) {
        if(this.boardState[x][y] == -1) {
            this.boardState[x][y] = player;
        }
    }
    
    public void reset() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.boardState[i][j] = -1;
            }
        }
    }
    
    public ArrayList<State> getPossibleMoves(int player) {
        ArrayList<State> states = new ArrayList<State>();
        
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(this.boardState[i][j] == -1) {
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
                out += "[" + (this.boardState[i][j] > -1? this.boardState[i][j]: " ") + "]";
            }
            out += "\n";
        }
        
        out += "\n";
        
        return out;
    }
}