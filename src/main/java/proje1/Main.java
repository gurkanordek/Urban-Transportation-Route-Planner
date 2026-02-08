package proje1;

import java.io.InputStream;
import java.util.Map;

public class Main {

	public static void main(String[] args) {

		ClassLoader classLoader = Main.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("veriseti.json");

		VeriOkuma veriOkuma = new VeriOkuma();

		Map<Durak, Map<Durak, Baglantilar>> graf = veriOkuma.grafaKaydet(inputStream);

		Gorsellestirme g = new Gorsellestirme();
		g.gorsellestirme(graf);
	}
}
