package com.example.japanesenamegenerator.config;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class RetryOnTimeoutInterceptor implements Interceptor {

    private final int maxRetries;
    private final long retryDelayMillis;

    public RetryOnTimeoutInterceptor(int maxRetries, long retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        IOException lastException = null;

        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                // Attempt the request
                return chain.proceed(request);
            } catch (SocketTimeoutException e) {
                lastException = e;

                // Wait before retrying the request
                try {
                    System.out.println("Retrying request after 15 seconds due to timeout...");
                    Thread.sleep(retryDelayMillis);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Retry interrupted", ie);
                }
            }
        }

        // If all retries fail, throw the last exception
        throw lastException != null ? lastException : new IOException("Max retries reached without a timeout exception");
    }
}
