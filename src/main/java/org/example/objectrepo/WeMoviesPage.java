package org.example.objectrepo;

import org.openqa.selenium.By;

public class WeMoviesPage {
    public By searchBar = By.xpath("//android.widget.ScrollView/android.widget.ImageView");
    public By noResultFound = By.xpath("//android.view.View[@content-desc=\"No results found.\"]");
    public By nowPlayingElement  = By.xpath("(//android.view.View[@content-desc='No results found.'])[1]");
    public By topRatedElement  = By.xpath("(//android.view.View[@content-desc='No results found.'])[2]");
    public By clearBtn = By.xpath("//android.widget.ImageView[@text=\"oebb\"]/android.view.View");

}
