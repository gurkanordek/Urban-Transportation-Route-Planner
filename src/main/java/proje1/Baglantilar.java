package proje1;

public class Baglantilar {

	private String baglantiId;
	private String baglantiTip;
	private double mesafe;
	private double maliyet;
	private double sure;

	public Baglantilar(String id, String tip, double mesafe, double maliyet, double sure) {

		this.baglantiId = id;
		this.baglantiTip = tip;
		this.mesafe = mesafe;
		this.maliyet = maliyet;
		this.sure = sure;
	}

	public String getBaglantiId() {

		return this.baglantiId;
	}

	public String getBaglantiTip() {

		return this.baglantiTip;
	}

	public double getMesafe() {

		return this.mesafe;
	}

	public double getMaliyet() {

		return this.maliyet;
	}

	public double getSure() {

		return this.sure;
	}

	public void setMaliyet(double maliyet) {

		this.maliyet = maliyet;
	}
}
