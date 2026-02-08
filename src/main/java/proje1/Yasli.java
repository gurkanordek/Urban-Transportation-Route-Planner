package proje1;

public class Yasli extends Yolcu {

	public Yasli(double baslangicEnlem, double baslangicBoylam, double hedefEnlem, double hedefBoylam,
			String yolcuTipi) {

		super(baslangicEnlem, baslangicBoylam, hedefEnlem, hedefBoylam, yolcuTipi);
	}

	@Override
	public double otobusIndirimOrani() {

		return 0.3;
	}

	@Override
	public double tramvayIndirimOrani() {

		return 0.3;
	}

	@Override
	public double aktarmaIndirimOrani() {

		return 0.5;
	}
}
