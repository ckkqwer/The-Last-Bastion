# The Last Bastion
An android game develop in android studio using Java.\

Introduction
=
Game Story
-
The game is called The Last Bastion. A lone cannon is all it has left to defend the village from the skeleton army. Defend the village until the very end!

Gameplay
-
Defeat an enemy to gain 1 score, your goal is to gain as many scores as possible before an enemy breach through the defense line. One enemy to pass the defensive line is all it takes to defeat and enemies will spawn infinitely.
Time will be counted at the bottom right on the screen, every 600 the level will increase by one, maximum is 4, and the speed of the enemy will increase as follows.

Mobile Game Multimedia Design
=
Audio
-
Four mp3 files were used as audio resources.
1.	music.mp3
	- As background music when the game started.
 	-	Stop when the game is paused or game over.
 	-	Will resume at the stopping point if paused, restart from the beginning if restarted.
 	-	Is looping
2.	hurt.mp3
	-	Play one time when a projectile hits an enemy.
3.	shoot.mp3
	-	Play one time when cannon fires.
4.	nioce.mp4
	-	Play one time when the game is over.

Sprite
-
> The cannon
![image](https://user-images.githubusercontent.com/61373385/161753265-d12c9dbb-e8f0-4577-9a6a-8ebbb6be2fab.png)

> The projectile
![image](https://user-images.githubusercontent.com/61373385/161753294-1f909a94-1ea8-4945-88f5-c00e3a30d5e8.png)

> The skeleton\
![image](https://user-images.githubusercontent.com/61373385/161753321-c79504d4-bf0a-4ff0-9711-d53acc5a5614.png)\
-Used 12 sprites out of 24(odd number) for walking animations.
![image](https://user-images.githubusercontent.com/61373385/161753339-2705e677-36e9-4adb-be76-ad3149c1efe1.png)

> Menu background
![image](https://user-images.githubusercontent.com/61373385/161753358-02bc6072-eeea-4986-b3ea-e72cd9b13ea1.png)

> In-game background 
![image](https://user-images.githubusercontent.com/61373385/161753376-056942f9-ce77-4573-9d36-2d3dd372c89d.png)

Additional features
=
Setting
-
Mute button
	-Mute/unmute all sounds
Gyro button
	-Enable/disable gyroscope control
Sensor
-
The game provided an optional gyroscope input for the player to control the cannon.
	-Hold the phone in landscape mode
	-within 90 degree, anti-clockwise is moving down
	-while clockwise is moving up

Highest Score Record
-
If the player managed to get their highest score compared to previous sessions, the score will be saved and displayed on the main menu.
-	A button allows the player to reset the score to zero.

Pause Button
-
A pause button exists to pause the game if pressed, press again while pause will resume.
-	The music will continue from the point of pausing.


Guide Button
-
A guide button to teach players about the game mechanics if they needed.

Technical report - Mobile Game Program Design
=
Game Initialization
-
- Start from MainActivity, as main menu
	- layout\
		![image](https://user-images.githubusercontent.com/61373385/161753454-9e7811dc-5489-4b58-8008-27642e8f8913.png)
	- Highest Score, Reset Record, Guide, Audio, Gyroscope
	- Press Start to initial GameActivity.
- GameActivity, content view is set as GameView
	- Initiate sensor and accelerometer.
- GameView
	- Initiate all asset classes.
	- In the construster
	- get the SharedPreferences data
	- Load in all sound, sprite
	- Initiate player, enemies, projectile array, paint
	- set adaptive screen ratio

Game Update and Render Game Cycle
-
GameView
-	run
-	handle game loop
-	update() and draw() are called here while isPlaying is true
-	update
-	handle all event that run in every frames
-	counting time
-	increase enemies speed as time goes by. Enemies speed starts at 10, +5 at every level that increases at every 600 time unit, maximum level is 4.
-	handle player movement (if gyro is enable)
-	remove projectile that is out of bound
-	spawn enemies on a random point at the y-axis on the right side.
-	onTouchEvent
-	handle all input, like player movement, shooting and pausing
-	player move up or down when button at bottom left is pressed
-	game pause when pause button pressed
-	projectile is spawn and shot when right half of the screen is pressed
-	draw
-	background, player, projectile, enemies, controlUI, text
-	game over text will be displayed when game finished


2D Graphics on Canvas and Draw
-
Different java classes is created for each object
-	Player, Enemy, Projectile, ControlUI, Background
Draw on canvas
-	each image will initiate as Bitmap, and scale based on screen size
-	call the object in GameView.draw() -> canvas.drawBitmap()
Animated Sprite
-	Enemy sprite has 12 image
-	under the method getSkeleton(), return each skeleton frame in order

Managing Game Data
-
SharedPreferences
-	Highest score
-	Check if the score break the record, put in the score if true
-	Audio and gyro setting
-	Set a boolean to remember the player preferences

Handling sizes of Android devices, and Portrait and Landscape Modes
Screen Ratio
-	import screenRatioX and screenRatioY and scale background and sprite based on it.
Landscape
-	The game is forced on landscape mode, portrait mode is not needed.



Boundary Checking and Virtual World
-
Projectile
-	projectile is removed if their x position is greater than screen x, meaning the projectile is out of bound at the right.
-	the game will be over if an enemy x position is smaller than 0, meaning the enemy is out of bound at the left.
Drawing and Game Messages
Score
-	Top of the middle, each enemy shot will be +1
Level
-	Bottom of the screen, every 600 in-game time passed will be increased by 1
Time
-	Every frame the count increased by 1
Game Over
-	Displayed when the game is finished
Button
-	Pause button at the top right of the screen
-	Control button at the button left of the screen

References
=
Assets used
https://www.gamedevmarket.net/asset/cannon-fire-7234/
https://www.gamedevmarket.net/asset/free-platformer-game-kit/
 
Image used
https://www.pinterest.com/pin/754212268842153960/
https://www.dreamstime.com/royalty-free-stock-photography-civil-war-cannon-image1711127
 
Music used
https://www.fesliyanstudios.com/royalty-free-music/download/8-bit-nostalgia/2289
 
Sound effect
https://www.myinstants.com/instant/minecraft-hurt/
https://www.myinstants.com/instant/rick-rolled-bwhahahahahaha/
