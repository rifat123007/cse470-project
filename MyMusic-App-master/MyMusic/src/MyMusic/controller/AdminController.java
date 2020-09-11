package MyMusic.controller;

import MyMusic.model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AdminController {
    User user;
    ArrayList<Track> tracks;
    ArrayList<Album> albums;
    ArrayList<Artist> artists;

    @FXML
    Button backButton;
    @FXML
    Button addButton;
    @FXML
    ScrollPane scrollPane;

    VBox tracksBox;
    VBox albumsBox;
    VBox artistsBox;


    @FXML
    public void editTracksBtnAction() {
        scrollPane.setContent(tracksBox);
        addButton.setVisible(true);
        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tracksBox.getChildren().add(new TrackEditBox());
            }
        });
    }

    @FXML
    public void editAlbumsBtnAction() {
        scrollPane.setContent(albumsBox);
        addButton.setVisible(true);
        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                albumsBox.getChildren().add(new AlbumEditBox());
            }
        });
    }

    @FXML
    public void editArtistsBtnAction() {
        scrollPane.setContent(artistsBox);
        addButton.setVisible(true);
        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                artistsBox.getChildren().add(new ArtistEditBox());

            }
        });
    }


    @FXML
    public void goBack() {
        PageChanger.getInstance().goToHomePage(backButton.getScene(), user, "");
    }

    @FXML
    public void setUp(User user) {
        this.user = user;
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            tracks = databaseManager.getAllTracks(user);
            albums = databaseManager.getAllAlbums(user);
            artists = databaseManager.getAllArtists(user);

            tracksBox = new VBox();
            albumsBox = new VBox();
            artistsBox = new VBox();

            HBox tracksLabelBox = new HBox();
            Label trackNameLabel = new Label("Name");
            trackNameLabel.setPrefWidth(300);
            Label trackMediaPathLabel = new Label("Media Path");
            trackMediaPathLabel.setPrefWidth(400);
            Label trackGenreLabel = new Label("Genre");
            trackGenreLabel.setPrefWidth(150);
            Label trackPlaysLabel = new Label("Plays");
            trackPlaysLabel.setPrefWidth(150);
            Label trackTimeLabel = new Label("Time");
            trackTimeLabel.setPrefWidth(150);
            tracksLabelBox.getChildren().addAll(trackNameLabel, trackMediaPathLabel, trackGenreLabel, trackPlaysLabel, trackTimeLabel);
            tracksBox.getChildren().add(tracksLabelBox);
            for(int i = 0; i < tracks.size(); i++) {
                tracksBox.getChildren().add(new TrackEditBox(tracks.get(i)));
            }


            HBox albumsLabelBox = new HBox();
            Label albumNameLabel = new Label("Name");
            albumNameLabel.setPrefWidth(300);
            Label albumImagePathLabel = new Label("Image Path");
            albumImagePathLabel.setPrefWidth(400);
            Label albumGenreLabel = new Label("Genre");
            albumGenreLabel.setPrefWidth(150);
            Label albumYearLabel = new Label("Year");
            albumYearLabel.setPrefWidth(150);
            Label albumRatingLabel = new Label("Rating");
            albumRatingLabel.setPrefWidth(150);
            albumsLabelBox.getChildren().addAll(albumNameLabel, albumImagePathLabel, albumGenreLabel, albumYearLabel, albumRatingLabel);
            albumsBox.getChildren().add(albumsLabelBox);
            for(int i = 0; i < albums.size(); i++) {
                albumsBox.getChildren().add(new AlbumEditBox(albums.get(i)));
            }


            HBox artistsLabelBox = new HBox();
            Label artistNameLabel = new Label("Name");
            artistNameLabel.setPrefWidth(300);
            Label artistImagePathLabel = new Label("Image Path");
            artistImagePathLabel.setPrefWidth(400);
            Label artistRatingLabel = new Label("Rating");
            artistRatingLabel.setPrefWidth(150);
            artistsLabelBox.getChildren().addAll(artistNameLabel, artistImagePathLabel, artistRatingLabel);
            artistsBox.getChildren().add(artistsLabelBox);
            for(int i = 0; i < artists.size(); i++) {
                artistsBox.getChildren().add(new ArtistEditBox(artists.get(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
