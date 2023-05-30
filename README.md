# Nazwa Projektu: QuizApp
## Opis projektu
QuizApp to aplikacja mobilna umożliwiająca społeczność użytkownikom tworzenie, udostępnianie i rozwiązywanie quizów. Użytkownicy mogą rywalizować ze sobą, sprawdzając swoją wiedzę w różnych dziedzinach. Aplikacja współpracuje z serwisem RESTowym backendu opartym na technologii Spring Boot z wykorzystaniem języka Kotlin oraz bazy danych Postgres. Backend jest wdrożony w Azure App Service i wykorzystuje Azure Database for Postgresql. Aplikacja mobilna została stworzona w technologii Android i opiera się na architekturze MVVM.

## Komponenty techniczne aplikacji
Projekt QuizApp składa się z kilku głównych komponentów technicznych, które pełnią kluczowe role w funkcjonowaniu aplikacji. Są to:

### 1. Backend (serwis RESTowy w Spring Boot z Kotlinem)
Backend aplikacji jest odpowiedzialny za przetwarzanie żądań i dostarczanie danych do aplikacji mobilnej. Wykorzystuje framework Spring Boot w połączeniu z językiem Kotlin. Backend implementuje RESTowe interfejsy API, które pozwalają na komunikację z frontendem. Zadania backendu obejmują:

Obsługę rejestracji i logowania użytkowników.
Zarządzanie quizami, pytaniami i odpowiedziami w bazie danych.
Przetwarzanie żądań związanych z rozwiązywaniem quizów i zwracanie wyników.
Udostępnianie informacji o wynikach i rankingach użytkowników.
### 2. Baza danych (Postgres)
Aplikacja wykorzystuje bazę danych Postgres do przechowywania informacji o użytkownikach, quizach, pytaniach, odpowiedziach i wynikach. Baza danych jest integralną częścią backendu i umożliwia trwałe przechowywanie danych aplikacji.
![diagram1](https://github.com/KlaudiaK/QuizApp-SQL-Project/blob/main/diagram/ER_diagram.png)
![diagram2](https://github.com/KlaudiaK/QuizApp-SQL-Project/blob/main/diagram/relational_diagram.png)

### 3. CI/CD (Azure App Service, Azure Database for Postgresql)
Aplikacja jest wdrożona w chmurze Azure przy użyciu usługi Azure App Service, która umożliwia hostowanie i skalowanie backendu. Backend łączy się z bazą danych Azure Database for Postgresql, co zapewnia niezawodne przechowywanie danych.

### 4. Aplikacja mobilna (Android)
Aplikacja mobilna QuizApp została zaimplementowana w natywnym Androidzie, z wykorzystaniem architektury MVVM (Model-View-ViewModel). Aplikacja udostępnia interfejs użytkownika, który umożliwia użytkownikom:

1. Rejestrację i logowanie.
2. Przeglądanie dostępnych quizów.
3. Rozwiązywanie quizów i przesyłanie odpowiedzi do backendu.
4. Przeglądanie wyników i rankingów społeczności.
