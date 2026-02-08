package proje1;

public class Ogrenci extends Yolcu {

	public Ogrenci(double baslangicEnlem, double baslangicBoylam, double hedefEnlem, double hedefBoylam,
			String yolcuTipi) {

		super(baslangicEnlem, baslangicBoylam, hedefEnlem, hedefBoylam, yolcuTipi);
	}

	@Override
	public double otobusIndirimOrani() {

		return 0.4;
	}
	
	@Override
	public double tramvayIndirimOrani() {

		return 0.5;
	}
	
	@Override
	public double aktarmaIndirimOrani() {

		return 0.7;
	}
}
