package testMyMusic;
import MyMusic.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import static org.junit.jupiter.api.Assertions.*;
import static java.time.Duration.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

class DBTest {
	private DatabaseManager dbManager;
	
	@BeforeEach
	public void init() throws Exception {
			dbManager = new DatabaseManager();
	}
	
	@Test
	void defaultAdminTest() {
		String username = "admin";
		String password = "admin";
		User user = dbManager.getUser(username, password);
		assertNotEquals(user, null);
	}

	@Test
	void QueryInLessThanASecond() throws Exception{
		ArrayList<String> genreList = new ArrayList<String>();
            genreList.add("Alternative");
            genreList.add("Blues");
            genreList.add("Classical");
            genreList.add("Comedy");
            genreList.add("Country");
            genreList.add("Dance");
            genreList.add("Electronic");
            genreList.add("Metal");
            genreList.add("Pop");
            genreList.add("Rap");
            genreList.add("Reggae");
            genreList.add("Rock");
            genreList.add("Soundtrack");
            
        assertTimeout(ofSeconds(1), () -> {
        	dbManager.searchAlbums("", true, true, true, genreList);
        });
	}
	
	@Test
	void TestPlaylistUseCases() {
		String username = "admin";
		String password = "admin";
		User user = dbManager.getUser(username, password);
         
		Playlist playlist = new Playlist(0, "Test", "MyMusic/view/musical-note.jpg", user.getUserId(), new ArrayList<Track>());
		dbManager.addPlaylist(playlist);
		dbManager.deletePlaylist(playlist);
		assertEquals(dbManager.playlistExists(playlist), false);
	}
}
