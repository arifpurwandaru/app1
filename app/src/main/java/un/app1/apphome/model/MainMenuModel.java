package un.app1.apphome.model;

public class MainMenuModel {

    public int image;
    public String titleMenu;

    public MainMenuModel(int image, String titleMenu) {
        this.image = image;
        this.titleMenu = titleMenu;
    }

    public MainMenuModel(String titleMenu) {
        this.titleMenu = titleMenu;
    }

}
