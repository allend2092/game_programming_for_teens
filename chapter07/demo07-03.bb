;demo 07-03.bb - Demonstrates Scaling

Graphics 1024,768

;VARIABLES
;Create the variables that define the rectangle
rectbeginx = 25 ;The x coordinate of the top left corner
rectbeginy = 25 ;The y coordinate of the top left corner
rectwidth  = 256 ;The x coordinate of the bottom right coordinate
rectheight = 256 ;The y coordinate of the bottom right coordinate

;MAIN SECTION
;Make sure the text goes near the bottom of the screen
Locate 0,700 
Print "This is our first rectangle." 

;Draw the first rectanlge, and make it not filled
Rect rectbeginx,rectbeginy,rectwidth ,rectheight,0

;Ask the user what the scaling factor is
sxy# = Input ("What would you like the scaling factor to be? (Ex: 50% = .5)? ==> ") 

;Multiply the width and height by the scaling factor
rectwidth = rectwidth  * sxy# 
rectheight = rectheight * sxy#

;Draw the new rectangle 
Rect rectbeginx,rectbeginy,rectwidth,rectheight,0 

Print "Press any key to exit." 

;Wait for the user to press a key before exiting.
WaitKey 