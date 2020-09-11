package MyMusic.model;

import java.util.ArrayList;

public class User {
    private int userId;
    private String name;
    private String username;
    private String password;
    public boolean isAdmin;
    private ArrayList<Playlist> playlists;

    public User() {

    }

    public User(int userId, String name, String username, String password, boolean isAdmin) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public ArrayList<Playlist> getPlaylists() { return playlists; }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) { this.playlists = playlists; }
}
