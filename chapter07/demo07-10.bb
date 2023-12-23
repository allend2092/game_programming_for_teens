;demo07-10.bb - A bad real-time rotation program

Graphics 800,600
;Grab Images by the center
AutoMidHandle True

;Initialize Back Buffer
SetBuffer BackBuffer()

;IMAGES
;Load the ship image that will be rotated
shipimage = LoadImage ("spaceship.bmp")


Print "Press Left to rotate counter-clockwise and right to rotate clockwise,"
Print "Press Esc to exit."

;MAIN LOOP
While Not KeyDown(1)
;Clear the Screen
Cls

;If the player presses left, rotate four degrees right, if he presses right, rotate four degrees right
If KeyDown (203) 
	RotateImage shipimage, -4
ElseIf KeyDown (205) 
	RotateImage shipimage,4
EndIf

;Draw the ship
DrawImage shipimage, 400,300
Flip
Wend
;END OF MAIN LOOP