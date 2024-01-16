package NFLDataApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    public List<PlayerStat> getPlayerStatsByName(String playerName) {
        List<PlayerStat> playerStats = new ArrayList<>();
        
        // SQL Query 1
        String sql = 
        		"SELECT players.name, players.HeadshotURL, player_stats.season, player_stats.week, player_stats.opponent_team, "
        		+ "player_stats.completions, player_stats.attempts, player_stats.passing_yards, player_stats.passing_tds, " 
                + "player_stats.interceptions, player_stats.carries, player_stats.rushing_yards, player_stats.rushing_tds, "
                + "player_stats.receptions, player_stats.targets, player_stats.receiving_yards, player_stats.receiving_tds, "
                + "player_stats.target_share "
                + "FROM players "
                + "JOIN player_stats ON players.player_id = player_stats.player_id "
                + "WHERE players.name = ? "
                + "ORDER BY player_stats.week ASC";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setString(1, playerName);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                PlayerStat stat = new PlayerStat(
                    rs.getString("name"),
                    rs.getString("HeadshotURL"),
                    rs.getInt("season"),
                    rs.getInt("week"),
                    rs.getString("opponent_team"),
                    rs.getInt("completions"),
                    rs.getInt("attempts"),
                    rs.getInt("passing_yards"),
                    rs.getInt("passing_tds"),
                    rs.getInt("interceptions"),
                    rs.getInt("carries"),
                    rs.getInt("rushing_yards"),
                    rs.getInt("rushing_tds"),
                    rs.getInt("receptions"),
                    rs.getInt("targets"),
                    rs.getInt("receiving_yards"),
                    rs.getInt("receiving_tds"),
                    rs.getFloat("target_share")
                );
                playerStats.add(stat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerStats;
    }
    
    // REUSE PLAYER STAT BUT SKIP OVER UNNEEDED INFORMATION 
    public List<PlayerStat> getPlayerStatTotal(String playerName) {
    	List<PlayerStat> playerStatTotals = new ArrayList<>();
    	
    	// SQL Query 2
    	String sql = 
    		    "SELECT players.name, SUM(player_stats.completions) AS completions, SUM(player_stats.attempts) AS attempts, " 
    		    + "SUM(player_stats.passing_yards) AS passing_yards, SUM(player_stats.passing_tds) AS passing_tds, "
    		    + "SUM(player_stats.interceptions) AS interceptions, SUM(player_stats.carries) AS carries, "
    		    + "SUM(player_stats.rushing_yards) AS rushing_yards, SUM(player_stats.rushing_tds) AS rushing_tds, "
    		    + "SUM(player_stats.receptions) AS receptions, SUM(player_stats.targets) AS targets, "
    		    + "SUM(player_stats.receiving_yards) AS receiving_yards, SUM(player_stats.receiving_tds) AS receiving_tds, "
    		    + "SUM(player_stats.target_share) AS target_share "
    		    + "FROM player_stats "
    		    + "JOIN players ON players.player_id = player_stats.player_id "
    		    + "WHERE players.name = ?";
    	
        try (Connection conn = DatabaseConnector.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
               
               statement.setString(1, playerName);
               ResultSet rs = statement.executeQuery();

               while (rs.next()) {
                   PlayerStat stat = new PlayerStat(
                	   rs.getString("name"),
                       rs.getInt("completions"),
                       rs.getInt("attempts"),
                       rs.getInt("passing_yards"),
                       rs.getInt("passing_tds"),
                       rs.getInt("interceptions"),
                       rs.getInt("carries"),
                       rs.getInt("rushing_yards"),
                       rs.getInt("rushing_tds"),
                       rs.getInt("receptions"),
                       rs.getInt("targets"),
                       rs.getInt("receiving_yards"),
                       rs.getInt("receiving_tds"),
                       rs.getFloat("target_share")
                   );
                   playerStatTotals.add(stat);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
           return playerStatTotals;
    }
    
    // get all players method to show the players on start for the players tab
    public List<PlayerInfo> getAllPlayers() {
        List<PlayerInfo> players = new ArrayList<>();
        
        // SQL Query 3
        String sql = "SELECT name, position, HeadshotURL FROM players";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PlayerInfo player = new PlayerInfo(
                    rs.getString("name"),
                    rs.getString("position")
                );
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    // method to filter players by position
	public List<PlayerInfo> getPlayersByPosition(String positionFilter) {
		List<PlayerInfo> players = new ArrayList<>();
		
		// SQL Query 4
		String sql = "SELECT name, position FROM players WHERE position = ?";
		
		try (Connection conn = DatabaseConnector.getConnection();
			     PreparedStatement statement = conn.prepareStatement(sql)) {

			    statement.setString(1, positionFilter); // Set the parameter here
			    ResultSet rs = statement.executeQuery(); // Now execute the query

			    while (rs.next()) {
			        PlayerInfo player = new PlayerInfo(
			            rs.getString("name"),
			            rs.getString("position")
			        );
			        players.add(player);
			    }
			} catch (SQLException e) {
			    e.printStackTrace();
			}
        return players;
       
	}
	
	// method to add players to fantasy team and insert into DB
	public void addPlayerToFantasyTeam(String playerName) {
	    
		// SQL Query 5
		String sql = "INSERT INTO fantasy_team (player_id, name, fantasy_points_ppr) " +
	                 "SELECT players.player_id, players.name, SUM(player_stats.fantasy_points_ppr) " +
	                 "FROM players " +
	                 "JOIN player_stats ON players.player_id = player_stats.player_id " +
	                 "WHERE players.name = ? " +
	                 "GROUP BY players.player_id;";

	    try (Connection conn = DatabaseConnector.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql)) {
	        
	        statement.setString(1, playerName);
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	// to remove players from fantasy team
	public void removePlayerFromFantasyTeam(String playerName) {
	    
		// SQL Query 6
		String sql = "DELETE FROM fantasy_team WHERE name = ?;";

	    try (Connection conn = DatabaseConnector.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql)) {
	        
	        statement.setString(1, playerName);
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// Shows fantasy team
	public List<FantasyPlayer> getFantasyTeam() {
	    List<FantasyPlayer> fantasyTeam = new ArrayList<>();
	    
	    // SQL Query 7
	    String sql = "SELECT players.player_id, players.name, SUM(player_stats.fantasy_points_ppr) AS total_fantasy_points " +
	                 "FROM fantasy_team " +
	                 "JOIN players ON fantasy_team.player_id = players.player_id " +
	                 "JOIN player_stats ON players.player_id = player_stats.player_id " +
	                 "GROUP BY players.player_id;";

	    try (Connection conn = DatabaseConnector.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql)) {
	        
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	            FantasyPlayer player = new FantasyPlayer(
	                rs.getString("name"),
	                rs.getDouble("total_fantasy_points")
	            );
	            fantasyTeam.add(player);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return fantasyTeam;
	}  
	
	// get player total filtered by totals for leaderboard
    public List<PlayerStat> getPlayersOrderedByStat(String stat) {
        List<PlayerStat> playerStats = new ArrayList<>();
        String orderByColumn = mapStatToColumn(stat);

        // SQL Query 8
        String sql = "SELECT players.name, " +
            "SUM(player_stats.completions) AS completions, " +
            "SUM(player_stats.attempts) AS attempts, " +
            "SUM(player_stats.passing_yards) AS passing_yards, " +
            "SUM(player_stats.passing_tds) AS passing_tds, " +
            "SUM(player_stats.interceptions) AS interceptions, " +
            "SUM(player_stats.carries) AS carries, " +
            "SUM(player_stats.rushing_yards) AS rushing_yards, " +
            "SUM(player_stats.rushing_tds) AS rushing_tds, " +
            "SUM(player_stats.receptions) AS receptions, " +
            "SUM(player_stats.targets) AS targets, " +
            "SUM(player_stats.receiving_yards) AS receiving_yards, " +
            "SUM(player_stats.receiving_tds) AS receiving_tds, " +
            "SUM(player_stats.target_share) AS target_share " +
            "FROM player_stats " +
            "JOIN players ON players.player_id = player_stats.player_id " +
            "GROUP BY players.player_id " +
            "ORDER BY " + orderByColumn + " DESC";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PlayerStat playerStat = new PlayerStat(
                		rs.getString("name"),
                		rs.getInt("completions"),
	                    rs.getInt("attempts"),
	                    rs.getInt("passing_yards"),
	                    rs.getInt("passing_tds"),
	                    rs.getInt("interceptions"),
	                    rs.getInt("carries"),
	                    rs.getInt("rushing_yards"),
	                    rs.getInt("rushing_tds"),
	                    rs.getInt("receptions"),
	                    rs.getInt("targets"),
	                    rs.getInt("receiving_yards"),
	                    rs.getInt("receiving_tds"),
	                    rs.getFloat("target_share")
	                   );
                playerStats.add(playerStat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerStats;
    }

    // to get the table to show the statistics
    private String mapStatToColumn(String stat) {
        switch(stat) {
            case "Completions": return "completions";
            case "Passing Yards": return "passing_yards";
            case "Attempts": return "attempts";
            case "Passing TDs": return "passing_tds";
            case "Interceptions": return "interceptions";
            case "Carries": return "carries";
            case "Rushing Yards": return "rushing_yards";
            case "Rushing TDs": return "rushing_tds";
            case "Receptions": return "receptions";
            case "Targets": return "targets";
            case "Receiving Yards": return "receiving_yards";
            case "Receiving TDs": return "receiving_tds";
            case "Target Share": return "target_share";
            default: throw new IllegalArgumentException("Invalid statistic name");
        }
    }

}
