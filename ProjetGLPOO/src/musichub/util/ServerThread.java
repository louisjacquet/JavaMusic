package musichub.util; 

import musichub.business.*; 

import java.io.*;
import java.net.*;
import java.util.*; 

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
 
    public ServerThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
			//create the streams that will handle the objects coming through the sockets
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
 
			MusicHub theHub = new MusicHub ();
		
			String text = "Type h for available commands";	//serialize and write the Student object to the stream
 			output.writeObject(text);
 			String text1 = (String)input.readObject();
 			String text2 = "t: display the album titles, ordered by date\ng: display songs of an album, ordered by genre\nd: display songs of an album\nu: display audiobooks ordered by author\nc: add a new song\na: add a new album\n+: add a song to an album\nl: add a new audiobook\np: create a new playlist from existing songs and audio books\n-: delete an existing playlist\ns: save elements, albums, playlists\nq: quit program" ;
 			String albumTitle = null;
 			while (text1.charAt(0)!= 'q') 	{
 				switch (text1.charAt(0)) 	{
					case 'h':
						System.out.println(text1);
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					case 't':
					//album titles, ordered by date
						System.out.println(text1);
						output.writeObject(theHub.getAlbumsTitlesSortedByDate());
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					case 'g':
					//songs of an album, sorted by genre
						System.out.println(text1);
						output.writeObject("Songs of an album sorted by genre will be displayed; enter the album name, available albums are:");
						output.writeObject(theHub.getAlbumsTitlesSortedByDate());
						albumTitle = (String)input.readObject();
						try {
							System.out.println(theHub.getAlbumSongsSortedByGenre(albumTitle));
							output.writeObject(0);
							output.writeObject(theHub.getAlbumSongsSortedByGenre(albumTitle));
						} catch (NoAlbumFoundException ex) {
							output.writeObject(1);
							output.writeObject("No album found with the requested title " + ex.getMessage());
						}
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					case 'd':
					//songs of an album
						System.out.println(text1);
						output.writeObject("Songs of an album will be displayed; enter the album name, available albums are:");
						output.writeObject(theHub.getAlbumsTitlesSortedByDate());
						albumTitle = (String)input.readObject();
						try {
							System.out.println(theHub.getAlbumSongs(albumTitle));
							output.writeObject(0);
							output.writeObject(theHub.getAlbumSongs(albumTitle));
						} catch (NoAlbumFoundException ex) {
							output.writeObject(1);
							output.writeObject("No album found with the requested title " + ex.getMessage());
						}
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					case 'u':
					//audiobooks ordered by author
						System.out.println(text1);
						output.writeObject(theHub.getAudiobooksTitlesSortedByAuthor());
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					case 'c':
					// add a new song
						System.out.println(text1);
						output.writeObject("Enter a new song: ");
						output.writeObject("Song title: ");
						String title = (String)input.readObject();
						output.writeObject("Song genre (jazz, classic, hiphop, rock, pop, rap):");
						String genre = (String)input.readObject();
						output.writeObject("Song artist: ");
						String artist = (String)input.readObject();
						output.writeObject("Song length in seconds: ");
						int length = (int)input.readObject();
						output.writeObject("Song content: ");
						String content = (String)input.readObject();
						Song s = new Song (title, artist, length, content, genre);
						theHub.addElement(s);
						output.writeObject("New element list: ");
						Iterator<AudioElement> it = theHub.elements();
						while(it.hasNext()) {
							output.writeObject(0);
							output.writeObject(it.next().getTitle());
						}
						output.writeObject(1);
						output.writeObject("Song created!");
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					case 'a':
					// add a new album
						System.out.println(text1);
						output.writeObject("Enter a new album: ");
						output.writeObject("Album title: ");
						String aTitle = (String)input.readObject();
						output.writeObject("Album artist: ");
						String aArtist = (String)input.readObject();
						output.writeObject("Album length in seconds: ");
						int aLength = (int)input.readObject();
						output.writeObject("Album date as YYYY-DD-MM: ");
						String aDate = (String)input.readObject();
						Album a = new Album(aTitle, aArtist, aLength, aDate);
						theHub.addAlbum(a);
						output.writeObject("New list of albums: ");
						Iterator<Album> ita = theHub.albums();
						while(ita.hasNext()) {
							output.writeObject(0);
							output.writeObject(ita.next().getTitle());
						}
						output.writeObject(1);
						output.writeObject("Album created!");
						output.writeObject(text2);
						text1 = (String)input.readObject();						
					break;
					case '+':
					//add a song to an album:
						System.out.println(text1);
						output.writeObject("Add an existing song to an existing album");
						output.writeObject("Type the name of the song you wish to add. Available songs: ");
						Iterator<AudioElement> itae = theHub.elements();
						while (itae.hasNext()) {
							output.writeObject(0);
							AudioElement ae = itae.next();
							if ( ae instanceof Song) {
								output.writeObject(1);
								output.writeObject(ae.getTitle());
							}
							else
								output.writeObject(0);
						}
						output.writeObject(2);
						String songTitle = (String)input.readObject();
						output.writeObject("Type the name of the album you wish to enrich. Available albums: ");
						Iterator<Album> ait = theHub.albums();
						while (ait.hasNext()) {
							output.writeObject(0);
							Album al = ait.next();
							output.writeObject(al.getTitle());
						}
						output.writeObject(1);
						String titleAlbum = (String)input.readObject();
						try {
							theHub.addElementToAlbum(songTitle, titleAlbum);
							output.writeObject("Song added to the album!");
						} catch (NoAlbumFoundException ex){
							output.writeObject(ex.getMessage());
						} catch (NoElementFoundException ex){
							output.writeObject(ex.getMessage());
						}						
						
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					case 'l':
					// add a new audiobook
						System.out.println(text1);
						output.writeObject("Enter a new audiobook: ");
						output.writeObject("AudioBook title: ");
						String bTitle = (String)input.readObject();
						output.writeObject("AudioBook category (youth, novel, theater, documentary, speech)");
						String bCategory = (String)input.readObject();
						output.writeObject("AudioBook artist: ");
						String bArtist = (String)input.readObject();
						output.writeObject("AudioBook length in seconds: ");
						int bLength = (int)input.readObject();
						output.writeObject("AudioBook content: ");
						String bContent = (String)input.readObject();
						output.writeObject("AudioBook language (french, english, italian, spanish, german)");
						String bLanguage = (String)input.readObject();
						AudioBook b = new AudioBook (bTitle, bArtist, bLength, bContent, bLanguage, bCategory);
                    				theHub.addElement(b);
						output.writeObject("Audiobook created! New element list: ");
						Iterator<AudioElement> itl = theHub.elements();
						while (itl.hasNext()) {
							output.writeObject(0);
							output.writeObject(itl.next().getTitle());
						}
						output.writeObject(1);	
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					case 'p':
					//create a new playlist from existing elements
						System.out.println(text1);
						output.writeObject("Add an existing song or audiobook to a new playlist");
						output.writeObject("Existing playlists:");
						Iterator<PlayList> itpl = theHub.playlists();
						while (itpl.hasNext()) {
							output.writeObject(0);
							PlayList pl = itpl.next();
							output.writeObject(pl.getTitle());
						}
						output.writeObject(1);
						output.writeObject("Type the name of the playlist you wish to create:");
						String playListTitle = (String)input.readObject();
						PlayList pl = new PlayList(playListTitle);
						theHub.addPlaylist(pl);
						output.writeObject("Available elements: ");
						Iterator<AudioElement> itael = theHub.elements();
						while (itael.hasNext()) {
							output.writeObject(0);
							AudioElement ae = itael.next();
							output.writeObject(ae.getTitle());
						}
						output.writeObject(1);
						while (text1.charAt(0)!= 'n') 	{
							output.writeObject(0);
							output.writeObject("Type the name of the audio element you wish to add or 'n' to exit:");
							String elementTitle = (String)input.readObject();	
      				                	try {
      				                	      	theHub.addElementToPlayList(elementTitle, playListTitle);
      				                	      	output.writeObject(0);
      					        	        } catch (NoPlayListFoundException ex) {
      					        	        	output.writeObject(1);	
      					        	        	output.writeObject(ex.getMessage());
               						        } catch (NoElementFoundException ex) {
               						        	output.writeObject(1);
                					        	output.writeObject(ex.getMessage());
                        					}
                            	
								output.writeObject("Type y to add a new one, n to end");
								text1 = (String)input.readObject();
						}
						output.writeObject(1);
						output.writeObject("Playlist created!");
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					case '-':
					//delete a playlist
						System.out.println(text1);
						output.writeObject("Delete an existing playlist. Available playlists:");
						Iterator<PlayList> itp = theHub.playlists();
						while (itp.hasNext()) {
							output.writeObject(0);
							PlayList p = itp.next();
							output.writeObject(p.getTitle());
						}
						output.writeObject(1);
						String plTitle = (String)input.readObject();
						try {
							theHub.deletePlayList(plTitle);
							output.writeObject("Playlist deleted!");
						}	catch (NoPlayListFoundException ex) {
							output.writeObject(ex.getMessage());
						}
						
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					case 's':
					//save elements, albums, playlists
						System.out.println(text1);
						theHub.saveElements();
						theHub.saveAlbums();
						theHub.savePlayLists();
						output.writeObject("Elements, albums and playlists saved!");
						output.writeObject(text2);
						text1 = (String)input.readObject();
					break;
					default:
						System.out.println(text1);
						text1 = (String)input.readObject();
					break;
					}
					
 				}
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();

		} catch (ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
    }

}
