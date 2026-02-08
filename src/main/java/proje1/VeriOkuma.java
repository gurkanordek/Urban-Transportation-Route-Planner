package proje1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VeriOkuma {

	private Map<Durak, Map<Durak, Baglantilar>> graf;

	public VeriOkuma() {

		this.graf = new HashMap<>();
	}

	public Map<Durak, Map<Durak, Baglantilar>> grafaKaydet(InputStream inputStream) {

		try {

			Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
			String icerik = scanner.hasNext() ? scanner.next() : "";
			scanner.close();

			JSONObject json = new JSONObject(icerik);
			JSONArray duraklar = json.getJSONArray("duraklar");

			for (int i = 0; i < duraklar.length(); i++) {

				JSONObject durakJson = duraklar.getJSONObject(i);
				String id = durakJson.getString("id");
				String isim = durakJson.getString("name");
				String tip = durakJson.getString("type");
				double enlem = durakJson.getDouble("lat");
				double boylam = durakJson.getDouble("lon");

				String emoji = null;

				if (tip.equals("bus"))
					emoji = "\uD83D\uDE8C";

				else if (tip.equals("tram"))
					emoji = "\uD83D\uDE8B";

				Durak durak = new Durak(id, isim, tip, enlem, boylam, emoji);
				graf.putIfAbsent(durak, new HashMap<>());
			}

			for (int i = 0; i < duraklar.length(); i++) {

				JSONObject durakJson = duraklar.getJSONObject(i);
				String kaynakId = durakJson.getString("id");
				Durak kaynakDurak = hedefDurakBul(kaynakId);

				if (durakJson.has("nextStops")) {

					JSONArray sonrakiDuraklar = durakJson.getJSONArray("nextStops");

					for (int j = 0; j < sonrakiDuraklar.length(); j++) {

						JSONObject durakBilgi = sonrakiDuraklar.getJSONObject(j);
						String hedefId = durakBilgi.getString("stopId");
						double mesafe = durakBilgi.getDouble("mesafe");
						double sure = durakBilgi.getDouble("sure");
						double ucret = durakBilgi.getDouble("ucret");

						Durak hedefDurak = hedefDurakBul(hedefId);

						if (kaynakDurak != null && hedefDurak != null) {

							String baglantiId = kaynakId + "-" + hedefId;
							String baglantiTip = kaynakDurak.getDurakTip() + "-" + kaynakDurak.getDurakTip();

							Baglantilar baglanti = new Baglantilar(baglantiId, baglantiTip, mesafe, ucret, sure);
							graf.get(kaynakDurak).put(hedefDurak, baglanti);

							Baglantilar tersBaglanti = new Baglantilar(hedefId + "-" + kaynakId,
									hedefDurak.getDurakTip() + "-" + kaynakDurak.getDurakTip(), mesafe, ucret, sure);
							graf.get(hedefDurak).put(kaynakDurak, tersBaglanti);
						}
					}
				}

				if (durakJson.has("transfer") && !durakJson.isNull("transfer")) {

					JSONObject transferBilgi = durakJson.getJSONObject("transfer");
					String transferStopId = transferBilgi.getString("transferStopId");
					double transferSure = transferBilgi.getDouble("transferSure");
					double transferUcret = transferBilgi.getDouble("transferUcret");

					Durak hedefDurak = hedefDurakBul(transferStopId);

					if (kaynakDurak != null && hedefDurak != null) {

						String baglantiId = kaynakId + "-" + transferStopId;
						String baglantiTip = kaynakDurak.getDurakTip() + "-" + hedefDurak.getDurakTip();
						double mesafe = Math.sqrt(Math.pow(kaynakDurak.getEnlem() - hedefDurak.getEnlem(), 2)
								+ Math.pow(kaynakDurak.getBoylam() - hedefDurak.getBoylam(), 2));

						Baglantilar transferBaglanti = new Baglantilar(baglantiId, baglantiTip, mesafe, transferUcret,
								transferSure);
						graf.get(kaynakDurak).put(hedefDurak, transferBaglanti);

						Baglantilar tersTransferBaglanti = new Baglantilar(transferStopId + "-" + kaynakId,
								hedefDurak.getDurakTip() + "-" + kaynakDurak.getDurakTip(), mesafe, transferUcret,
								transferSure);
						graf.get(hedefDurak).put(kaynakDurak, tersTransferBaglanti);
					}
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return graf;
	}

	private Durak hedefDurakBul(String id) {

		for (Durak d : graf.keySet()) {

			if (d.getDurakId().equals(id))
				return d;
		}

		return null;
	}

	public Taksi taksiUret(InputStream inputStream) {

		try {

			Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
			String icerik = scanner.hasNext() ? scanner.next() : "";
			scanner.close();

			JSONObject json = new JSONObject(icerik);

			if (json.has("taxi")) {

				JSONObject taksiBilgi = json.getJSONObject("taxi");
				double acilisUcreti = taksiBilgi.getDouble("openingFee");
				double kmBasinaUcret = taksiBilgi.getDouble("costPerKm");

				return new Taksi(acilisUcreti, kmBasinaUcret);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}
}
