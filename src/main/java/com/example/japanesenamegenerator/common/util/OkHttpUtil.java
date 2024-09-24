package com.example.japanesenamegenerator.common.util;

import com.example.japanesenamegenerator.config.RetryOnTimeoutInterceptor;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class OkHttpUtil {


    public static Response requestPost(String url, String requestBody)
            throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(requestBody, mediaType);

        return request("POST", url, body);
    }

    public static Response request(String method, String url, RequestBody body)
            throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .connectTimeout(Duration.ofSeconds(120L))
                .readTimeout(Duration.ofSeconds(120L))
                .build();

        Request.Builder builder = new Request.Builder().url(url).method(method, body);
//        header.forEach(builder::addHeader);
        Request request = builder.build();

        return client.newCall(request).execute();
    }

    public static Response requestGetWithNoRedirect(String url, Map<String, String> header) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .followRedirects(false)
                .followSslRedirects(false)
                .retryOnConnectionFailure(true)
                .connectTimeout(Duration.ofSeconds(120L))
                .readTimeout(Duration.ofSeconds(120L))
                .build();

        Request.Builder builder = new Request.Builder().url(url).method("GET", null);
        header.forEach(builder::addHeader);
        Request request = builder.build();

        return client.newCall(request).execute();
    }


    public static String unWrapJsonString(String input) {
        int startIndex = input.indexOf('(');
        int endIndex = input.lastIndexOf(')');

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return input.substring(startIndex + 1, endIndex).trim();
        } else {
            throw new IllegalArgumentException("Invalid JSON string");
        }
    }

    public static Response requestGetWithHeaders(String url, Map<String, String> header)
            throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(Duration.ofSeconds(15L))
                .readTimeout(Duration.ofSeconds(15L))
                .addInterceptor(new RetryOnTimeoutInterceptor(5, 15000))
                .build();

        Request.Builder builder = new Request.Builder().url(url).method("GET", null);
        if (header != null) {
            header.forEach(builder::addHeader);
        }
        Request request = builder.build();

        return client.newCall(request).execute();
    }



}
