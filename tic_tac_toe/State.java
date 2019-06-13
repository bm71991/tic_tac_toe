import java.util.ArrayList;

public class State 
{
	private String[] board; // 9 item array representation of tic tac toe board.
	private int movesLeft;


	//calls State constructor with an "empty board" and 9 moves left.
	public State()
	{
		String [] emptyBoard = {" ", " ", " ",
		 			" ", " ", " ",
		 			" ", " ", " "};
		this.board = emptyBoard;
		this.movesLeft = 9;
	}

	public State (String[] board, int movesLeft)
	{
		this.board = board;
		this.movesLeft = movesLeft;
	}

	public void putX(int index)
	{
		board[index] = "X";
		movesLeft--;
	}

	public void putO(int index)
	{
		board[index] = "O";
		movesLeft--;
	}

	public boolean isLegal(int moveIndex)
	{
		boolean result = false;

		if ((moveIndex <= 8) && (moveIndex >= 0) &&
			(board[moveIndex] == " "))	{
			result = true;
		}
		
		return result;
	}

	public String[] getBoard()
	{
		return board;
	}

	public int getMovesLeft()
	{
		return movesLeft;
	}

	/*****************************************
	If a terminal state has been reached,
	returns the utility value of that state.
	Possible values: 
	 1: X wins,
	-1: O wins,
	 0: a draw.
	****************************************/
	public Integer terminalTest()
	{
		Integer result = null;

		if (checkForWin("X"))
			result = 1;
		else if (checkForWin("O"))
			result = -1;
		else if (movesLeft == 0)
			result = 0;
		// System.out.println("TERMINAL TEST: " + result);

		return result;
	}

	private boolean checkForWin(String letter)
	{			
		boolean result = false;

		// horizontal win patterns
		if ((board[0] == letter && board[1] == letter && board[2] == letter) ||
				(board[3] == letter && board[4] == letter && board[5] == letter) ||
				(board[6] == letter && board[7] == letter && board[8] == letter) ||
				//diagonal win patterns
				(board[0] == letter && board[4] == letter && board[8] == letter) ||
				(board[2] == letter && board[4] == letter && board[6] == letter) ||
				//vertical win patterns
				(board[0] == letter && board[3] == letter && board[6] == letter) ||
				(board[1] == letter && board[4] == letter && board[7] == letter) ||
				(board[2] == letter && board[5] == letter && board[8] == letter))
		{
			result = true;
		}

		return result;
	}

	public ArrayList<Integer> getAvailableMoves()
	{
		//list of available moves on the board by index
		ArrayList<Integer> availableMoves = new ArrayList<>();
		for (int index = 0; index < board.length; index++)
		{
			if (board[index] == " ")
				availableMoves.add(index);
		}

		return availableMoves;
	}

	public void printBoard()
	{
		System.out.println("\n" + board[0] + "|" + board[1] + "|" + board[2]);
		System.out.println("-----");
		System.out.println(board[3] + "|" + board[4] + "|" + board[5]);
		System.out.println("-----");
		System.out.println(board[6] + "|" + board[7] + "|" + board[8] + "\n");
	}
}
