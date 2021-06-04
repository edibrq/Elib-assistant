package siteAPI.entities;

import org.openqa.selenium.WebElement;

public class TableElement {
    private String title;
    private String href;

    public TableElement(String title, String href) {
        this.title = title;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "TableElement{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
