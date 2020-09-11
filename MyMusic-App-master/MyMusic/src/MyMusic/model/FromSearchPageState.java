package MyMusic.model;

import javafx.scene.Scene;

public class FromSearchPageState implements PageOriginState {
    @Override
    public void goBack(Scene scene, User user) {
        PageChanger.getInstance().goToSearchPage(scene, user);
    }

    @Override
    public void printState() {
        System.out.println("Came From Search Page");
    }
}
