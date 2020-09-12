# novelCheck
 
 Miroserwis mający na celu sprawdzenie najnowszych wydanych rozdziałów lightnovel.
 
 Wpisując http://localhost:8081/request/{ID} uzysujemy informację zwrotną w posiatci najnowszych wydanych rozdziałów.
 
 eureka-server pozwala na odkrywanie mikroserwisów bez znajomości ich portu
 
 request-update zajmuje się odpytaniem scraperów zgodnie z listą zawartą w update-List i zwrócemiem informacji od webscapperów
 
 update-List zawiera w sobie listę novelek
 
 scraper-novelup scraper przeznaczony od wydobywania informaji z witryny NovelUpdates
 
 scraper-scribblehub scraper przeznaony do wydobywania informacj z witryny ScribbeHub
 
 TODO update-db
 
