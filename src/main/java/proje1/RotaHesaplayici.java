package proje1;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class RotaHesaplayici {

	private double duragaUzaklik;
	private double hedefeUzaklik;
	private double yurumeHizi;
	private Gorsellestirme gorsellestirme;

	public RotaHesaplayici(Gorsellestirme gorsellestirme) {

		this.gorsellestirme = gorsellestirme;
		this.yurumeHizi = 1;
	}

	private Durak baslangicaYakinDurak(Map<Durak, Map<Durak, Baglantilar>> graf, Yolcu yolcu) {

		Durak dondurulecek = null;
		double min = -1;

		for (Durak durak : graf.keySet()) {

			if (min == -1) {

				min = Math.sqrt(Math.pow(durak.getEnlem() - yolcu.getBaslangicEnlem(), 2)
						+ Math.pow(durak.getBoylam() - yolcu.getBaslangicBoylam(), 2));
				dondurulecek = durak;
			}

			else if (min > Math.sqrt(Math.pow(durak.getEnlem() - yolcu.getBaslangicEnlem(), 2)
					+ Math.pow(durak.getBoylam() - yolcu.getBaslangicBoylam(), 2))) {

				min = Math.sqrt(Math.pow(durak.getEnlem() - yolcu.getBaslangicEnlem(), 2)
						+ Math.pow(durak.getBoylam() - yolcu.getBaslangicBoylam(), 2));
				dondurulecek = durak;
			}
		}

		duragaUzaklik = min;

		return dondurulecek;
	}

	private Durak hedefeYakinDurak(Map<Durak, Map<Durak, Baglantilar>> graf, Yolcu yolcu) {

		Durak dondurulecek = null;
		double min = -1;

		for (Durak durak : graf.keySet()) {

			if (min == -1) {

				min = Math.sqrt(Math.pow(durak.getEnlem() - yolcu.getHedefEnlem(), 2)
						+ Math.pow(durak.getBoylam() - yolcu.getHedefBoylam(), 2));
				dondurulecek = durak;
			}

			else if (min > Math.sqrt(Math.pow(durak.getEnlem() - yolcu.getHedefEnlem(), 2)
					+ Math.pow(durak.getBoylam() - yolcu.getHedefBoylam(), 2))) {

				min = Math.sqrt(Math.pow(durak.getEnlem() - yolcu.getHedefEnlem(), 2)
						+ Math.pow(durak.getBoylam() - yolcu.getHedefBoylam(), 2));
				dondurulecek = durak;
			}
		}

		hedefeUzaklik = min;

		return dondurulecek;
	}

	private List<Durak> dijkstra(Map<Durak, Map<Durak, Baglantilar>> graf, Durak kaynak, Durak hedef,
			String parametre) {

		Map<Durak, Double> mesafe = new HashMap<>();
		Map<Durak, Durak> onceki = new HashMap<>();
		PriorityQueue<Durak> kuyruk = new PriorityQueue<>(Comparator.comparingDouble(mesafe::get));

		for (Durak durak : graf.keySet()) {

			mesafe.put(durak, Double.MAX_VALUE);
			onceki.put(durak, null);
		}

		mesafe.put(kaynak, 0.0);
		kuyruk.add(kaynak);

		while (!kuyruk.isEmpty()) {

			Durak mevcut = kuyruk.poll();

			if (mevcut.equals(hedef))
				break;

			if (graf.containsKey(mevcut)) {

				for (Map.Entry<Durak, Baglantilar> entry : graf.get(mevcut).entrySet()) {
					Durak komsu = entry.getKey();
					Baglantilar baglanti = entry.getValue();

					double agirlik = (parametre.equals("maliyet")) ? baglanti.getMaliyet() : baglanti.getSure();
					double alternatifMesafe = mesafe.get(mevcut) + agirlik;

					if (alternatifMesafe < mesafe.get(komsu)) {

						mesafe.put(komsu, alternatifMesafe);
						onceki.put(komsu, mevcut);
						kuyruk.add(komsu);
					}
				}
			}
		}

		List<Durak> yol = new ArrayList<>();
		Durak adim = hedef;

		while (adim != null) {

			yol.add(adim);
			adim = onceki.get(adim);
		}

		Collections.reverse(yol);
		return yol;
	}

	public void enKisaYol(Map<Durak, Map<Durak, Baglantilar>> graf, Yolcu yolcu) {

		Arac otobus = new Otobus();
		Arac tramvay = new Tramvay();

		otobus.indirimUygula(graf, yolcu);
		tramvay.indirimUygula(graf, yolcu);

		gorsellestirme.temizleSonucEkrani();

		Durak kaynak = baslangicaYakinDurak(graf, yolcu);
		Durak hedef = hedefeYakinDurak(graf, yolcu);

		String oncekiSonuc = gorsellestirme.getSonucEkrani();

		gorsellestirme.appendSonucEkrani("\nmaliyet oncelikli rota:\n");
		List<Durak> yol = dijkstra(graf, kaynak, hedef, "maliyet");
		tamGuzergah(yol, graf, "maliyet");

		gorsellestirme.appendSonucEkrani("\n\nzaman oncelikli rota:\n");
		yol = dijkstra(graf, kaynak, hedef, "sure");
		tamGuzergah(yol, graf, "sure");

		gorsellestirme.appendSonucEkrani("\n\nsadece taksi rotasi:\n");
		taksiRotasi(yolcu);

		otobus.eskiyeDondur(graf, yolcu);
		tramvay.eskiyeDondur(graf, yolcu);
	}

	private void tamGuzergah(List<Durak> yol, Map<Durak, Map<Durak, Baglantilar>> graf, String oncelik) {

		if (yol == null || yol.isEmpty()) {

			gorsellestirme.setSonucEkrani("yol bulunamadi.");
			return;
		}

		StringBuilder guzergah = new StringBuilder();
		double toplamMaliyet = 0;
		double toplamSure = 0;

		ClassLoader classLoader = Main.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("veriseti.json");
		VeriOkuma v = new VeriOkuma();
		Taksi taksi = v.taksiUret(inputStream);

		DecimalFormat df = new DecimalFormat("#.##");

		String otobusEmoji = "\uD83D\uDE8C";
		String tramvayEmoji = "\uD83D\uDE8B";
		String taksiEmoji = "\uD83D\uDE96";
		String okEmoji = "  \u27A1  ";
		String yurumeEmoji = "\uD83D\uDEB6";
		String aktarmaEmoji = "\uD83D\uDD04";

		if (oncelik.equals("sure") && duragaUzaklik != 0) {

			guzergah.append(taksiEmoji + "taksi" + okEmoji + yol.get(0).getEmoji() + yol.get(0).getDurakIsim()
					+ "  (maliyet: " + df.format(taksi.maliyetHesapla(duragaUzaklik)) + ", sure: "
					+ df.format(taksi.sureHesapla(duragaUzaklik)) + ", mesafe:" + df.format(duragaUzaklik) + ")\n");
			toplamSure += taksi.sureHesapla(duragaUzaklik);
			toplamMaliyet += taksi.maliyetHesapla(duragaUzaklik);

		} else if (this.duragaUzaklik > 3 && duragaUzaklik != 0) {

			guzergah.append(taksiEmoji + "taksi" + okEmoji + yol.get(0).getEmoji() + yol.get(0).getDurakIsim()
					+ "  (maliyet: " + df.format(taksi.maliyetHesapla(duragaUzaklik)) + ", sure: "
					+ df.format(taksi.sureHesapla(duragaUzaklik)) + ", mesafe: " + df.format(duragaUzaklik) + ")\n");
			toplamMaliyet += taksi.maliyetHesapla(duragaUzaklik);
			toplamSure += taksi.sureHesapla(duragaUzaklik);

		} else if (duragaUzaklik != 0) {

			guzergah.append(yurumeEmoji + "yuruyus" + okEmoji + yol.get(0).getEmoji() + yol.get(0).getDurakIsim()
					+ "  (maliyet: 0.0, sure: " + df.format(duragaUzaklik / yurumeHizi) + ", mesafe: "
					+ df.format(duragaUzaklik) + ")\n");
			toplamSure += duragaUzaklik / yurumeHizi;
		}

		for (int i = 0; i < yol.size() - 1; i++) {

			Durak baslangic = yol.get(i);
			Durak hedef = yol.get(i + 1);

			Baglantilar baglanti = graf.get(baslangic).get(hedef);
			double maliyet = baglanti.getMaliyet();
			double sure = baglanti.getSure();
			double mesafe = baglanti.getMesafe();

			if (baglanti.getBaglantiTip().equals("bus-tram") || baglanti.getBaglantiTip().equals("tram-bus"))
				guzergah.append(baslangic.getEmoji() + baslangic.getDurakIsim() + okEmoji + hedef.getEmoji()
						+ hedef.getDurakIsim() + "  (maliyet: " + df.format(maliyet) + ", sure: " + sure + ", mesafe: "
						+ df.format(mesafe) + ")" + aktarmaEmoji + "\n");

			else
				guzergah.append(baslangic.getEmoji() + baslangic.getDurakIsim() + okEmoji + hedef.getEmoji()
						+ hedef.getDurakIsim() + "  (maliyet: " + maliyet + ", sure: " + sure + ", mesafe: "
						+ df.format(mesafe) + ")\n");

			toplamMaliyet += maliyet;
			toplamSure += sure;
		}

		if (oncelik.equals("sure") && hedefeUzaklik != 0) {

			guzergah.append(taksiEmoji + "taksi" + okEmoji + " hedef  (maliyet: "
					+ df.format(taksi.maliyetHesapla(hedefeUzaklik)) + ", sure: "
					+ df.format(taksi.sureHesapla(hedefeUzaklik)) + ", mesafe: " + df.format(hedefeUzaklik) + ")\n");
			toplamSure += taksi.sureHesapla(hedefeUzaklik);
			toplamMaliyet += taksi.maliyetHesapla(hedefeUzaklik);

		} else if (this.hedefeUzaklik > 3 && hedefeUzaklik != 0) {

			guzergah.append(taksiEmoji + "taksi" + okEmoji + "hedef  (maliyet: "
					+ df.format(taksi.maliyetHesapla(hedefeUzaklik)) + ", sure: "
					+ df.format(taksi.sureHesapla(hedefeUzaklik)) + ", mesafe: " + df.format(hedefeUzaklik) + ")\n");
			toplamMaliyet += taksi.maliyetHesapla(hedefeUzaklik);
			toplamSure += taksi.sureHesapla(hedefeUzaklik);

		} else if (hedefeUzaklik != 0) {

			guzergah.append(yurumeEmoji + "yuruyus" + okEmoji + "hedef  (maliyet: 0.0, sure: "
					+ df.format(hedefeUzaklik / yurumeHizi) + ", mesafe: " + df.format(hedefeUzaklik) + ")\n");
			toplamSure += hedefeUzaklik / yurumeHizi;

		} else
			guzergah.append(okEmoji + "hedef");

		guzergah.append("\n\ntoplam maliyet: " + df.format(toplamMaliyet) + "\ntoplam sure: " + df.format(toplamSure));
		gorsellestirme.appendSonucEkrani(guzergah.toString());
	}

	private void taksiRotasi(Yolcu yolcu) {

		StringBuilder guzergah = new StringBuilder();

		VeriOkuma v = new VeriOkuma();
		ClassLoader classLoader = Main.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("veriseti.json");
		Taksi taksi = v.taksiUret(inputStream);

		DecimalFormat df = new DecimalFormat("#.##");
		String taksiEmoji = "\uD83D\uDE96";
		String okEmoji = "  \u27A1  ";

		double uzaklik = Math.sqrt(Math.pow(yolcu.getBaslangicEnlem() - yolcu.getHedefEnlem(), 2)
				+ Math.pow(yolcu.getBaslangicBoylam() - yolcu.getHedefBoylam(), 2));

		guzergah.append(taksiEmoji + "taksi" + okEmoji + "hedef  (maliyet: " + df.format(taksi.maliyetHesapla(uzaklik))
				+ ", sure: " + df.format(taksi.sureHesapla(uzaklik)) + ", mesafe: " + df.format(uzaklik) + ")");

		gorsellestirme.appendSonucEkrani(guzergah.toString());
	}
}
