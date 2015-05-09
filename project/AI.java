import java.util.*;

public class AI{

	private int myColor;
	private int opColor;

	public AI(int myColor, int opColor){
		assert (myColor == 1 || myColor == 2);
		assert (opColor == 1 || myColor == 2);
		this.myColor = myColor;
		this.opColor = opColor;
	}

///*Checker[] checkers*/
	public int[] determineMove(List<Checker> checkers){
	//Minimax
		InternalRepresentation start = new InternalRepresentation(checkers,this.myColor);
		Move best = null;
		int maxVal = 0;

		if (start.isTerminal()){
			return new int[]{-1,-1,-1,-1};
		}

		for (Move m : start.validMoves){
			InternalRepresentation next = 
				mini(new InternalRepresentation(start,m,this.myColor),7);
			if (best == null) {
				best = m;
				maxVal = next.value;
			} else{
				if (next.value > maxVal){
					maxVal = next.value;
					best = m;
				}
			}
		}


		if (best.take){
			int[] toMove = new int[best.takes.size() + 4];
			toMove[0] = best.oldX;
			toMove[1] = best.oldY;
			toMove[2] = best.newX;
			toMove[3] = best.newY;
			int s = 4;
			for (Integer i : best.takes){
				toMove[s] = i;
				s ++;
			}
			return toMove;

		}
		else return new int[]{best.oldX,best.oldY,best.newX,best.newY};
	}

	private InternalRepresentation maxi(InternalRepresentation internalRep,int depth){
		if (depth <= 0 || internalRep.isTerminal()){
			return internalRep;
		} else {
			InternalRepresentation best = null;
			int maxVal = 0;

			for (Move m : internalRep.validMoves){
				InternalRepresentation next = 
					mini(new InternalRepresentation(internalRep,m,this.myColor),depth - 1);
				if (best == null) {
					best = next;
					maxVal = next.value;
				} else{
					if (next.value > maxVal){
						maxVal = next.value;
						best = next;
					}
				}
			}

			return best;
		}
	}

	private InternalRepresentation mini(InternalRepresentation internalRep, int depth){
		if (depth <= 0 || internalRep.isTerminal()){
			return internalRep;
		} else {
			InternalRepresentation worst = null;
			int minVal = 0;

			for (Move m : internalRep.validMoves){
				InternalRepresentation next = 
					maxi(new InternalRepresentation(internalRep,m,this.opColor),depth - 1);
				if (worst == null) {
					worst = next;
					minVal = next.value;
				} else{
					if (next.value < minVal){
						minVal = next.value;
						worst = next;
					}
				}
			}

			return worst;
		}
	}

	public void setColor(int color){
		this.myColor = color;
	}

	public void setOpColor(int color){
		this.opColor = color;
	}

//*************************************************************	
//*************************************************************
// classes to use for internal representation of minimax ******
//*************************************************************
//*************************************************************
	private class InternalRepresentation{
		public int me;
		public int meCount;
		public int meKing;
		public int opCount;
		public int opKing;
		public int value;
		public Integer[][] board;
		public List<Move> validMoves;

		public InternalRepresentation(List<Checker> checkers, int myColor){
			board = new Integer[8][8];
			for (int i = 0; i < 8; i++ ){
				for (int j = 0; j < 8; j++ ){
					board[i][j] = 0;
				}
			}
			this.me = myColor;
			for (Checker c : checkers){
				if (c.king) board[c.x-1][c.y-1] = c.player * 10; 
				else board[c.x-1][c.y-1] = c.player;
				if (c.player == me){
					meCount ++;
					if (c.king){
						meKing ++;
					}
				} else {
					opCount ++;
					if (c.king){
						opKing ++;
					}
				}
			}
			validMoves = new ArrayList<Move>();
			calculateValue();
			determineValidMoves(me);
		}

		public InternalRepresentation(InternalRepresentation oldR, Move move, int who){
			this.board = new Integer[8][8];
			for (int i = 0; i < 8; i++ ){
				for (int j = 0; j < 8; j++ ){
					board[i][j] = oldR.board[i][j];
				}
			}
			this.me = oldR.me;
			this.meCount = oldR.meCount;
			this.opCount = oldR.opCount;
			if (move.take){
				board[move.newX][move.newY] = board[move.oldX][move.oldY];
				board[move.oldX][move.oldY] = 0;
				assert move.takes.size() % 2 == 0;
				for (int i = 0; i < move.takes.size(); i += 2){
					int takenP = board[move.takes.get(i)][move.takes.get(i + 1)];
					board[move.takes.get(i)][move.takes.get(i+1)] = 0;
					if (takenP == me || takenP == me * 10) meCount--;
					else opCount --;
				}
			} else{
				if (move.newY == 7 || move.newY == 0) {
					if (board[move.oldX][move.oldY] % 10 != 0)
						board[move.newX][move.newY] = board[move.oldX][move.oldY] * 10;
					else board[move.newX][move.newY] = board[move.oldX][move.oldY];
				} else board[move.newX][move.newY] = board[move.oldX][move.oldY];
				board[move.oldX][move.oldY] = 0;
			}
			validMoves = new ArrayList<Move>();
			calculateValue();
			determineValidMoves(who);
		}

		private void calculateValue(){
			this.value = (meCount + meKing) - (opCount + opKing);
		}

		private void  determineValidMoves(int whoseMove){
			if (whoseMove == 1){
				determineRedMoves();
			}
			if (whoseMove == 2){
				determineBlackMoves();
			}
		}

		private void determineRedMoves(){
			boolean takeMove = false;
			for (int i = 0; i < 8; i++ ){
				for (int j = 0; j < 8; j++ ){
					if (board[i][j] != 0 && (board[i][j]) == 1){
						if (takeMove){
							List<Move> jump = getRedJump(i,j);
							if (jump != null){
								validMoves.addAll(jump);
							}
						} else{
							List<Move> jump = getRedJump(i,j);
							if (jump != null){
								validMoves.clear();
								validMoves.addAll(jump);
								takeMove = true;
							}
							if (!takeMove){
								List<Move> move = getRedMove(i,j);
								if (move != null){
									validMoves.addAll(move);
								}
							}
						}
					}

					if (board[i][j] != 0 && board[i][j] == 10){
						if (takeMove){
							List<Move> jump = getRedKingJump(i,j);
							if (jump != null){
								validMoves.addAll(jump);
							}
						} else{
							List<Move> jump = getRedKingJump(i,j);
							if (jump != null){
								validMoves.clear();
								validMoves.addAll(jump);
								takeMove = true;
							}
							if (!takeMove){
								List<Move> move = getKingMove(i,j);
								if (move != null){
									validMoves.addAll(move);
								}
							}
						}
					}
				}
			}
		}

		private List<Move> getRedJump(int i, int j){
			List<Move> jumps = new ArrayList<Move>();
			if (i + 1 < 8 && j + 1 < 8 && i + 2 < 8 && j + 2 < 8 && 
				board[i+1][j+1] == 2 && board[i+2][j+2] == 0){
				// jumps.add(new Move(i,j,i+2,j+2,true,i+1,j+1));
				List<Move> doub = getRedJump(i+2,j+2);
				if (doub != null){
					for (Move m : doub){
						Move jump = new Move(i,j,i+2,j+2,true);
						jump.takes.add(i+1);
						jump.takes.add(j+1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else{
					Move jump = new Move(i,j,i+2,j+2,true);
					jump.takes.add(i+1);
					jump.takes.add(j+1);
					jumps.add(jump);
				}
			}
			if (i - 1 >= 0 && j + 1 < 8 && i - 2 >= 0 && j + 2 < 8 && 
				board[i-1][j+1] == 2 && board[i-2][j+2] == 0){
				// jumps.add(new Move(i,j,i-2,j+2,true,i-1,j+1));
				List<Move> doub = getRedJump(i-2, j+2);
				if (doub != null){
					for (Move m : doub){
						Move jump = new Move(i,j,i-2,j+2,true);
						jump.takes.add(i-1);
						jump.takes.add(j+1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else {
					Move jump = new Move(i,j,i-2,j+2,true);
					jump.takes.add(i-1);
					jump.takes.add(j+1);
					jumps.add(jump);
				}
			}
			if (jumps.size() > 0){
				return jumps;
			} else{
				return null;
			}
		}

		private List<Move> getRedMove(int i, int j){

			List<Move> moves = new ArrayList<Move>();

			if (i + 1 < 8 && j + 1 < 8 && board[i+1][j+1] == 0){
				moves.add(new Move(i,j,i+1,j+1,false));
			}
			if (i - 1 >= 0 && j + 1 < 8 && board[i-1][j+1] == 0){
				moves.add(new Move(i,j,i-1,j+1,false));
			}
			if (moves.size() > 0){
				return moves;
			}
			else {
				return null;
			}
		}

		private List<Move> getRedKingJump(int i, int j){
			List<Move> jumps = new ArrayList<Move>();

			if (i + 1 < 8 && j + 1 < 8 && i + 2 < 8 && j + 2 < 8 && 
				board[i+1][j+1] == 2 && board[i+2][j+2] == 0){
				// jumps.add(new Move(i,j,i+2,j+2,true,i+1,j+1));
				int temp = board[i+1][j+1];
				board[i+1][j+1] = 0;
				List<Move> doub = getRedKingJump(i+2,j+2);
				board[i+1][j+1] = temp;
				if (doub != null){
					// jump.takes.addAll(doub.takes);
					// jump.newX = doub.newX;
					// jump.newY = doub.newY;
					for (Move m : doub){
						Move jump = new Move(i,j,i+2,j+2,true);
						jump.takes.add(i+1);
						jump.takes.add(j+1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else{
					Move jump = new Move(i,j,i+2,j+2,true);
					jump.takes.add(i+1);
					jump.takes.add(j+1);
					jumps.add(jump);
				}

			}
			if (i - 1 >= 0 && j + 1 < 8 && i - 2 >= 0 && j + 2 < 8 && 
				board[i-1][j+1] == 2 && board[i-2][j+2] == 0){
				// jumps.add(new Move(i,j,i-2,j+2,true,i-1,j+1));
				int temp = board[i-1][j+1];
				board[i-1][j+1] = 0;
				List<Move> doub = getRedKingJump(i-2,j+2);
				board[i-1][j+1] = temp;
				if (doub != null){
					for (Move m : doub){
						Move jump = new Move(i,j,i-2,j+2,true);
						jump.takes.add(i-1);
						jump.takes.add(j+1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else {
					Move jump = new Move(i,j,i-2,j+2,true);
					jump.takes.add(i-1);
					jump.takes.add(j+1);
					jumps.add(jump);
				}
			}
			if (i + 1 < 8 && j - 1 >= 0 && i + 2 < 8 && j - 2 >= 0 &&
				board[i+1][j-1] == 2 && board[i+2][j-2] == 0){
				// jumps.add(new Move(i,j,i+2,j-2,true,i+1,j-1));
				int temp = board[i+1][j-1];
				board[i+1][j-1] = 0;
				List<Move> doub = getRedKingJump(i+2,j-2);
				board[i+1][j-1] = temp;
				if (doub != null){
					for (Move m : doub){
						Move jump = new Move(i,j,i+2,j-2,true);
						jump.takes.add(i+1);
						jump.takes.add(j-1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else {
					Move jump = new Move(i,j,i+2,j-2,true);
					jump.takes.add(i+1);
					jump.takes.add(j-1);
					jumps.add(jump);
				}
			}
			if (i -1 >= 0 && j -1 >= 0 && i - 2 >= 0 && j - 2 >= 0 &&
				board[i-1][j-1] == 2 && board[i-2][j-2] == 0){
				int temp = board[i-1][j-1];
				board[i-1][j-1] = 0;
				List<Move> doub = getRedKingJump(i-2,j-2);
				board[i-1][j-1] = temp;
				if (doub != null){
					for (Move m : doub){
						Move jump = new Move(i,j,i-2,j-2,true);
						jump.takes.add(i-1);
						jump.takes.add(j-1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else {
					Move jump = new Move(i,j,i-2,j-2,true);
					jump.takes.add(i-1);
					jump.takes.add(j-1);
					jumps.add(jump);
				}
			}
			if (jumps.size() > 0){
				return jumps;
			} 
			else {
				return null;
			}
		}

		private List<Move> getBlackKingJump(int i, int j){
			List<Move> jumps = new ArrayList<Move>();

			if (i + 1 < 8 && j + 1 < 8 && i + 2 < 8 && j + 2 < 8 && 
				board[i+1][j+1] == 1 && board[i+2][j+2] == 0){
				int temp = board[i+1][j+1];
				board[i+1][j+1] = 0;
				List<Move> doub = getBlackKingJump(i+2,j+2);
				board[i+1][j+1] = temp;
				if (doub != null){
					// jump.takes.addAll(doub.takes);
					// jump.newX = doub.newX;
					// jump.newY = doub.newY;
					for (Move m : doub){
						Move jump = new Move(i,j,i+2,j+2,true);
						jump.takes.add(i+1);
						jump.takes.add(j+1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else{
					Move jump = new Move(i,j,i+2,j+2,true);
					jump.takes.add(i+1);
					jump.takes.add(j+1);
					jumps.add(jump);
				}
			}
			if (i - 1 >= 0 && j + 1 < 8 && i - 2 >= 0 && j + 2 < 8 && 
				board[i-1][j+1] == 1 && board[i-2][j+2] == 0){

				int temp = board[i-1][j+1];
				board[i-1][j+1] = 0;
				List<Move> doub = getBlackKingJump(i-2,j+2);
				board[i-1][j+1] = temp;
				if (doub != null){
					for (Move m : doub){
						Move jump = new Move(i,j,i-2,j+2,true);
						jump.takes.add(i-1);
						jump.takes.add(j+1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else {
					Move jump = new Move(i,j,i-2,j+2,true);
					jump.takes.add(i-1);
					jump.takes.add(j+1);
					jumps.add(jump);
				}
			}
			if (i + 1 < 8 && j - 1 >= 0 && i + 2 < 8 && j - 2 >= 0 &&
				board[i+1][j-1] == 1 && board[i+2][j-2] == 0){
				int temp = board[i+1][j-1];
				board[i+1][j-1] = 0;
				List<Move> doub = getBlackKingJump(i+2,j-2);
				board[i+1][j-1] = temp;
				if (doub != null){
					for (Move m : doub){
						Move jump = new Move(i,j,i+2,j-2,true);
						jump.takes.add(i+1);
						jump.takes.add(j-1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else {
					Move jump = new Move(i,j,i+2,j-2,true);
					jump.takes.add(i+1);
					jump.takes.add(j-1);
					jumps.add(jump);
				}
			}
			if (i -1 >= 0 && j -1 >= 0 && i - 2 >= 0 && j - 2 >= 0 &&
				board[i-1][j-1] == 1 && board[i-2][j-2] == 0){
				int temp = board[i-1][j-1];
				board[i-1][j-1] = 0;
				List<Move> doub = getBlackKingJump(i-2,j-2);
				board[i-1][j-1] = temp;
				if (doub != null){
					for (Move m : doub){
						Move jump = new Move(i,j,i-2,j-2,true);
						jump.takes.add(i-1);
						jump.takes.add(j-1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else {
					Move jump = new Move(i,j,i-2,j-2,true);
					jump.takes.add(i-1);
					jump.takes.add(j-1);
					jumps.add(jump);
				}
			}
			if (jumps.size() > 0){
				return jumps;
			}else {
				return null;
			}
		}

		private List<Move> getKingMove(int i, int j){
			List<Move> moves = new ArrayList<Move>();
			if (i + 1 < 8 && j + 1 < 8 && board[i+1][j+1] == 0){
				moves.add(new Move(i,j,i+1,j+1,false));
			}
			if (i - 1 >= 0 && j + 1 < 8 && board[i-1][j+1] == 0){
				moves.add(new Move(i,j,i-1,j+1,false));
			}
			if (i + 1 < 8 && j - 1 >= 0 && board[i+1][j-1] == 0){
				moves.add(new Move(i,j,i+1,j-1,false));
			}
			if (i -1 >= 0 && j -1 >= 0 && board[i-1][j-1] == 0){
				moves.add(new Move(i,j,i-1,j-1,false));
			}
			if (moves.size() > 0){
				return moves;
			}
			else {
				return null;
			}
		}

		private void determineBlackMoves(){
			boolean takeMove = false;
			for (int i = 0; i < 8; i++ ){
				for (int j = 0; j < 8; j++ ){
					if (board[i][j] != 0 && (board[i][j]) == 2){
						if (takeMove){
							List<Move> jump = getBlackJump(i,j);
							if (jump != null){
								validMoves.addAll(jump);
							}
						} else{
							List<Move> jump = getBlackJump(i,j);
							if (jump != null){
								validMoves.clear();
								validMoves.addAll(jump);
								takeMove = true;
							}
							if (!takeMove){
								List<Move> move = getBlackMove(i,j);
								if (move != null){
									validMoves.addAll(move);
								}
							}
						}
					}

					if (board[i][j] != 0 && board[i][j] == 20){
						if (takeMove){
							List<Move>  jump =  getBlackKingJump(i,j);
							if (jump != null){
								validMoves.addAll(jump);
							}
						} else{
							List<Move> jump= getBlackKingJump(i,j);
							if (jump != null){
								validMoves.clear();
								validMoves.addAll(jump);
								takeMove = true;
							}
							if (!takeMove){
								List<Move> move = getKingMove(i,j);
								if (move != null){
									validMoves.addAll(move);
								}
							}
						}
					}
				}
			}
		}

		private List<Move> getBlackJump(int i, int j){
			List<Move> jumps = new ArrayList<Move>();
			if (i + 1 < 8 && j - 1 >= 0 && i + 2 < 8 && j - 2 >= 0 &&
				board[i+1][j-1] == 1 && board[i+2][j-2] == 0){
				List<Move> doub = getBlackJump(i+2,j-2);
				if (doub != null){
					for (Move m : doub){
						Move jump = new Move(i,j,i+2,j-2,true);
						jump.takes.add(i+1);
						jump.takes.add(j-1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else {
					Move jump = new Move(i,j,i+2,j-2,true);
					jump.takes.add(i+1);
					jump.takes.add(j-1);
					jumps.add(jump);
				}
			}
			if (i -1 >= 0 && j -1 >= 0 && i - 2 >= 0 && j - 2 >= 0 &&
				board[i-1][j-1] == 1 && board[i-2][j-2] == 0){
				List<Move> doub = getBlackJump(i-2,j-2);
				if (doub != null){
					for (Move m : doub){
						Move jump = new Move(i,j,i-2,j-2,true);
						jump.takes.add(i-1);
						jump.takes.add(j-1);
						jump.takes.addAll(m.takes);
						jump.newX = m.newX;
						jump.newY = m.newY;
						jumps.add(jump);
					}
				} else {
					Move jump = new Move(i,j,i-2,j-2,true);
					jump.takes.add(i-1);
					jump.takes.add(j-1);
					jumps.add(jump);
				}
			}
			if (jumps.size() > 0){
				return jumps;
			}
			else {
				return null;
			}
		}

		private List<Move> getBlackMove(int i, int j){
			List<Move> moves = new ArrayList<Move>();
			if (i + 1 < 8 && j - 1 >= 0 && board[i+1][j-1] == 0){
				moves.add(new Move(i,j,i+1,j-1,false));
			}
			if (i -1 >= 0 && j - 1 >= 0 && board[i-1][j-1] == 0){
				moves.add(new Move(i,j,i-1,j-1,false));
			}
			if (moves.size() > 0){
				return moves;
			}
			else {
				return null;
			}
		}

		public boolean isTerminal(){
			return meCount == 0 || opCount == 0 || validMoves.size() == 0;
		}
	}

	private class Move{
		public int newX;
		public int newY;

		public int oldX;
		public int oldY;

		public List<Integer> takes;

		public boolean take;

		public Move( int oX, int oY, int nX, int nY, boolean t){
			this.newX = nX;
			this.newY = nY;
			this.oldX = oX;
			this.oldY = oY;
			this.take = t;
			if (this.take){
				takes = new ArrayList<Integer>();
			}
		}
	}
}
