# User
DELETE FROM User WHERE user_id >= 0;
ALTER TABLE User AUTO_INCREMENT = 1;
INSERT INTO User (name, username, password, admin) VALUES ("Michael", "admin", "admin", 1);
INSERT INTO User (name, username, password, admin) VALUES ("user", "un", "pw", 0);
SELECT * FROM User;

# Artist
DELETE FROM Artist WHERE artist_id > 0;
ALTER TABLE Artist AUTO_INCREMENT = 1;
INSERT INTO Artist (name, imagePath, rating) VALUES ("Weird Al Yankovic", "MyMusic/Images/Artists/Weird_Al_Yankovic.jpg", 5.0);
INSERT INTO Artist (name, imagePath, rating) VALUES ("The Beatles", "MyMusic/Images/Artists/The_Beatles.jpeg", 5.0);
SELECT * FROM Artist;

# Album
DELETE FROM Album WHERE album_id > 0;
ALTER TABLE Album AUTO_INCREMENT = 1;
INSERT INTO Album (name, imagePath, genre, year, rating) VALUES ("Mandatory Fun", "MyMusic/Images/Albums/Mandatory_Fun.jpg", "Comedy", 2014, 5.0);
INSERT INTO Album (name, imagePath, genre, year, rating) VALUES ("Alpocalypse", "MyMusic/Images/Albums/Alpocalypse.jpg", "Comedy", 2011, 5.0);
INSERT INTO Album (name, imagePath, genre, year, rating) VALUES ("Abbey Road", "MyMusic/Images/Albums/Abbey_Road.jpg", "Rock", 1969, 5.0);
SELECT * FROM Album;

# Album_has_Artist
DELETE FROM Album_has_Artist WHERE album_id > 0;
ALTER TABLE Album AUTO_INCREMENT = 1;
INSERT INTO Album_has_Artist VALUES (1, 1);
INSERT INTO Album_has_Artist VALUES (2, 1);
INSERT INTO Album_has_Artist VALUES (3, 2);
SELECT * FROM Album_has_Artist;

# Track
DELETE FROM Track WHERE track_id > 0;
ALTER TABLE Track AUTO_INCREMENT = 1;
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Handy", "Comedy", 0,"02:56", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Handy.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Lame Clain to Fame", "Comedy", 0,"03:45", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Lame_Claim_to_Fame.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Foil", "Comedy", 0,"02:23", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Foil.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Sports Song", "Comedy", 0,"02:15", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Sports_Song.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Word Crimes", "Comedy", 0,"03:43", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Word_Crimes.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("My Own Eyes", "Comedy", 0,"03:41", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/My_Own_Eyes.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("NOW That's What I Call Polka!", "Comedy", 0,"04:06", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/NOW_That's_What_I_Call_Polka!.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Mission Statement", "Comedy", 0,"04:29", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Mission_Statement.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Inactive", "Comedy", 0,"02:56", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Inactive.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("First World Problems", "Comedy", 0,"03:14", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/First_World_Problems.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Tacky", "Comedy", 0,"02:53", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Tacky.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Jackson Park Express", "Comedy", 0,"09:05", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Jackson_Park_Express.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Perform This Way", "Comedy", 0,"02:55", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Perform_This_Way.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Polka Face", "Comedy", 0,"04:47", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Polka_Face.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Party in the CIA", "Comedy", 0,"02:57", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Party_In_the_CIA.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Come Together", "Rock", 0,"04:19", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Come_Together.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Here Comes the Sun", "Rock", 0,"03:06", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Here_Comes_the_Sun.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Golden Slumbers", "Rock", 0,"01:32", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Golden_Slumbers.mp3");
INSERT INTO Track (name, genre, plays, time, mediaPath) VALUES ("Carry that Weight", "Rock", 0,"01:36", "/Users/Michael/GitHub/CS6359001-Course_Project-team8/MyMusic/src/MyMusic/Media/Carry_That_Weight.mp3");
SELECT * FROM Track;


# Album_has_Track
DELETE FROM Album_has_Track WHERE track_id > 0;
ALTER TABLE Album_has_Track AUTO_INCREMENT = 1;
INSERT INTO Album_has_Track VALUES (1,1);
INSERT INTO Album_has_Track VALUES (1,2);
INSERT INTO Album_has_Track VALUES (1,3);
INSERT INTO Album_has_Track VALUES (1,4);
INSERT INTO Album_has_Track VALUES (1,5);
INSERT INTO Album_has_Track VALUES (1,6);
INSERT INTO Album_has_Track VALUES (1,7);
INSERT INTO Album_has_Track VALUES (1,8);
INSERT INTO Album_has_Track VALUES (1,9);
INSERT INTO Album_has_Track VALUES (1,10);
INSERT INTO Album_has_Track VALUES (1,11);
INSERT INTO Album_has_Track VALUES (1,12);
INSERT INTO Album_has_Track VALUES (2,13);
INSERT INTO Album_has_Track VALUES (2,14);
INSERT INTO Album_has_Track VALUES (2,15);
INSERT INTO Album_has_Track VALUES (3,16);
INSERT INTO Album_has_Track VALUES (3,17);
INSERT INTO Album_has_Track VALUES (3,18);
INSERT INTO Album_has_Track VALUES (3,19);


# Track_has_Artist
DELETE FROM Track_has_Artist WHERE artist_id > 0;
ALTER TABLE Track_has_Artist AUTO_INCREMENT = 1;
INSERT INTO Track_has_Artist VALUES (1, 1);
INSERT INTO Track_has_Artist VALUES (2, 1);
INSERT INTO Track_has_Artist VALUES (3, 1);
INSERT INTO Track_has_Artist VALUES (4, 1);
INSERT INTO Track_has_Artist VALUES (5, 1);
INSERT INTO Track_has_Artist VALUES (6, 1);
INSERT INTO Track_has_Artist VALUES (7, 1);
INSERT INTO Track_has_Artist VALUES (8, 1);
INSERT INTO Track_has_Artist VALUES (9, 1);
INSERT INTO Track_has_Artist VALUES (10, 1);
INSERT INTO Track_has_Artist VALUES (11, 1);
INSERT INTO Track_has_Artist VALUES (12, 1);
INSERT INTO Track_has_Artist VALUES (13, 1);
INSERT INTO Track_has_Artist VALUES (14, 1);
INSERT INTO Track_has_Artist VALUES (15, 1);
INSERT INTO Track_has_Artist VALUES (16, 2);
INSERT INTO Track_has_Artist VALUES (17, 2);
INSERT INTO Track_has_Artist VALUES (18, 2);
INSERT INTO Track_has_Artist VALUES (19, 2);
SELECT * FROM Track_has_Artist;

# Playlist
DELETE FROM Playlist WHERE playlist_id > 0;
ALTER TABLE Playlist AUTO_INCREMENT = 1;
INSERT INTO Playlist (name, user_id) VALUES ("Polka", 1);
SELECT * FROM Playlist;

# Playlist_has_Track
DELETE FROM Playlist_has_Track WHERE playlist_id > 0;
ALTER TABLE Playlist_has_Track AUTO_INCREMENT = 1;
INSERT INTO Playlist_has_Track VALUES (1, 7);
INSERT INTO Playlist_has_Track VALUES (1, 14);