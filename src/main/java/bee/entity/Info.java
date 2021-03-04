package bee.entity;

public class Info {
	private int beehives;
	private String honeyType, place;

	public Info(int beehives, String honeyType, String place) {
		this.beehives = beehives;
		this.honeyType = honeyType;
		this.place = place;
	}

	public int getBeehives() {
		return beehives;
	}

	public String getHoneyType() {
		return honeyType;
	}

	public String getPlace() {
		return place;
	}

}
