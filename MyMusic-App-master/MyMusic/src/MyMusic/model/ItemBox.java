package MyMusic.model;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class ItemBox extends VBox {
    private ImageView imageView;
    private Label label1;
    private Label label2;

    public ItemBox(Album album) throws FileNotFoundException {
        // Set ImageView
        imageView = new ImageView();
        imageView.setFitWidth(135);
        imageView.setFitHeight(135);

        // Set image for image view
        Image image = new Image("MyMusic/view/musical-note.jpg");
        System.out.println(album.getImagePath());
        if (album.getImagePath() != null){
            image = new Image(album.getImagePath());
        }
        imageView.setImage(image);

        // Set label 1 (name)
        label1 = new Label(album.getName());

        // Set label 2 (artist)
        label2 = new Label(album.getArtistName());

        // Add children
        getChildren().addAll(imageView, label1, label2);

        // Style
        setMaxSize(200,200);
        setCursor(Cursor.HAND);
    }

    public ItemBox(Album album, int size) throws FileNotFoundException {
        // Set ImageView
        imageView = new ImageView();
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        // Set image for image view
        Image image = new Image("MyMusic/view/musical-note.jpg");
        if (album.getImagePath() != null){
            image = new Image(album.getImagePath());
        }
        imageView.setImage(image);

        // Set label 1 (name)
        label1 = new Label(album.getName());

        // Set label 2 (artist)
        label2 = new Label(album.getArtistName());

        // Add children
        getChildren().addAll(imageView, label1, label2);

        // Style
        setMaxSize(size + 100,size + 100);
        setCursor(Cursor.HAND);
    }

    public ItemBox(Artist artist) throws FileNotFoundException {
        // Set ImageView
        imageView = new ImageView();
        imageView.setFitWidth(135);
        imageView.setFitHeight(135);

        // Set image for image view
        Image image = new Image("MyMusic/view/musical-note.jpg");
        if (artist.getImagePath() != null){
            image = new Image(artist.getImagePath());
        }
        imageView.setImage(image);

        // Set label 1 (name)
        label1 = new Label(artist.getName());

        // Add children
        getChildren().addAll(imageView, label1);

        // Style
        setMaxSize(200,200);
        setCursor(Cursor.HAND);
    }

    public ItemBox(Artist artist, int size) throws FileNotFoundException {
        // Set ImageView
        imageView = new ImageView();
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        // Set image for image view
        Image image = new Image("MyMusic/view/musical-note.jpg");
        if (artist.getImagePath() != null){
            image = new Image(artist.getImagePath());
        }
        imageView.setImage(image);

        // Set label 1 (name)
        label1 = new Label(artist.getName());

        // Add children
        getChildren().addAll(imageView, label1);

        // Style
        setMaxSize(size + 100,size + 100);
        setCursor(Cursor.HAND);
    }

    public ItemBox(Playlist playlist) throws FileNotFoundException {
        // Set spacing
        setSpacing(5);

        // Set ImageView
        imageView = new ImageView();
        imageView.setFitWidth(205);
        imageView.setFitHeight(205);

        Image image = new Image("MyMusic/view/musical-note.jpg");
        if (playlist.getImagePath() != null){
            image = new Image(playlist.getImagePath());
        }
        imageView.setImage(image);

        // Set label 1 (name)
        label1 = new Label(playlist.getName());
        label1.getStyleClass().add("label-s");

        setCursor(Cursor.HAND);

        // Add children
        getChildren().addAll(imageView, label1);
    }
}
