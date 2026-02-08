package proje1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Gorsellestirme extends JFrame {

	private JTextField mevcutEnlem, mevcutBoylam, hedefEnlem, hedefBoylam;
	private JComboBox<String> yolcuTipi;
	private JTextPane sonucEkrani;

	public void gorsellestirme(Map<Durak, Map<Durak, Baglantilar>> graf) {

		setTitle("rota planlama sistemi");
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		Dimension textFieldSize = new Dimension(100, 25);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("mevcut enlem:"), gbc);
		gbc.gridx = 1;
		mevcutEnlem = new JTextField();
		mevcutEnlem.setPreferredSize(textFieldSize);
		add(mevcutEnlem, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("mevcut boylam:"), gbc);
		gbc.gridx = 1;
		mevcutBoylam = new JTextField();
		mevcutBoylam.setPreferredSize(textFieldSize);
		add(mevcutBoylam, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("hedef enlem:"), gbc);
		gbc.gridx = 1;
		hedefEnlem = new JTextField();
		hedefEnlem.setPreferredSize(textFieldSize);
		add(hedefEnlem, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("hedef boylam:"), gbc);
		gbc.gridx = 1;
		hedefBoylam = new JTextField();
		hedefBoylam.setPreferredSize(textFieldSize);
		add(hedefBoylam, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		add(new JLabel("yolcu tipi:"), gbc);
		gbc.gridx = 1;
		yolcuTipi = new JComboBox<>(new String[] { "genel", "yasli", "ogrenci" });
		add(yolcuTipi, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		JButton hesaplaButton = new JButton("rotayi hesapla");
		add(hesaplaButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		sonucEkrani = new JTextPane();
		sonucEkrani.setEditable(false);
		add(new JScrollPane(sonucEkrani), gbc);

		hesaplaButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				rotayiHesapla(graf);
			}
		});

		setVisible(true);
	}

	private void rotayiHesapla(Map<Durak, Map<Durak, Baglantilar>> graf) {

		try {

			double enlem1 = Double.parseDouble(mevcutEnlem.getText());
			double boylam1 = Double.parseDouble(mevcutBoylam.getText());
			double enlem2 = Double.parseDouble(hedefEnlem.getText());
			double boylam2 = Double.parseDouble(hedefBoylam.getText());
			String yolcu = (String) yolcuTipi.getSelectedItem();

			Yolcu y = null;

			if (yolcu.equals("genel"))
				y = new Genel(enlem1, boylam1, enlem2, boylam2, yolcu);

			else if (yolcu.equals("ogrenci"))
				y = new Ogrenci(enlem1, boylam1, enlem2, boylam2, yolcu);

			else if (yolcu.equals("yasli"))
				y = new Yasli(enlem1, boylam1, enlem2, boylam2, yolcu);

			RotaHesaplayici hesapla = new RotaHesaplayici(this);
			hesapla.enKisaYol(graf, y);

		} catch (NumberFormatException e) {

			sonucEkrani.setText("lutfen gecerli bir enlem ve boylam girin!");
		}
	}

	public void setSonucEkrani(String mesaj) {

		sonucEkrani.setText(mesaj);
	}

	public String getSonucEkrani() {

		return this.sonucEkrani.getText();
	}

	public void appendSonucEkrani(String yeniMetin) {

		String mevcutMetin = this.sonucEkrani.getText();
		this.sonucEkrani.setText(mevcutMetin + "\n" + yeniMetin);
	}

	public void temizleSonucEkrani() {

		this.sonucEkrani.setText("");
	}
}
