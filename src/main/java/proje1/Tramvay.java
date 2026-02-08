package proje1;

import java.util.Map;

public class Tramvay extends Arac {

	public Tramvay() {

	}

	@Override
	public void indirimUygula(Map<Durak, Map<Durak, Baglantilar>> graf, Yolcu yolcu) {

		for (Durak kaynak : graf.keySet()) {

			for (Map.Entry<Durak, Baglantilar> entry : graf.get(kaynak).entrySet()) {

				Durak hedef = entry.getKey();
				Baglantilar baglanti = entry.getValue();

				if (baglanti.getBaglantiTip().equals("tram-tram")) {

					baglanti.setMaliyet(baglanti.getMaliyet() * (1 - yolcu.tramvayIndirimOrani()));
				}

				if (baglanti.getBaglantiTip().equals("tram-bus")) {

					baglanti.setMaliyet(baglanti.getMaliyet() * (1 - yolcu.aktarmaIndirimOrani()));
				}
			}
		}
	}

	@Override
	public void eskiyeDondur(Map<Durak, Map<Durak, Baglantilar>> graf, Yolcu yolcu) {

		for (Durak kaynak : graf.keySet()) {

			for (Map.Entry<Durak, Baglantilar> entry : graf.get(kaynak).entrySet()) {

				Durak hedef = entry.getKey();
				Baglantilar baglanti = entry.getValue();

				if (baglanti.getBaglantiTip().equals("tram-tram")) {

					baglanti.setMaliyet(baglanti.getMaliyet() * (1 / (1 - yolcu.tramvayIndirimOrani())));
				}

				if (baglanti.getBaglantiTip().equals("tram-bus")) {

					baglanti.setMaliyet(baglanti.getMaliyet() * (1 / (1 - yolcu.aktarmaIndirimOrani())));
				}
			}
		}
	}
}
