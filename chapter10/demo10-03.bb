;demo10-03.bb - Demonstrates FlushKeys

Graphics 800,600

;Create the background image that will be scrolled
backgroundimage = LoadImage ("stars.bmp")

;Create variable that determines how much background has scrolled
scrolly = 0

;MAIN LOOP
While Not KeyDown(1)

;Scroll background a bit by incrementing scrolly
scrolly = scrolly + 1

;Tile the background
TileBlock backgroundimage,0,scrolly

;Reset scrolly if the number grows too large
If scrolly > ImageHeight(backgroundimage)
	scrolly = 0
EndIf

;Print necessary text
Locate 0,0 ;Locate text to top left corner of screen
Print "When you want to quit, press Esc."
Print "Hopefully, a message stating 'Press any key to exit' will appear after you hit Esc."

;Delay the program for a fraction of a second 
Delay 25

Wend ;END OF MAIN LOOP

;Flush the memory that holds the pervious key presses
FlushKeys


Print "Press any key to exit"

;Wait for player to press a key
WaitKey