import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class FormClicker {

    private static String login = "185116";
    private static String password = "Эдуард";
    private static String  enterURL = "http://elibrary.misis.ru/login.php?errorMessage=%D0%A2%D1%80%D0%B5%D0%B1%D1%83%D0%B5%D1%82%D1%81%D1%8F%20%D0%BF%D0%B0%D1%80%D0%BE%D0%BB%D1%8C%20%D0%B4%D0%BB%D1%8F%20%D0%B4%D0%BE%D1%81%D1%82%D1%83%D0%BF%D0%B0%20%D0%BA%20%D1%8D%D1%82%D0%BE%D0%B9%20%D1%81%D1%82%D1%80%D0%B0%D0%BD%D0%B8%D1%86%D0%B5&redirect=http%3A%2F%2Felibrary.misis.ru%2Fabout.php";
    private static String searchValue = "854";

    public static void main(String[] args) {
        String projectPath = System.getProperty("E\\Java\\ParseProject\\");
        System.setProperty("webdriver.chrome.driver", projectPath + "\\driver/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get(enterURL);
        String title = driver.getTitle();
        if(title.equals("Вход | Либэр. Электронная библиотека")){
            WebElement loginElement = driver.findElement(By.id("username"));
            loginElement.sendKeys(login);

            WebElement passwordElement = driver.findElement(By.id("password"));
            passwordElement.sendKeys(password);

            WebElement enterElement = driver.findElement(new By.ByXPath("//*[@id=\"formbox\"]/form/div[2]/input"));
            enterElement.click();

            WebElement search = driver.findElement(new By.ByXPath("//*[@id=\"txtSearchBar1\"]"));
            search.sendKeys(searchValue);

            WebElement searchButton = driver.findElement(new By.ByXPath("//*[@id=\"ext-gen20\"]"));
            searchButton.click();
        }
    }

    public void OpenBrowser(String login,String password, String enterURL){
        String projectPath = System.getProperty("E\\Java\\ParseProject\\");
        System.setProperty("webdriver.chrome.driver", projectPath + "\\driver/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get(enterURL);

    }
}
