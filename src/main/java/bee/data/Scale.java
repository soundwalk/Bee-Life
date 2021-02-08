package bee.data;

public class Scale {
	private int day;
	private double value, status;

	public Scale(int day, double value, double status) {
		this.day = day;
		this.value = value;
		this.status = status;
	}

	public int getDay() {
		return day;
	}

	public double getValue() {
		return value;
	}

	public double getStatus() {
		return status;
	}

}
