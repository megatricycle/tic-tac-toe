import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
 
    public static void main(String[] args) { 

        State state = new State();
        Random rand = new Random();
        Scanner scan = new Scanner(System.in);

        state.printBoardState();

        System.out.println("Who's gonna move first? (1)Computer (2)User: ");
        int choice = scan.nextInt();
        if (choice == 1) {
            state.playerMove(rand.nextInt(3), rand.nextInt(3), 1);
            state.printBoardState();
        }

        while (!state.isGameOver()) {
            System.out.println("Your move: ");
            int x = scan.nextInt();
            int y = scan.nextInt();
            System.out.println("x:" +x+" y: "+y);
            state.playerMove(x, y, 2); //2 for O and O is the user
            state.printBoardState();
            if (state.isGameOver()) break;
            
            TicTacToeAI solver = new TicTacToeAI(state);
            Coordinates move = solver.getAImove();
            state.playerMove(move.getX(), move.getY(), 1);
            //Solver solver = new Solver(state.getBoardState());
            // Coordinate move = solver.solve();
            // state.playerMove(move.x, move.y, 1);
            state.printBoardState();
        }
        if (state.winnerAI()) {
            System.out.println("Unfortunately, you lost!");
        } else if (state.winnerUser()) {
            System.out.println("You win!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}