package proje1;

import java.util.Map;

public abstract class Arac {

	public Arac() {

	}

	public abstract void indirimUygula(Map<Durak, Map<Durak, Baglantilar>> graf, Yolcu yolcu);
	
	public abstract void eskiyeDondur(Map<Durak, Map<Durak, Baglantilar>> graf, Yolcu yolcu);
}
