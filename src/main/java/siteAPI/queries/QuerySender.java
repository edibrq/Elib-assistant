package siteAPI.queries;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class QuerySender {

    private String username;
    private String password;

    public QuerySender(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void sendPostQuery() throws IOException {
        URL url = new URL("http://elibrary.misis.ru/login.php");
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST"); // PUT is another valid option
        http.setDoOutput(true);

        Map<String, String> arguments = new HashMap<>();
        arguments.put("username", this.username);
        arguments.put("password", this.password);
        StringJoiner stringJoiner = new StringJoiner("&");
        for(Map.Entry<String, String> entry : arguments.entrySet()){
            stringJoiner.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        byte[] out = stringJoiner.toString().getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        http.connect();

        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
    }
}
