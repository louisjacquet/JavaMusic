package musichub.util;

import musichub.business.*;
import java.io.*;  
import java.net.*; 
import java.util.*; 

public class SimpleClient {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;
	
	public void connect(String ip)
	{
		int port = 6666;
        try  {
			//create the socket; it is defined by an remote IP address (the address of the server) and a port number
			socket = new Socket(ip, port);

			//create the streams that will handle the objects coming and going through the sockets
			output = new ObjectOutputStream(socket.getOutputStream());
		        input = new ObjectInputStream(socket.getInputStream());

		        String text = (String) input.readObject();
		        System.out.println(text);
			Scanner scan = new Scanner(System.in);
			String choice = scan.nextLine();
			output.writeObject(choice);
			String albumTitle = null;
			while (choice.charAt(0)!= 'q') 	{
				switch (choice.charAt(0))	{
					case 'h':
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case 't':
					//album titles, ordered by date
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case 'g':
					//songs of an album, sorted by genre
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						albumTitle = scan.nextLine();
						output.writeObject(albumTitle);
						int b;
						b = (int) input.readObject();
						if(b == 1)
							System.out.println((String) input.readObject());
						else
							System.out.println((ArrayList) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case 'd':
					//songs of an album
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						albumTitle = scan.nextLine();
						output.writeObject(albumTitle);
						int a;
						a = (int) input.readObject();
						if(a == 1)
							System.out.println((String) input.readObject());
						else
							System.out.println((ArrayList) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case 'u':
					//audiobooks ordered by author
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case 'c':
					// add a new song
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						int length = Integer.parseInt(scan.nextLine());
						output.writeObject(length);
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
//						Iterator<AudioElement> it = (Iterator<AudioElement>) input.readObject();
//						while (it.hasNext()) System.out.println(it.next().getTitle());
						while((int) input.readObject() == 0) {
							System.out.println((String) input.readObject());
						}
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case 'a':
					// add a new album
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						int lengthA = Integer.parseInt(scan.nextLine());
						output.writeObject(lengthA);
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						while((int) input.readObject() == 0) {
							System.out.println((String) input.readObject());
						}
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case '+':
					//add a song to an album:
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						while((int) input.readObject() == 0) {
							if((int) input.readObject() == 1) {
								System.out.println((String) input.readObject());
							}
						}
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						while((int) input.readObject() == 0) {
							System.out.println((String) input.readObject());
						}
						choice = scan.nextLine();
						output.writeObject(choice);
						
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case 'l':
					// add a new audiobook
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						int blength = Integer.parseInt(scan.nextLine());
						output.writeObject(blength);
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						while((int) input.readObject() == 0) {
							System.out.println((String) input.readObject());
						}
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case 'p':
					//create a new playlist from existing elements
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						while((int) input.readObject() == 0) {
							System.out.println((String) input.readObject());
						}
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						while((int) input.readObject() == 0) {
							System.out.println((String) input.readObject());
						}
						while((int) input.readObject() == 0) {
							System.out.println((String) input.readObject());
							choice = scan.nextLine();
							output.writeObject(choice);
							if((int) input.readObject() == 1)
								System.out.println((String) input.readObject());
							System.out.println((String) input.readObject());
							choice = scan.nextLine();
							output.writeObject(choice);
						}
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case '-':
					//delete a playlist
						System.out.println((String) input.readObject());
						while((int) input.readObject() == 0) {
							System.out.println((String) input.readObject());
						}
						choice = scan.nextLine();
						output.writeObject(choice);
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					case 's':
					//save elements, albums, playlists
						System.out.println((String) input.readObject());
						System.out.println((String) input.readObject());
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					default:
						choice = scan.nextLine();
						output.writeObject(choice);
					break;
					}
				}

	    } catch  (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		catch  (IOException ioe) {
			ioe.printStackTrace();
		}
		catch  (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
