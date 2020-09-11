package MyMusic.model;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class PlaylistTrackRowData {
    private User user;
    private Track track;
    private Playlist playlist;
    private Button playBtn;
    private CheckBox yoursCheckBox;
    private VBox playlistBox;
    private Button playlistBtn;
    private TextField playlistTextField;



    public PlaylistTrackRowData(User user, Track track, Playlist playlist) {
        this.user = user;
        this.track = track;
        this.playlist = playlist;
        playBtn = new Button("Play");
        yoursCheckBox = new CheckBox();
        yoursCheckBox.setSelected(track.isYours());

        playlistBtn = new Button("Remove");
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
                DatabaseManager.getInstance().removeTrackFromPlaylist(playlist, track);
                user.setPlaylists(DatabaseManager.getInstance().getPlaylists(user));

                TableRow parentRow = (TableRow) playlistBtn.getParent().getParent().getParent();
                TableView tableView = parentRow.getTableView();
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
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
