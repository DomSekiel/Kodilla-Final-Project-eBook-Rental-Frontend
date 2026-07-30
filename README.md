# Readme, Kodilla Final Project, e-book Rental Frontend - UI Test Automation

## Opis projektu:
Projekt końcowy wykonany w ramach kursu Kodilla QA Automation.
Repozytorium zawiera zestaw automatycznych testów interfejsu użytkownika dla aplikacji eBook Rental Frontend. Framework został przygotowany w języku Java z wykorzystaniem Selenium WebDriwer, JUnit 5 oraz wzorca Page Object Model (POM).

Projekt ma na celu automatyczną weryfikację najważniejszych funkcjonalności aplikacji, takich jak:

- logowanie i rejestracja użytkowników,
- zarządzanie tytułami,
- zarzadzanie egzemplarzami,
- walidacja formularzy,
- nawigacja pomiędzy ekranami.

## Przegląd projektu

### Aplikacja



## Testowana aplikacja

Adres WWW aplikacji:

https://ta-bookrental-fe.onrender.com

Przed uruchomieniem testów należy:

1. Otworzyć aplikację.
2. Założyć konto przy użyciu formularza Register.
3. Skopiować plik config.properties.example i zmienić jego nazwę na config.properties
4. Wpisać dane do logowania utworożne podczas rejestracji (hasło i login) do pliku config.properties

## Wykorzystane technologie

- Java 21
- Selenium WebDriver 4.40.0
- JUnit 5
- AssertJ
- Gradle
- WebDriverManager
- GitHubActions
- Page Object Model (POM)

## Architektura projektu

Projekt został wykonany zgodnie z wzorcem projektowym Page Object Model.

Struktura projektu:

src
|_ test
    |_ java
    |   |_ base
    |   |_  pages
    |   |_  tests
    |   |_  utils
    |___ resources

### BasePage

Klasa zawiera wspólne metody wykorzystywane przez wszystkie klasy Page Object.

Najważniejsze metody:

- click()
- type ()
- waitForLoaderDissapear ()

BasePage odpowiada również za:

- synchronizację z loaderem,
- explicit waits,
- kliknięcia z wykorzystaniem JavaScriptExecutor,
- eliminację powielania kodu,

### BaseTest

Klasa odpowiada za:

- uruchomienie przeglądarki,
- otwarcie aplikacji,
- zamkmnięcie przeglądarki po zakończeniu testu.

### DriverFactory

Odpowiada za:

- konfigurację ChromeDriver,
- obsługę trybu headless,
- konfigurację przeglądarki,
- automatyczne pobieranie sterownika przez WebDriverManager.

### ConfigReader

Odczytuje konfigurację z pliku:

src/test/resources/config.properties

### TestDataGenerator

Generuje unikalne dane testowe z wykorzystaniem UUID.

## Zakres testów

### Logowanie

- TC #1 - poprawne logowanie
- TC #2 - błędne hasło
- TC #3 - puste dane logowania
- TC #4 - wielokrotne nieudane logowanie
- TC #5 - rejestracja nowego użytkownika

### Tytuły

- TC #6 - wyświetlanie listy tytułów
- TC #7 - dodanie tytułu
- TC #8 - walidacja pustego formularza
- TC #9 - edycja tytułu
- TC #10 - usunięcie tytułu
- TC #11 - przejście do listy egzemplarzy
- TC #12 - blokada zapisu pustego formularza
- TC #13.1 - zachowanie danych po ponownym zalogowaniu
- TC #13.2 - przekierowanie do ekranu logowania po odświeżeniu strony
- TC #14 - blokada usunięcia tytułu posiadającego egzemplarze

#### Uwaga dotycząca scenariusz TC #13

W pierwotnej wersji dokumentacji csenariusz TC #13 zakładał jedynie odświeżenie strony po dodaniu nowego tytułu i 
sprawdzenie, czy pozostaje on widoczny na liście.

Podczas analizy aplikacji stwierdziłem, że odświeżenie strony powoduje wylogowanie użytkownika oraz przekierowanie do
ekranu logowania. W związku z tym scenariusz został rozdzielony na dwa niezależne przypadki testowe (TC #13.1; TC #13.2.
Takie rozwiązanie pozwoliło zachować zgodność testów z rzeczywistym zachowaniem aplikacji.

### Egzemplarze

- TC #15 - dodanie egzemplarza
- TC #16 - edycja egzemplarza
- TC #17 - usunięcie egzemplarza
- TC #18 - przejście do historii wypożyczeń

#### Uwaga dotycząca walidacji formularza

Podczas przeglądu projektu mentor zwrócił uwagę na brak scenariusza sprawdzającego walidację pustego formularza
dodawania egzemplarza.
Po analizie aplikacji zdecydowałem o nieimplementowaniu takiego testu, ponieważ formularz zawiera wyłącznie pole wyboru
daty (DatePicker). Z poziomu interfejsu użytkownik nie ma możliwości usunięcia ani pozostawienia pustej wartości pola.
W związku z tym scenariusz wysłania pustego formularza nie jest możliwy do wykonania w rzeczywistym użyciu aplikacji
i nie został zaimplementowany w ramach testów UI.

### Wypożyczenia

- TC #19 - dodanie wypożyczenia
- TC #20 - walidacja pustego formularza
- TC #21 - edycja wypożyczenia
- TC #22 - ponowne wypożyczenie tego samego egzemplarza
- TC #23 - usunięcie wypożyczenia
- TC #24 - zmiana statusu egzemplarza po wypożyczeniu

## Znane błędy aplikacji

Scenariusze TC #22 oraz TC #24 zostały zaimplementowane jako testy oznaczone adnotacją @Disabled, ponieważ dokumentują aktualne błędy aplikacji.

TC #22 

Aplikacja umożliwia ponowne wypożyczenie egzemplarza, który powinien posiadać status "Rented"

TC #24 

Po dodaniu wypożyczenia status egzemplarza pozostaje "Avaliable " zamiast zmienić się na "Rented"

Po poprawieniu błędów przez autorów aplikacji testy mogą zostać ponownie włączone.

## Konfiguracja

Plik src/test/resources/config.properties nie znajduje się w repozytorium.

Aby uruchomić projekt:

1. Skpopiuj plik config.properties.example.
2. Zmien nazwę kopii na config.properties
3. Uzupełnij danymi do logowania stworzonymi podczas rejsetracji nowego użytkownika.

Przykład:

base.url=https://ta-bookrental-fe.onrender.com/
timeout.seconds=60
short.timeout.seconds=5
valid.login=your_login
valid.password=your_password
invalid.password=wrongpassword
empty.login=
empty.password=

## Uruchomienie projektu

### InteliJ IDEA

1. Skopiuj repozytorium.
2. Otwórz projekt
3. Zaimportuj zależności Gradle
4. Skopiuj config.properties.example do config.properties
5. Uzupełnij dane logowania
6. Uruchom wybrana klasę testową lub cały pakiet tests

### Terminal

Komenda do uruchomienia testów na środowisku Windows:

.\gradlew.bat test

Komenda do uruchomienia testów na środowisku Linux/macOS:

./gradlew test

## Raport z testów

Po zakończeniu wykonywania testó Gradle automatycznie generuje raport HTML zawierający:

- liczbę wykonanych testów,
- liczbę testów zakończonych sukcesem,
- liczbę niepowodzeń,
- liczbę pominiętych testów,
- czas wykonania,
- szczegółowe wyniki dla każdej klasy testowej.

Raport znajduje się w katalogu:

build/reports/tests/test/index.html

## Continuous Integration

Projekt wykożystuje GitHub Actions.

Workflow automatycznie pobiera kod z repozytorium, tworzy plik config.properties z GitHub Secrets i uruchamia testy
przy użyciu Gradle.

Wymagane GitHub Secrets:

- BASE_URL
- VALID_LOGIN
- VALID_PASSWORD

