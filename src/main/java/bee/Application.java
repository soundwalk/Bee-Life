package bee;

import java.util.Locale;

public class Application {

	public static void main (String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		CSVManager.initData();
		new FrameUI();
	}
	
}
