package game.view;

public class View {
	private static View instance;

	public static View getInstance() {
		if (instance == null)
			instance = new View();
		return instance;
	}

	private View() {
		// TODO Auto-generated constructor stub
	}

}
