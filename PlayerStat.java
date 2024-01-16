package NFLDataApp;

public class PlayerStat {

	private String name;
	private String headshotURL;
	private int season;
	private int week;
    private String opponent_team;
    private int completions;
    private int attempts;
    private int passing_yards;
    private int passing_tds;
    private int interceptions;
    private int carries;
    private int rushing_yards;
    private int rushing_tds;
    private int receptions;
    private int targets;
    private int receiving_yards;
    private int receiving_tds;
    private float target_share;
    
    //Constructor for PlayerStat
    public PlayerStat(String name, String headshotURL, int season, int week, String opponent_team,
    		int completions, int attempts, int passing_yards, int passing_tds, int interceptions, int carries, 
    		int rushing_yards, int rushing_tds, int receptions, int targets, int receiving_yards,
    		int receiving_tds, float target_share) {
    	this.name = name;
    	this.headshotURL = headshotURL;
    	this.season = season;
    	this.week = week;
    	this.opponent_team = opponent_team;
    	this.completions = completions;
    	this.attempts = attempts;
    	this.passing_yards = passing_yards;
    	this.passing_tds = passing_tds;
    	this.interceptions = interceptions;
    	this.carries = carries;
    	this.rushing_yards = rushing_yards;
    	this.rushing_tds = rushing_tds;
    	this.receptions = receptions;
    	this.targets = targets;
    	this.receiving_yards = receiving_yards;
    	this.receiving_tds = receiving_tds;
    	this.target_share = target_share;
    }
    
    //Constructor for PlayerStatTotals
    public PlayerStat(String name,
    		int completions, int attempts, int passing_yards, int passing_tds, int interceptions, int carries, 
    		int rushing_yards, int rushing_tds, int receptions, int targets, int receiving_yards,
    		int receiving_tds, float target_share) {
    	this.name = name;
    	this.headshotURL = " ";
    	this.season = 0;
    	this.week = 0;
    	this.opponent_team = " ";
    	this.completions = completions;
    	this.attempts = attempts;
    	this.passing_yards = passing_yards;
    	this.passing_tds = passing_tds;
    	this.interceptions = interceptions;
    	this.carries = carries;
    	this.rushing_yards = rushing_yards;
    	this.rushing_tds = rushing_tds;
    	this.receptions = receptions;
    	this.targets = targets;
    	this.receiving_yards = receiving_yards;
    	this.receiving_tds = receiving_tds;
    	this.target_share = target_share;
    }
    
    //Getters and Setters
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getHeadshotURL() {
    	return headshotURL;
    }
    
    public void setHeadshotURL(String headshotURL) {
    	this.headshotURL = headshotURL;
    }
    
    public int getSeason() {
    	return season;
    }
    
    public void setSeason(int season) {
    	this.season = season;
    }
    
    public int getWeek() {
    	return week;
    }
    
    public void setWeek(int week) {
    	this.week = week;
    }
    
    public String getOpponent_team() {
        return opponent_team;
    }

    public void setOpponent_team(String opponent_team) {
        this.opponent_team = opponent_team;
    }

    public int getCompletions() {
        return completions;
    }

    public void setCompletions(int completions) {
        this.completions = completions;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getPassing_yards() {
        return passing_yards;
    }

    public void setPassing_yards(int passing_yards) {
        this.passing_yards = passing_yards;
    }

    public int getPassing_tds() {
        return passing_tds;
    }

    public void setPassing_tds(int passing_tds) {
        this.passing_tds = passing_tds;
    }

    public int getInterceptions() {
        return interceptions;
    }

    public void setInterceptions(int interceptions) {
        this.interceptions = interceptions;
    }

    public int getCarries() {
        return carries;
    }

    public void setCarries(int carries) {
        this.carries = carries;
    }

    public int getRushing_yards() {
        return rushing_yards;
    }

    public void setRushing_yards(int rushing_yards) {
        this.rushing_yards = rushing_yards;
    }

    public int getRushing_tds() {
        return rushing_tds;
    }

    public void setRushing_tds(int rushing_tds) {
        this.rushing_tds = rushing_tds;
    }

    public int getReceptions() {
        return receptions;
    }

    public void setReceptions(int receptions) {
        this.receptions = receptions;
    }

    public int getTargets() {
        return targets;
    }

    public void setTargets(int targets) {
        this.targets = targets;
    }

    public int getReceiving_yards() {
        return receiving_yards;
    }

    public void setReceiving_yards(int receiving_yards) {
        this.receiving_yards = receiving_yards;
    }

    public int getReceiving_tds() {
        return receiving_tds;
    }

    public void setReceiving_tds(int receiving_tds) {
        this.receiving_tds = receiving_tds;
    }

    public float getTarget_share() {
        return target_share;
    }

    public void setTarget_share(float target_share) {
        this.target_share = target_share;
    }
}
