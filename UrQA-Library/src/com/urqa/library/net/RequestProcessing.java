package com.urqa.library.net;

import org.apache.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author seunoh on 2014. 1. 26..
 */
public class RequestProcessing {

    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    public NetworkResponse performRequest(Request request) throws IOException {
        HttpURLConnection connection = openConnection(request);

        setConnectionParametersForRequest(connection, request);

        int responseCode = connection.getResponseCode();
        if (responseCode == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }

        return new NetworkResponse(responseCode, responseData(connection), responseHeader(connection));
    }


    static byte[] responseData(HttpURLConnection connection) throws IOException {
        int status = connection.getResponseCode();
        InputStream inputStream;
        if(status >= HttpStatus.SC_BAD_REQUEST)
            inputStream = connection.getErrorStream();
        else
            inputStream = connection.getInputStream();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        if (inputStream == null) {
           return  buffer.toByteArray();
        } else {
            int read;
            byte[] data = new byte[2048];

            while ((read = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }

            buffer.flush();
            return buffer.toByteArray();
        }
    }

    static Map<String, String> responseHeader(URLConnection connection) {

        Map<String, String> map = new HashMap<String, String>();
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        Set<String> headerFieldsSet = headerFields.keySet();

        for (String headerFieldKey : headerFieldsSet) {
            List<String> headerFieldValue = headerFields.get(headerFieldKey);
            StringBuilder sb = new StringBuilder();
            for (String value : headerFieldValue) {
                sb.append(value);
                sb.append("");
            }

            map.put(headerFieldKey, sb.toString());
        }
        return map;
    }

    /**
     * Create an {@link java.net.HttpURLConnection} for the specified {@code url}.
     */
    protected HttpURLConnection createConnection(URL url, Proxy proxy) throws IOException {
        if (proxy == null)
            return (HttpURLConnection) url.openConnection();
        else
            return (HttpURLConnection) url.openConnection(proxy);
    }

    private HttpURLConnection openConnection(Request request) throws IOException {
        String url = request.getUrl();
        URL parsedUrl = new URL(url);

        HttpURLConnection connection = createConnection(parsedUrl, request.getProxy());

        int timeoutMs = request.getTimeoutMs();

        connection.setConnectTimeout(timeoutMs);
        connection.setReadTimeout(timeoutMs);
        connection.setUseCaches(false);
        connection.setDoInput(true);


        return connection;
    }


    /* package */
    static void setConnectionParametersForRequest(HttpURLConnection connection,
                                                  Request request) throws IOException {
        switch (request.getMethod()) {
            case GET:
                // Not necessary to set the request method because connection defaults to GET but
                // being explicit here.
                connection.setRequestMethod(request.getMethod().name());
                break;
            case DELETE:
                connection.setRequestMethod(request.getMethod().name());
                break;
            case POST:
                connection.setRequestMethod(request.getMethod().name());
                addBodyIfExists(connection, request);
                break;
            case PUT:
                connection.setRequestMethod(request.getMethod().name());
                addBodyIfExists(connection, request);
                break;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    private static void addBodyIfExists(HttpURLConnection connection, Request request)
            throws IOException {
        byte[] body = request.getBody();
        if (body != null) {
            connection.setDoOutput(true);
            connection.setRequestProperty(HEADER_CONTENT_TYPE, request.getHeaderContentType());
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(body);
            out.close();
        }
    }

}
