# Challenge — Festival line-up

**Tijd:** 2 uur · **Leerstof:** hoofdstuk 1 t/m 7 (REST, exceptions, validatie, JUnit, lambda's)

Een festival heeft drie dagen, drie podia en achttien acts. De organisatie wil een kleine
REST API om de line-up te beheren en te bevragen. Jij bouwt ze.

De opdracht bestaat uit **drie niveaus en een bonus**. Werk ze in volgorde af: een afgewerkt
niveau 1 en 2 is meer waard dan drie half afgewerkte niveaus. Je hoeft niet alles af te krijgen,
de challenge laat vooral zien waar je staat.

---

## Starten

1. Het startproject staat al in je repository, in de map **`challenge-festival/`**.
   Haal het binnen met **`git pull`** voor je iets anders doet.
2. Open de map `challenge-festival` in IntelliJ als Maven-project (rechtsklik op `pom.xml` →
   *Add as Maven Project*) en start `FestivalApplication`.
3. Surf naar <http://localhost:8080/api/acts>: je ziet 18 acts in JSON.
4. Werk in deze map; maak geen nieuw project aan.

**Wat je al krijgt:**

| Klasse | Inhoud |
|---|---|
| `model/Act` | een optreden: naam, genre, podium, dag, startuur, duur (min), gage (€), headliner ja/nee |
| `model/Genre`, `Stage`, `FestivalDay` | enums |
| `service/LineupService` | een `HashMap<Long, Act>` met 18 acts, en `findAll()` |
| `controller/LineupController` | `GET /api/acts` |
| `festival.http` | alle requests van deze opdracht, om te testen vanuit IntelliJ |

Commit na elk niveau (`"challenge: niveau 1 af"` …) en push op het einde.

---

## Niveau 1 — REST (H2–H3)

Werk de CRUD-endpoints uit op `/api/acts`. De data blijft in de `HashMap` van de service.

| Methode | Pad | Wat | Status |
|---|---|---|---|
| `GET` | `/api/acts?day=SATURDAY` | alle acts, of enkel die van één dag als `day` meegegeven is | 200 |
| `GET` | `/api/acts/{id}` | één act | 200 |
| `POST` | `/api/acts` | nieuwe act; de service kent het id toe | **201** |
| `PUT` | `/api/acts/{id}` | act aanpassen | 200 |
| `DELETE` | `/api/acts/{id}` | act verwijderen | **204** |

- Gebruik `@PathVariable` en `@RequestParam` (`required = false` voor `day`).
- Logica hoort in de **service**, niet in de controller.

## Niveau 2 — Fouten en validatie (H4–H5)

1. **Onbestaand id.** Maak een eigen `ActNotFoundException` (unchecked). Een onbekend id bij
   `GET`, `PUT` of `DELETE` geeft **404** met een JSON-body:
   ```json
   { "message": "Act met id 999 bestaat niet" }
   ```
2. **Validatie.** Voeg `spring-boot-starter-validation` toe aan je `pom.xml` en valideer de body
   van `POST` en `PUT` met `@Valid`:
   - `name` niet leeg
   - `genre`, `stage`, `day` en `startTime` verplicht
   - `durationMinutes` tussen 15 en 180
   - `fee` niet negatief

   Een ongeldige act geeft **400**, met per veld wat er mis is.
3. Vang beide af in één klasse met `@RestControllerAdvice`.
4. **Uitdaging:** per dag en per podium mag er maar **één headliner** zijn. Een tweede headliner
   geeft **409 Conflict** met een duidelijke boodschap.

## Niveau 3 — Lambda's (H7)

Maak een klasse `LineupFilters` met herbruikbare lambda's. Gebruik de functionele interfaces uit
`java.util.function`: `Predicate`, `Function`, `Consumer` en `Supplier`.

1. **`Predicate<Act>`.** Schrijf in `LineupFilters` vier statische methodes die een
   `Predicate<Act>` teruggeven: `byDay(FestivalDay)`, `byGenre(Genre)`, `isHeadliner()` en
   `startsAfter(LocalTime)`.

   Schrijf in de service `List<Act> filter(Predicate<Act> predicate)`, gesorteerd op dag en
   startuur. Maak het endpoint:

   `GET /api/acts/search?day=SATURDAY&genre=ROCK&headliner=true&after=20:00`

   Alle parameters zijn optioneel. Combineer enkel de predicates van de parameters die
   meegegeven zijn, met `.and(...)`.

2. **`Function<Act, String>`.** Een affiche toont per act één regel:
   ```
   21:30 | MAIN | The Velvet Owls (INDIE) ★
   ```
   (★ alleen bij een headliner). Schrijf die omzetting als `Function<Act, String>` en maak

   `GET /api/acts/poster?day=FRIDAY` → een lijst van strings, gesorteerd op startuur.

3. **`Consumer<Act>`.** Op een podium loopt het uit. Schrijf

   `POST /api/acts/delay?day=FRIDAY&stage=MAIN&minutes=30`

   Dat verschuift het startuur van alle acts op dat podium en die dag met een `Consumer<Act>`.
   Je geeft de aangepaste acts terug.

4. **`Supplier`.** Pas je `findById` aan zodat je exception uit niveau 2 via een `Supplier`
   gemaakt wordt: `Optional.ofNullable(...).orElseThrow(() -> ...)`.

5. **Statistieken.** `GET /api/acts/stats` geeft het aantal acts, de totale gage en de naam van
   de act met de langste set terug:
   ```json
   { "numberOfActs": 18, "totalFee": 294500.0, "longestSet": "DJ Mistral" }
   ```

Gebruik waar het kan een **method reference** (`Act::getStartTime`, `Act::isHeadliner` …).

## Bonus

- **JUnit (H6).** Schrijf tests voor `LineupFilters` en voor de service: gebruik `@BeforeEach` en
  `assertThrows` voor een onbestaand id.
- **Streams verder.** Voeg aan `/stats` de totale gage per dag en het aantal acts per genre toe
  (`Collectors.groupingBy`).

---

## Afspraken

- Je cursus, je eigen oefeningen en de Spring-documentatie mag je gebruiken.
- **AI-tools zijn niet toegestaan:** geen ChatGPT, Claude, Copilot, Gemini, de AI-assistent in
  IntelliJ of gelijkaardige tools. Zet AI-code-aanvulling in je IDE uit voor je begint.
- Werk alleen.
- Na 2 uur push je wat je hebt, ook als het niet af is.
