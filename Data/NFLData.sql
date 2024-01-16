-- Create a new database
CREATE DATABASE IF NOT EXISTS NFLData;
USE NFLData;

-- create player_stats table
CREATE TABLE IF NOT EXISTS player_stats (
    stat_id INT AUTO_INCREMENT PRIMARY KEY,
    player_id VARCHAR(10) NOT NULL,
	season YEAR,
    week INT,
    season_type VARCHAR(50),
	recent_team VARCHAR(50),
    opponent_team VARCHAR(50),
    completions INT,
    attempts INT,
    passing_yards INT,
    passing_tds INT,
    interceptions INT,
    carries INT,
    rushing_yards INT,
    rushing_tds INT,
    receptions INT,
    targets INT,
    receiving_yards INT,
    receiving_tds INT,
    target_share FLOAT,
    fantasy_points_ppr DECIMAL(10,2)
);

-- Create players table
CREATE TABLE IF NOT EXISTS players (
    player_id VARCHAR(10) NOT NULL,
    name VARCHAR(50),
    position VARCHAR(10),
    HeadshotURL VARCHAR(255),
    PRIMARY KEY (player_id)
);

CREATE TABLE IF NOT EXISTS fantasy_team (
	player_id VARCHAR(10) NOT NULL,
    name VARCHAR(50),
    fantasy_points_ppr DOUBLE
);

DROP TEMPORARY TABLE IF EXISTS TempPlayerStats;

-- Create temporary table to store all CSV data
CREATE TEMPORARY TABLE IF NOT EXISTS TempPlayerStats (
    player_id VARCHAR(10) NOT NULL,
    player_name VARCHAR(50),
    position VARCHAR(10),
    HeadshotURL VARCHAR(255),
    recent_team VARCHAR(50),
    season YEAR,
    week INT,
    season_type VARCHAR(50),
    opponent_team VARCHAR(50),
    completions INT,
    attempts INT,
    passing_yards INT,
    passing_tds INT,
    interceptions INT,
    carries INT,
    rushing_yards INT,
    rushing_tds INT,
    receptions INT,
    targets INT,
    receiving_yards INT,
    receiving_tds INT,
    target_share FLOAT,
    fantasy_points_ppr FLOAT
);

-- Load data into the temporary table
-- **REPLACE PATH WITH YOUR PATH TO PLAYER STATS FILE**
LOAD DATA LOCAL INFILE '/Users/anthonymariani/desktop/player_stats_2023.csv'
INTO TABLE TempPlayerStats
FIELDS TERMINATED BY ',' 
OPTIONALLY ENCLOSED BY '"' 
LINES TERMINATED BY '\n' 
IGNORE 1 ROWS
(
    player_id, @omit1, player_name, position, @omit2, HeadshotURL, recent_team, 
    season, week, season_type, opponent_team, completions, attempts, 
    passing_yards, passing_tds, interceptions, 
    @omit3, @omit4, @omit5, @omit6, @omit7, @omit8, @omit9, @omit10, @omit11,
    @omit12, @omit13, carries , rushing_yards, rushing_tds, 
    @omit14, @omit15, @omit16, @omit17, @omit18, receptions, targets, 
    receiving_yards, receiving_tds, @omit19, @omit20, @omit21, @omit22, 
    @omit23, @omit24, @omit25, @omit26, target_share,
    @omit27, @omit28, @omit29, @omit30, fantasy_points_ppr
);

-- Put data into player_stats table from temp table
INSERT INTO player_stats (player_id, season, season_type, week, recent_team, opponent_team, completions, attempts, passing_yards, passing_tds, interceptions, carries, rushing_yards, rushing_tds, receptions, targets, receiving_yards, receiving_tds, target_share, fantasy_points_ppr)
SELECT player_id, season, season_type, week, recent_team, opponent_team, completions, attempts, passing_yards, passing_tds, interceptions, carries, rushing_yards, rushing_tds, receptions, targets, receiving_yards, receiving_tds, target_share, fantasy_points_ppr
FROM TempPlayerStats;

-- put players data into players table
INSERT INTO players (player_id, name, position, HeadShotURL)
SELECT DISTINCT player_id, player_name, position, HeadShotURL
FROM TempPlayerStats;

-- Now that players is populated, make player_id in player_stats reference players table
ALTER TABLE player_stats
ADD FOREIGN KEY (player_id) REFERENCES players(player_id);

ALTER TABLE fantasy_team
ADD FOREIGN KEY (player_id) REFERENCES players(player_id);
