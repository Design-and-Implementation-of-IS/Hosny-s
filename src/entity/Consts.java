package entity;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * http://www.javapractices.com/topic/TopicAction.do?Id=2
 */
public class Consts {
	private Consts() {
		throw new AssertionError();
	}

	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH;
	
	
	
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			// System.out.println(decoded) - Can help to check the returned path
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				return decoded + "/entity/EX2.accdb";
			} else {
				System.out.println(decoded.substring(0, decoded.lastIndexOf('/'))+ "/entity/EX2.accdb" );
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				System.out.println(decoded);
				return decoded + "src/entity/EX2.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
