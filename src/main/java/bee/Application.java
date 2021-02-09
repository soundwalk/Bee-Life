package bee;

import bee.ui.FrameUI;
import bee.utilities.CSVManager;

import java.util.Locale;

import javax.swing.UIManager;

public class Application {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		CSVManager.initData();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {

		}

		new FrameUI();
	}

}
