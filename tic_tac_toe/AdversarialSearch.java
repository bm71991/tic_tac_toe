import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AdversarialSearch
{
	/***********************************************
	Calculates the best utility for MIN (O) with the 
	available game positions left in boardState.
	***********************************************/
	private static Integer minValue(State boardState)
	{
		Integer terminalState = boardState.terminalTest();
		/***************************************
		If the game has reached a terminal state,
		return the utility value of that state.
		***************************************/
		if (terminalState != null) 
			return terminalState;
		/*******************************
		Start utilty value at +infinity
		(anything above 1).
		******************************/
		Integer minUtilityValue = 2;

		/***************************
		Get available moves for this
		board state.
		***************************/
		ArrayList<Integer> availableMoves = boardState.getAvailableMoves();
		
		/**************************************************************************
		For each available move, create a new state from playing the available
		move, and then find the utility value for this state. If the utility
		value for this state is smaller than previous states made from 
		previously iterated moves (because MIN will always play the move
		with the smallest utility value), then this state is the current utility
		value.
		*************************************************************************/
		for (Integer currentMove : availableMoves)
		{
			State newState = new State(copyStringArray(boardState.getBoard()),
																 boardState.getMovesLeft());
			
			newState.putO(currentMove);
			minUtilityValue = Math.min (minUtilityValue, maxValue(newState));
		}
		return minUtilityValue;
	}

	/**************************************************
	Calculates the best utility for MAX (X) with the 
	available game positions left in boardState.
	**************************************************/
	private static Integer maxValue(State boardState)
	{
		Integer terminalState = boardState.terminalTest();

		/***************************************
		If the game has reached a terminal state,
		return the utility value of that state.
		***************************************/
		if (terminalState != null) 
			return terminalState;

		/*******************************
		Start utilty value at -infinity
		(anything below -1).
		******************************/
		Integer maxUtilityValue = -2;

		/***************************
		Get available moves for this
		board state.
		***************************/
		ArrayList<Integer> availableMoves = boardState.getAvailableMoves();	

		/**************************************************************************
		For each available move, create a new state from playing the available
		move, and then find the utility value for this state. If the utility
		value for this state is larger than previous states made from 
		previously iterated moves (because MAX will always play the move
		with the highest utility value), then this state is the current utility
		value.
		*************************************************************************/
		for (Integer currentMove : availableMoves)
		{
			State newState = new State(copyStringArray(boardState.getBoard()),
																 boardState.getMovesLeft());
			newState.putX(currentMove);

			maxUtilityValue = Math.max (maxUtilityValue, minValue(newState));
		}
		return maxUtilityValue;
	}
	
	/**************************************************************
	Returns the best possible move (an index of a space on the
	board) for X.
	**************************************************************/
	public static Integer minimax(State boardState)
	{
		/* Get available moves for MAX to play */
		ArrayList<Integer> availableMoves = boardState.getAvailableMoves();
		/*********************************************************
		Make an empty list of MoveState objects, which will record
		the utility value for every index at which MAX can play.
		*********************************************************/
		ArrayList<MoveState> moveStates = new ArrayList<>();

		/****************************************************************************************
		For each available move to MAX, make a new State (newState) object to represent
		the state of the board after the move has been made. Store the utility
		value of this new State in a MoveState object (newMoveState), and then add the MoveState
		object to the list of MoveState objects.
		****************************************************************************************/
		for (Integer moveIndex: availableMoves)
		{
			MoveState newMoveState = new MoveState(moveIndex);
			State newState = new State(copyStringArray(boardState.getBoard()),
																 boardState.getMovesLeft());
			newState.putX(moveIndex);

			/***********************************************************************
			Added to pick the move which has a utility value of 1 AND is the fastest
			to a terminal state. 
			***********************************************************************/
			if (newState.terminalTest() != null && newState.terminalTest() == 1)
				return moveIndex;

			/******************************************************
			The utility value of the newState will be the 
			minimum utility value of its children (Assume MIN will
			pick optimally, which would result in MIN choosing the
			action which results in the smallest utility value).
			******************************************************/
			newMoveState.setUtilityValue(minValue(newState));

			moveStates.add(newMoveState);
		}
		/********************************************************
		Since the list may have more than one MoveState with the
		max utility value, the moveStates list is shuffled so 
		that the same move is not always played in a situation
		where there are multiple optimal moves.
		*********************************************************/
		Collections.shuffle(moveStates);
		MoveState bestAction = getMaxMoveState(moveStates);

		return bestAction.getMoveLocation();
	}

	private static String[] copyStringArray(String[] array)
	{
		String[] copiedArray = new String[array.length];

		for (int index = 0; index < array.length; index++)
			copiedArray[index] = array[index];

		return copiedArray;
	}

	/**********************************************************************
	Returns from a list of MoveState objects the MoveState object with the
	highest utility value. If multiple MoveState objects have the same 
	highest utility value, it returns the first object in the list with the
	highest value.
	**********************************************************************/
	private static MoveState getMaxMoveState(ArrayList<MoveState> moveStates)
	{
		MoveState maxMoveState = moveStates.get(0); //get the first moveState in moveStates

		/***************************************************
		Iterate through all MoveStates in the list. If 
		currentMoveState's utility value is higher than
		the maxMoveState's utility value, set maxMoveState to
		currentMoveState.
		currentMoveState will not be set to maxMoveState if
		the utility values of currentMoveState and maxMoveState 
		are equal.
		****************************************************/
		for (int index = 1; index < moveStates.size(); index++)
		{
			MoveState currentMoveState = moveStates.get(index);

			if (currentMoveState.getUtilityValue() > maxMoveState.getUtilityValue())
				maxMoveState = currentMoveState;
		}
		System.out.println("\nMAX's utility value is " + maxMoveState.getUtilityValue());
		return maxMoveState;
	}

	private static class MoveState
	{
		private Integer utilityValue;
		private Integer moveLocation;

		public MoveState(Integer moveLocation)
		{
			utilityValue = null;
			this.moveLocation = moveLocation;
		}

		private Integer getUtilityValue()
		{
			return utilityValue;
		}

		private Integer getMoveLocation()
		{
			return moveLocation;
		}

		private void setUtilityValue(Integer utilityValue)
		{
			this.utilityValue = utilityValue;
		}

		private void setMoveLocation(Integer moveLocation)
		{
			this.moveLocation = moveLocation;
		}
	}
}