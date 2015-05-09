import java.util.*;

public class AITestSandbox{

	public static void main(String[] args){
		System.out.println("Test Start....");
		AI brainTest = new AI(1,2);

		List<Checker> testCheckers = new ArrayList<Checker>();
		for(int i = 0; i < 3; i++){
			for(int j = 2; j <= 8; j+=2){
				if(i%2 == 0){
					Checker checker = new Checker(j-1, i, 1);
					testCheckers.add(checker);
				}else{
					Checker checker = new Checker(j -2, i, 1);
					testCheckers.add(checker);
				}
			}
		}

		for(int i = 5; i < 8; i++){
			for(int j = 2; j <= 8; j+=2){
				if(i%2 == 0){
					Checker checker = new Checker(j-1, i, 2);
					testCheckers.add(checker);
				}else{
					Checker checker = new Checker(j-2, i, 2);
					testCheckers.add(checker);
				}
			}
		}

		// for (Checker c : testCheckers){
		// 	System.out.println("X: " + c.x + ", Y: " + c.y);
		// 	System.out.println("COLOR: " + c.color);
		// }

		int[] move = brainTest.determineMove(testCheckers);
		// System.out.println("ox: " + move[0] +  ", oy: " + move[1] +  ", nx: " + move[2] + ", ny: " + move[3]);

		System.out.println("**************************");

		// testCheckers.set(15,new Checker(6,3,2));

		// for (Checker c : testCheckers){
		// 	System.out.println("X: " + c.x + ", Y: " + c.y);
		// 	System.out.println("COLOR: " + c.color);
		// }


		// int[] move2 = brainTest.determineMove(testCheckers);
		// System.out.println("ox: " + move2[0] +  ", oy: " + move2[1] +  ", nx: " + move2[2] + ", ny: " + move2[3] + 
		// 	", tx: " +  move2[4] + ", ty: " + move2[5]);

		// testCheckers.set(13,new Checker(2,3,2));

		// int[] move3 = brainTest.determineMove(testCheckers);
		// System.out.println("ox: " + move3[0] +  ", oy: " + move3[1] +  ", nx: " + move3[2] + ", ny: " + move3[3] + 
		// 	", tx: " +  move3[4] + ", ty: " + move3[5]);

		Checker[][] testBoard = new Checker[8][8];
		for (Checker c: testCheckers){
			testBoard[c.x][c.y] = c;
		}

		AI red = new AI(1,2);
		AI black = new AI(2,1);

		while (testCheckers.size() > 0){
			System.out.println(testCheckers.size());
			// if (testCheckers.size() == 12){
			// 	System.out.println(testCheckers);
			// }
			int [] moveR = red.determineMove(testCheckers);
			if (moveR[0] == -1){
				break;
			}
			Checker moving = testBoard[moveR[0]][moveR[1]];
			System.out.println("RED: ");
			if (moveR.length >= 6) {
				int numJumps = (moveR.length - 4) / 2; 
				System.out.println("TAKE!" + "*" + numJumps);
				System.out.println("ox: " + moveR[0] +  ", oy: " + moveR[1] +  ", nx: " + moveR[2] + ", ny: " + moveR[3]);
				testBoard[moveR[0]][moveR[1]] = null;
				testBoard[moveR[2]][moveR[3]] = moving;
				if (moveR[3] == 7){
					moving.king = true;
				}
				moving.x = moveR[2];
				moving.y = moveR[3];
				int s = 4;
				while (s < moveR.length){
					Checker removing = testBoard[moveR[s]][moveR[s+1]];
					testBoard[moveR[s]][moveR[s+1]] = null;
					testCheckers.remove(removing);
					s+=2;
				}
			}else{
				System.out.println("ox: " + moveR[0] +  ", oy: " + moveR[1] +  ", nx: " + moveR[2] + ", ny: " + moveR[3]);
				testBoard[moveR[0]][moveR[1]] = null;
				testBoard[moveR[2]][moveR[3]] = moving;
				moving.x = moveR[2];
				moving.y = moveR[3];
				if (moveR[3] == 7){
					moving.king = true;
				}
			}
			int [] moveB = black.determineMove(testCheckers);
			if (moveB[0] == -1){
				break;
			}
			System.out.println("BLACK: ");
			moving = testBoard[moveB[0]][moveB[1]];
			if (moveB.length >= 6) {
				int numJumps = (moveB.length - 4) / 2; 
				System.out.println("TAKE!" + "*" + numJumps);
				System.out.println("ox: " + moveB[0] +  ", oy: " + moveB[1] +  ", nx: " + moveB[2] + ", ny: " + moveB[3]);
				testBoard[moveB[0]][moveB[1]] = null;
				testBoard[moveB[2]][moveB[3]] = moving;
				if (moveB[3] == 0){
					moving.king = true;
				}
				moving.x = moveB[2];
				moving.y = moveB[3];
				int s = 4;
				while (s < moveB.length){
					Checker removing = testBoard[moveB[s]][moveB[s+1]];
					testBoard[moveB[s]][moveB[s+1]] = null;
					testCheckers.remove(removing);
					s+=2;
				}
			}else{
				System.out.println("ox: " + moveB[0] +  ", oy: " + moveB[1] +  ", nx: " + moveB[2] + ", ny: " + moveB[3]);
				testBoard[moveB[0]][moveB[1]] = null;
				testBoard[moveB[2]][moveB[3]] = moving;
				if (moveB[3] == 0){
					moving.king = true;
				}
				moving.x = moveB[2];
				moving.y = moveB[3];
			}

		}

	}
}