package MyMusic.controller;

import MyMusic.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


/*
// Controller for playlist.fxml
*/
public class PlaylistController {
    private Playlist playlist;
    private User user;

    @FXML
    ImageView playlistImageView;

    @FXML
    TextField playlistNameTextField;

    @FXML
    TextField playlistImagePathTextField;

    @FXML
    HBox playlistTracksContent;



    @FXML
    public void goBack() {
        PageChanger.getInstance().goToHomePage(playlistImageView.getScene(), user, "playlist");
    }


    // Method called when the save button is clicked
    // Calls the savePlaylist method of the DatabaseManager and updates the playlist
    @FXML
    public void savePlaylist() {
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            playlist.setName(playlistNameTextField.getText());
            playlist.setImagePath(playlistImagePathTextField.getText());
            databaseManager.savePlaylist(playlist);
            user.setPlaylists(databaseManager.getPlaylists(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Method called when the delete button is clicked
    // Calls the deletePlaylist method of the DatabaseManager and updates the use object's playlist list
    @FXML
    public void deletePlaylist() {
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            databaseManager.deletePlaylist(playlist);
            user.setPlaylists(databaseManager.getPlaylists(user));
            goBack();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Set up method called when 'page' is changed to playlist.fxml
    // Sets up the UI elements of playlist.fxml with the proper values
    public void setUp(Playlist playlist, User user) {
        this.playlist = playlist;
        this.user = user;

        if (playlist != null) {
            Image image = new Image(playlist.getImagePath());
            playlistImageView.setImage(image);
            playlistNameTextField.setText(playlist.getName());
            playlistTracksContent.getChildren().clear();
            TableView tableView = new TrackTableView(playlist.getTracks(), user, playlist);
            tableView.setMinWidth(1600);
            playlistTracksContent.getChildren().add(tableView);
        }
    }
}
