package proje1;

public class Genel extends Yolcu {

	public Genel(double baslangicEnlem, double baslangicBoylam, double hedefEnlem, double hedefBoylam,
			String yolcuTipi) {

		super(baslangicEnlem, baslangicBoylam, hedefEnlem, hedefBoylam, yolcuTipi);
	}

	@Override
	public double otobusIndirimOrani() {

		return 0.0;
	}

	@Override
	public double tramvayIndirimOrani() {

		return 0.0;
	}

	@Override
	public double aktarmaIndirimOrani() {

		return 0.0;
	}
}
