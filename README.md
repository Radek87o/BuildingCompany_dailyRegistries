# BuildingCompany_dailyRegistries

#2019-02-18

Idea:
Aplikacja oparta na autentycznej potrzebie biznesowej pewnej firmy budowlanej. Celem jest automatyzacja przesyłania i gromadzenia danych z dziennych rejestrów pracowników budowy.

Wykorzystane technologie:
- Java 8
- Spring 5 (Core, MVC - Annotations All Java Configuration, Security)
- Hibernate 5.4.0
- MySQL 8.0.13
- Maven
- HTML 5
- CSS 3
- Bootstrap 4

Idea:
Dzienne rejestry zawierają informacje o:
  - liczbie przepracowanych godzin
  - ew. nieobecności pracownika
  - dacie rejestru
  - noclegu i cateringu jeśli były wykorzystywane
  
 Każdy rejestr jest przypisany do pracownika i projektu.  
 Rejestry są pomocniczym narzędziem dla księgowych i pozwalają uzasadnić i potwierdzić spływające do firmy faktur (np. za nocleg lub catering).
 Rejestry dla wszystkich pracowników projektu wypełniają kierownicy budowy (użytkownicy).
 Koordynator (Admin) zakłada projekty w systemie, przypisuje im kierowników i pracowników
 
 Funkcjonalność:
 1) funkcje administratora
    - rejestracja, update i usunięcie użytkownika (kierownik budowy)
    - stworzenie, update, zmiana statusu i usunięcie projektu
    - dodanie, update i usunięcie pracownika
    - przypisanie i wypisanie pracownika z projektu (pracownik może być tylko w jednym aktualnym projekcie)
    - raport z pełną listą rejestrów
    + wszystkie funkcje użytkowników
 2) funckje użytkownika
    - wypełnienie zbiorczego rejestru
    - edycja rejestru dla poszczególnych pracowników
    - usuwanie rejestrów
    - edycja rejestrów dla podanej daty
    - kopiowanie rejestru historycznego do nowej daty
    - dodanie obiektu Cateringu
    - dodanie obiektu noclegu
