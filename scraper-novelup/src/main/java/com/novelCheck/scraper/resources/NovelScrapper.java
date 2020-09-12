package com.novelCheck.scraper.resources;

import com.novelCheck.scraper.model.NovelChap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/scrape")
public class NovelScrapper {

    @RequestMapping("/{seriaNazwa}")
    public NovelChap getNewChapScrape(@PathVariable("seriaNazwa") String seriaNazwa) {
        Document doc = null;

        try {
            doc = Jsoup.connect("https://www.novelupdates.com/series/"+seriaNazwa).get();
            Elements content = doc.getElementsByClass("chp-release");//znajduje chaptery

            return new NovelChap(content.attr("title"),content.attr("href"));

        } catch (IOException ex) {
            ex.printStackTrace();
            return new NovelChap("error","error");
        }




    }
}

/*for (Element chapter : content) {//to mi wypisuje wszystkie a chyba tylko naj mi wystarczy
            String chapNum = chapter.attr("title");//wypisuje nr chapterów
            String chapLink = chapter.attr("href");//wypisuje wszystkie linki
        }*/
//System.out.println();
