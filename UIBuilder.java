package NFLDataApp;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class UIBuilder {

    public static VBox createPlayerSearchUI() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        // Text field for player name
        TextField searchField = new TextField();
        searchField.setPromptText("Enter Player Name");

        // Button for search
        Button searchButton = new Button("Search");

        // ImageView for player's headshot
        ImageView playerImageView = new ImageView();
        playerImageView.setFitHeight(100);
        playerImageView.setPreserveRatio(true);

        // Label for player's name
        Label playerNameLabel = new Label();
        playerNameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // TableView for player stats
        TableView<PlayerStat> statsTable = new TableView<>();
        setupPlayerStatsTable(statsTable);
        
        //TableView for player Total Stats
        TableView<PlayerStat> statsTotalTable = new TableView<>();
        setupPlayerStatsTotalTable(statsTotalTable);

        // Search button action
        searchButton.setOnAction(event -> {
            String playerName = searchField.getText();
            PlayerDAO dao = new PlayerDAO();

            // Retrieve individual game stats
            List<PlayerStat> stats = dao.getPlayerStatsByName(playerName);

            // Retrieve total stats
            List<PlayerStat> totalStats = dao.getPlayerStatTotal(playerName);

            if (!stats.isEmpty()) {
                PlayerStat firstStat = stats.get(0); // Assuming all stats belong to the same player
                playerNameLabel.setText(firstStat.getName());
                playerImageView.setImage(new Image(firstStat.getHeadshotURL(), true));
                statsTable.setItems(FXCollections.observableList(stats));
            }

            // Set total stats to the table
            if (!totalStats.isEmpty()) {
                statsTotalTable.setItems(FXCollections.observableList(totalStats));
            }
        });

        layout.getChildren().addAll(searchField, searchButton, playerNameLabel, playerImageView, statsTable, statsTotalTable);
        return layout;
    }

    // set up the player stat table
    private static void setupPlayerStatsTable(TableView<PlayerStat> table) {
        setupTableColumn(table, "Season", "season");
        setupTableColumn(table, "Week", "week");
        setupTableColumn(table, "Opponent Team", "opponent_team");
        setupTableColumn(table, "Completions", "completions");
        setupTableColumn(table, "Attempts", "attempts");
        setupTableColumn(table, "Passing Yards", "passing_yards");
        setupTableColumn(table, "Passing TDs", "passing_tds");
        setupTableColumn(table, "Interceptions", "interceptions");
        setupTableColumn(table, "Carries", "carries");
        setupTableColumn(table, "Rushing Yards", "rushing_yards");
        setupTableColumn(table, "Rushing TDs", "rushing_tds");
        setupTableColumn(table, "Receptions", "receptions");
        setupTableColumn(table, "Targets", "targets");
        setupTableColumn(table, "Receiving Yards", "receiving_yards");
        setupTableColumn(table, "Receiving TDs", "receiving_tds");
        setupTableColumn(table, "Target Share", "target_share");
    }
    // set up the player total stat table
    private static void setupPlayerStatsTotalTable(TableView<PlayerStat> table) {
        setupTableColumn(table, "Completions", "completions");
        setupTableColumn(table, "Attempts", "attempts");
        setupTableColumn(table, "Passing Yards", "passing_yards");
        setupTableColumn(table, "Passing TDs", "passing_tds");
        setupTableColumn(table, "Interceptions", "interceptions");
        setupTableColumn(table, "Carries", "carries");
        setupTableColumn(table, "Rushing Yards", "rushing_yards");
        setupTableColumn(table, "Rushing TDs", "rushing_tds");
        setupTableColumn(table, "Receptions", "receptions");
        setupTableColumn(table, "Targets", "targets");
        setupTableColumn(table, "Receiving Yards", "receiving_yards");
        setupTableColumn(table, "Receiving TDs", "receiving_tds");
        setupTableColumn(table, "Target Share", "target_share");
    }
    
    //create player info GUI section
    public static VBox createPlayerInfoUI() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        ComboBox<String> positionFilter = new ComboBox<>();
        positionFilter.getItems().addAll("All", "QB", "RB", "WR", "TE", "P");
        positionFilter.setValue("All");

        // TilePane for displaying player info
        TilePane playerInfoPane = new TilePane();
        playerInfoPane.setHgap(10);
        playerInfoPane.setVgap(10);
        playerInfoPane.setPrefColumns(3); // Adjust the number of columns as needed

        // Wrap TilePane in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(playerInfoPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);

        // Populate the player info initially
        updatePlayerInfo(playerInfoPane, "All");

        // Filter action for position
        positionFilter.setOnAction(event -> {
            String selectedPosition = positionFilter.getValue();
            updatePlayerInfo(playerInfoPane, selectedPosition);
        });

        layout.getChildren().addAll(positionFilter, scrollPane);
        return layout;
    }

    private static void updatePlayerInfo(TilePane playerInfoPane, String positionFilter) {
        playerInfoPane.getChildren().clear();
        PlayerDAO dao = new PlayerDAO();
        List<PlayerInfo> players = positionFilter.equals("All") ? dao.getAllPlayers() : dao.getPlayersByPosition(positionFilter);

        for (PlayerInfo player : players) {
            VBox playerBox = new VBox(5);
            Label nameLabel = new Label(player.getName());
            Label positionLabel = new Label(player.getPosition());
            playerBox.getChildren().addAll(nameLabel, positionLabel);
            playerInfoPane.getChildren().add(playerBox);
        }
    }
    
    // UI for fantasy team tab
    public static VBox createFantasyTeamUI() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        TextField searchField = new TextField();
        searchField.setPromptText("Enter Player Name");

        Button addButton = new Button("Add to Fantasy Team");
        Button removeButton = new Button("Remove from Fantasy Team");

        TableView<FantasyPlayer> fantasyTeamTable = new TableView<>();
        setupFantasyTeamTable(fantasyTeamTable);

        PlayerDAO dao = new PlayerDAO();

        addButton.setOnAction(event -> {
            String playerName = searchField.getText();
            dao.addPlayerToFantasyTeam(playerName);
            updateFantasyTeamTable(fantasyTeamTable, dao);
        });

        removeButton.setOnAction(event -> {
            String playerName = searchField.getText();
            dao.removePlayerFromFantasyTeam(playerName);
            updateFantasyTeamTable(fantasyTeamTable, dao);
        });

        updateFantasyTeamTable(fantasyTeamTable, dao);

        layout.getChildren().addAll(searchField, addButton, removeButton, fantasyTeamTable);
        return layout;
    }
    
    // Updates the fantasy table live
    private static void updateFantasyTeamTable(TableView<FantasyPlayer> table, PlayerDAO dao) {
        table.setItems(FXCollections.observableList(dao.getFantasyTeam()));
    }

    // sets up fantasy table in tab
    private static void setupFantasyTeamTable(TableView<FantasyPlayer> table) {
        TableColumn<FantasyPlayer, String> nameColumn = new TableColumn<>("Player Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<FantasyPlayer, Double> pointsColumn = new TableColumn<>("Total Fantasy Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("fantasy_points_ppr"));

        table.getColumns().add(nameColumn);
        table.getColumns().add(pointsColumn);
    }
    
    
    //table set up for tables
    private static <T> void setupTableColumn(TableView<PlayerStat> table, String columnName, String propertyName) {
        TableColumn<PlayerStat, T> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        table.getColumns().add(column);
    }
    
    // UI for leaderboard tab
    public static VBox createLeaderboardsUI() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        ComboBox<String> statsDropdown = new ComboBox<>();
        statsDropdown.getItems().addAll(
        	    "Completions", "Passing Yards", "Attempts", "Passing TDs", 
        	    "Interceptions", "Carries", "Rushing Yards", "Rushing TDs", 
        	    "Receptions", "Targets", "Receiving Yards", "Receiving TDs", "Target Share"
        	);

        TableView<PlayerStat> leaderboardsTable = new TableView<>();
        setupLeaderboardsTable(leaderboardsTable);

        statsDropdown.setOnAction(event -> {
            String selectedStat = statsDropdown.getValue();
            updateLeaderboardsTable(leaderboardsTable, selectedStat);
        });

        layout.getChildren().addAll(statsDropdown, leaderboardsTable);
        return layout;
    }

    //updates the leaderboards by statline
    private static void updateLeaderboardsTable(TableView<PlayerStat> table, String statName) {
        PlayerDAO dao = new PlayerDAO();
        List<PlayerStat> topPlayers = dao.getPlayersOrderedByStat(statName);
        table.setItems(FXCollections.observableList(topPlayers));
    }

    // to set up the table in the tab for leaderboards
    private static void setupLeaderboardsTable(TableView<PlayerStat> table) {
    	setupTableColumn(table, "Name", "name");
        setupTableColumn(table, "Completions", "completions");
        setupTableColumn(table, "Attempts", "attempts");
        setupTableColumn(table, "Passing Yards", "passing_yards");
        setupTableColumn(table, "Passing TDs", "passing_tds");
        setupTableColumn(table, "Interceptions", "interceptions");
        setupTableColumn(table, "Carries", "carries");
        setupTableColumn(table, "Rushing Yards", "rushing_yards");
        setupTableColumn(table, "Rushing TDs", "rushing_tds");
        setupTableColumn(table, "Receptions", "receptions");
        setupTableColumn(table, "Targets", "targets");
        setupTableColumn(table, "Receiving Yards", "receiving_yards");
        setupTableColumn(table, "Receiving TDs", "receiving_tds");
        setupTableColumn(table, "Target Share", "target_share");
    }
    
    


}
