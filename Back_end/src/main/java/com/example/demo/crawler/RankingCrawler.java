package com.example.demo.crawler;


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
@Getter
public class RankingCrawler {
    private String NetflixRankingURL = "https://some.co.kr/media/home";
    public String getNetflixRanking() throws IOException {
        Document jsoup = Jsoup.connect(NetflixRankingURL).get();
        System.out.println(jsoup.toString());
        return jsoup.toString();
    }
   
}
