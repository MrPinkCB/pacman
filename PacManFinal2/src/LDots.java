public class LDots {
	static int[][] arraydots;
	static int nbrDots = 0;
	
	public static void initializeArray(int[][] array) {
		//Copy the given array in array dots
		nbrDots=0;
		arraydots = new int[22][23];
		for (int i = 0; i < 22; i++) {
			for (int j = 0; j < 23; j++) {
				arraydots[i][j]=array[i][j];
				if (array[i][j]==1) {
					nbrDots++;
				}
			}
		}
		
	}
	
	public static void manageLDots(Object character) {
		//Firstly, we search the position of the object in the array
		//Then, we look if the case contains a dot or not (if it does, the value will be 1)
		//If the value is 1, then we add 2 to the score and we set the value of the case to 0
		//If there aren't any dots to the screen, the player has won
		int pxAr = (int)(character.x/20);
		int pyAr = (int)(character.y/20);
		
		if (arraydots[pyAr][pxAr] == 1) {
			Game.score+=2;
			Window.labelName.setText("Score : " + Game.score);
			arraydots[pyAr][pxAr]=0;
			nbrDots--;
		}
		
		if (nbrDots==0) {
			Game.win();
		}
	}
	
	public static boolean dot(int i, int j) {
		//check if the given case contains a dot or not
		if (arraydots[i][j] == 1) {
			return true;
		}
		return false;
	}
	
}
