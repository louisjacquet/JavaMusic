package musichub.main;
import musichub.business.*;
import musichub.util.*;
import java.io.*;  
import java.net.*; 

public class ServerConnection
{

	public static void main (String[] args) {
		AbstractServer as = new FirstServer();
		String ip = "localhost";
		as.connect(ip);
		
	}
}
