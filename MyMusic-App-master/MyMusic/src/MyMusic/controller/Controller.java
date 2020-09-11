package MyMusic.controller;

import MyMusic.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/*
// Controller for home.fxml
*/
public class Controller {
    private User user;

    private TrackTableView trackContent;
    private VBox albumContent;
    private VBox artistContent;
    private VBox playlistContent;

    @FXML
    private Label nameLabel;

    @FXML
    private Button adminButton;

    @FXML
    private VBox yourMusicContent;



    // Display methods to set the shown content when tabs are clicked
    @FXML
    private void displayTracks() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));
        yourMusicContent.getChildren().add(trackContent);
    }

    @FXML
    private void displayAlbums() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));
        yourMusicContent.getChildren().add(albumContent);
    }

    @FXML
    private void displayArtists() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));
        yourMusicContent.getChildren().add(artistContent);
    }

    @FXML
    private void displayPlaylists() {
        yourMusicContent.getChildren().clear();
        yourMusicContent.setPadding(new Insets(0,10,0,10));
        yourMusicContent.getChildren().add(playlistContent);
    }


    // Get a UI element to show a users's tracks
    public void setTrackContent(List<Track> trackList) {
        trackContent = new TrackTableView(trackList, user);
    }

    // Get a UI element to show a users's albums
    public void setAlbumContent(List<Album> albumList) {
        albumContent = new VBox();
        albumContent.setSpacing(5);
        //albumContent.setPadding(new Insets(5,5,5,5));

        HBox itemBoxRow = new HBox();
        itemBoxRow.setPadding(new Insets(15,35,15,35));
        itemBoxRow.setSpacing(20);

        for (int i = 0; i <= albumList.size(); i++) {
            if (i == albumList.size()) {
                albumContent.getChildren().add(itemBoxRow);
                break;
            }

            try {
                Album album = albumList.get(i);
                ItemBox itemBox = new ItemBox(album);
                itemBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        PageChanger.getInstance().goToAlbumPage(yourMusicContent.getScene(), album, user, new FromHomePageState());
                    }
                });
                itemBoxRow.getChildren().add(itemBox);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Add item HBox row to search results box after every 7th item
            if ((i + 1) % 7 == 0) {
                albumContent.getChildren().add(itemBoxRow);

                // Create new item box row
                itemBoxRow = new HBox();
                itemBoxRow.setPadding(new Insets(15,35,15,35));
                itemBoxRow.setSpacing(20);
            }
        }
    }

    // Get a UI element to show a users's artists
    public void setArtistContent(List<Artist> artistList) {
        artistContent = new VBox();
        artistContent.setSpacing(5);
        //artistContent.setPadding(new Insets(5,5,5,5));

        HBox itemBoxRow = new HBox();
        itemBoxRow.setPadding(new Insets(15,35,15,35));
        itemBoxRow.setSpacing(20);

        for (int i = 0; i <= artistList.size(); i++) {
            if (i == artistList.size()) {
                artistContent.getChildren().add(itemBoxRow);
                break;
            }

            try {
                Artist artist = artistList.get(i);
                ItemBox itemBox = new ItemBox(artist);
                itemBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        PageChanger.getInstance().goToArtistPage(yourMusicContent.getScene(), artist, user, new FromHomePageState());
                    }
                });
                itemBoxRow.getChildren().add(itemBox);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Add item HBox row to search results box after every 7th item
            if ((i + 1) % 7 == 0) {
                artistContent.getChildren().add(itemBoxRow);

                // Create new item box row
                itemBoxRow = new HBox();
                itemBoxRow.setPadding(new Insets(15,35,15,35));
                itemBoxRow.setSpacing(20);
            }
        }
    }

    // Get a UI element to show an users's playlists
    public void setPlaylistContent() {
        VBox content = new VBox();

        // Row of playlist item boxes
        HBox itemBoxRow = new HBox();
        itemBoxRow.setPadding(new Insets(15,35,15,35));
        itemBoxRow.setSpacing(20);

        for (int i = 0; i <= user.getPlaylists().size(); i++) {
            if (i == user.getPlaylists().size()) {
                content.getChildren().add(itemBoxRow);
                break;
            }

            try {
                Playlist playlist = user.getPlaylists().get(i);
                ItemBox itemBox = new ItemBox(playlist);
                itemBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        PageChanger.getInstance().goToPlaylistPage(yourMusicContent.getScene(), playlist, user);
                    }
                });
                itemBoxRow.getChildren().add(itemBox);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Add item HBox row to search results box after every 7th item
            if ((i + 1) % 7 == 0) {
                content.getChildren().add(itemBoxRow);

                // Create new item box row
                itemBoxRow = new HBox();
                itemBoxRow.setPadding(new Insets(15,35,15,35));
                itemBoxRow.setSpacing(20);
            }
        }

        // Add playlist button
        HBox buttonRow = new HBox();
        buttonRow.setPadding(new Insets(15,35,15,35));
        Button addButton = new Button("Add New Playlist");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Playlist playlist = new Playlist(0, "My Playlist", "MyMusic/view/musical-note.jpg", user.getUserId(), new ArrayList<Track>());
                PageChanger.getInstance().goToPlaylistPage(yourMusicContent.getScene(), playlist, user);
            }
        });
        buttonRow.getChildren().add(addButton);
        content.getChildren().add(buttonRow);

        playlistContent = content;
    }


    // Go to admin page when admin tab is clicked
    public void adminBtnAction() {
        PageChanger.getInstance().goToAdminPage(nameLabel.getScene(), user);
    }

    // Set up method called when 'page' is changed to home.fxml
    // Sets up the UI elements of home.fxml with the proper values
    // Set the content based on tab param
    public void setUp(User user, String tab) {
        this.user = user;
        nameLabel.setText(user.getName());
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            setTrackContent(databaseManager.getUsersTracks(user));
            trackContent.setPrefHeight(1000);
            setAlbumContent(databaseManager.getUsersAlbums(user));
            setArtistContent(databaseManager.getUsersArtists(user));
            setPlaylistContent();

            if (tab.equals("track")) {
                displayTracks();
            }
            else if (tab.equals("album")) {
                displayAlbums();
            }
            else if (tab.equals("artist")) {
                displayArtists();
            }
            else if (tab.equals("playlist")) {
                displayPlaylists();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Only display admin tab if the current user is an admin
        if (user.isAdmin()) {
            adminButton.setVisible(true);
        }
    }

    @FXML
    public void goToSearchPage() {
        PageChanger.getInstance().goToSearchPage(yourMusicContent.getScene(), user);
    }

    @FXML
    public void logout() {
        PageChanger.getInstance().goToLoginPage(yourMusicContent.getScene());
        MediaPlayerManager.stop();
    }
}
