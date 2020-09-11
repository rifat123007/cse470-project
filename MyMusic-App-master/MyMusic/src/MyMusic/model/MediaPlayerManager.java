package MyMusic.model;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/*
// Responsible for playing, pausing, etc. media/songs
*/
public class MediaPlayerManager {
    private static MediaPlayer mediaPlayer;
    private static int playIndex;

    // Plays a track and makes the play button that called playTrack a pause button
    public static void playTrack(Track track, Button button) {
        // If the track is the active track, continue playing it, else stop the active track and play a new one
        if (track.isActiveTrack()) {
            mediaPlayer.play();
        }
        else {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            // Get media file and play the file
            File f = new File(track.getMediaPath());
            Media m = new Media(f.toURI().toString());
            mediaPlayer = new MediaPlayer(m);
            mediaPlayer.play();
            track.setIsActiveTrack(true);

            // Set mediaPlayer event methods
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    track.setIsActiveTrack(false);
                }
            });
            mediaPlayer.setOnPaused(new Runnable() {
                @Override
                public void run() {
                    button.setText("Continue");
                }
            });
            mediaPlayer.setOnPlaying(new Runnable() {
                @Override
                public void run() {
                    button.setText("Pause");
                }
            });
            mediaPlayer.setOnStopped(new Runnable() {
                @Override
                public void run() {
                    button.setText("Play");
                    track.setIsActiveTrack(false);
                }
            });
        }
    }

    // Continue playing the current media
    public static void continuePlaying() {
        mediaPlayer.play();
    }

    // Play an album
    public static void playAlbum(Album album, Button button) {
        if (playIndex < album.getTracks().size()) {
            playTrack(album.getTracks().get(playIndex), button);

            // Set mediaPlayer event methods
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    playIndex++;
                    playAlbum(album, button);
                }
            });
            mediaPlayer.setOnPaused(new Runnable() {
                @Override
                public void run() {
                    button.setText("Play");
                }
            });
            mediaPlayer.setOnPlaying(new Runnable() {
                @Override
                public void run() {
                    button.setText("Pause");
                }
            });
            mediaPlayer.setOnStopped(new Runnable() {
                @Override
                public void run() {
                    button.setText("Play");
                    album.getTracks().get(playIndex).setIsActiveTrack(false);
                }
            });
        }
        else {
            playIndex = 0;
        }
    }

    // Pause
    public static void pause() {
        mediaPlayer.pause();
    }

    // Stop
    public static void stop() {
        mediaPlayer.stop();
        playIndex = 0; // reset the play index
    }
}
