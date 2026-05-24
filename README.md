# 📦 Firma Kurierska - REST API

Backend aplikacji webowej dla zarządzania operacjami firmy kurierskiej. System obsługuje pełny CRUD dla klientów, kurierów, samochodów, tras i paczek z zaawansowaną obsługą błędów i dokumentacją API.

**Stack techniczny:** Spring Boot 3.5.14 • Java 21 • Spring Data JPA • H2 Database • Maven • Swagger/OpenAPI

---

## 🚀 Szybki Start

### Wymagania
- **Java 21+**
- **Maven 3.8+** (w projekcie: `./mvnw`)
- **Spring Tool Suite 5** (rekomendowane)

### Uruchomienie

```bash
# Sklonuj repozytorium
git clone <repository-url>
cd firma-kurierska-take

# Uruchom aplikację
./mvnw spring-boot:run

# Aplikacja będzie dostępna pod: http://localhost:8080
```

Aplikacja startuje z pustą bazą H2 w pamięci — dane są resetowane po każdym restarcie.

---

## 📖 Dokumentacja API

### Swagger UI
Po uruchomieniu aplikacji dokumentacja interaktywna dostępna jest pod:
```
http://localhost:8080/swagger-ui/index.html
```

Tutaj możesz testować wszystkie endpointy bezpośrednio z przeglądarki.

### H2 Console (Development)
Baza danych dostępna pod:
```
http://localhost:8080/h2-console
```
- **URL:** `jdbc:h2:mem:firmakurierskadb`
- **Username:** `sa`
- **Password:** (puste)

---

## 🔌 API Endpoints

### 👥 Klienci (`/klienci`)

| Metoda | Endpoint | Opis |
|--------|----------|------|
| `GET` | `/klienci` | Pobierz wszystkich klientów (można filtrować `?nazwisko=Kowalski`) |
| `GET` | `/klienci/{id}` | Pobierz klienta po ID |
| `POST` | `/klienci` | Dodaj nowego klienta |
| `PUT` | `/klienci/{id}` | Zaktualizuj dane klienta |
| `DELETE` | `/klienci/{id}` | Usuń klienta |

#### Przykład: Dodanie klienta
```bash
curl -X POST http://localhost:8080/klienci \
  -H "Content-Type: application/json" \
  -d '{
    "nazwa": "ABC Logistics",
    "adres": "ul. Główna 10, 40-000 Katowice",
    "telefon": "123456789",
    "email": "abc@example.com"
  }'
```

---

### 🏍️ Kurierzy (`/kurierzy`)

| Metoda | Endpoint | Opis |
|--------|----------|------|
| `GET` | `/kurierzy` | Pobierz wszystkich kurierów (można filtrować `?nazwisko=Nowak`) |
| `GET` | `/kurierzy/{id}` | Pobierz kuriera po ID |
| `POST` | `/kurierzy` | Dodaj nowego kuriera |
| `PUT` | `/kurierzy/{id}` | Zaktualizuj dane kuriera |
| `DELETE` | `/kurierzy/{id}` | Usuń kuriera |

#### Przykład: Dodanie kuriera
```bash
curl -X POST http://localhost:8080/kurierzy \
  -H "Content-Type: application/json" \
  -d '{
    "imie": "Jan",
    "nazwisko": "Nowak",
    "numerPracownika": "KUR001"
  }'
```

---

### 🚗 Samochody (`/samochody`)

| Metoda | Endpoint | Opis |
|--------|----------|------|
| `GET` | `/samochody` | Pobierz wszystkie samochody |
| `GET` | `/samochody/{id}` | Pobierz samochód po ID |
| `POST` | `/samochody` | Dodaj nowy samochód |
| `PUT` | `/samochody/{id}` | Zaktualizuj dane samochodu |
| `DELETE` | `/samochody/{id}` | Usuń samochód |

#### Przykład: Dodanie samochodu
```bash
curl -X POST http://localhost:8080/samochody \
  -H "Content-Type: application/json" \
  -d '{
    "marka": "Mercedes",
    "numerRejestracyjny": "KR12345",
    "ladownoscKg": 1000.0
  }'
```

---

### 🛣️ Trasy (`/trasy`)

| Metoda | Endpoint | Opis |
|--------|----------|------|
| `GET` | `/trasy` | Pobierz wszystkie trasy |
| `GET` | `/trasy/{id}` | Pobierz trasę po ID |
| `POST` | `/trasy` | Dodaj nową trasę |
| `PUT` | `/trasy/{id}` | Zaktualizuj trasę |
| `DELETE` | `/trasy/{id}` | Usuń trasę |

#### Przykład: Dodanie trasy
```bash
curl -X POST http://localhost:8080/trasy \
  -H "Content-Type: application/json" \
  -d '{
    "nazwa": "Trasa Katowice-Kraków",
    "rejon": "Śląsk",
    "dataWyjazdu": "2026-05-25",
    "kurierId": 1,
    "samochodId": 1
  }'
```

---

### 📦 Paczki (`/paczki`)

| Metoda | Endpoint | Opis |
|--------|----------|------|
| `GET` | `/paczki` | Pobierz wszystkie paczki |
| `GET` | `/paczki/{id}` | Pobierz paczkę po ID |
| `POST` | `/paczki` | Dodaj nową paczkę |
| `PUT` | `/paczki/{id}` | Zaktualizuj paczkę |
| `DELETE` | `/paczki/{id}` | Usuń paczkę |

#### Przykład: Dodanie paczki
```bash
curl -X POST http://localhost:8080/paczki \
  -H "Content-Type: application/json" \
  -d '{
    "waga": 2.5,
    "status": "OCZEKUJE",
    "nadawcaId": 1,
    "odborcaId": 2,
    "aktualnaTrasaId": null
  }'
```

---

## 🔒 Obsługa Błędów

Wszystkie błędy zwracane są w standardowym formacie JSON z odpowiednimi kodami HTTP:

### Kodem 400 – Bad Request
```json
{
  "timestamp": "2026-05-24T22:30:00+02:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Imię jest wymagane",
  "path": "/kurierzy"
}
```

### Kod 404 – Not Found
```json
{
  "timestamp": "2026-05-24T22:30:00+02:00",
  "status": 404,
  "error": "Not Found",
  "message": "Kurier o ID 999 nie istnieje",
  "path": "/kurierzy/999"
}
```

### Kod 500 – Internal Server Error
```json
{
  "timestamp": "2026-05-24T22:30:00+02:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Nieoczekiwany błąd serwera",
  "path": "/kurierzy"
}
```

---

## 🔗 HATEOAS (Hypermedia As The Engine Of Application State)

Wszystkie odpowiedzi zawierają **linki** do powiązanych zasobów:

```json
{
  "id": 1,
  "imie": "Jan",
  "nazwisko": "Nowak",
  "numerPracownika": "KUR001",
  "_links": {
    "self": {
      "href": "http://localhost:8080/kurierzy/1"
    },
    "kurierzy": {
      "href": "http://localhost:8080/kurierzy"
    }
  }
}
```

Te linki pozwalają klientowi odkrywać API bez poprzedniej znajomości struktury.

---

## 📁 Struktura Projektu

```
src/main/java/pl/polsl/take/firmakurierska/
├── controller/           # Kontrolery REST
│   ├── KlientController.java
│   ├── KurierController.java
│   ├── SamochodController.java
│   └── TrasaController.java
├── entity/               # Encje JPA
│   ├── Klient.java
│   ├── Kurier.java
│   ├── Samochod.java
│   ├── Trasa.java
│   ├── Paczka.java
│   └── StatusPaczki.java
├── repository/           # Repozytoria Spring Data JPA
│   ├── KlientRepository.java
│   ├── KurierRepository.java
│   ├── SamochodRepository.java
│   ├── TrasaRepository.java
│   └── PaczkaRepository.java
├── dto/                  # Data Transfer Objects
│   ├── KlientModel.java
│   ├── KlientCreateRequest.java
│   ├── KlientUpdateRequest.java
│   ├── KurierModel.java
│   ├── KurierCreateRequest.java
│   └── KurierUpdateRequest.java
├── hateoas/              # Assemblery dla HATEOAS
│   ├── KlientModelAssembler.java
│   └── KurierModelAssembler.java
├── exception/            # Obsługa wyjątków
│   ├── ResourceNotFoundException.java
│   ├── ApiErrorResponse.java
│   └── GlobalExceptionHandler.java
└── FirmaKurierskaApplication.java

src/main/resources/
└── application.properties   # Konfiguracja aplikacji
```

---

## 🛠️ Technologia

- **Spring Boot 3.5.14** — Framework webowy i kontener IoC
- **Spring Data JPA** — Abstrakcja dostępu do danych
- **H2 Database** — In-memory baza dla developmentu
- **Spring HATEOAS** — Obsługa linków i descoberable API
- **Springdoc OpenAPI 2.8.5** — Dokumentacja Swagger/OpenAPI
- **Lombok** — Zmniejszenie boilerplate'u
- **Jakarta Validation** — Walidacja danych wejściowych (Jakarta, nie javax!)
- **Maven** — Zarządzanie zależnościami i build

---

## 📝 Walidacja Danych

Wszystkie pola wymagane są walidowane po stronie serwera:

- **@NotBlank** — Pole nie może być puste
- **@NotNull** — Pole musi być obecne
- **@Min/@Max** — Zakresy liczbowe
- **@Email** — Format email

Błędy walidacji zwracane są w odpowiedzi 400 Bad Request z detalami.

---

## 🧪 Testowanie

### W Postmanie
1. Pobierz lub zainstaluj [Postman](https://www.postman.com/downloads/)
2. Utwórz kolekcję żądań wskazując `http://localhost:8080`
3. Używaj tabeli endpointów powyżej

### W przeglądarce
Odwiedź Swagger UI:
```
http://localhost:8080/swagger-ui/index.html
```

### W terminalu
```bash
# Pobierz wszystkich kurierów
curl http://localhost:8080/kurierzy

# Dodaj kuriera
curl -X POST http://localhost:8080/kurierzy \
  -H "Content-Type: application/json" \
  -d '{"imie":"Maria","nazwisko":"Kowalska","numerPracownika":"KUR002"}'

# Usunięcie
curl -X DELETE http://localhost:8080/kurierzy/1
```

---

## 📌 Ważne Notatki

- ✅ **Bez uwierzytelniania** — Brak Spring Security, wszystko jest publiczne
- ✅ **H2 in-memory** — Dane są resetowane po restarcie
- ✅ **Importy z `jakarta.*`** — Projekt używa Jakarta EE (nie starszego javax)
- ✅ **Obsługa błędów** — Wszystkie błędy mapowane na odpowiednie kody HTTP
- ✅ **Walidacja** — Dane wejściowe zawsze weryfikowane

---

## 📧 Kontakt

Projekt zaliczeniowy z przedmiotu **TAKE** na Politechnice Śląskiej.

---

**Ostatnia aktualizacja:** 24.05.2026
