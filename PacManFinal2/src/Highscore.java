import java.util.LinkedList;
import java.util.List;

public class Highscore {
	public static List<Integer> scores = new LinkedList<Integer>();
	public static List<String> names = new LinkedList<String>();
	public static int maxLength = 0;
	
	public static void setL() {
		//Look what name has the longest width when it's written on a String with the given font
		maxLength = Camera.maxInFontW(Game.drawingLink.getGraphics(), names, Settings.font1);
	}
	
	public static void scoreSet() {
		//Type the set of score
		scores.clear();
		List<score> lscore = DataBaseHandler.getData();
		for (score s : lscore) {
			scores.add(s.score);
			names.add(s.name);
		}
		
		setL();
	}
	
	public static class score{
		public int score;
		public String name;
	}
}
