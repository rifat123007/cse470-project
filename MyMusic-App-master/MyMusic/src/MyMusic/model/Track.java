package MyMusic.model;

import javafx.scene.control.Button;

public class Track implements Component {
	private int id;
	private String name;
	private String genre;
	private int plays;
	private String time;
	private String artist_name;
	private String album_name;
	private String mediaPath;
	private int album_id;
	private boolean isYours;
    private boolean isActiveTrack;


    public Track() {}

	public Track(String name, String genre, int plays, String time, String artist_name, String album_name) {
		this.name = name;
		this.genre = genre;
		this.plays = plays;
		this.time = time;
		this.artist_name = artist_name;
		this.album_name = album_name;
	}

	public Track(int id, String name, String genre, int plays, String time, String artist_name, String album_name, String mediaPath) {
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.plays = plays;
		this.time = time;
		this.artist_name = artist_name;
		this.album_name = album_name;
		this.mediaPath = mediaPath;
		this.isYours = false;
		isActiveTrack = false;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public int getAlbumId()
	{
		return this.album_id;
	}

	public String getTime()
	{
		return this.time;
	}

	public int getNumPlays()
	{
		return this.plays;
	}

	public String getArtistName()
	{
		return artist_name;
	}

	public String getAlbumName()
	{
		return album_name;
	}

	public String getGenre()
	{
		return genre;
	}

	public String getMediaPath()
	{
		return mediaPath;
	}

	public Boolean isYours()
	{
		return isYours;
	}

	public Boolean isActiveTrack()
	{
		return isActiveTrack;
	}



	public void setId(int id)
	{
		this.id=id;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public void setAlbumId(int album_id)
	{
		this.album_id=album_id;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public void setPlays(int plays)
	{
		 this.plays = plays;
	}

	public void setArtistName(String artist_name)
	{
		this.artist_name=artist_name;
	}

	public void setAlbumName(String album_name)
	{
		this.album_name=album_name;
	}

	public void setGenre(String genre)
	{
		this.genre=genre;
	}

	public void setMediaPath(String mediaPath)
	{
		this.mediaPath=mediaPath;
	}

	public void setIsYours(boolean isYours)
	{
		this.isYours=isYours;
	}

	public void setIsActiveTrack(boolean isActiveTrack)
	{
		this.isActiveTrack=isActiveTrack;
	}



	public void printInfo() {
    	System.out.println("Track");
		System.out.println("Name: " + name);
		System.out.println("Genre:" + genre);
		System.out.println("Time: " + time);
		System.out.println("Artist name: " + artist_name);
	}

	public void play(Button playButton) {
        MediaPlayerManager.playTrack(this, playButton);
	}

	public void continuePlaying() {
		MediaPlayerManager.continuePlaying();
	}

	public void pause() {
        MediaPlayerManager.pause();
    }

    public void stop() {
        MediaPlayerManager.stop();
    }

}
