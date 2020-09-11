package MyMusic.model;

import javafx.scene.Scene;

public class FromHomePageState implements PageOriginState {
    @Override
    public void goBack(Scene scene, User user) {
        PageChanger.getInstance().goToHomePage(scene, user, "album");
    }

    @Override
    public void printState() {
        System.out.println("Came From Home Page");
    }
}
