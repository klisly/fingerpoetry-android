package com.klisly.bookbox.utils;

import com.klisly.bookbox.api.BookRetrofit;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class ChapterParser {
    public static String getContet(String url) {
        try {
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            addHeader(url, builder);
            Request request = builder.build();
            Response response = BookRetrofit.getInstance().getOkHttpClient().newCall(request).execute();
            Document doc = Jsoup.parse(response.body().string());
            Elements eles = doc.select("#content");
            Timber.i("load content from source");
            if (eles.size() > 0) {
                String data = eles.get(0).html();
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void addHeader(String url, Connection con) {
//        con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        con.header("Accept-Encoding","gzip, deflate, sdch");
//        con.header("Cache-Control", "no-cache");
//        con.header("Range","bytes="+"");
//        con.header("Accept-Language", "en-US,en;q=0.8,ja;q=0.6,th;q=0.4,zh-TW;q=0.2,zh;q=0.2,zh-CN;q=0.2");
//        con.header("User-Agent", RandomUserAgent.getRandomUserAgent());
//        con.header("Referer", url.substring(0, url.lastIndexOf("/")));
    }

    private static void addHeader(String url, Request.Builder builder) {
//        builder.addHeader("Accept", "text/html");
//        builder.addHeader("Accept-Encoding","gzip, deflate");
//        builder.addHeader("Cache-Control", "no-cache");
//        builder.addHeader("Connection", "close");
//        builder.addHeader("User-Agent", RandomUserAgent.getRandomUserAgent());
//        builder.addHeader("Referer", url.substring(0, url.lastIndexOf("/")));
    }
}
