# projectDarkContinent
Giant mess of a code for a self-made tactical-RPG

For those who found this and wants to test it:
- this unfinished...let's call it demo, can be launched from src\net\clockworkgiant\gamebase\Launcher.java

Few additional notes, as of right now:
- folder res\Worlds contains file TestWorld.txt:
-- world is generated in square made of tiles;
-- first two numbers are sides of the world;
-- second two numbers are tiles at which first player character will spawn 
(as of right now, all other are hardcoded in src\net\clockworkgiant\entities\mob\Player.java)
--after that we have giant matrix which is represented by tile id's
--you can find all tiles at src\net\clockworkgiant\tiles (Tile.java contains tile id's)

- folder res\textures contains textures (placeholder textures are made by me, other were found on OpenGameArt and will be removed after I make proper substitute)
-- if you want to add your own textures, you must change some code in src\net\clockworkgiant\gfx\Assets.java
