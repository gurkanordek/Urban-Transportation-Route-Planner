# 🚌 Urban Transportation Route Planner (İzmit Şehir İçi Ulaşım Rota Planlayıcı)

Bu proje, İzmit şehri için geliştirilmiş, farklı ulaşım modlarını (Otobüs, Tramvay, Taksi, Yürüyüş) entegre eden ve kullanıcı profillerine göre en uygun rotayı (en kısa süre veya en düşük maliyet) hesaplayan **Java** tabanlı bir simülasyon ve rota planlama uygulamasıdır.

## 📖 Proje Hakkında

Şehir içi ulaşımda çeşitliliğin artmasıyla birlikte, kullanıcıların en verimli rotayı seçmesi karmaşık bir hal almıştır. Bu proje, **Graf Teorisi (Graph Theory)** ve **Dijkstra Algoritması** kullanarak duraklar arasındaki bağlantıları analiz eder. Kullanıcının öğrenci veya yaşlı olması gibi durumları göz önüne alarak dinamik fiyatlandırma yapar ve A noktasından B noktasına en optimum güzergahı çizer.

### 🚀 Öne Çıkan Özellikler

* **Çoklu Ulaşım Modu:** Otobüs, Tramvay ve Taksi entegrasyonu.
* **Kullanıcı Profili Bazlı Fiyatlandırma:**
    * 🎓 **Öğrenci:** Belirli oranlarda indirim uygulanır.
    * 👴 **65+ Yaş:** Özel tarife ve indirimler uygulanır.
    * 👤 **Genel:** Standart tarife.
* **Rota Optimizasyonu:**
    * 📉 **En Düşük Maliyet:** Cüzdan dostu rotaları hesaplar.
    * ⚡ **En Kısa Süre:** Zaman tasarrufu sağlayan rotaları hesaplar.
* **Akıllı Aktarma:** Otobüs-Tramvay arası geçişlerde aktarma indirimlerini otomatik hesaplar.
* **Taksi Hesaplaması:** Açılış ücreti ve km başına maliyet üzerinden dinamik taksi ücreti hesabı.
* **Görsel Arayüz:** Java Swing ile geliştirilmiş kullanıcı dostu arayüz.

## 🛠️ Kullanılan Teknolojiler ve Mimari

* **Dil:** Java (JDK 8+)
* **Arayüz (GUI):** Java Swing
* **Veri Formatı:** JSON (Durak ve hat bilgileri için)
* **Algoritma:** Dijkstra En Kısa Yol Algoritması
* **Tasarım Desenleri:**
    * **OOP Prensipleri:** Kalıtım (Inheritance) ve Çok Biçimlilik (Polymorphism) `Arac` ve `Yolcu` sınıflarında etkin kullanılmıştır.
    * **Strategy Pattern Benzeri Yapı:** Her araç türü (`Otobus`, `Tramvay`), `indirimUygula` metoduyla kendi maliyet stratejisini grafa uygular.

## 📂 Sınıf Yapısı ve İşleyiş

Proje, sağlam bir Nesne Yönelimli Programlama altyapısına sahiptir:

* **Arac (Abstract):** `Otobus`, `Tramvay` ve `Taksi` sınıfları buradan türetilir. Her araç, graf üzerindeki ağırlıkları (maliyetleri) kendi kurallarına göre manipüle eder.
* **Yolcu (Abstract):** `Ogrenci`, `Yasli` ve `Genel` sınıfları buradan türetilir. İndirim oranları bu sınıflarda tanımlıdır.
* **RotaHesaplayici:** Sistemin beyni olan bu sınıf, Dijkstra algoritmasını çalıştırarak graf üzerindeki en optimum yolu bulur.
* **VeriOkuma:** `veriseti.json` dosyasını parse ederek durakları ve bağlantıları bir Graf yapısına dönüştürür.

## ⚙️ Kurulum ve Çalıştırma

Projeyi yerel makinenizde çalıştırmak için:

### 1. Gereksinimler
* Java Development Kit (JDK) yüklü olmalıdır.
* Proje, JSON verilerini işlemek için `org.json` kütüphanesine ihtiyaç duyar. (Maven kullanıyorsanız `pom.xml` dosyasında tanımlıdır).

### 2. Veri Seti
`src/main/resources` (veya proje kök dizini) altında `veriseti.json` dosyasının bulunduğundan emin olun. Bu dosya durak koordinatlarını ve bağlantı maliyetlerini içerir.

### 3. Çalıştırma
`src/proje1/Main.java` dosyasını derleyip çalıştırarak uygulamayı başlatabilirsiniz.

```bash
# Eğer terminalden derleyecekseniz (örnek):
javac -cp "lib/json.jar:." src/proje1/*.java
java -cp "lib/json.jar:src" proje1.Main
```
