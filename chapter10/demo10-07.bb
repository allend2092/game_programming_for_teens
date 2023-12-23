;demo10-07.bb - What if there is no mouse cursor?
Graphics 640,480
;Set default drawing surface to back buffer
SetBuffer BackBuffer()

;MAIN LOOP
While Not KeyDown(1)

;Clear the Screen
Cls
;Reset printing commands to top left hand corner
Locate 0,0
Print "Try guessing where the mouse actually is!"

;Print X and Y coordinates
Print "MouseX: " + MouseX()
Print "MouseY: " + MouseY()

;Flip front and back buffers
Flip

Wend ;End of Main loop