class State {
    private int[][] boardState = new int[3][3];
    
    public State() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.boardState[i][j] = -1;
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
}