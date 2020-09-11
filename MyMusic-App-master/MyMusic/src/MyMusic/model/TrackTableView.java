package MyMusic.model;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


// Table view UI element for tracks
public class TrackTableView extends TableView {
    public TrackTableView(List<Track> trackList, User user) {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setPrefWidth(1380);

        // Instantiate table columns

        TableColumn playCol = new TableColumn("Play");
        playCol.setCellValueFactory(new PropertyValueFactory<>("PlayBtn"));

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));

        TableColumn artistCol = new TableColumn("Artist");
        artistCol.setCellValueFactory(new PropertyValueFactory<>("ArtistName"));

        TableColumn albumCol = new TableColumn("Album");
        albumCol.setCellValueFactory(new PropertyValueFactory<>("AlbumName"));

        TableColumn genreCol = new TableColumn("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory<>("Genre"));

        TableColumn playCountCol = new TableColumn("Plays");
        playCountCol.setCellValueFactory(new PropertyValueFactory<>("NumPlays"));

        TableColumn yoursCol = new TableColumn("Yours");
        yoursCol.setCellValueFactory(new PropertyValueFactory<>("YoursCheckBox"));

        TableColumn playlistCol = new TableColumn("");
        playlistCol.setCellValueFactory(new PropertyValueFactory<>("PlaylistBox"));


        // Add table columns
        getColumns().addAll(playCol, nameCol, timeCol, artistCol, albumCol, genreCol, playCountCol, yoursCol, playlistCol);

        for (int i = 0; i < trackList.size(); i++) {
            getItems().add(new TrackRowData(user, trackList.get(i)));
        }
    }

    public TrackTableView(List<Track> trackList, User user, Playlist playlist) {
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setPrefWidth(1380);

        // Instantiate table columns

        TableColumn playCol = new TableColumn("Play");
        playCol.setCellValueFactory(new PropertyValueFactory<>("PlayBtn"));

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("Time"));

        TableColumn artistCol = new TableColumn("Artist");
        artistCol.setCellValueFactory(new PropertyValueFactory<>("ArtistName"));

        TableColumn albumCol = new TableColumn("Album");
        albumCol.setCellValueFactory(new PropertyValueFactory<>("AlbumName"));

        TableColumn genreCol = new TableColumn("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory<>("Genre"));

        TableColumn playCountCol = new TableColumn("Plays");
        playCountCol.setCellValueFactory(new PropertyValueFactory<>("NumPlays"));

        TableColumn yoursCol = new TableColumn("Yours");
        yoursCol.setCellValueFactory(new PropertyValueFactory<>("YoursCheckBox"));

        TableColumn playlistCol = new TableColumn("");
        playlistCol.setCellValueFactory(new PropertyValueFactory<>("PlaylistBox"));



        // Add table columns
        getColumns().addAll(playCol, nameCol, timeCol, artistCol, albumCol, genreCol, playCountCol, yoursCol, playlistCol);

        for (int i = 0; i < trackList.size(); i++) {
            getItems().add(new PlaylistTrackRowData(user, trackList.get(i), playlist));
        }

    }
}
