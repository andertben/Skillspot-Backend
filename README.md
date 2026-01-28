# Skillspot Backend

Dieses Repository enthaelt das Backend fuer **Skillspot**, eine Plattform zum Anbieten, Finden und Buchen von Dienstleistungen. Die Anwendung ist als Spring Boot Service umgesetzt und kombiniert klassische CRUD-Module (Anbieter, Dienstleistungen, Bewertungen, Kategorien) mit einem Chat-System.

Die README ist bewusst ausfuehrlich gehalten, damit das Projekt nachvollziehbar und leicht erweiterbar ist.

## Inhaltsuebersicht

- Projektueberblick
- Tech-Stack
- Architektur und Aufbau
- Konfiguration
- Lokal starten (DB + App)
- Sicherheit und Authentifizierung
- API Ueberblick (Endpoints)
- Datenmodell und Migrationen
- Tests
- Erweiterungen und Best Practices
- Nützliche Kommandos

## Projektueberblick

Skillspot ist eine Service-Plattform:

- **Benutzer** legen ihr Profil an und koennen als **USER** oder **PROVIDER** auftreten.
- **Anbieter** (Provider) koennen Dienstleistungen anlegen und verwalten.
- **Dienstleistungen** sind kategorisiert und enthalten Titel/Beschreibung.
- **Bewertungen** sind an Dienstleistungen gebunden.
- **Kategorien** sind hierarchisch (Oberkategorie -> Unterkategorie) und i18n-faehig.
- **Chat** ermoeglicht Threads und Nachrichten zwischen User und Anbieter.

## Tech-Stack

- Java 17
- Spring Boot 3.5.7
- Spring Web, Security, OAuth2 Resource Server
- PostgreSQL
- Flyway fuer Migrationen
- Spring Data JPA (Chat) + JdbcTemplate (Rest)
- MapStruct (DTO <-> Entity)
- Lombok
- Springdoc OpenAPI (Swagger UI)
- Maven
- Testcontainers + JUnit 5

## Architektur und Aufbau

Die Anwendung folgt einer klassischen Schichtenarchitektur:

- **controller**: REST API Endpoints
- **service**: Fachlogik
- **store**: Datenzugriff mit JdbcTemplate (SQL)
- **repository**: JPA Repositories (Chat)
- **entity**: Datenmodelle (JPA und einfache DTO-Modelle)
- **dto**: API-DTOs fuer Requests/Responses
- **mapper**: MapStruct Mapping
- **configurator**: Security und JWT Validierung

Wichtig: Das Projekt nutzt **zwei Datenzugriffswege**:

- **JdbcTemplate** fuer die meisten Module (Benutzer, Anbieter, Dienstleistung, Bewertung, Kategorie)
- **JPA** nur fuer das Chat-System (Threads, Messages)

## Projektstruktur (Auszug)

- `pom.xml` Maven Konfiguration
- `Dockerfile` Build + Runtime Image
- `src/main/java/de/skillspot` Anwendungscode
- `src/main/resources/application.properties` lokale Konfiguration
- `src/main/resources/application-prod.properties` prod Profil
- `src/main/resources/db/migration` Flyway SQLs
- `src/main/resources/docker` Docker Compose + DB Dumps
- `src/test/java` Integrationstests

## Konfiguration

### application.properties (lokal)

- DB Verbindung:
  - `spring.datasource.url` default: `jdbc:postgresql://localhost:6000/postgres?currentSchema=skillspot`
  - `spring.datasource.username` default: `postgres`
  - `spring.datasource.password` default: `postgres`
- Flyway aktiviert
- Auth0 Issuer: `spring.security.oauth2.resourceserver.jwt.issuer-uri`
- Auth0 Audience: `app.auth0.audience`
- CORS Origin lokal: `app.frontend.origin` (default `http://localhost:5173`)

### application-prod.properties (prod)

- CORS Origin: `https://skillspot.site`
- Context Path: `server.servlet.context-path=/api`

Wichtig: Im Prod Profil verschieben sich die Endpoints um `/api`.

### Relevante Umgebungsvariablen

- `DB_URL` (optional, ueberschreibt DB URL)
- `DB_PASSWORD` (optional)

## Lokal starten

### 1) PostgreSQL mit Docker starten

In `src/main/resources/docker` liegt eine `docker-compose.yml`:

```bash
cd src/main/resources/docker

docker compose up -d
```

Das Setup startet Postgres auf Port **6000** und initialisiert die DB mit `psql_dump_latest.sql`.

### 2) Backend starten

```bash
./mvnw spring-boot:run
```

Swagger UI ist danach erreichbar unter:

- lokal: `/swagger-ui/index.html`
- prod (mit `/api`): `/api/swagger-ui/index.html`

## Sicherheit und Authentifizierung

- Security basiert auf **JWT Bearer Tokens** (Auth0).
- Es wird eine **Audience** validiert (`app.auth0.audience`).
- Die **Issuer URI** ist in den Properties definiert.
- CORS ist konfiguriert fuer lokale und prod Frontend Origins.

### Public Endpoints (ohne Auth)

- `GET /kategorien/**`
- `GET /dienstleistungen`
- `GET /anbieter`
- `GET /reviews`
- Swagger/OpenAPI: `/v3/api-docs/**`, `/swagger-ui/**`

Alle anderen Endpoints benoetigen ein gueltiges JWT.

## API Ueberblick (Endpoints)

Hinweis: Im Prod Profil ist der Basis-Pfad `/api`.

### Kategorien

- `GET /kategorien` (optional `?lang=de|en`, sonst `Accept-Language`)
- `GET /kategorien/tree` (Baumstruktur)

### Dienstleistungen

- `GET /dienstleistungen` (alias: `/services`) public
- `GET /dienstleistungen/my` (Provider) auth
- `POST /dienstleistungen` (Provider) auth
- `DELETE /dienstleistungen/{id}` (Provider) auth

### Anbieter

- `GET /anbieter` public

### Bewertungen

- `GET /reviews` (alias: `/bewertungen`) public
- `GET /bewertungen/service/{serviceId}`
- `GET /bewertungen/provider/{providerId}`
- `GET /bewertungen/average/{serviceId}`
- `POST /bewertungen` (auth)

### Chat

- `POST /chat/threads` (Thread erstellen/holen)
- `GET /chat/threads` (Thread Liste + Summary)
- `GET /chat/unread-count`
- `POST /chat/threads/{id}/read`
- `GET /chat/threads/{id}` (Thread Header)
- `GET /chat/threads/{id}/messages`
- `POST /chat/threads/{id}/messages`

### Profil / Me

- `GET /me`
- `POST /me/complete-profile`
- `PUT /me/profile`

## Request Bodies (Auszug)

### CompleteProfileRequest

- `displayName` (required)
- `role` = `USER` | `PROVIDER`
- `locationLat`, `locationLon` (optional)

### UpdateProfileRequest

- `displayName` (required)
- `role` = `USER` | `PROVIDER`
- `address` (optional)
- `locationLat`, `locationLon` (optional)

Wenn `role=PROVIDER` und keine Koordinaten vorhanden sind, wird die Adresse via **Nominatim** (OpenStreetMap) geocoded.

### CreateDienstleistungRequest

- `kategorieId` (required)
- `title` (required)
- `beschreibung` (required)
- `preis` (optional)

### CreateBewertungRequest

- `dienstleistungId` (required)
- `bewertung` (1..5)
- `text` (optional)

### CreateThreadRequest

- `dienstleistungId` (required)

### SendMessageRequest

- `text` (required)

## Datenmodell (Kurzuebersicht)

Die wichtigsten Tabellen (Schema `skillspot`):

- `benutzer`: User Profile, Auth0 Sub, Rolle
- `anbieter`: Provider Details
- `dienstleistung`: Service Angebote
- `kategorie`: Kategorien (hierarchisch)
- `kategorie_i18n`: Uebersetzungen
- `bewertung`: Reviews
- `chat_thread`: Chat Threads
- `chat_message`: Chat Messages

Die Migrationen liegen in `src/main/resources/db/migration`.

## Migrationen und Seeds

Flyway wird standardmaessig ausgefuehrt (lokal aktiviert). Migrationen:

- `V1__init_schema.sql` Erweiterungen der Benutzer Tabelle + Chat Tabellen
- `V1.1__create_missing_kategorie.sql` Kategorie Tabelle + Seed Daten
- `V2__create_kategorie_i18n.sql` i18n Tabelle
- `V3__seed_kategorie_i18n_en.sql` EN Seeds
- `V4__seed_kategorie_i18n_en_full.sql` EN Seeds (vollstaendig)

## Tests

Tests sind Integrationstests mit **Testcontainers** (Postgres). Sie legen ihre Basis-Tabellen bei Bedarf selbst an und fuehren Flyway aus.

Wichtige Tests:

- `SecuritySmokeTest` (Public/Private Endpoints)
- `DienstleistungIntegrationTest`
- `BewertungIntegrationTest`
- `MeIntegrationTest`
- `ChatThreadIntegrationTest`
- `KategorieI18nIntegrationTest`

Testlauf:

```bash
./mvnw test
```

## Erweiterungen und Best Practices

Wenn du neue Features hinzufuegst, ist der empfohlene Ablauf:

1. **DTO** fuer Request/Response definieren (`src/main/java/.../dto`)
2. **Entity** erweitern bzw. neues Model
3. **Store** (JdbcTemplate) oder **Repository** (JPA) erweitern
4. **Service** implementieren
5. **Controller** Endpoint hinzufuegen
6. **Integrationstest** schreiben

Hinweis: `BenutzerController` ist aktuell leer. Profile laufen ueber `MeController`.

## Nuetzliche Kommandos

DB Dumps (aus `src/main/resources/docker/cmd.txt`):

```bash
# Dump erstellen
pg_dump -h localhost -p 6000 -U postgres -d postgres --schema=skillspot -f psql_dump_xxx.sql

# Dump einspielen
psql -h localhost -p 6000 -U postgres -d postgres -f psql_dump_20251214.sql
```

## Lizenz

Keine Lizenz definiert.
