package MyMusic.model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


// Used as rows in a TrackTableView
public class TrackRowData {
    private User user;
    private Track track;
    private Button playBtn;
    private CheckBox yoursCheckBox;
    private VBox playlistBox;
    private Button playlistBtn;
    private TextField playlistTextField;


    public TrackRowData(User user, Track track) {
        this.user = user;
        this.track = track;
        playBtn = new Button("Play");
        yoursCheckBox = new CheckBox();
        yoursCheckBox.setSelected(track.isYours());

        playlistBtn = new Button("Add to Playlist");
        playlistTextField = new TextField();
        playlistTextField.setVisible(false);
        playlistTextField.setManaged(false);
        playlistBox = new VBox();
        playlistBox.getChildren().addAll(playlistBtn, playlistTextField);

        playBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (playBtn.getText().equals("Play")) {
                    track.play(playBtn);
                }
                else if (playBtn.getText().equals("Continue")) {
                    track.play(playBtn);
                }
                else if (playBtn.getText().equals("Pause")){
                    track.pause();
                }
            }
        });

        yoursCheckBox.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (track.isYours()) {
                    try {
                        DatabaseManager databaseManager = DatabaseManager.getInstance();
                        databaseManager.deleteUser_has_Track(user, track);
                        track.setIsYours(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        DatabaseManager databaseManager = DatabaseManager.getInstance();
                        databaseManager.addUser_has_Track(user, track);
                        track.setIsYours(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        playlistBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!playlistTextField.isVisible()) {
                    playlistTextField.setVisible(true);
                    playlistTextField.setManaged(true);
                    playlistBtn.setText("Add");
                }
                else {
                    String playlistName = playlistTextField.getText();
                    Playlist playlist = DatabaseManager.getInstance().getPlaylistByName(user, playlistName);
                    if (playlist != null) {
                        DatabaseManager.getInstance().addTrackToPlaylist(playlist, track);
                        user.setPlaylists(DatabaseManager.getInstance().getPlaylists(user));
                    }
                    else {
                        // Message to user
                    }
                    playlistBtn.setText("Add to Playlist");
                    playlistTextField.setVisible(false);
                    playlistTextField.setManaged(false);
                }
            }
        });
    }

    public int getId() { return track.getId(); }

    public String getName()
    {
        return track.getName();
    }

    public int getAlbumId() { return track.getAlbumId(); }

    public String getTime()
    {
        return track.getTime();
    }

    public int getNumPlays()
    {
        return track.getNumPlays();
    }

    public String getArtistName()
    {
        return track.getArtistName();
    }

    public String getAlbumName()
    {
        return track.getAlbumName();
    }

    public String getGenre()
    {
        return track.getGenre();
    }

    public String getMediaPath()
    {
        return track.getMediaPath();
    }

    public Button getPlayBtn() { return playBtn; }

    public CheckBox getYoursCheckBox() { return yoursCheckBox; }

    public VBox getPlaylistBox() { return playlistBox; }

    public Button getPlaylistBtn() { return playlistBtn; }
}
