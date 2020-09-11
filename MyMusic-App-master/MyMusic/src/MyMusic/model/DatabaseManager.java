package MyMusic.model;

import java.sql.*;
import java.util.ArrayList;

/*
// DatabaseManager:
// Creates a connection to the database
// Uses methods that search, insert, update, and/or delete from the database
// Singleton
 */
public final class DatabaseManager {

	private static final DatabaseManager INSTANCE = new DatabaseManager();
	private Connection myConn;

	public DatabaseManager()
	{
		// Database details/credentials
		String db_name="MyMusic";
		String dbUrl="jdbc:mysql://localhost:3306/"+db_name+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user="root";
		String password="root";
		
		try {
			myConn=DriverManager.getConnection(dbUrl, user, password);
			System.out.println("Database connected sucessfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Singleton
	public static DatabaseManager getInstance() {
		return INSTANCE;
	}


	// SEARCH METHODS //

	// Search for artists based on a search string, flags for table to search on, and flags for genres to search on
	public  ArrayList<Artist> searchArtists(User user, String searchString, boolean searchByTrackName, boolean searchByAlbumName, boolean searchByArtistName, ArrayList<String> selectedGenres) throws Exception
	{
		String s="%"+searchString+"%";
		PreparedStatement myStmt=null;
		ResultSet myRs;
		ArrayList<Artist> artists=new ArrayList<Artist>();
		try
		{
			myStmt = myConn.prepareStatement(
						"SELECT DISTINCT Artist.artist_id, Artist.name, Artist.imagePath, Artist.rating FROM Artist" +
							" LEFT JOIN Album_has_Artist ON Artist.artist_id = Album_has_Artist.artist_id" +
							" LEFT JOIN Album ON Album_has_Artist.album_id = Album.album_id" +
							" LEFT JOIN Track_has_Artist ON Track_has_Artist.artist_id = Artist.artist_id " +
							" LEFT JOIN Track ON Track.track_id = Track_has_Artist.track_id" +
								filterSQL(searchString, searchByTrackName, searchByAlbumName, searchByArtistName) + genreSQL(selectedGenres, "Track") +
								" ORDER BY Artist.name ASC;");;
			
			myRs=myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("artist_id");
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				Float rating = myRs.getFloat("rating");

				Artist artist = new Artist(id, name, imagePath, rating);
				artist.setIsYours(userHasArtist(user, artist));
				artist.setAlbums(getAlbumsByArtist(user, id));
				artist.setTracks(getTracksByArtist(user, id));
				artists.add(artist);
			}
			System.out.println("Query successful");
			return artists;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return artists;}
	}

	// Search for albums based on a search string, flags for table to search on, and flags for genres to search on
	public  ArrayList<Album> searchAlbums(User user, String searchString, boolean searchByTrackName, boolean searchByAlbumName, boolean searchByArtistName, ArrayList<String> selectedGenres) throws Exception
	{
		String s = "%"+searchString+"%";
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Album> albums = new ArrayList<Album>();
		try
		{
		    myStmt = myConn.prepareStatement(
		            	"SELECT DISTINCT Album.album_id, Album.name, Album.imagePath, Album.genre, Album.year, Album.rating, Artist.name as artist_name FROM Album" +
                    		" LEFT JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" +
							" LEFT JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
							" LEFT JOIN Album_has_Track ON Album_has_Track.album_id = Album.album_id" +
							" LEFT JOIN Track ON Track.track_id = Album_has_Track.track_id" +
							filterSQL(searchString, searchByTrackName, searchByAlbumName, searchByArtistName) + genreSQL(selectedGenres, "Album") +
							" ORDER BY Album.name ASC;");


			System.out.println(myStmt.toString());

			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("album_id");
				String name = myRs.getString("name");
                String imagePath = myRs.getString("imagePath");
                String artistName = myRs.getString("artist_name");
				int year = myRs.getInt("year");
				String genre = myRs.getString("genre");
				float rating = myRs.getFloat("rating");

				Album album = new Album(id, name, artistName, year, genre, imagePath, rating);
				album.setIsYours(userHasAlbum(user, album));
				album.setTracks(getTracksInAlbum(user, id));
				albums.add(album);
			}
			System.out.println("Query successful");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return albums;}
	}

	// Search for tracks based on a search string, flags for table to search on, and flags for genres to search on
	public ArrayList<Track> searchTracks(User user, String searchString, boolean searchByTrackName, boolean searchByAlbumName, boolean searchByArtistName, ArrayList<String> selectedGenres) throws Exception {
		String s = "%"+searchString+"%";
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.track_id, Track.name, Track.genre, Track.plays, Track.time, Track.mediaPath, Album.name as album_name, Artist.name as artist_name FROM Track" +
						" LEFT JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
						" LEFT JOIN Album ON Album.album_id = Album_has_Track.album_id" +
						" LEFT JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" +
						" LEFT JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
						filterSQL(searchString, searchByTrackName, searchByAlbumName, searchByArtistName) + genreSQL(selectedGenres, "Track") +
						" ORDER BY Album.name ASC, Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("track_id");
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getString( "time");
				String mediaPath = myRs.getString("mediaPath");
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

				Track track = new Track(id, name, genre, plays, time, artistName, albumName, mediaPath);
				track.setIsYours(userHasTrack(user, track));
				tracks.add(track);
			}
			System.out.println("Search track successful");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			return tracks;
		}
	}


	// GET METHODS //

	// Get a user by searching for a entry in the User table with the given username and password
	// Used for logging in
	public User getUser(String username, String password) {
		try {
			// Login SQL Query
			PreparedStatement loginStmt = myConn.prepareStatement("Select * FROM User WHERE username = ?  AND password = ?");
			loginStmt.setString(1, username);
			loginStmt.setString(2, password);

			ResultSet rs = loginStmt.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("user_id");
				String name = rs.getString("name");
                Boolean isAdmin = rs.getBoolean("admin");
				User user = new User(userId, name, username, password, isAdmin);
				user.setPlaylists(getPlaylists(user));
				return user;
			}
			else {
				// Result set is empty. Therefore, a user with the given username and password does not exist in the database.
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Checks if a user with the given username exists in the database
	public boolean isUsernameTaken(String username) {
		try {
			// Login SQL Query
			PreparedStatement loginStmt = myConn.prepareStatement("Select username FROM User WHERE username = ?");
			loginStmt.setString(1, username);

			ResultSet rs = loginStmt.executeQuery();

			if (rs.next()) {
				return true;
			}
			else {
				// Result set is empty. Therefore, a user with the given username and password does not exist in the database.
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	// Get all tracks in the database. Used to display list of tracks that an admin can edit
	public ArrayList<Track> getAllTracks(User user) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.track_id, Track.name, Track.genre, Track.plays, Track.time, Track.mediaPath, Album.name as album_name, Artist.name as artist_name FROM Track" +
							" LEFT JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
							" LEFT JOIN Album ON Album.album_id = Album_has_Track.album_id" +
							" LEFT JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" +
							" LEFT JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
							" ORDER BY Album.name ASC, Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("track_id");
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getString( "time").toString();
				String mediaPath = myRs.getString("mediaPath");
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

				Track track = new Track(id, name, genre, plays, time, artistName, albumName, mediaPath);
				track.setIsYours(userHasTrack(user, track));
				tracks.add(track);
			}
			System.out.println("Search track successful");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			return tracks;
		}
	}

	// Get all albums in the database. Used to display list of albums that an admin can edit
	public ArrayList<Album> getAllAlbums(User user) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Album> albums = new ArrayList<Album>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Album.album_id, Album.name, Album.imagePath, Album.genre, Album.year, Album.rating, Artist.name as artist_name FROM Album" +
							" LEFT JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" +
							" LEFT JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
							" LEFT JOIN Album_has_Track ON Album_has_Track.album_id = Album.album_id" +
							" LEFT JOIN Track ON Track.track_id = Album_has_Track.track_id" +
							" ORDER BY Album.name ASC;");
			System.out.println(myStmt.toString());

			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("album_id");
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				String artistName = myRs.getString("artist_name");
				int year = myRs.getInt("year");
				String genre = myRs.getString("genre");
				float rating = myRs.getFloat("rating");

				Album album = new Album(id, name, artistName, year, genre, imagePath, rating);
				album.setIsYours(userHasAlbum(user, album));
				album.setTracks(getTracksInAlbum(user, id));
				albums.add(album);
			}
			System.out.println("Query successful");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return albums;}
	}

	// Get all artists in the database. Used to display list of artists that an admin can edit
	public ArrayList<Artist> getAllArtists(User user) {
		PreparedStatement myStmt=null;
		ResultSet myRs;
		ArrayList<Artist> artists=new ArrayList<Artist>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Artist.artist_id, Artist.name, Artist.imagePath, Artist.rating FROM Artist" +
							" LEFT JOIN Album_has_Artist ON Artist.artist_id = Album_has_Artist.artist_id" +
							" LEFT JOIN Album ON Album_has_Artist.album_id = Album.album_id" +
							" LEFT JOIN Track_has_Artist ON Track_has_Artist.artist_id = Artist.artist_id " +
							" LEFT JOIN Track ON Track.track_id = Track_has_Artist.track_id" +
							" ORDER BY Artist.name ASC;");

			myRs=myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("artist_id");
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				Float rating = myRs.getFloat("rating");

				Artist artist = new Artist(id, name, imagePath, rating);
				artist.setIsYours(userHasArtist(user, artist));
				artist.setAlbums(getAlbumsByArtist(user, id));
				artist.setTracks(getTracksByArtist(user, id));
				artists.add(artist);
			}
			System.out.println("Query successful");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return artists;}
	}

	// Get tracks that a user has in their library
	public ArrayList<Track> getUsersTracks(User user) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.track_id, Track.name, Track.genre, Track.plays, Track.time, Track.mediaPath, Album.name as album_name, Artist.name as artist_name FROM Track" +
							" LEFT JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
							" LEFT JOIN Album ON Album.album_id = Album_has_Track.album_id" +
							" LEFT JOIN Track_has_Artist ON Track.track_id = Track_has_Artist.track_id" +
							" LEFT JOIN Artist ON Track_has_Artist.artist_id = Artist.artist_id" +
							" ORDER BY Album.name ASC, Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("track_id");
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getString( "time");
				String mediaPath = myRs.getString("mediaPath");
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

				Track track = new Track(id, name, genre, plays, time, artistName, albumName, mediaPath);
				track.setIsYours(userHasTrack(user, track));
				if (track.isYours()) {
					tracks.add(track);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			return tracks;
		}
	}

	// Get albums that a user has in their library
	public ArrayList<Album> getUsersAlbums(User user) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Album> albums = new ArrayList<Album>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Album.album_id, Album.name, Album.imagePath, Album.genre, Album.year, Album.rating, Artist.name as artist_name FROM Album" +
							" LEFT JOIN Album_has_Artist ON Album.album_id = Album_has_Artist.album_id" +
							" LEFT JOIN Artist ON Album_has_Artist.artist_id = Artist.artist_id" +
							" LEFT JOIN Album_has_Track ON Album_has_Track.album_id = Album.album_id" +
							" LEFT JOIN Track ON Track.track_id = Album_has_Track.track_id" +
							" ORDER BY Album.name ASC;");
			System.out.println(myStmt.toString());

			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("album_id");
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				String artistName = myRs.getString("artist_name");
				int year = myRs.getInt("year");
				String genre = myRs.getString("genre");
				float rating = myRs.getFloat("rating");


				Album album = new Album(id, name, artistName, year, genre, imagePath, rating);
				album.setIsYours(userHasAlbum(user, album));
				if (album.isYours()) {
					album.setTracks(getTracksInAlbum(user, id));
					albums.add(album);
				}
			}
			System.out.println("Query successful");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return albums;}
	}

	// Get artists that a user has in their library
	public ArrayList<Artist> getUsersArtists(User user) {
		PreparedStatement myStmt=null;
		ResultSet myRs;
		ArrayList<Artist> artists=new ArrayList<Artist>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Artist.artist_id, Artist.name, Artist.imagePath, Artist.rating FROM Artist" +
							" LEFT JOIN Album_has_Artist ON Artist.artist_id = Album_has_Artist.artist_id" +
							" LEFT JOIN Album ON Album_has_Artist.album_id = Album.album_id" +
							" LEFT JOIN Track_has_Artist ON Track_has_Artist.artist_id = Artist.artist_id " +
							" LEFT JOIN Track ON Track.track_id = Track_has_Artist.track_id" +
							" ORDER BY Artist.name ASC;");

			myRs=myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("artist_id");
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				Float rating = myRs.getFloat("rating");

				Artist artist = new Artist(id, name, imagePath, rating);
				artist.setIsYours(userHasArtist(user, artist));
				if (artist.isYours()) {
					artist.setAlbums(getAlbumsByArtist(user, id));
					artist.setTracks(getTracksByArtist(user, id));
					artists.add(artist);
				}
			}
			System.out.println("Query successful");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return artists;}
	}


	// Get playlists that a user has created
	public ArrayList<Playlist> getPlaylists(User user) {
		ArrayList<Playlist> playlists = new ArrayList<Playlist>();
		PreparedStatement myStmt = null;
		ResultSet myRs;
		try
		{
			myStmt = myConn.prepareStatement("SELECT * From Playlist WHERE user_id = ?");
			myStmt.setInt(1, user.getUserId());

			myRs = myStmt.executeQuery();
			int index = 0;
			while(myRs.next())
			{
				int id = myRs.getInt("playlist_id");
				String name = myRs.getString("name");
				String imagePath = "MyMusic/view/musical-note.jpg";
				ArrayList<Track> tracks = getTracksInPlaylist(user, id);

				Playlist playlist = new Playlist(id, name, imagePath, user.getUserId(), tracks);
				playlists.add(playlist);

				index++;
			}
			System.out.println("Query successful");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {return playlists;}
	}

	// Get tracks in a playlist
	public ArrayList<Track> getTracksInPlaylist(User user, int playlistId) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.track_id, Track.name, Track.genre, Track.plays, Track.time, Track.mediaPath, Album.name as album_name, Artist.name as artist_name FROM Track" +
							" LEFT JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
							" LEFT JOIN Album ON Album.album_id = Album_has_Track.album_id" +
							" LEFT JOIN Track_has_Artist ON Track.track_id = Track_has_Artist.track_id" +
							" LEFT JOIN Artist ON Track_has_Artist.artist_id = Artist.artist_id" +
							" LEFT JOIN Playlist_has_Track on Track.track_id = Playlist_has_Track.track_id" +
							" WHERE Playlist_has_Track.playlist_id = " + playlistId +
							" ORDER BY Album.name ASC, Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("track_id");
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getString( "time");
				String mediaPath = myRs.getString("mediaPath");
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

				Track track = new Track(id, name, genre, plays, time, artistName, albumName, mediaPath);
				track.setIsYours(userHasTrack(user, track));
				tracks.add(track);
			}
			System.out.println("Search track successful");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			return tracks;
		}
	}

	// Get tracks in an album
	public ArrayList<Track> getTracksInAlbum(User user, int albumId) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<Track>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.track_id, Track.name, Track.genre, Track.plays, Track.time, Track.mediaPath, Album.name as album_name, Artist.name as artist_name FROM Track" +
							" JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
							" JOIN Album ON Album.album_id = Album_has_Track.album_id" +
							" JOIN Track_has_Artist ON Track.track_id = Track_has_Artist.track_id" +
							" JOIN Artist ON Track_has_Artist.artist_id = Artist.artist_id" +
							" WHERE Album_has_Track.album_id = " + albumId +
							" ORDER BY Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("track_id");
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getString( "time");
				String mediaPath = myRs.getString("mediaPath");
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

				Track track = new Track(id, name, genre, plays, time, artistName, albumName, mediaPath);
				track.setIsYours(userHasTrack(user, track));
				tracks.add(track);
			}
			System.out.println("Search track successful");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			return tracks;
		}
	}

	// Get albums by an artist
	public ArrayList<Album> getAlbumsByArtist(User user, int artistId) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Album> albums = new ArrayList<Album>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Album.album_id, Album.name, Album.imagePath, Album.genre, Album.year, Album.rating, Artist.name AS artist_name FROM Album" +
							" JOIN Album_has_Artist ON Album_has_Artist.album_id = Album.album_id" +
							" JOIN Artist ON Artist.artist_id = Album_has_Artist.artist_id" +
							" WHERE Artist.artist_id = " + artistId +
							" ORDER BY Album.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("album_id");
				String name = myRs.getString("name");
				String imagePath = myRs.getString("imagePath");
				String artistName = myRs.getString("artist_name");
				int year = myRs.getInt("year");
				String genre = myRs.getString("genre");
				float rating = myRs.getFloat("rating");

				Album album = new Album(id, name, artistName, year, genre, imagePath, rating);
				album.setIsYours(userHasAlbum(user, album));
				album.setTracks(getTracksInAlbum(user, id));
				albums.add(album);
			}
			System.out.println("Search track successful");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			return albums;
		}
	}

	// Get tracks by an artist
	public ArrayList<Track> getTracksByArtist(User user, int artistId) {
		PreparedStatement myStmt = null;
		ResultSet myRs;
		ArrayList<Track> tracks = new ArrayList<Track>();
		try
		{
			myStmt = myConn.prepareStatement(
					"SELECT DISTINCT Track.track_id, Track.name, Track.genre, Track.plays, Track.time, Track.mediaPath, Album.name as album_name, Artist.name as artist_name FROM Track" +
							" JOIN Album_has_Track ON Album_has_Track.track_id = Track.track_id" +
							" JOIN Album ON Album.album_id = Album_has_Track.album_id" +
							" JOIN Track_has_Artist ON Track.track_id = Track_has_Artist.track_id" +
							" JOIN Artist ON Track_has_Artist.artist_id = Artist.artist_id" +
							" WHERE Track_has_Artist.artist_id = " + artistId +
							" ORDER BY Track.name ASC;");
			myRs = myStmt.executeQuery();
			while(myRs.next())
			{
				int id = myRs.getInt("track_id");
				String name = myRs.getString("name");
				String genre = myRs.getString("genre");
				int plays = myRs.getInt("plays");
				String time = myRs.getString( "time");
				String mediaPath = myRs.getString("mediaPath");
				String artistName = myRs.getString("artist_name");
				String albumName = myRs.getString("album_name");

				Track track = new Track(id, name, genre, plays, time, artistName, albumName, mediaPath);
				track.setIsYours(userHasTrack(user, track));
				tracks.add(track);
			}
			System.out.println("Search track successful");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			return tracks;
		}
	}

	// Get a playlist by its name and user
	public Playlist getPlaylistByName(User user, String playlistName) {
		PreparedStatement myStmt = null;
		Playlist playlist = null;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM Playlist WHERE user_id = ? AND name = ?");
			myStmt.setInt(1, user.getUserId());
			myStmt.setString(2, playlistName);
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.next()) {
				int id = myRs.getInt("playlist_id");
				String name = myRs.getString("name");
				String imagePath = "MyMusic/view/musical-note.jpg";
				ArrayList<Track> tracks = getTracksInPlaylist(user, id);

				playlist = new Playlist(id, name, imagePath, user.getUserId(), tracks);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return playlist;
	}

	// SAVE & EXISTS METHODS //

	// Save a playlist
	public void savePlaylist(Playlist playlist) {
		PreparedStatement myStmt = null;
		try {
			if (playlistExists(playlist)) {
				updatePlaylist(playlist);
			}
			else {
				addPlaylist(playlist);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Check if a playlist already exists
	public boolean playlistExists(Playlist playlist) {
		PreparedStatement myStmt = null;
		boolean exists = false;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM Playlist WHERE playlist_id = ?");
			myStmt.setInt(1, playlist.getId());
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.next()) {
				exists = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	// Check if a user has a particular track in their library
	public boolean userHasTrack(User user, Track track) {
		PreparedStatement myStmt = null;
		boolean exists = false;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM User_has_Track WHERE user_id = ? AND track_id = ?");
			myStmt.setInt(1, user.getUserId());
			myStmt.setInt(2, track.getId());
			ResultSet myRs = myStmt.executeQuery();

			if (myRs.next()) {
				exists = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	// Check if a user has a particular album in their library
	public boolean userHasAlbum(User user, Album album) {
		PreparedStatement myStmt = null;
		boolean exists = false;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM User_has_Album WHERE user_id = ? AND album_id = ?");
			myStmt.setInt(1, user.getUserId());
			myStmt.setInt(2, album.getId());
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.next()) {
				exists = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

	// Check if a user has a particular artist in their library
	public boolean userHasArtist(User user, Artist artist) {
		PreparedStatement myStmt = null;
		boolean exists = false;
		try {
			myStmt = myConn.prepareStatement("SELECT * FROM User_has_Artist WHERE user_id = ? AND artist_id = ?");
			myStmt.setInt(1, user.getUserId());
			myStmt.setInt(2, artist.getId());
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.next()) {
				exists = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}


	// ADD & UPDATE METHODS //

	// Add a user to the database. Used on sign up
	public boolean addUser(User user) {
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO USER(name, username, password, admin) values (?,?,?,?)");
			myStmt.setString(1, user.getName());
			myStmt.setString(2, user.getUsername());
			myStmt.setString(3, user.getPassword());
			myStmt.setBoolean(4, user.isAdmin());
			myStmt.executeUpdate();
			System.out.println("user added successfully");
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
	}

	// Add a new track to the database
	public void addTrack(Track track)
	{
		PreparedStatement myStmt = null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO Track(name, genre, plays, time, mediaPath) VALUES (?, ?, ?, ?, ?)");
			myStmt.setString(1, track.getName());
			myStmt.setString(2, track.getGenre());
			myStmt.setInt(3, track.getNumPlays());
			myStmt.setString(4, track.getTime());
			myStmt.setString(5, track.getMediaPath());
			myStmt.executeUpdate();
			System.out.println("Track added successfully");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	// Add a new artist to the database
	public void addArtist(Artist artist)
	{
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO Artist (name, imagePath, rating) values (?, ?, ?)");
			myStmt.setString(1, artist.getName());
			myStmt.setString(2, artist.getImagePath());
			myStmt.setFloat(3, artist.getRating());
			myStmt.executeUpdate();
			System.out.println("Artist added successfully");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

	}

	// Add a new album to the database
	public void addAlbum(Album album)
	{
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO Album (name, imagePath, genre, year, rating) values (?,?,?,?,?)");
			myStmt.setString(1, album.getName());
			myStmt.setString(2, album.getImagePath());
			myStmt.setString(3, album.getGenre());
			myStmt.setInt(4, album.getYear());
			myStmt.setFloat(5, album.getRating());
			myStmt.executeUpdate();
			System.out.println("Album added successfully");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Add an artist to an album
	public void addAlbum_has_Artist(Album album, Artist artist) {
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO Album_has_Artist values (?,?)");
			myStmt.setInt(1, album.getId());
			myStmt.setInt(2, artist.getId());
			myStmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Add an track to an album
	public void addAlbum_has_Track(Album album, Track track) {
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO Album_has_Track values (?,?)");
			myStmt.setInt(1, album.getId());
			myStmt.setInt(2, track.getId());
			myStmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Add an track to a playlist
	public void addTrackToPlaylist(Playlist playlist, Track track) {
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO Playlist_has_Track values (?,?)");
			myStmt.setInt(1, playlist.getId());
			myStmt.setInt(2, track.getId());
			myStmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Add an track to an artist
	public void addTrack_has_Artist(Track track, Artist artist) {
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO Track_has_Artist values (?,?)");
			myStmt.setInt(1, track.getId());
			myStmt.setInt(2, artist.getId());
			myStmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Update a track
	public void updateTrack(Track track) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE Track SET name = ?, genre = ?, plays = ?, time = ?, mediaPath = ? WHERE track_id = ?;");
			myStmt.setString(1, track.getName());
			myStmt.setString(2, track.getGenre());
			myStmt.setInt(3, track.getNumPlays());
			myStmt.setString(4, track.getTime());
			myStmt.setString(5, track.getMediaPath());
			myStmt.setInt(6, track.getId());
			myStmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Update a track's play count
	public void updateTrackPlays(Track track) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE Track SET plays = ? WHERE track_id = ?");
			myStmt.setInt(1, track.getNumPlays());
			myStmt.setInt(2, track.getId());
			myStmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Updatte an artist
	public void updateArtist(Artist artist) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE Artist SET name = ?, imagePath = ?, rating = ? WHERE artist_id = ?");
			myStmt.setString(1, artist.getName());
			myStmt.setString(2, artist.getImagePath());
			myStmt.setFloat(3, artist.getRating());
			myStmt.setInt(4, artist.getId());
			myStmt.executeUpdate();
			//updatePlaylistHasTrack(album);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Update an album
	public void updateAlbum(Album album) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE Album SET name = ?, imagePath = ?, genre = ?, year = ?, rating = ? WHERE album_id = ?");
			myStmt.setString(1, album.getName());
			myStmt.setString(2, album.getImagePath());
			myStmt.setString(3, album.getGenre());
			myStmt.setInt(4, album.getYear());
			myStmt.setFloat(5, album.getRating());
			myStmt.setInt(6, album.getId());
			myStmt.executeUpdate();
			//updatePlaylistHasTrack(album);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Update a playlist
	public void updatePlaylist(Playlist playlist) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("UPDATE Playlist SET name = ? WHERE playist_id = ?");
			myStmt.setString(1, playlist.getName());
			myStmt.setInt(2, playlist.getId());
			myStmt.executeUpdate();
			updatePlaylistHasTrack(playlist);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Add & Remove tracks in a playlist
	public void updatePlaylistHasTrack(Playlist playlist) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteSQL = "DELETE FROM Playlist_has_Track WHERE playlist_id = " + playlist.getId() + ";";
			String addSql = addPlaylist_hasTrackSQL(playlist);
			myStmt.addBatch(deleteSQL);
			myStmt.addBatch(addSql);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Create a new playlistt
	public void addPlaylist(Playlist playlist) {
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("INSERT INTO Playlist (name, user_id) values (?, ?);");
			myStmt.setString(1, playlist.getName());
			myStmt.setInt(2, playlist.getUserId());
			myStmt.executeUpdate();
			addTracksToPlaylist(playlist, playlist.getTracks());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Add tracks to a playlist
	public boolean addTracksToPlaylist(Playlist playlist, ArrayList<Track> tracks) {
		Boolean success = true;
		for (int i = 0; i < tracks.size(); i++) {
			PreparedStatement myStmt = null;
			try {
				myStmt = myConn.prepareStatement("INSERT INTO Playlist_has_Track values (?, ?);");
				myStmt.setInt(1, playlist.getId());
				myStmt.setInt(2, tracks.get(i).getId());
				myStmt.executeUpdate();
			}
			catch (Exception e) {
				e.printStackTrace();
				success = false;
			}
		}
		return success;
	}

	// Add track to a user's library
	public void addUser_has_Track(User user, Track track) {
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO User_has_Track values (?, ?)");
			myStmt.setInt(1, track.getId());
			myStmt.setInt(2, user.getUserId());
			myStmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Add album to a user's library
	public void addUser_has_Album(User user, Album album) {
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO User_has_Album values (?, ?)");
			myStmt.setInt(1, user.getUserId());
			myStmt.setInt(2, album.getId());
			myStmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// Add artist to a user's library
	public void addUser_has_Artist(User user, Artist artist) {
		PreparedStatement myStmt=null;
		try
		{
			myStmt = myConn.prepareStatement("INSERT INTO User_has_Artist values (?, ?)");
			myStmt.setInt(1, user.getUserId());
			myStmt.setInt(2, artist.getId());
			myStmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	// DELETE METHODS //

	// Delete an artist
	public void deleteArtist(Artist artist) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteArtistSQL = "DELETE FROM Artist WHERE artist_id = " + artist.getId() + ";";
			String deleteTrackHasArtistSQL = "DELETE FROM Track_has_Artist WHERE artist_id = " + artist.getId() + ";";
			String deleteAlbumHasArtistSQL = "DELETE FROM Album_has_Artist WHERE artist_id = " + artist.getId() + ";";
			String deleteUserHasArtistSQL = "DELETE FROM User_has_Artist WHERE artist_id = " + artist.getId() + ";";
			myStmt.addBatch(deleteArtistSQL);
			myStmt.addBatch(deleteTrackHasArtistSQL);
			myStmt.addBatch(deleteAlbumHasArtistSQL);
			myStmt.addBatch(deleteUserHasArtistSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete an album
	public void deleteAlbum(Album album) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteAlbumSQL = "DELETE FROM Album WHERE album_id = " + album.getId() + ";";
			String deleteAlbumHasTrackSQL = "DELETE FROM Album_has_Track WHERE album_id = " + album.getId() + ";";
			String deleteAlbumHasArtistSQL = "DELETE FROM Album_has_Artist WHERE album_id = " + album.getId() + ";";
			String deleteUserHasAlbumSQL = "DELETE FROM User_has_Album WHERE album_id = " + album.getId() + ";";
			myStmt.addBatch(deleteAlbumSQL);
			myStmt.addBatch(deleteAlbumHasTrackSQL);
			myStmt.addBatch(deleteAlbumHasArtistSQL);
			myStmt.addBatch(deleteUserHasAlbumSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteTrack(Track track) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteTrackSQL = "DELETE FROM Track WHERE track_id = " + track.getId() + ";";
			String deleteAlbumHasTrackSQL = "DELETE FROM Album_has_Track WHERE track_id = " + track.getId() + ";";
			String deleteTrackHasArtistSQL = "DELETE FROM Track_has_Artist WHERE track_id = " + track.getId() + ";";
			String deleteUserHasTrackSQL = "DELETE FROM User_has_Track WHERE track_id = " + track.getId() + ";";
			myStmt.addBatch(deleteTrackSQL);
			myStmt.addBatch(deleteAlbumHasTrackSQL);
			myStmt.addBatch(deleteTrackHasArtistSQL);
			myStmt.addBatch(deleteUserHasTrackSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete a playlist
	public void deletePlaylist(Playlist playlist) {
		try {
			Statement myStmt = myConn.createStatement();
			String deletePlaylistSQL = "DELETE FROM Playlist WHERE playlist_id = " + playlist.getId() + ";";
			String deletePlaylistHasTrackSQL = "DELETE FROM Playlist_has_Track WHERE playlist_id = " + playlist.getId() + ";";
			myStmt.addBatch(deletePlaylistSQL);
			myStmt.addBatch(deletePlaylistHasTrackSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete/Remove an artist from an album
	public void deleteAlbum_has_Artist(Album album, Artist artist) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteSQL = "DELETE FROM Album_has_Artist WHERE album_id = " + album.getId() + " AND artist_id = " + artist.getId() + ";";
			myStmt.addBatch(deleteSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete/Remove an track from an album
	public void deleteAlbum_has_Track(Album album, Track track) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteSQL = "DELETE FROM Album_has_Track WHERE album_id = " + album.getId() + " AND track_id = " + track.getId() + ";";
			myStmt.addBatch(deleteSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete/Remove an track from an album
	public void deleteTrack_has_Artist(Track track, Artist artist) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteSQL = "DELETE FROM Track_has_Artist WHERE artist_id = " + artist.getId() + " AND track_id = " + track.getId() + ";";
			myStmt.addBatch(deleteSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete/Remove an track from a playlist
	public void removeTrackFromPlaylist(Playlist playlist, Track track) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteSQL = "DELETE FROM Playlist_has_Track WHERE playlist_id = " + playlist.getId() + " AND track_id = " + track.getId() + ";";
			myStmt.addBatch(deleteSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete/Remove a track from a user's library
	public void deleteUser_has_Track(User user, Track track) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteSQL = "DELETE FROM User_has_Track WHERE user_id = " + user.getUserId() + " AND track_id = " + track.getId() + ";";
			myStmt.addBatch(deleteSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete/Remove an album from a user's library
	public void deleteUser_has_Album(User user, Album album) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteSQL = "DELETE FROM User_has_Album  WHERE user_id = " + user.getUserId() + " AND album_id = " + album.getId() + ";";
			myStmt.addBatch(deleteSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Delete/Remove an artist from a user's library
	public void deleteUser_has_Artist(User user, Artist artist) {
		try {
			Statement myStmt = myConn.createStatement();
			String deleteSQL = "DELETE FROM User_has_Artist WHERE user_id = " + user.getUserId() + " AND artist_id = " + artist.getId() + ";";
			myStmt.addBatch(deleteSQL);
			myStmt.executeBatch();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	// SQL GENERATION METHODS //

	// Method to create a WHERE clause in SQL for filtering search results by a search string
	private String filterSQL(String searchString, boolean searchByTrackName, boolean searchByAlbumName, boolean searchByArtistName) {
		if (!searchByTrackName && !searchByAlbumName && !searchByArtistName) {
			return " WHERE true";
		}
		else {
			searchString = "\'%"+searchString+"%\'";
			String sql = "";
			sql = " WHERE (";
			if (searchByTrackName) {
				sql += " Track.name LIKE " + searchString;
			}
			if (searchByAlbumName) {
				if (searchByTrackName) {
					sql += " OR";
				}
				sql += " Album.name LIKE " + searchString;
			}
			if (searchByArtistName) {
				if (searchByTrackName || searchByAlbumName) {
					sql += " OR";
				}
				sql += " Artist.name LIKE " + searchString;
			}
			sql += ")";
			return sql;
		}
	}

	// Method to create a part of a WHERE clause in SQL for filtering search results by genres
	private String genreSQL(ArrayList<String> selectedGenres, String tableName) {
	    String sql = "";
	    if (selectedGenres.size() > 0) {
	        sql += " AND (";
        }
        else {
        	return "";
		}

        for (int i = 0; i < selectedGenres.size(); i++) {
            sql += tableName + ".genre = \'" + selectedGenres.get(i) + "\'";
            if (i != selectedGenres.size() - 1) {
                sql += " OR ";
            }
            else {
                sql += ")";
            }
        }
        return sql;
    }

	// Method to create a series of SQL inserts for inerting tracks into a playlist
    private String addPlaylist_hasTrackSQL(Playlist playlist) {
		String sql = "";
		for (int i = 0; i < playlist.getTracks().size(); i++) {
			sql += "INSERT INTO Playlist_has_Track Values (" + playlist.getId() + ", " + playlist.getTracks().get(i).getId() + "); ";
		}

		return sql;
	}
}
