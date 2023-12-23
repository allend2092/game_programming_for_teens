;INVADERS!!!
;Developed by Maneesh Sethi on 7/22/02
;A Space invaders clone


;Set the graphics mode to 640x480
Graphics 640,480 

;Set up backbuffer and AutoMidHandle
SetBuffer BackBuffer() 
AutoMidHandle True

;Set up random generator
SeedRnd MilliSecs() 

;IMAGES
;Player's images
;These images are related to the player
Global playerimage = LoadAnimImage("player.bmp",35,32,0,13)   ;The player's appearance on screen
Global bulletplayerimage = LoadAnimImage("playerbullet.bmp",40,19,0,10)    ;The player's bullet
Global playerexplosionimage = LoadAnimImage("playerexplosion.bmp",64,64,0,8)

;Enemies' image
;These images are related to the enemy
Global enemyimage = LoadAnimImage("enemy.bmp",68,38, 0, 10) ;The enemies' appearance on screen
Global bulletenemyimage = LoadAnimImage("enemybullet.bmp",40,19,0,5) ;The enemies' bullet
Global enemyexplosionimage = LoadAnimImage("enemyexplosion.bmp",64,64,0,8)

;Miscellaneous images
Global healthimage = LoadAnimImage("health.bmp",33,21,0,10)    ;the health image
Global backgroundimage = LoadImage("stars.bmp") ;the background image
splashscreenimage = LoadImage("splashscreen.bmp")   ;the splash screen



;SOUNDS/MUSIC
;Sounds
Global explosionsound = LoadSound("explode.wav")
Global bulletsound = LoadSound("zing.wav")

;Play background music
backgroundmusic = PlayMusic ("Interim Nation - Human Update.mp3")

;CONSTANTS

;Keycodes
Const ESCKEY = 1, SPACEBAR = 57, LEFTKEY = 203, RIGHTKEY = 205 

;The number of milliseconds between enemy direction changes
Const CHANGEENEMYDIRECTION = 700 

;Number of milliseconds between enemy bullet fires
Const TIMEBETWEENENEMYBULLETS = 1200

;GLOBALS
;Create global variables that will be used through the program
Global numofenemies  ;the amount of enemies

Global directiontime ;counter between enemy direction changes

Global shotsfiredtime ;counter between enemy shots 

Global enemyhits ;How many times player has hit enemy ships

Global shotsfired ;How many shots player has fired

Global playerdamage ;How many times player has been hit

Global playerscore   ;The player's current score

Global scrolly   ;the scrolling variable for background

Global healthframe  ;the variable that keeps track of the spinning health box


;TYPES

;The ship type: enemies are ships
Type ship           
	Field x,y  ;The x and y coords
	Field hits  ;ship's hitpoints
	Field xv,yv  ;the x and y direction velocities
	Field frame ;the frame of the ship's image
End Type



;The user type: the player is a user
Type user
	Field x,y   ;the player's coordinates
	Field hits  ;the player's hitpoints
	Field frame ;the current frame of the player
	Field draw  ;1 if player should be drawn, 0 if he shouldn't
End Type

;Set up player for beginning of game
Global player.user = New user  ;Create a new player ship
player\x = 640/2 ;Player is in the middle of scren
player\y = 440	;Player is near the bottom
player\hits = 3
player\draw = 1


;The bullet type: describes both player and enemy bullets
Type bullet           
	Field x,y   ;the bullet's coordinates
	Field draw ;draw is 1 if the bullet should be drawn, 0 if it should be deleted
	Field from  ;if from is 1, bullet is from player, if 2, its from enemy
	Field frame   ;the frame of the image. There are 10 frames for player's bullet and 5 for enemy's
End Type

;The explosion type: describes explosions for players and enemies
Type explosion
	Field x,y    ;the x and y coordinate position
	Field from   ;1 = player, 2 = enemy
	Field frame  ;the frame of the image
End Type

;Set the level the player is on: beginning with level 1
level = 1 

;;;;;;;;;;;;;;;;;;;
;MAIN PROGRAM (first function)
;Only initializes players and calls other functions (utilizes input as well)
;;;;;;;;;;;;;;;;;;;
;Make the sound of the bullet fire not very loud
;It will be at 25% volume
SoundVolume bulletsound, .25

;Display the splash screen for 7 seconds
DrawImage(splashscreenimage, 320,240)
Flip
Delay 7000


;Make the time of enemy direction changes and enemy bullets equal to the time
directiontime = shotsfiredtime = MilliSecs()

;Set up level
InitializeLevel(level)


;MAIN LOOP

While Not KeyDown(ESCKEY) 

	;Draw the background at its position
	TileBlock backgroundImage,0,scrolly
	
	;Scroll the background a little
	scrolly=scrolly + 1
	
	;If scrolly is too big, reset it
	If scrolly=ImageHeight(backgroundImage) Then
		scrolly=0
	EndIf

	
	;If there are no enemies left on screen, player has beaten level. Thus, begin next level.
	If numofenemies = 0 Then
		
		;Make the player's level increment by one
		level = level + 1
		
		;Initalize the new level
		InitializeLevel(level)
	EndIf
	
	;If the space bar is hit
	If KeyHit(SPACEBAR) 
			
			;Create a new bullet at the player's position ('1' signifies that the bullet is from the player, and not an enemy)
		    CreateBullet(player\x,player\y , 1) 
		
			;Increment the variable that holds how many bullets have been fired
			shotsfired = shotsfired + 1
			;play bullet sound
			PlaySound(bulletsound)
		EndIf
		

	;If left key is pressed, move player 10 pixels left
	If KeyDown(LEFTKEY)
		
		player\x = player\x - 10
		
		
		;If player moves offscreen, keep him onscreen
		If player\x <= 0
			player\x = 10
		EndIf

		;tilt player left
		player\frame = player\frame - 1
		
		;dont let frame get too low
		If player\frame <= 0
			player\frame = 0
		EndIf
	EndIf
	
	;If right key is pressed, move player right 10 pixel
	If KeyDown(RIGHTKEY)
		player\x = player\x + 10
	
		;if player moves off screen, keep him on screen
		If player\x >= 610
			player\x = 610
		EndIf
		
		
		;tilt player right
		player\frame = player\frame + 1
		
		;dont let frame get too high
		If player\frame >= 12
			player\frame = 12
		EndIf
	EndIf
	
	;Begin resetting the player's frame
	If (Not KeyDown(RIGHTKEY)) And (Not KeyDown(LEFTKEY))
		If player\frame < 7
			player\frame = player\frame + 1
		ElseIf player\frame > 7
			player\frame = player\frame - 1
		EndIf
	EndIf 
	;Call the necessary user-defined functions
	;Draw the information the player needs to see
	DrawHUD()
	;Draw all of the ships, player and enemies
	DrawShips()
	;Update enemy's movement and bullet fire
	EnemyAI()
	;Move all of the bullets, and delete uncessary one
	UpdateBullets() 
	;update all existing explosions
	UpdateExplosions()

	Flip
	
	;hold on for a fraction of a second
	Delay 25
Wend ;End of While Loop

GameOver()
;;;;;;;;;;;;;;;;;;;
;END OF MAIN PROGRAM
;
;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;
;CreateBullet(x,y)
;This function takes the x and y coords of the bullet and creates it
;;;;;;;;;;;;;;;;;;;
Function CreateBullet(x,y, from)

 
	;Create a new bullet
	bullets.bullet = New bullet 
	
	;set the x and y coordinates to the position of the ship that fired it
	bullets\x = x
	bullets\y = y  
	
	;Make sure the bullet should be drawable
	bullets\draw = 1 
	
	;Tell where the bullet originated from: a player or an enemy
	bullets\from = from 



End Function ;End CreateBullet


;;;;;;;;;;;;;;;;;;;
;UpdateBullets()
;Updates each bullet and deletes them if they are no longer on the screen
;;;;;;;;;;;;;;;;;;;

Function UpdateBullets()


;Check every bullet. Depending on who fired it, move it in the corect direction. Then, draw the bullet. 
;Finally, check and see if the bullets have hit any other ships
For i.bullet = Each bullet ;Check every bullet
	
	;If shot by player, move bullet up
	If i\from = 1 
		i\y = i\y - 10 
	
	;If shot by player, move bullet down
	Else 
		i\y = i\y + 10 ;move bullet down 
	EndIf
			
	;if bullet should be drawn (its on screen), draw it with the proper color
	If i\draw = 1 
		
		;If its from player, draw player bullet
		If i\from = 1
			DrawImage (bulletPlayerImage, i\x, i\y, i\frame) 
		
		;If its from enemy, draw enemy bullet
		Else
			DrawImage (bulletEnemyImage, i\x, i\y, i\frame) 
		EndIf
	EndIf
	
	
	
	
	;We now test to see if the bullets have hit any ships
	
	;Test the players bullets if it hit an enemy
	If i\from = 1 
	
	;For each enemy, if bullet has collided, subtract a hitpoint
	For e.ship = Each ship ;check each enemy
		
		;If the bullet has hit the enemy, remove one hitpoint
		If ImagesCollide(bulletplayerimage, i\x,i\y,0, enemyimage, e\x, e\y, 0) 
			
			;Subtract a hitpoin
			e\hits = e\hits - 1 
		
		
			;increment the number of times an enemy has been hit by player
			enemyhits = enemyhits + 1
			
			;if the ship is out of hitpoints, remove the enemy
			If e\hits <= 0
			
				numofenemies = numofenemies - 1
				;create an explosion with enemy's coordinates. "2" signifies it is from enemy. not player.
				CreateExplosion(e\x,e\y,2)
			
				;destroy the enemy ship
				Delete e 
				;play explosion sound
				PlaySound(explosionsound)
				

			EndIf 
		
		;Make sure the bullet is not drawn (it will be deleted next frame)
		i\draw = 0
		EndIf 
		
		
	Next   ;check next enemy ship
	
	EndIf 
	
	;check if the bullet is shot by enemy, check if it hit player
	;don't check if the player has already exploded
	If i\from = 2 And playerexplosion = 0
		;if the bullet hit the player, decrease hitpoints and add damage
		If ImagesCollide(bulletenemyimage,i\x, i\y, 0, playerimage, player\x, player\y, 0)
			
			;decrease a hitpoint
			player\hits = player\hits - 1
		

			;If player is out of hitpoints, call gameover()
			If player\hits <= 0 
				;play explosion sound
				PlaySound(explosionsound)
				CreateExplosion(player\x,player\y,1)
				;don't draw player anymore
				player\draw = 0
			EndIf
		;Make sure the bullet is not drawn anymore
		i\draw = 0
		
		EndIf 
		
	EndIf
	
		
		;If from player, draw player explosion
	If player\hits <= 0
	
		For ex.explosion = Each explosion
			If ex\from = 1 And ex\frame >= 7
	
				GameOver()
	
			EndIf
		Next	
	EndIf


	;increment frame
	i\frame = i\frame + 1
	;if from player, there are 10 frames: dont let frame get too large
	If i\from = 1 And i\frame >= 10
		i\frame = 0
	;if from enemy there are 5 franes: don't let frame get too large
	ElseIf i\from = 2 And i\frame >=5
		i\frame = 0
	EndIf
		
	;If the bullet is offscreen or if it hit a ship, delete it	
	If i\y < 0 Or i\draw = 0
		Delete i 
	EndIf 

	

Next ;Go to the next bullet

End Function

;;;;;;;;;;;;;;;;;;;
;InitializeLevel(level)
;Sets up parameters for the current level
;;;;;;;;;;;;;;;;;;;

Function InitializeLevel(level)


;Delete each and every bullet
For i.bullet = Each bullet
	Delete i
Next


;Display intro text
Text 230,240,"NOW ENTERING Level " + level
Flip

;Delay for a few seconds
Delay 2000


;Reset player's hitcounter
player\hits = 3

;reset player's frame
player\frame = 7   ;(7 is the middle frame)

;Make the total number of enemies a value between 3 and 5 + the current level
numofenemies = level + Rand(3 , 5)    ;Level 1 will have 4-6 enemies, 2 will have 5-7, etc.

;reset health boxes' frame
healthframe = 0


;create the required number of enemies
For e=1 To numofenemies

	CreateNewEnemy(level) 
Next
	
	
End Function 
;;;;;;;;;;;;;;;;;;;
;CreateNewEnemy(level)
;Create a new enemy
;;;;;;;;;;;;;;;;;;;

Function CreateNewEnemy(level)

;Create a new enemy ship
enemy.ship = New ship

;release point determines if enemy will be released from top, left, or bottom of screen
releasepoint = Rand(1,4) 

;25% of enemies will be unleashed from left\right, 50% from top
Select releasepoint

	Case 1   ;enemy released from left side
		
		;set up enemy's coordinates
		enemy\x = 5
		enemy\y = Rand(0 , 420)   ;Enemy is released on a random point on the left side
		
		;set up random velocities
		enemy\xv = Rand(1 , 2)
		enemy\yv = Rand(-2,2)
		
	Case 2   ;enemy released from top
		;set up enemy's coorinates
		enemy\x = Rand(0,640)    ;Enemy is released on a random point on the top
		enemy\y = 5
		
		;set up random velocities
		enemy\xv = Rand(-2, 2)
		enemy\yv = Rand(1,2)
		
	Case 3 ;enemy released from top
		;set up enemy coordinates
		enemy\x = Rand(0,640)   ;Enemy is released on a random point on the top
		enemy\y = 5
		
		;Set up random velocities
		enemy\xv = Rand(-2, 2)
		enemy\yv = Rand(1,2)
		
		
	Case 4 ;enemy released from right side
		;set up enemy coordinates
		enemy\x = 620
		enemy\y = Rand(0 , 420) ;Enemy is released on a random point on the right side
		
		;set up random velocities
		enemy\xv = Rand(-2, -1)
		enemy\yv = Rand(-2,2)
		
		
	Default ;if there is a major error
		Print "ERROR, Enemy unleashed in a nonexistant place!"
End Select

enemy\hits = level
End Function
		


;;;;;;;;;;;;;;;;;;;
;DrawShips()
;Draw Players and enemies
;;;;;;;;;;;;;;;;;;;
Function DrawShips()

;Draw each ship, enemy and player
;For each enemy
For i.ship = Each ship   
	DrawImage(enemyImage, i\x, i\y, i\frame)
Next
;For each player
For j.user = Each user 
	;if user should be drawn
	If j\draw = 1
		DrawImage(playerImage, j\x, j\y, j\frame)
	EndIf
Next

End Function 


;;;;;;;;;;;;;;;;;;;
;EnemyAI()
;Update the enemies' stats and determine their movements
;;;;;;;;;;;;;;;;;;;
Function EnemyAI()


;Check every enemy ship. Determine if they should change their velocities, make sure they dont hit any walls, and fire their bullets
For i.ship = Each ship 

	;if the directiontime timer has gone through, change the velocity of the ship
		If directiontime <= MilliSecs() - CHANGEENEMYDIRECTION 
		i\xv = i\xv+Rand(-2,2)   ;nudge the x velocity
		i\yv = i\yv+Rand(-2,2)   ;nude the y velocity
		
		;Reset the direction reset timer after the later
		resetdirectiontime = 1 
	EndIf
	
	;If x velocity is too large, lower it
	If ixv > 5 Then
	   i\xv = i\xv - 2
	EndIf
	
	;If y velocity is too large, lower it
	If i\yv > 5 Then
		i\yv = i\yv - 2
	EndIf
	
	;If x velocity is too low, raise it
	If ixv < -5 Then
	   i\xv = i\xv + 2
	EndIf
	
	;If y velocity is too low, lower it
	If i\yv < -5 Then
		i\yv = i\yv +2
	EndIf

	;Update ship's velocities
	i\x = i\x + i\xv 
	i\y = i\y + i\yv
	
	;Check walls with the ship
	
	;If ship hits left wall, reverse its velocity
	If i\x <= 0 
		i\x = 5    ;keep it on screen
		i\xv = -i\xv    ;fix velocity
		
	;If ship hits right wall, reverse its velocity
	Else If i\x >=620
		i\x = 615    ;keep it on screen
		i\xv = -i\xv   ;fix its velocity
	
	;If ship hits upper wall, reverse its velocity
	Else If i\y <= 0  
		i\y = 5    ;keep it on screen
		i\yv = -i\yv  ;fix its velocity
	
	;If ship hits lower wall, reverse its velocity
	Else If i\y >= 410 
		i\y = 405   ;keep it on screen
		i\yv = -i\yv  ;fix its velocity
	End If
	
	;If it is time for the enemy to fire another bullet, fire the bullet
	If MilliSecs() >= shotsfiredtime + TIMEBETWEENENEMYBULLETS
		
		;Create the new bullet
		CreateBullet(i\x,i\y,2)

		;reset the bullet fire timer
		resetshotstime = 1
	EndIf
	
	;Update ship's frame
	i\frame = i\frame + 1

	;make sure frame isnt too large
	If i\frame >= 9
		i\frame = 0
	EndIf


Next ;next ship

;If the directiontime counter should be reset, reset it
If resetdirectiontime Then 
	directiontime = MilliSecs() 
	resetdirectiontime = 0
EndIf

;If the shotstime counter should be reset, reset it
If resetshotstime Then
	shotsfiredtime = MilliSecs()
	resetshotstime = 0
EndIf


End Function


;;;;;;;;;;;;;;;;;;;
;DrawHUD()
;Draws the HUD - the displays, health, etc.
;;;;;;;;;;;;;;;;;;;

Function DrawHUD()

;increment health's frame
healthframe = healthframe + 1

;don't let health frame get too large
If healthframe >=10
	healthframe = 0
EndIf

;Draw as many hitpoints on the HUD as hitpoints the player has remaining
For i=1 To player\hits

	;Draw healthimage in topleft
	DrawImage healthimage,i*50 - 35,10,healthframe

Next

;Display topright info
Text 510, 10, "Enemy Hits:  " + enemyhits 
Text 510, 30, "Shots Fired: " + shotsfired

End Function

;;;;;;;;;;;;;;;;;;;
;CreateExplosions
;Creates an explosion. First parameter is x coordinate, second is y coord, and thrid is 1 if  the explosion is from a player, and 2 if from an enemy
;;;;;;;;;;;;;;;;;;;

Function CreateExplosion(x,y,from)

ex.explosion = New explosion
ex\x = x
ex\y = y
ex\from = from
ex\frame = 0

End Function

;;;;;;;;;;;;;;;;;;;
;UpdateExposions()
;Updates all of the enemy explosions
;;;;;;;;;;;;;;;;;;;

Function UpdateExplosions()

 
For ex.explosion = Each explosion
;If explosion is from enemy, draw it and update it


	DrawImage enemyexplosionimage,ex\x,ex\y,ex\frame


	;Update explosion
	ex\frame = ex\frame + 1

	;if the enemy has exploded, delete the explosion
	;Don't delete player explosions, we need it to detect when the player has dies
	If ex\from = 2
	If ex\frame>=7	
		Delete ex
	EndIf
	EndIf
Next
End Function






;;;;;;;;;;;;;;;;;;;
;GameOver()
;Deletes every object, quits game
;;;;;;;;;;;;;;;;;;;

Function GameOver()




Text 320,270, "Press Any Key To Quit.",True,True
Flip

;Delay for 2 seconds
Delay 2000

;Clear the key buffer
FlushKeys

;Wait for user to press something
WaitKey()

;Delete all the bullets
For i.bullet = Each bullet 
	Delete i
Next

;Delete all the enemy ships
For e.ship = Each ship 
	Delete e
Next

;Delete the player
For u.user = Each user ;Delete player
	Delete u
Next

;End the game
End
End Function