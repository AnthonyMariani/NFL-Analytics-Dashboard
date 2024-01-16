package NFLDataApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
    	TabPane mainTabPane = new TabPane();
	
	    // Player Search and Profile Display Tab
		Tab playerStatSearchTab = new Tab("Player Search");
	    playerStatSearchTab.setContent(UIBuilder.createPlayerSearchUI()); 
	    playerStatSearchTab.setClosable(false);
	
	    // Player Info Tab
	    Tab playerInfoTab = new Tab("All Players");
	    playerInfoTab.setContent(UIBuilder.createPlayerInfoUI());
	    playerInfoTab.setClosable(false);
	    
	    // fantasy tab
	    Tab fantasyTab = new Tab("Fantasy Team");
	    fantasyTab.setContent(UIBuilder.createFantasyTeamUI());
	    fantasyTab.setClosable(false);
	    
	    // leaderboards tab
	    Tab leaderboardsTab = new Tab("Leaderboards");
	    leaderboardsTab.setContent(UIBuilder.createLeaderboardsUI());
	    leaderboardsTab.setClosable(false);
	
	    // add tabs
	    mainTabPane.getTabs().addAll(
	        playerStatSearchTab, playerInfoTab,leaderboardsTab, fantasyTab
	    );
	
	    Scene scene = new Scene(mainTabPane, 800, 600);
	    primaryStage.setTitle("NFL Player Data Application");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

    public static void main(String[] args) {
    	launch(args);
    }
}

