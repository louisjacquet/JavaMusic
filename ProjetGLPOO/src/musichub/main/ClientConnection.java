package musichub.main; 
import musichub.business.*;
import musichub.util.*;
public class ClientConnection
{
	public static void main (String[] args)
	{
		SimpleClient c1 = new SimpleClient();
		c1.connect("localhost");
	}
} 
