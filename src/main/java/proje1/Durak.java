package proje1;

public class Durak {

	private String durakId;
	private String durakIsim;
	private String durakTip;
	private double enlem;
	private double boylam;
	private String emoji;

	public Durak(String id, String isim, String tip, double enlem, double boylam, String emoji) {

		this.durakId = id;
		this.durakIsim = isim;
		this.durakTip = tip;
		this.enlem = enlem;
		this.boylam = boylam;
		this.emoji = emoji;
	}

	public String getDurakId() {

		return this.durakId;
	}

	public String getDurakIsim() {

		return this.durakIsim;
	}

	public String getDurakTip() {

		return this.durakTip;
	}

	public double getEnlem() {

		return this.enlem;
	}

	public double getBoylam() {

		return this.boylam;
	}

	public String getEmoji() {

		return this.emoji;
	}

	@Override
	public String toString() {

		return this.durakId;
	}

}
