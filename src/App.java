import com.game.password.Color;
import com.game.password.Game;

public class App {
	public static void main(String[] args) {
		Game game = new Game();
		
		Color[] attempt = new Color[] { Color.Green, Color.Red, Color.Green, Color.Red };
		
		game.Try(attempt);
	}
}