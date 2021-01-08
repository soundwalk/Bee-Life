import java.util.Locale;

public class Driver {
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		CSVManager.initData();
		new FrameUI();
	}
}