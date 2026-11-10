import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import lombok.Builder;
import lombok.Getter;

import org.json.JSONObject;

@Builder
public class BannerEntry {
	@Getter int id;
	@Getter String netid;
	@Getter int type;
	@Getter String message;
	@Getter Boolean display;
	@Getter Date end;
	
	public static String[] TYPES = {"Danger (Red)","Warning (Yellow)","Info (Blue)"};
	
	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		
		json
			.put("id", id)
			.put("netid", netid)
			.put("type", type)
			.put("message", message)
			.put("display", display)
			.put("end", end);
		
		return json;
	}
	
	public static void create(String netid, int type, String message, Boolean display, Date end) {
		SqlQuery.insert(
				"INSERT INTO `BannerEntries` (`netid`,`type`,`message`,`display`,`end`) VALUES (?,?,?,?,?);",
				new ArrayList<>(Arrays.asList(netid, type, message, display, end))
			);
	}
	
	public static ArrayList<BannerEntry> getAll() {
		return SqlQuery.selectAll(
			"SELECT `id`,`netid`,`type`,`message`,`display`,`end` from `BannerEntries` ORDER BY `id` DESC",
			new ArrayList<>(),
			rs -> {
				ArrayList<BannerEntry> entryList = new ArrayList<BannerEntry>();
				while (rs.next()) {
					entryList.add(new BannerEntry(
							rs.getInt("id"),
							rs.getString("netid"),
							rs.getInt("type"),
							rs.getString("message"),
							rs.getBoolean("display"),
							rs.getDate("end")
						)
					);
				}
				return entryList;
			}
		);
	}
	
	public static ArrayList<BannerEntry> getAllActive() {
		return SqlQuery.selectAll(
			"SELECT `id`,`netid`,`type`,`message`,`display`,`end` from `BannerEntries` WHERE `display` = 1 AND (`end` > NOW() OR `end`) is NULL ORDER BY `end` DESC;",
			new ArrayList<>(),
			rs -> {
				ArrayList<BannerEntry> entryList = new ArrayList<BannerEntry>();
				while (rs.next()) {
					entryList.add(new BannerEntry(
							rs.getInt("id"),
							rs.getString("netid"),
							rs.getInt("type"),
							rs.getString("message"),
							rs.getBoolean("display"),
							rs.getDate("end")
						)
					);
				}
				return entryList;
			}
		);
	}
}
