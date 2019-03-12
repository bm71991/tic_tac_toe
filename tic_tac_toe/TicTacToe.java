import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToe
{
	private enum Player {X, O};
	private Player currentTurn;
	private State gameBoard;
	private boolean gameOver;

	public TicTacToe()
	{
		currentTurn = null;
		gameBoard = new State();
		gameOver = false;
	}

	public void start(String firstTurn)
	{
		if (firstTurn.equals("O"))
			currentTurn = Player.O;
		else 
			currentTurn = Player.X;

		while (!gameOver)
		{
			makeMove();
			checkGameState();
			switchTurns();
		}
	}

	private void makeMove()
	{
		boolean validMove = false;
		Scanner scan = new Scanner(System.in);

		gameBoard.printBoard();

		if (currentTurn == Player.X)
		{
			System.out.println("Current player is X");
			Integer nextMove = AdversarialSearch.minimax(gameBoard);
			gameBoard.putX(nextMove); 
		}
		else if (currentTurn == Player.O)
		{
			System.out.println("Current player is O");
			System.out.println("Please pick a space to play: enter index.");
			while (validMove == false)
			{
				int nextMove = scan.nextInt();
				if (gameBoard.isLegal(nextMove))
				{
					validMove = true;
					gameBoard.putO(nextMove);
				}
				else
					System.out.println("Index entered was not valid. Please try again.");
			}
			System.out.println();
		}
	}

	private void switchTurns()
	{
		if (currentTurn == Player.X)
			currentTurn = Player.O;
		else
			currentTurn = Player.X;
	}

	private void checkGameState()
	{
		Integer currentState = gameBoard.terminalTest();

		if (currentState != null)
		{
			gameBoard.printBoard();
			gameOver = true;

			if (currentState == 1)
				System.out.println("\nX wins the game.");
			else if (currentState == -1)
				System.out.println("\nO wins the game");
			else if (currentState == 0)
				System.out.println("\nIt is a draw.");
		}
	}
}