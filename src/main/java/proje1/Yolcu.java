package proje1;

public abstract class Yolcu {

	private double baslangicEnlem;
	private double baslangicBoylam;
	private double hedefEnlem;
	private double hedefBoylam;
	private String yolcuTipi;

	public Yolcu(double baslangicEnlem, double baslangicBoylam, double hedefEnlem, double hedefBoylam,
			String yolcuTipi) {

		this.baslangicEnlem = baslangicEnlem;
		this.baslangicBoylam = baslangicBoylam;
		this.hedefEnlem = hedefEnlem;
		this.hedefBoylam = hedefBoylam;
		this.yolcuTipi = yolcuTipi;
	}

	public double getBaslangicEnlem() {

		return baslangicEnlem;
	}

	public double getBaslangicBoylam() {

		return baslangicBoylam;
	}

	public double getHedefEnlem() {

		return hedefEnlem;
	}

	public double getHedefBoylam() {

		return hedefBoylam;
	}

	public String getYolcuTipi() {

		return yolcuTipi;
	}

	public abstract double otobusIndirimOrani();

	public abstract double tramvayIndirimOrani();

	public abstract double aktarmaIndirimOrani();
}
