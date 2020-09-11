package MyMusic.model;

import java.util.ArrayList;

public class Artist {
	private int id;
	private String name;
	private String imagePath;
	private float rating;
	private ArrayList<Album> albums;
	private ArrayList<Track> tracks;
	private boolean isYours;

	Artist() {}

	Artist(int id, String name, String imagePath, Float rating)
	{
		this.id = id;
		this.name = name;
		this.imagePath = imagePath;
		this.rating = rating;
		this.tracks = new ArrayList<Track>();
		this.albums = new ArrayList<Album>();
		this.isYours = false;
	}

	public int getId()
	{
		return this.id;
	}
	public String getName() { return this.name; }
	public String getImagePath()
	{
		return this.imagePath;
	}
	public float getRating() { return this.rating; }
	public ArrayList<Track> getTracks()
	{
		return this.tracks;
	}
	public ArrayList<Album> getAlbums()
	{
		return this.albums;
	}
	public Boolean isYours()
	{
		return isYours;
	}

	public void setId(int id)
	{
		this.id=id;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public void setImagePath(String imagePath)
	{
		this.imagePath=imagePath;
	}
	public void setRating(float rating)
	{
		this.rating=rating;
	}
	public void setTracks(ArrayList<Track> tracks) { this.tracks = tracks; }
	public void setAlbums(ArrayList<Album> albums) { this.albums = albums; }
	public void setIsYours(boolean isYours)
	{
		this.isYours=isYours;
	}


	public void addTrack(Track track)
	{
		tracks.add(track);
	}
	public void addAlbum(Album album) { albums.add(album); }


}
