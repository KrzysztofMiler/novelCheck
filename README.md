# novelCheck
 
 #### Miroserwis mający na celu sprawdzenie najnowszych wydanych rozdziałów lightnovel z wykorzystamiem architektury micorservices w Java Spring.
 
 ### Przedstawienie aktualnej (1/10/20) struktury serwisu
 ![Opis architektury](https://i.imgur.com/UMmtmih.png)
 
### Wpisując http://localhost:8080/index przechodzimy do strony pozwalającej na wykorzystywanie wszystkich funkcji serwisu.
 
#### Wykorzystane technologie i techniki:
 
 * Architektura microservices
 * Spring Boot Web/MVC/Data do stworzenia praktycznie całego projektu
 * Discovery service za pomocą EurekaServer
 * Komunikacja poprzez REST
 * Webscraping za pomocą Jsoup
 * Thymeleaf do komunikacji z użytkownikiem
 * JavaMail do komunikacji przez e-mail
 
#### Krótki opis poszczególnych elementów:
 
 eureka-server pozwala na odkrywanie mikroserwisów bez znajomości ich portu 

 scraper-novelup scraper przeznaczony od wydobywania informacji z witryny NovelUpdates, oparty o Jsoup.
 
 scraper-scribblehub scraper przeznaony do wydobywania informacji z witryny ScribbeHub, oparty o Jsoup.
 
 update-dbH2 serwis przeznaczony do zarządzania bazą danych, włącznie z wpisywaaniem i pobiieraniem danych.
 
 CheckMVC serwis przeznaczony do komunikacji z użytkownikiem, wysyła zapytania od pozostałych elementów, odbierając od nich dane lub wysyłając im je.
 
 novelMail serwis przeznaczony do wysyłki powiadomień przez e-mail dotyczących nowych wydań w novelach które użytkownik zasubskrybował
 
 Serwisy request-update,update-db,update-List są serwisami stworzonymi podaczas developmentu, są nieużywane przez program i aktualnie służą jedynie do testów.
