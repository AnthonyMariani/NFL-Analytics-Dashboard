# NFL-Analytics-Dashboard
The NFL Analytics Dashboard is an innovative tool designed to distill comprehensive NFL player statistics from a CSV-sourced SQL database into user-friendly visual data, facilitating easy access to player performance metrics, fantasy football management, and in-depth statistical analysis through a sleek JavaFX interface.

#### PlayerDAO.java
The data access object class to retrieve and store data from database to find relevant statistics to display.

#### NFLData.sql
Creates a database from player_stats_2023.csv to be used in application.

#### MainApp.java
Orchestrates the flow of the program, initializing the user interface, and integrating various components of the application together.

#### UIBuilder.java
Class to handles all UI elements to make user-friendly interface.

#### FantasyPlayer.java | PlayerInfo.java | PlayerStat.java | PlayerStatTotal.java
These classes function as data transfer objects Representing the database tables.

## Navigating the Application:
● The first tab allows you to search by any player in the database and reveal information about them. For example, if you wanted to look at the season stats for Joe Burrow, you would type in his name, click search, and it would come up with his full stats for every game in the 2023 season and his season total stats. If you are unsure about the names of players, the next section is made to help you

● The next section is all of the players in the database and their position in the league. You may filter by which position you would like to look at by using the drop down menu at the top of the tab. You may use this to narrow down the players, and find the player you would like to look at.

● The third tab allows you to look at all of the season leaders in certain statistics across the NFL 2023 season. There is a drop down menu with all sorts of statistics and whichever one you choose the database will be filtered to show the leaders of that statistic at the top and descends down the database. You may change the stat and it will update to show the leader in the next statistic you choose.

● The final section of this application will allow you to save players that you may have on your fantasy team, or save players that you may want to add to your fantasy team right within the tab. You type in a player name, and then either click add or remove player depending on what you are trying to accomplish. For each player in the fantasy team, you can see their name and total fantasy PPR points on the season.
