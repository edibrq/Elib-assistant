package siteAPI;

import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws IOException {
         String login = "185116";
         String password = "Эдуард";
         String  enterURL = "http://elibrary.misis.ru/login.php?errorMessage=%D0%A2%D1%80%D0%B5%D0%B1%D1%83%D0%B5%D1%82%D1%81%D1%8F%20%D0%BF%D0%B0%D1%80%D0%BE%D0%BB%D1%8C%20%D0%B4%D0%BB%D1%8F%20%D0%B4%D0%BE%D1%81%D1%82%D1%83%D0%BF%D0%B0%20%D0%BA%20%D1%8D%D1%82%D0%BE%D0%B9%20%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B5&redirect=http%3A%2F%2Felibrary.misis.ru%2Fabout.php";
         String searchValue = "Разработка технологии выплавки азотосодержащих марок стали в индукционных печах";
         String searchValue1 = "123";

         FormClicker formClicker = new FormClicker(login, password, enterURL, searchValue);
         formClicker.OpenBrowser();
    }
}
