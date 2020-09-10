package connection;

public abstract class Connection {
	
	private final static String Url = "rmi://localhost:5099/";
	
	public static String getUrl() {
		return Url;
	}

}
