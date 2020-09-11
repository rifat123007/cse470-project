package MyMusic.model;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

public class Album implements Component {
	private int id;
	private String name;
	private String imagePath;
	private String artistName;
	private int year;
	private String genre;
	private float rating;
	private ArrayList<Track> tracks;
	private boolean isYours;
	private MediaPlayer player;
	private int playIndex = 0;

	Album() {}

	Album(int id, String name, String artistName, int year, String genre, String imagePath, float rating)
	{
		this.id = id;
		this.name = name;
		this.artistName = artistName;
		this.imagePath = imagePath;
		this.year = year;
		this.genre = genre;
		this.rating = rating;
		this.isYours = false;
	}

	public int  getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public String getArtistName()
	{
		return this.artistName;
	}

	public String getImagePath()
	{
		return this.imagePath;
	}

	public String getGenre()
	{
		return this.genre;
	}

	public  int getYear()
	{
		return this.year;
	}

	public float getRating() { return this.rating; }

	public ArrayList<Track> getTracks() { return this.tracks; }

	public Boolean isYours()
	{
		return isYours;
	}


	public void setName(String name)
	{
		this.name=name;
	}

	public void setArtistName(String artistName)
	{
		this.artistName=artistName;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath=imagePath;
	}

	public void setGenre(String genre)
	{
		this.genre=genre;
	}

	public void setYear(int year)
	{
		this.year=year;
	}

	public void setRating(float rating)
	{
		this.rating=rating;
	}

	public void setTracks(ArrayList<Track> trackList) {
		tracks = trackList;
	}

	public void setIsYours(boolean isYours)
	{
		this.isYours=isYours;
	}


	public void addTrack(Track track)
	{
		this.tracks.add(track);
	}

	public void play(Button playButton) {
		MediaPlayerManager.playAlbum(this, playButton);
	}

	public void pause() {
		MediaPlayerManager.pause();
	}

	public void continuePlaying() {
		MediaPlayerManager.continuePlaying();
	}

	public void stop() {
		MediaPlayerManager.stop();
	}


	// Method used in composite pattern
	public void printInfo() {
		System.out.println("Album");
		System.out.println("Name: " + name);
		System.out.println("Genre:" + genre);
		System.out.println("Year: " + year);
		System.out.println("Artist name: " + artistName);
		System.out.println("Rating: " + rating);

		for (Track track : tracks) {
			System.out.println();
			track.printInfo();
		}
	}

}
