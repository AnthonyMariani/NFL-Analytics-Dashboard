package NFLDataApp;

public class PlayerInfo {
	private String name;
	private String position;
	
	// Constructor
	public PlayerInfo(String name, String position) {
		this.name = name;
		this.position = position;
	}
	
	// Getters and Setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
}
