; demo06-07.bb - A ReadPixelFast/WritePixeFast Example 

Graphics 800,600 

Print "Press any key to use ReadPixel" 
;wait for user to do something
WaitKey 

;load rectangle image
image =LoadImage("rectangle.bmp") 

;Draw the intro screen
DrawImage image,0,0 
DrawImage image,100,100

;Hold up  a second
Delay (1000)

;Create a pixel array that will hold the entire screen
Dim pixelarray(GraphicsWidth(),GraphicsHeight()) 

;lock the buffer REQUIRED
LockBuffer 

;Copy all of the pixels of the screen to the array
For rows=0 To GraphicsWidth() 

	For cols=0 To GraphicsHeight() 
	
		;Copy the current pixel
		pixelarray(rows,cols)=ReadPixelFast(rows,cols) 
	Next 
Next 

;Unlock the buffer
UnlockBuffer 

Cls 
Locate 0,0 
Print "Press another key to copy pixels backwards"

;Wait for key press
WaitKey

;Lock the buffer to allow WritePixelFast
LockBuffer 

; Use WritePixel to redraw the screen using the color information we got earlier 
For rows=0 To GraphicsWidth() 
	For cols=0 To GraphicsHeight() 
		;Draw the current pixels
		WritePixelFast rows,cols,pixelarray(GraphicsWidth()-rows,cols) 
	Next 
Next 

; Unlock buffer after using WritePixelFast 
UnlockBuffer 

Print "Press a key to exit"
WaitKey

