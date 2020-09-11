package MyMusic.model;

import java.util.ArrayList;

public class TrackListFactory {
    public ArrayList<Track> getTrackList(String owner, User user, int id) {
        ArrayList<Track> trackList = null;
        if (owner.equals("Artist")) {
            trackList = DatabaseManager.getInstance().getTracksByArtist(user, id);
        }
        else if (owner.equals("Album")) {
           trackList = DatabaseManager.getInstance().getTracksInAlbum(user, id);
        }
        else {

        }
        return trackList;
    }
}
