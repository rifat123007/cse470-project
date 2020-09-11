package MyMusic.model;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// UI element for editing and adding albums in the admin tab
public class AlbumEditBox extends HBox {
    private TextField nameField;
    private TextField imagePathField;
    private TextField genreField;
    private TextField yearField;
    private TextField ratingField;
    private Button saveButton;
    private Button cancelButton;
    private Button deleteButton;

    private Album album;
    private boolean isNew; // was added to layout using the add button

    // No params when adding an album
    public AlbumEditBox() {
        isNew = true;
        this.album = new Album();
        nameField = new TextField();
        imagePathField = new TextField();
        genreField = new TextField();
        yearField = new TextField();
        ratingField = new TextField();

        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
        deleteButton = new Button("Delete");

        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                save();
            }
        });

        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resetChildren();
            }
        });

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                delete();
            }
        });

        setAlignment(Pos.CENTER);
        nameField.setPrefWidth(300);
        imagePathField.setPrefWidth(400);
        genreField.setPrefWidth(150);
        yearField.setPrefWidth(150);
        ratingField.setPrefWidth(150);

        getChildren().addAll(nameField, imagePathField, genreField, yearField, ratingField, saveButton, cancelButton, deleteButton);
    }

    public AlbumEditBox(Album album) {
        isNew = false;
        this.album = album;
        nameField = new TextField(album.getName());
        imagePathField = new TextField(album.getImagePath());
        genreField = new TextField(album.getGenre());
        yearField = new TextField(album.getYear()+"");
        ratingField = new TextField(album.getRating()+"");

        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
        deleteButton = new Button("Delete");

        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                save();
            }
        });

        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resetChildren();
            }
        });

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                delete();
            }
        });

        setAlignment(Pos.CENTER);
        nameField.setPrefWidth(300);
        imagePathField.setPrefWidth(400);
        genreField.setPrefWidth(150);
        yearField.setPrefWidth(150);
        ratingField.setPrefWidth(150);

        getChildren().addAll(nameField, imagePathField, genreField, yearField, ratingField, saveButton, cancelButton, deleteButton);
    }

    public Album getAlbum() {
        album.setName(nameField.getText());
        album.setImagePath(imagePathField.getText());
        album.setGenre(genreField.getText());
        album.setYear(Integer.parseInt(yearField.getText()));
        album.setRating(Float.parseFloat(ratingField.getText()));
        return album;
    }

    private boolean validateYearField() {
        boolean isValid = false;

        return isValid;
    }

    private boolean validateRatingField() {
        boolean isValid = false;

        return isValid;
    }

    // Save the album
    private void save() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();

        // If new insert, else update
        if (isNew) {
            try {
                databaseManager.addAlbum(getAlbum());
                isNew = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                databaseManager.updateAlbum(getAlbum());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Reset any changes
    private void resetChildren() {
        if (isNew) {
            nameField.setText("");
            imagePathField.setText("");
            genreField.setText("");
            yearField.setText("");
            ratingField.setText("");
        }
        else {
            nameField.setText(album.getName());
            imagePathField.setText(album.getImagePath());
            genreField.setText(album.getGenre());
            yearField.setText(album.getYear()+"");
            ratingField.setText(album.getRating()+"");
        }
    }

    // Delete the album
    private void delete() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        if (isNew) {
            // Don't need to use DatabaseManager if was a new album, so just remove self from parent
            ((VBox) getParent()).getChildren().remove(this);
        }
        else {
            try {
                databaseManager.deleteAlbum(getAlbum());
                ((VBox) getParent()).getChildren().remove(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
