package com.novelCheck.scraper.resources;

import com.novelCheck.scraper.model.NovelChap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/scrape")
public class NovelScrapper {

    @RequestMapping("/{seriaID}/{seriaNazwa}")
    public NovelChap getNewChapScrape(@PathVariable("seriaNazwa") String seriaNazwa,@PathVariable("seriaID") String seriaID) {
        Document doc = null;

        try {//+seriaID+"/"+seriaNazwa
            doc = Jsoup.connect("https://www.scribblehub.com/series/"+seriaID+"/"+seriaNazwa).get();//TODO rozdzielić id serii z jej nazwą
            Elements content = doc.getElementsByClass("toc_w");//znajduje chaptery ostatni
            Elements link = doc.getElementsByClass("toc_a");//może jest lepszy sposób
            Element tytul = content.select("a").first();
            return new NovelChap(content.attr("order"),link.attr("href"),tytul.text());

        } catch (IOException ex) {
            ex.printStackTrace();
            return new NovelChap("error","error","error");
        }




    }
}

/*for (Element chapter : content) {//to mi wypisuje wszystkie a chyba tylko naj mi wystarczy
            String chapNum = chapter.attr("title");//wypisuje nr chapterów
            String chapLink = chapter.attr("href");//wypisuje wszystkie linki
        }*/
//System.out.println();
