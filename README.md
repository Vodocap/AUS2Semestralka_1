# Implementácia K-D stromu v aplikácii pre geodetov

Tento projekt je semestrálna práca vypracovaná ako súčasť predmetu **Algoritmy a dátové štruktúry 2**. Cieľom je vytvoriť aplikáciu, ktorá implementuje K-D strom na správu a vyhľadávanie geografických údajov, vrátane parciel a nehnuteľností.

---

## Obsah

- [Funkcionalita](#funkcionalita)
- [Štruktúra-projektu](#štruktúra-projektu)
- [Inštalácia](#inštalácia)
- [Použitie](#použitie)
- [Zložitosť-algoritmov](#zložitosť-algoritmov)
- [Príklady-metód](#príklady-metód)
- [Prispievanie](#prispievanie)

---

## Funkcionalita

Aplikácia umožňuje:
1. Správu geografických objektov:
   - **Parcely**
   - **Nehnuteľnosti**
2. Vyhľadávanie podľa geografických súradníc
3. Vizualizáciu a správu údajov prostredníctvom GUI

Každý geografický objekt si uchováva odkazy na svojich susedov, čo umožňuje efektívne hľadanie prekrývajúcich sa oblastí.

---

## Štruktúra projektu

Projekt je rozdelený do troch hlavných balíčkov:
1. **Štruktúry**
   - `TrNode`: Reprezentuje uzol stromu.
   - `IData`: Rozhranie pre dáta vkladané do stromu.
   - `KDTree`: Trieda implementujúca K-D strom.
2. **Aplikácia**
   - `GPSData`: Dáta reprezentujúce súradnice objektu.
   - `UzemnyCelok`, `Parcela`, `Nehnutelnost`: Reprezentácia geografických celkov.
   - `TrControl`: Riadenie stromov a operácií nad nimi.
3. **GUI**
   - `AppLauncher`: Spúšťa hlavné grafické rozhranie aplikácie.
   - `MainWindow`: Hlavné okno aplikácie.
   - `PridajCelok`, `VymazCelok`: Funkcie na pridávanie a mazanie objektov.

---

## Inštalácia

1. **Klonovanie repozitára**:
   ```bash
   git clone https://github.com/Vodocap/AUS2Semestralka.git
   cd AUS2Semestralka

## Použitie

### Spustenie aplikácie
- Aplikáciu spustíte spustením súboru **AppLauncher**.
- Po spustení sa zobrazí grafické používateľské rozhranie.
- Funkcie zahŕňajú:
  - Načítanie dát zo súboru
  - Ukladanie aktuálneho stavu
  - Vyhľadávanie a editácia geografických objektov

### Príklady funkcií
- **Vyhľadávanie:** Zadajte GPS súradnice pre konkrétne vyhľadávanie.
- **Pridávanie objektov:** Vyberte možnosť **"Pridať parcelu"** alebo **"Pridať nehnuteľnosť"**.
-  **Mazanie objektov:** Vyberte možnosť **"Vyraď parcelu"** alebo **"Vyraď nehnuteľnosť"**.
-  **Upravovanie objektov:** Vyberte možnosť **"Uprav parcelu"** alebo **"Uprav nehnuteľnosť"**.
-  **Generovanie dát:** Vyberte možnosť **"Vygeneruj dáta"**.
-  **Uloženie dát do súboru:** Vyberte možnosť **"Ulož dáta do súboru"**.
-  **Načítanie dát zo súboru:** Vyberte možnosť **"Načítaj dáta do súboru"**.


---

## Zložitosť algoritmov

### Vyhľadávanie
- **Nehnuteľností:** O(log m)
- **Parciel:** O(log p)
- **Všetkých objektov:** O(log n)

### Pridávanie
- **Nehnuteľností:**
  - Bez aktualizácie prekrývaní: O(log m)
  - S aktualizáciou: O(log m + log p + k * (s + g))
- **Parciel:**
  - Bez aktualizácie prekrývaní: O(log p)
  - S aktualizáciou: O(log p + log m + k * (s + g))

### Editácia
- **Nehnuteľností:**
  - Ak sa nemenia kľúčové parametre: O(1)
  - Pri zmene parametrov: O(log m + log n + log p + k * (s + g))
- **Parciel:**
  - Ak sa nemenia kľúčové parametre: O(1)
  - Pri zmene parametrov: O(log p + log n + log m + k * (s + g))

### Mazanie
- **Nehnuteľností:** O(log m + log n + log p + k * (log n + s))
- **Parciel:** O(log p + log n + log m + k * (log n + s))

---

## Príklady metód

### 1. Vloženie do stromu
- **Metóda `insert`:**
  - Prijíma generický typ údajov `IData`.
  - Vloží ich do stromu

### 2. Vyhľadávanie
- **`find`:** Nájde konkrétny objekt podľa jeho ID.
- **`findAll`:** Nájde všetky objekty podľa vybraných parametrov.

### 3. Mazanie
- **`delete`:** Odstraňuje uzol a automaticky reorganizuje strom.
- **`deleteNode`:** Privátna metóda na priamu manipuláciu s uzlami.

---

## Prispievanie

Ak chcete prispieť:
1. Vytvorte **fork** tohto repozitára.
2. Vytvorte novú vetvu pre svoju úpravu.
3. Pošlite **pull request**.
---

