# 🚌 Urban Transportation Route Planner

This project is a **Java-based** simulation and route planning application developed for the city of İzmit. It integrates different modes of transportation (Bus, Tram, Taxi, Walking) and calculates the most optimal route (shortest time or lowest cost) based on user profiles.

## 📖 About the Project

With the increasing diversity in urban transportation, choosing the most efficient route has become complex for users. This project analyzes connections between stops using **Graph Theory** and **Dijkstra's Algorithm**. It performs dynamic pricing by considering user profiles (such as students or the elderly) and plots the most optimal route from point A to point B.

### 🚀 Key Features

* **Multi-Modal Transportation:** Integration of Bus, Tram, and Taxi.
* **User Profile-Based Pricing:**
    * 🎓 **Student:** Specific discount rates are applied.
    * 👴 **65+ Age:** Special tariffs and discounts are applied.
    * 👤 **General:** Standard tariff.
* **Route Optimization:**
    * 📉 **Lowest Cost:** Calculates wallet-friendly routes.
    * ⚡ **Shortest Time:** Calculates time-saving routes.
* **Smart Transfer:** Automatically calculates transfer discounts between Bus and Tram.
* **Taxi Calculation:** Dynamic taxi fare calculation based on opening fee and cost per km.
* **Visual Interface:** User-friendly interface developed with Java Swing.

## 🛠️ Technologies and Architecture

* **Language:** Java (JDK 8+)
* **Interface (GUI):** Java Swing
* **Data Format:** JSON (For stop and line information)
* **Algorithm:** Dijkstra's Shortest Path Algorithm
* **Design Patterns:**
    * **OOP Principles:** Inheritance and Polymorphism are effectively used in `Arac` (Vehicle) and `Yolcu` (Passenger) classes.
    * **Strategy Pattern-like Structure:** Each vehicle type (`Otobus`, `Tramvay`) applies its own cost strategy to the graph via the `indirimUygula` (applyDiscount) method.

## 📂 Class Structure and Workflow

The project has a solid Object-Oriented Programming infrastructure:

* **Arac (Abstract):** The `Otobus` (Bus), `Tramvay` (Tram), and `Taksi` (Taxi) classes are derived from here. Each vehicle manipulates the weights (costs) on the graph according to its own rules.
* **Yolcu (Abstract):** The `Ogrenci` (Student), `Yasli` (Elderly), and `Genel` (General) classes are derived from here. Discount rates are defined in these classes.
* **RotaHesaplayici (RouteCalculator):** The brain of the system; this class runs Dijkstra's algorithm to find the optimum path on the graph.
* **VeriOkuma (DataReading):** Parses the `veriseti.json` file and converts stops and connections into a Graph structure.

## ⚙️ Installation and Execution

To run the project on your local machine:

### 1. Requirements
* Java Development Kit (JDK) must be installed.
* The project requires the `org.json` library to process JSON data. (If using Maven, it is defined in `pom.xml`).

### 2. Data Set
Ensure that the `veriseti.json` file exists under `src/main/resources` (or the project root directory). This file contains stop coordinates and connection costs.

### 3. Execution
You can start the application by compiling and running the `src/proje1/Main.java` file.

```bash
# If compiling from the terminal (example):
javac -cp "lib/json.jar:." src/proje1/*.java
java -cp "lib/json.jar:src" proje1.Main
