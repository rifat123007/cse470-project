package MyMusic.model;

import javafx.scene.control.Button;

// Component interface used to implement the composite pattern with albums and tracks
public interface Component {
    void play(Button button);
    void pause();
    void continuePlaying();
    void stop();
    void printInfo();
}
