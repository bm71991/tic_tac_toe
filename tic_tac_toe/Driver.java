import java.util.ArrayList;
import java.util.Scanner;
public class Driver
{
	public static void main (String[] args)
	{
		TicTacToe game = new TicTacToe();
		Scanner scan = new Scanner(System.in);

		System.out.println("Would you like to play first? (y or n)");
		String choice = scan.next();

		if (choice.equals("y"))
			game.start("O");
		else
			game.start("X");
	}
}

