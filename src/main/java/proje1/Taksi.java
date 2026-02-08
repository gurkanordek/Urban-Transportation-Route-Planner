package proje1;

import java.util.Map;

public class Taksi extends Arac {

	private double acilis;
	private double kmBasina;
	private double hiz;

	public Taksi(double acilis, double kmBasina) {

		this.acilis = acilis;
		this.kmBasina = kmBasina;
		this.hiz = 5;
	}

	public double getAcilis() {

		return this.acilis;
	}

	public double getKmBasina() {

		return this.kmBasina;
	}

	public double getHiz() {

		return this.hiz;
	}

	@Override
	public void indirimUygula(Map<Durak, Map<Durak, Baglantilar>> graf, Yolcu yolcu) {

		System.out.println("toplu tasima olmadigi icin yolcu tipine gore indirim yoktur");

	}

	@Override
	public void eskiyeDondur(Map<Durak, Map<Durak, Baglantilar>> graf, Yolcu yolcu) {

		System.out.println("ucretlendirmede degisiklik olmamistir");

	}

	public double maliyetHesapla(double gidilenMesafe) {

		return this.acilis + this.kmBasina * gidilenMesafe;
	}

	public double sureHesapla(double gidilenMesafe) {

		return gidilenMesafe / this.hiz;
	}
}
