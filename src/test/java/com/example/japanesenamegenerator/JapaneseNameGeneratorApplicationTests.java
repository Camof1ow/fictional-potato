package com.example.japanesenamegenerator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class JapaneseNameGeneratorApplicationTests {

    @Test
    void contextLoads() throws IOException {

        String requestUrl = "https://m.map.kakao.com/actions/searchJson?type=PLACE&q=%EC%8B%9D%EB%8B%B9&wxEnc=LVVTRM&wyEnc=QNLQNMS&pageNo=2&cidx=&sort=0&rcode=&busStopCount=135&placeCount=1077031&service=&qa_type=&datetime=";
        boolean success = false;
        Map<String, String> map = getHeaders();
        while (true)


    }

    private static Response requestGetWithHeaders(String url, Map<String, String> header)
            throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(Duration.ofSeconds(15L))
                .readTimeout(Duration.ofSeconds(15L)).build();

        Request.Builder builder = new Request.Builder().url(url).method("GET", null);
        if(header != null) {
            header.forEach((key, value) -> {
                builder.addHeader(key, value);
            });
        }

        Request request = builder.build();

        return client.newCall(request).execute();
    }

    private static Map<String,String> getHeaders(){
        String requestUrl = "https://stat.tiara.kakao.com/track?d=%7B%22sdk%22%3A%7B%22type%22%3A%22WEB%22%2C%22version%22%3A%221.1.33%22%7D%2C%22env%22%3A%7B%22screen%22%3A%222560X1440%22%2C%22tz%22%3A%22%2B9%22%2C%22cke%22%3A%22Y%22%2C%22uadata%22%3A%7B%22fullVersionList%22%3A%5B%7B%22brand%22%3A%22Not)A%3BBrand%22%2C%22version%22%3A%2299.0.0.0%22%7D%2C%7B%22brand%22%3A%22Google%20Chrome%22%2C%22version%22%3A%22127.0.6533.122%22%7D%2C%7B%22brand%22%3A%22Chromium%22%2C%22version%22%3A%22127.0.6533.122%22%7D%5D%2C%22mobile%22%3Afalse%2C%22model%22%3A%22%22%2C%22platform%22%3A%22Windows%22%2C%22platformVersion%22%3A%2219.0.0%22%7D%7D%2C%22common%22%3A%7B%22session_timeout%22%3A%221800%22%2C%22svcdomain%22%3A%22m.map.kakao.com%22%2C%22deployment%22%3A%22production%22%2C%22url%22%3A%22https%3A%2F%2Fm.map.kakao.com%2Factions%2FsearchView%3Fq%3D%25EB%25A7%259B%25EC%25A7%2591%26wxEnc%3DLWQOQP%26wyEnc%3DQNMOOTN%26lvl%3D8%26rect%3D440535%2C1083750%2C533975%2C1181030%26viewmap%3Dtrue%26BT%3D1724597156464%22%2C%22title%22%3A%22%EA%B2%80%EC%83%89%20%7C%20%EC%B9%B4%EC%B9%B4%EC%98%A4%EB%A7%B5%22%2C%22page%22%3A%22searchView%22%7D%2C%22etc%22%3A%7B%22client_info%22%3A%7B%22tuid%22%3A%22w-1b6p4TkQknRW_240825235941371%22%2C%22tsid%22%3A%22w-1b6p4TkQknRW_240825235941371%22%2C%22uuid%22%3A%22w-YLfjwpIHkv80_240825618086138%22%2C%22suid%22%3A%22w-YLfjwpIHkv80_240825618086138%22%2C%22isuid%22%3A%22w-F2EClk9h3zS3_240825650573437%22%2C%22client_timestamp%22%3A1724597981824%7D%7D%2C%22action%22%3A%7B%22type%22%3A%22Event%22%2C%22name%22%3A%22searchView%22%2C%22kind%22%3A%22%22%7D%2C%22custom_props%22%3A%7B%22te1%22%3A%22layeron%22%2C%22te2%22%3A%22M%22%7D%7D&uncri=33986&uncrt=0";
        Map<String, String> headers = new HashMap<>();
        headers.put("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
        try (Response response = requestGetWithNoRedirect(requestUrl, headers)) {
            Map<String, List<String>> headerMap = response.headers().toMultimap();
            List<String[]> list = headerMap.get("set-cookie").stream().map(str -> {
                int index = str.indexOf(';');
                return str.substring(0, index).split("=");
            }).toList();
            Map<String, String> cookies = new HashMap<>();
            list.forEach(arr -> {
                if(arr.length == 2) cookies.put(arr[0], arr[1]);

            });
            cookies.put("__T","1");
            cookies.put("__T_SECURE","1");
            cookies.put("LOCAL_LOG_SUID","3wqZY6Fgx9UJ_20240826232243487");
            cookies.put("maptype","roadmap");
            cookies.put("nowCenter","606160%2C952885%2C11");


            return cookies;

            //todo : Cookie 에서 T_ANO= 값 가져오기.
            //todo : 아래와 같은 형태로 헤더 만들어주기
            //
            //__T_=1
            //__T_SECURE=1
            //maptype=roadmap
            //_T_ANO=NlUhArf0Sieml29ScOIakJa9q6Ifr0w+3kCzVTBGUn5YMRhQMovt/OphQQOIlICfX0TCyj5v6+DSuqBj9j6qis35AdOBlUaWTeIqlk4rdOhBt8RRO1iskLyV0/S7qWAXKmyFdLwtzwiSeiEIouHGHR2iLrowVspMQZdAkN1boMwvTN2s4tHcW0iUpbmBlmgTzSXuCfZkQ0ztSUyTrDJAumsqn0bXmOIB08v18iIvnPVs0jqN6vG6VGg9qJ8lpIbdrHow7ywOQ7560q6k8fWc1/SB4ISqr1VxPRzr0rnr3dYWlP7OX1MN59Wz9w6iYlhYZGUIZ5FO5YAwbgaSh8MW8w==
            //nowCenter=495280%2C1129800%2C4
            ////LOCAL_LOG_SUID=5AXu0OWbn8ZV_20240826000032625
            //https://m.map.kakao.com/actions/searchJson?type=PLACE&q=%EB%A7%9B%EC%A7%91&wxEnc=LWQOQP&wyEnc=QNMOOTN&pageNo=3&sort=0&placeCount=260879

        }catch (Exception e){
            return null;
        }
    }

    private static Response request(String method, String url, RequestBody body)
            throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(Duration.ofSeconds(15L))
                .readTimeout(Duration.ofSeconds(15L)).build();
        Request request = new Request.Builder()
                .url(url)
                .method(method, body)
                .build();
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

}
