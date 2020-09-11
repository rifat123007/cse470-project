package MyMusic.controller;

import MyMusic.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/*
// Controller for album.fxml
*/
public class AlbumController {
    private Album album;
    private User user;
    boolean inEditMode;

    // State object
    private PageOriginState pageOriginState;


    @FXML
    private Button playButton;
    @FXML
    private ImageView albumImageView;
    @FXML
    private Label albumNameLabel;
    @FXML
    private Label albumArtistLabel;
    @FXML
    private Label albumYearLabel;
    @FXML
    private Label albumGenreLabel;
    @FXML
    private HBox albumTracksContent;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField albumNameTextField;
    @FXML
    private TextField imagePathTextField;
    @FXML
    private TextField yearTextField;
    @FXML
    private TextField genreTextField;
    @FXML
    private CheckBox yoursCheckBox;

    private TableView trackTableView;


    // When check mark box is clicked either add album or remove album from the current user's library
    @FXML
    private void markAsYours() {
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            if (yoursCheckBox.isSelected()) {
                databaseManager.addUser_has_Album(user, album);
                album.setIsYours(true);
            }
            else {
                databaseManager.deleteUser_has_Album(user, album);
                album.setIsYours(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Modify UI elements when shifting to and from edit mode
    @FXML
    public void toggleEditMode() {
        if (inEditMode) {
            inEditMode = false;
            albumNameLabel.setManaged(true);
            albumNameLabel.setVisible(true);
            albumArtistLabel.setManaged(true);
            albumArtistLabel.setVisible(true);
            albumYearLabel.setManaged(true);
            albumYearLabel.setVisible(true);
            albumGenreLabel.setManaged(true);
            albumGenreLabel.setVisible(true);
            editButton.setManaged(true);
            editButton.setVisible(true);
            saveButton.setManaged(false);
            saveButton.setVisible(false);
            albumNameTextField.setManaged(false);
            albumNameTextField.setVisible(false);
            imagePathTextField.setManaged(false);
            imagePathTextField.setVisible(false);
            yearTextField.setManaged(false);
            yearTextField.setVisible(false);
            genreTextField.setManaged(false);
            genreTextField.setVisible(false);
        }
        else {
            inEditMode = true;
            albumNameLabel.setManaged(false);
            albumNameLabel.setVisible(false);
            albumArtistLabel.setManaged(false);
            albumArtistLabel.setVisible(false);
            albumYearLabel.setManaged(false);
            albumYearLabel.setVisible(false);
            albumGenreLabel.setManaged(false);
            albumGenreLabel.setVisible(false);
            editButton.setManaged(true);
            editButton.setVisible(true);
            saveButton.setManaged(true);
            saveButton.setVisible(true);
            albumNameTextField.setManaged(true);
            albumNameTextField.setVisible(true);
            imagePathTextField.setManaged(true);
            imagePathTextField.setVisible(true);
            yearTextField.setManaged(true);
            yearTextField.setVisible(true);
            genreTextField.setManaged(true);
            genreTextField.setVisible(true);

            albumNameTextField.setText(album.getName());
            imagePathTextField.setText(album.getImagePath());
            yearTextField.setText(album.getYear()+"");
            genreTextField.setText(album.getGenre());

        }
    }


    @FXML
    private void save() {
        DatabaseManager databaseManager = null;
        try {
            databaseManager = DatabaseManager.getInstance();

            // Set properties of album
            album.setName(albumNameTextField.getText());
            album.setImagePath(imagePathTextField.getText());
            album.setYear(Integer.parseInt(yearTextField.getText()));
            album.setGenre(genreTextField.getText());

            // Set the values of the UI elements
            albumNameLabel.setText(album.getName());
            Image image = new Image(album.getImagePath());
            albumImageView.setImage(image);
            albumYearLabel.setText(album.getYear()+"");
            albumGenreLabel.setText(album.getGenre());

            // Update the album in the DB
            databaseManager.updateAlbum(album);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void goBack() {
        pageOriginState.goBack(albumNameLabel.getScene(), user);
    }

    @FXML
    public void play() {
        if (playButton.getText().equals("Play")) {
            album.play(playButton);
        }
        else {
            album.pause();
        }

    }

    // Set up method called when 'page' is changed to album.fxml
    // Initializes variables and sets up the UI elements of album.fxml with the proper values
    public void setUp(Album album, User user, PageOriginState state) {
        this.album = album;
        this.user = user;
        inEditMode = false;
        pageOriginState = state;

        if (album != null) {
            Image image = new Image(album.getImagePath());
            albumImageView.setImage(image);
            albumNameLabel.setText(album.getName());
            albumArtistLabel.setText(album.getArtistName());
            albumYearLabel.setText(album.getYear()+"");
            albumGenreLabel.setText(album.getGenre());
            albumTracksContent.getChildren().clear();
            trackTableView = new TrackTableView(album.getTracks(), user);
            trackTableView.setMinWidth(1600);
            albumTracksContent.getChildren().add(trackTableView);

            if (album.isYours()) {
                yoursCheckBox.setSelected(true);
            }
        }

        // Admin
        if (user.isAdmin()) {
            editButton.setVisible(true);
            editButton.setManaged(true);
        }
        else {
            editButton.setVisible(false);
            editButton.setManaged(false);
        }
    }
}
