;demo10-08.bb - Demonstrates drawing a mouse cursor
Graphics 640,480
;Set default drawing surface to back buffer
SetBuffer BackBuffer()

;IMAGES
;Load the background and the mouse cursor
backgroundimage = LoadImage("stars.bmp")
mouseimage = LoadImage("mouseimage.bmp")

;Set handle to top left for mouseimage
HandleImage mouseimage,0,0

;Create an indicator variable for scrolling background
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

;Reset printing commands to top left hand corner
Locate 0,0
Print "Mouse is easier to find now, huh"

;Print X and Y coordinates
Print "MouseX: " + MouseX()
Print "MouseY: " + MouseY()

;draw the mouse image
DrawImage mouseimage,MouseX(),MouseY()

;Flip front and back buffers
Flip

Wend ;End of Main loop