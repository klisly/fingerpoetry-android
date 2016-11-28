package com.klisly.bookbox.utils;

import com.klisly.bookbox.api.BookRetrofit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class ChapterParser {
    public static String getContet(String url) {
        try {
//            Request request = chain.request()
//                    .newBuilder()
//                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                    .addHeader("Accept-Encoding", "gzip, deflate")
//                    .addHeader("Connection", "keep-alive")
//                    .addHeader("Accept", "*/*")
//                    .addHeader("Cookie", "add cookies here")
//                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = BookRetrofit.getInstance().getOkHttpClient().newCall(request).execute();
            Document doc = Jsoup.parse(response.body().string());
            Elements eles = doc.select("#content");
            Timber.i("load content from source");
            if (eles.size() > 0) {
                return eles.get(0).html();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
