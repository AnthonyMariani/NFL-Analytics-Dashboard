package NFLDataApp;

public class FantasyPlayer {
	private String name;
	private double fantasy_points_ppr;
	
	public FantasyPlayer(String name, double fantasy_points_ppr) {
		this.name = name;
		this.fantasy_points_ppr = fantasy_points_ppr;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getFantasy_points_ppr() {
		return fantasy_points_ppr;
	}
	
	public void setFantasy_points_ppr(double fantasy_points_ppr) {
		this.fantasy_points_ppr = fantasy_points_ppr;
	}
}
