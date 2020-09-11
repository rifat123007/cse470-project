package MyMusic.controller;

import MyMusic.model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/*
// Controller for search.fxml
*/
public class SearchController {
    private User user;

    @FXML
    private Label nameLabel;

    @FXML
    private VBox genreBox;

    @FXML
    private TextField searchField;

    @FXML
    private VBox searchResultsBox;

    @FXML
    private CheckBox trackCheckBox;

    @FXML
    private CheckBox albumCheckBox;

    @FXML
    private CheckBox artistCheckBox;

    @FXML
    private CheckBox trackNameCheckBox;

    @FXML
    private CheckBox albumNameCheckBox;

    @FXML
    private CheckBox artistNameCheckBox;

    @FXML
    private CheckBox allGenresCheckBox;

    @FXML
    private CheckBox alternativeCheckBox;

    @FXML
    private CheckBox bluesCheckBox;

    @FXML
    private CheckBox classicalCheckBox;

    @FXML
    private CheckBox comedyCheckBox;

    @FXML
    private CheckBox countryCheckBox;

    @FXML
    private CheckBox danceCheckBox;

    @FXML
    private CheckBox electronicCheckBox;

    @FXML
    private CheckBox metalCheckBox;

    @FXML
    private CheckBox popCheckBox;

    @FXML
    private CheckBox rapCheckBox;

    @FXML
    private CheckBox reggaeCheckBox;

    @FXML
    private CheckBox rockCheckBox;

    @FXML
    private CheckBox soundtrackCheckBox;


    @FXML
    private void search() {
        // Search string
        String searchString = searchField.getText();

        // Search criteria flags
        Boolean searchTracks = trackCheckBox.isSelected();
        Boolean searchAlbums = albumCheckBox.isSelected();
        Boolean searchArtists = artistCheckBox.isSelected();
        Boolean searchByTrackName = trackNameCheckBox.isSelected();
        Boolean searchByAlbumName = albumNameCheckBox.isSelected();
        Boolean searchByArtistName = artistNameCheckBox.isSelected();



        // Use DatabaseManager to send search query and receive results.

        // Remove any previous search results
        searchResultsBox.getChildren().clear();

        try {
            DatabaseManager dbManager = DatabaseManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        ArrayList<String> genres = getGenreList();
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            if (searchAlbums) {
                // Select from album table query to get list of albums
                ArrayList<Album> albumResults = databaseManager.searchAlbums(user, searchString, searchByTrackName, searchByAlbumName, searchByArtistName, genres);
                searchResultsBox.getChildren().add(getAlbumResults(albumResults));
            }
            if (searchArtists) {
                // Select from artist table query to get list of artist
                ArrayList<Artist> artistResults = databaseManager.searchArtists(user, searchString, searchByTrackName, searchByAlbumName, searchByArtistName, genres);
                searchResultsBox.getChildren().add(getArtistResults(artistResults));
            }
            if (searchTracks) {
                // Select from track table query to get list of tracks
                ArrayList<Track> trackResults = databaseManager.searchTracks(user, searchString, searchByTrackName, searchByAlbumName, searchByArtistName, genres);
                searchResultsBox.getChildren().add(getTrackResults(trackResults));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Node getTrackResults(List<Track> trackList) {
        // VBox that will contain everything and be returned
        VBox trackResults = new VBox();
        trackResults.setSpacing(5);
        trackResults.setPadding(new Insets(10,5,20,5));

        // Label for album results
        Label tracksLabel = new Label("Tracks");
        tracksLabel.setStyle("-fx-font-size: 28;");
        trackResults.getChildren().add(tracksLabel);

        // Scroll pane
        ScrollPane trackScrollPane = new ScrollPane();
        trackScrollPane.setMaxHeight(800);
        trackScrollPane.setFitToHeight(true);


        trackScrollPane.setContent(new TrackTableView(trackList, user));
        trackResults.getChildren().add(trackScrollPane);

        return trackResults;
    }

    private Node getAlbumResults(List<Album> albumList) {
        // VBox that will contain everything and be returned
        VBox albumResults = new VBox();
        albumResults.setSpacing(5);
        albumResults.setPadding(new Insets(10,5,10,5));

        // Label for album results
        Label albumsLabel = new Label("Albums");
        albumsLabel.setStyle("-fx-font-size: 28;");
        albumResults.getChildren().add(albumsLabel);

        // Scroll pane
        ScrollPane albumScrollPane = new ScrollPane();
        albumScrollPane.setMaxHeight(400);
        albumScrollPane.setFitToHeight(true);

        // Album results in a VBox that will be the content of the scroll pane
        VBox albumVBox = new VBox();

        // Row of album item boxes
        HBox itemBoxRow = new HBox();
        itemBoxRow.setPadding(new Insets(10,10,10,10));
        itemBoxRow.setSpacing(20);

        for (int i = 0; i <= albumList.size(); i++) {
            if (i == albumList.size()) {
                albumVBox.getChildren().add(itemBoxRow);
                break;
            }

            try {
                Album album = albumList.get(i);
                ItemBox itemBox = new ItemBox(album);
                itemBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        PageChanger.getInstance().goToAlbumPage(searchField.getScene(), album, user, new FromSearchPageState());
                    }
                });
                itemBoxRow.getChildren().add(itemBox);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // Add item HBox row to search results box after every 9th item
            if ((i + 1) % 9 == 0) {
                albumVBox.getChildren().add(itemBoxRow);

                // Create new item box row
                itemBoxRow = new HBox();
                itemBoxRow.setPadding(new Insets(10,10,10,10));
                itemBoxRow.setSpacing(20);
            }
        }
        albumScrollPane.setContent(albumVBox);
        albumResults.getChildren().add(albumScrollPane);

        return albumResults;
    }

    private Node getArtistResults(List<Artist> artistList) {
        // VBox that will contain everything and be returned
        VBox artistResults = new VBox();
        artistResults.setSpacing(5);
        artistResults.setPadding(new Insets(10,5,10,5));

        // Label for album results
        Label artistLabel = new Label("Artists");
        artistLabel.setStyle("-fx-font-size: 28;");
        artistResults.getChildren().add(artistLabel);

        // Scroll pane
        ScrollPane artistScrollPane = new ScrollPane();
        artistScrollPane.setMaxHeight(400);
        artistScrollPane.setFitToHeight(true);


        // Artist results in a VBox that will be the content of the scroll pane
        VBox artistVBox = new VBox();

        // Row of artist item boxes
        HBox itemBoxRow = new HBox();
        itemBoxRow.setPadding(new Insets(10,10,10,10));
        itemBoxRow.setSpacing(20);

        for (int i = 0; i <= artistList.size(); i++) {
            if (i == artistList.size()) {
                artistVBox.getChildren().add(itemBoxRow);
                break;
            }

            try {
                Artist artist = artistList.get(i);
                ItemBox itemBox = new ItemBox(artist);
                itemBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        PageChanger.getInstance().goToArtistPage(searchField.getScene(), artist, user, new FromSearchPageState());
                    }
                });
                itemBoxRow.getChildren().add(itemBox);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // Add item HBox row to search results box after every 9th item
            if ((i + 1) % 9 == 0) {
                artistVBox.getChildren().add(itemBoxRow);

                // Create new item box row
                itemBoxRow = new HBox();
                itemBoxRow.setPadding(new Insets(10,10,10,10));
                itemBoxRow.setSpacing(20);
            }
        }
        artistScrollPane.setContent(artistVBox);
        artistResults.getChildren().add(artistScrollPane);

        return artistResults;
    }

    @FXML
    public void goToHomePage() {
        PageChanger.getInstance().goToHomePage(searchField.getScene(), user, "");
    }

    public void toggleGenreCheckBoxes() {
        if (allGenresCheckBox.isSelected()) {
            alternativeCheckBox.setSelected(false);
            bluesCheckBox.setSelected(false);
            classicalCheckBox.setSelected(false);
            comedyCheckBox.setSelected(false);
            countryCheckBox.setSelected(false);
            danceCheckBox.setSelected(false);
            electronicCheckBox.setSelected(false);
            metalCheckBox.setSelected(false);
            popCheckBox.setSelected(false);
            rapCheckBox.setSelected(false);
            reggaeCheckBox.setSelected(false);
            rockCheckBox.setSelected(false);
            soundtrackCheckBox.setSelected(false);
        }
    }

    public void toggleAllGenresCheckBox() {
        allGenresCheckBox.setSelected(false);
    }

    private ArrayList<String> getGenreList() {
        ArrayList<String> genreList = new ArrayList<String>();
        Boolean all = false;

        if (allGenresCheckBox.isSelected()) {
            all = true;
        }

        if (all || alternativeCheckBox.isSelected()) {
            genreList.add("Alternative");
        }

        if (all || bluesCheckBox.isSelected()) {
            genreList.add("Blues");
        }

        if (all || classicalCheckBox.isSelected()) {
            genreList.add("Classical");
        }

        if (all || comedyCheckBox.isSelected()) {
            genreList.add("Comedy");
        }

        if (all || countryCheckBox.isSelected()) {
            genreList.add("Country");
        }

        if (all || danceCheckBox.isSelected()) {
            genreList.add("Dance");
        }

        if (all || electronicCheckBox.isSelected()) {
            genreList.add("Electronic");
        }

        if (all || metalCheckBox.isSelected()) {
            genreList.add("Metal");
        }

        if (all || popCheckBox.isSelected()) {
            genreList.add("Pop");
        }

        if (all || rapCheckBox.isSelected()) {
            genreList.add("Rap");
        }

        if (all || reggaeCheckBox.isSelected()) {
            genreList.add("Reggae");
        }

        if (all || rockCheckBox.isSelected()) {
            genreList.add("Rock");
        }

        if (all || soundtrackCheckBox.isSelected()) {
            genreList.add("Soundtrack");
        }

        return genreList;
    }

    // Set up method called when 'page' is changed to search.fxml
    public void setUp(User user) {
        this.user = user;
        nameLabel.setText(user.getName());
    }

    @FXML
    public void logout() {
        PageChanger.getInstance().goToLoginPage(searchField.getScene());
        MediaPlayerManager.stop();
    }
}
