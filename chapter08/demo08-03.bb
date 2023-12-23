;demo08-03.bb - Demonstrates rotation a rectangle
Graphics 800,600

;Handle images from the center
AutoMidHandle True

;Load the animated rectangles
rectanglesimage = LoadAnimImage("rectangles.bmp",250,250,0,2)

;Create variable that counts how many rotations occured
rotationcount = 0 

;MAIN LOOP
While Not KeyDown(1)
;Clear the screen
Cls

;Position text at top left of screen
Locate 0,0

;Print the number of rotations
Print "Number of Rotations: " + rotationcount

;Draw the rectangle image with the proper frame
DrawImage rectanglesimage,400,300,rotationcount Mod 2 

;Increment the rotation count variable
rotationcount = rotationcount + 1 

;Wait a while
Delay 100 


Wend
;END OF MAIN LOOP

Print "Press any Key to Exit"
;Clear key buffer
FlushKeys 

;Wait for player to press a key before exiting.
WaitKey 