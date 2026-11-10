import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.util.logging.Logger;

import org.apache.commons.lang3.tuple.Pair;

public class SqlQuery {
	static Logger log = Logger.getLogger(SqlQuery.class.getName());
	
	private static Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String baseURL = Config.getValue("ba.baseURL");
		String dbName = Config.getValue("ba.dbName");
		String username = Config.getValue("ba.username");
		String password = Config.getValue("ba.password");
		
		return DriverManager.getConnection(baseURL + dbName, username, password);
	}
	
	static public <T> T selectOne(String query, ArrayList<Object> valueList, SqlFunction<ResultSet, T> handler) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (Connection con = getDatabaseConnection(); PreparedStatement ps = con.prepareStatement(query)) {
				for (int i = 0; i < valueList.size(); i++) {
					Object value = valueList.get(i);
					
					if (value instanceof Timestamp)		{	ps.setTimestamp(i + 1, (Timestamp) value);	}
					else if (value instanceof Date)		{	ps.setDate(i + 1, (Date) value);			}
					else if (value instanceof Integer)	{	ps.setInt(i + 1, (Integer) value);			}
					else if (value instanceof Long)		{	ps.setLong(i + 1, (Long) value);			}
					else if (value instanceof Double)	{	ps.setDouble(i + 1, (Double) value);		}
					else if (value instanceof Float)	{	ps.setFloat(i + 1, (Float) value);			}
					else 								{	ps.setString(i + 1, (String) value);		}
				}
				
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						return handler.apply(rs);
					}
				}
			}
		}
		catch (ClassNotFoundException | SQLException e) {
			log.info(String.format("Database Error: %s", e.getMessage()));
			e.printStackTrace();
		}
		
		return null;
	}
	
	static public <T> T selectAll(String query, ArrayList<Object> valueList, SqlFunction<ResultSet, T> handler) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			try (Connection con = getDatabaseConnection(); PreparedStatement ps = con.prepareStatement(query)) {
				for (int i = 0; i < valueList.size(); i++) {
					Object value = valueList.get(i);
					
					if (value instanceof Timestamp)		{	ps.setTimestamp(i + 1, (Timestamp) value);	}
					else if (value instanceof Date)		{	ps.setDate(i + 1, (Date) value);			}
					else if (value instanceof Integer)	{	ps.setInt(i + 1, (Integer) value);			}
					else if (value instanceof Long)		{	ps.setLong(i + 1, (Long) value);			}
					else if (value instanceof Double)	{	ps.setDouble(i + 1, (Double) value);		}
					else if (value instanceof Float)	{	ps.setFloat(i + 1, (Float) value);			}
					else 								{	ps.setString(i + 1, (String) value);		}
				}
				
				try (ResultSet rs = ps.executeQuery()) {
					return handler.apply(rs);
				}
			}
		}
		catch (ClassNotFoundException | SQLException e) {
			log.info(String.format("Database Error: %s", e.getMessage()));
			e.printStackTrace();
		}
		
		return null;
	}
	
	static public Pair<Integer,String> insert(String query, ArrayList<Object> valueList) {
		int updateCount = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (Connection con = getDatabaseConnection(); PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				for (int i = 0; i < valueList.size(); i++) {
					Object value = valueList.get(i);
					
					if (value instanceof Timestamp)		{	ps.setTimestamp(i + 1, (Timestamp) value);	}
					else if (value instanceof Date)		{	ps.setDate(i + 1, (Date) value);			}
					else if (value instanceof Integer)	{	ps.setInt(i + 1, (Integer) value);			}
					else if (value instanceof Long)		{	ps.setLong(i + 1, (Long) value);			}
					else if (value instanceof Double)	{	ps.setDouble(i + 1, (Double) value);		}
					else if (value instanceof Float)	{	ps.setFloat(i + 1, (Float) value);			}
					else if (value instanceof Boolean)	{	ps.setBoolean(i + 1, (Boolean) value);		}
					else 								{	ps.setString(i + 1, (String) value);		}
				}
				
				updateCount = ps.executeUpdate();
				
				if (updateCount > 0) {
					try (ResultSet rs = ps.getGeneratedKeys()) {
						if (rs.next()) {
							return Pair.of(updateCount,rs.getString(1));
						}
					}
				}
			}
		}
		catch (ClassNotFoundException | SQLException e) {
			log.info(String.format("Database Error: %s", e.getMessage()));
			e.printStackTrace();
		}
		
		return Pair.of(updateCount, null);
	}
	
	static public Integer update(String query, ArrayList<Object> valueList) {
		int updateCount = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (Connection con = getDatabaseConnection(); PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				for (int i = 0; i < valueList.size(); i++) {
					Object value = valueList.get(i);
					
					if (value instanceof Timestamp)		{	ps.setTimestamp(i + 1, (Timestamp) value);	}
					else if (value instanceof Date)		{	ps.setDate(i + 1, (Date) value);			}
					else if (value instanceof Integer)	{	ps.setInt(i + 1, (Integer) value);			}
					else if (value instanceof Long)		{	ps.setLong(i + 1, (Long) value);			}
					else if (value instanceof Double)	{	ps.setDouble(i + 1, (Double) value);		}
					else if (value instanceof Float)	{	ps.setFloat(i + 1, (Float) value);			}
					else if (value instanceof Boolean)	{	ps.setBoolean(i + 1, (Boolean) value);		}
					else 								{	ps.setString(i + 1, (String) value);		}
				}
				
				updateCount = ps.executeUpdate();
			}
		}
		catch (ClassNotFoundException | SQLException e) {
			log.info(String.format("Database Error: %s", e.getMessage()));
			e.printStackTrace();
		}
		
		return updateCount;
	}
	
	@FunctionalInterface
	public interface SqlFunction<T, R> {
		R apply(T t) throws SQLException;
	}
}
