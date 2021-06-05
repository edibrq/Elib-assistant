package siteAPI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import siteAPI.entities.TableElement;

import java.util.*;


public class FormClicker {

    private final String nextPageRefPrev = "http://elibrary.misis.ru/search2.php?action=oldSearchResults&page=";
    private final String nextPageRefNext = "&sort_on=ktcore.columns.title&sort_order=asc\"";


    private final String login;
    private final String password;
    private final String enterURL;
    private final String searchValue;
    private ArrayList<TableElement> tableElements;


    public FormClicker(String login, String password,
                       String enterURL, String searchValue) {
        this.login = login;
        this.password = password;
        this.enterURL = enterURL;
        this.searchValue = searchValue;
        tableElements = new ArrayList<>();
    }

    public void start() {
        String projectPath = System.getProperty("E\\Java\\ParseProject\\");
        System.setProperty("webdriver.chrome.driver", projectPath + "\\driver/chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get(this.enterURL);
        String title = driver.getTitle();

        if (title.equals("Вход | Либэр. Электронная библиотека")) {
            WebElement loginElement = driver.findElement(By.id("username"));
            loginElement.sendKeys(this.login);

            WebElement passwordElement = driver.findElement(By.id("password"));
            passwordElement.sendKeys(this.password);

            WebElement enterElement = driver.findElement(new By.ByXPath("//*[@id=\"formbox\"]/form/div[2]/input"));
            enterElement.click();

            WebElement search = driver.findElement(new By.ByXPath("//*[@id=\"txtSearchBar1\"]"));
            search.sendKeys(this.searchValue);

            WebElement searchButton = driver.findElement(new By.ByXPath("//*[@id=\"ext-gen20\"]"));
            searchButton.click();

//            WebElement selectElem = driver.findElement(By.xpath("//*[@id=\"content\"]/form/table/tfoot/tr/td[2]/select"));
//            Select select = new Select(selectElem);
//            select.selectByValue("50");

            searchResult(driver);
            download(driver);
        }
    }

    public int parseString(String text){
        String[] split = text.split("\\s");
        return Integer.parseInt(split[0]);
    }

    public String getLinkFromDocument(Document document){
        return null;
    }

    public void addElementsToList(List<TableElement> tableElements, String pageSource){

        Document page = Jsoup.parse(pageSource);

        Element wrapper = page.select("div#wrapper").first();

        Element table = wrapper.select("table").get(6);
        Elements a = table.select("a");

        for (Element href: a){
            tableElements.add(new TableElement(href.text(),href.attr("href")));
        }
        tableElements.removeIf(tableElement -> tableElement.getHref().contains("http://"));
    }

    public void addReferenceToTableElement(TableElement element){
        String href = element.getHref();
        StringBuilder stringBuilder = new StringBuilder(href);
        stringBuilder.insert(0,"http://elibrary.misis.ru");
        element.setHref(stringBuilder.toString());
    }

    public void editHrefOfElements(List<TableElement> tableElements){
        tableElements.stream().forEach(this::addReferenceToTableElement);
    }

    public TableElement searchElement(String searchValue, List<TableElement> tableElements){
        TableElement tableElement = tableElements.stream()
                .filter(element -> element.getTitle().contains(searchValue))
                .findFirst().get();

        System.out.println("Search result:"+tableElement.getTitle());
        if (tableElement != null)
            return tableElement;

        return null;
    }

    public void searchResult(ChromeDriver driver) {
        WebElement numberOfElements = driver.findElement(new By.ByClassName("descriptiveText"));

        String text = numberOfElements.getText();
        System.out.println(text);
        int results = parseString(text);
        System.out.println(results);

        if (results < 50) {
            String pageSource = driver.getPageSource();

            addElementsToList(tableElements, pageSource);
            editHrefOfElements(tableElements);

            System.out.println("Найдено " + tableElements.size() + " записей");

            try{
                goToBookPage(driver, searchElement(searchValue, tableElements));
            } catch (NoSuchElementException e){
                System.out.println("No such element!");
            }
            
        } else {
            int numberOfPages = results / 50 + 1;
            System.out.println("Number of pages:"+numberOfPages);
            for (int i = 1; i <= numberOfPages; i++) {
                int pageNumber = i;
                StringBuilder nextPageRef = new StringBuilder();
                nextPageRef.append(nextPageRefPrev).append(i).append(nextPageRefNext);

                String pageSource = driver.getPageSource();
                addElementsToList(tableElements, pageSource);
                editHrefOfElements(tableElements);

                goToNextPage(driver, i);
            }
        }
    }

    public void goToBookPage(ChromeDriver driver, TableElement result){
        //Logic of opening new Window
        if (!result.getTitle().contains(searchValue)) {
            System.out.println("Not found.");
        } else {
            driver.executeScript("window.open('" + result.getHref() + "', 'new_window')");
            Set<String> windowHandles = driver.getWindowHandles();
            Iterator<String> iterator = windowHandles.iterator();
            iterator.next();

            WebDriver windowNew = driver.switchTo().window(iterator.next());
            System.out.println("switched to " + driver.getTitle() + "  Window");

            WebElement numberOfPagesStr = windowNew.findElement(By.xpath("//*[@id=\"SecView-page-count\"]"));
            System.out.println("Number of pages:" + parseString(numberOfPagesStr.getText()));


            String pageSource = driver.getPageSource();
            Document document = Jsoup.parse(pageSource);
            System.out.println(document);
        }
    }

    public void goToNextPage(ChromeDriver driver, int pageNumber){
//        String oldTab = driver.getWindowHandle();

        StringBuilder linkText = new StringBuilder("//*[@id=\"content\"]/form/table/tfoot/tr/td[1]/span[2]/span/a[");
        linkText.append(pageNumber);
        linkText.append("]");
        driver.findElement(By.xpath(linkText.toString())).click();

//        ArrayList<String> newTab = new ArrayList<>(driver.getWindowHandles());
//        System.out.println(newTab);

        addElementsToList(tableElements, driver.getPageSource());
        editHrefOfElements(tableElements);


        try{
            goToBookPage(driver, searchElement(searchValue, tableElements));
        } catch (NoSuchElementException e){
            System.out.println("No such element!");
        }

//        newTab.remove(oldTab);
        // change focus to new tab
//        driver.switchTo().window(newTab.get(0));

        addElementsToList(tableElements, driver.getPageSource());
        editHrefOfElements(tableElements);
        System.out.println("Найдено " + tableElements.size() + " записей");
        // Do what you want here, you are in the new tab

//        driver.close();
        // change focus back to old tab
//        driver.switchTo().window(oldTab);
    }

    private void download(ChromeDriver driver) {

    }
}