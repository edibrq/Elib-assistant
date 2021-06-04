import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    private static String URL = "http://elibrary.misis.ru/login.php?errorMessage=%D0%A2%D1%80%D0%B5%D0%B1%D1%83%D0%B5%D1%82%D1%81%D1%8F%20%D0%BF%D0%B0%D1%80%D0%BE%D0%BB%D1%8C%20%D0%B4%D0%BB%D1%8F%20%D0%B4%D0%BE%D1%81%D1%82%D1%83%D0%BF%D0%B0%20%D0%BA%20%D1%8D%D1%82%D0%BE%D0%B9%20%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B5&redirect=http%3A%2F%2Felibrary.misis.ru%2Fabout.php";
    private static String formElement = "div#loginbox.hasDisclaimer";
    public static void main(String[] args) throws IOException {
        
        Document document = Jsoup.connect(URL).userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();

        Elements login = document.select(formElement);

        for (Element element : login.select("label")) {
            System.out.println(element.text());
        }
      
    }
}
