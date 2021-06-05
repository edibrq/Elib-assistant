package siteAPI.queries;

import java.io.IOException;

public class TestMain {
    public static void main(String[] args) {
        QuerySender querySender = new QuerySender("185116", "Эдуард");
        try {
            querySender.sendPostQuery();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
