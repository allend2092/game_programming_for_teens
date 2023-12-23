;demo10-06.bb - Demonstrates the return value of KeyHit()

;Set up graphics so that you can read all of the text, make it windowed
Graphics 800,600,0,2

;Begin introductory text
Print "You know what's cool? Game Programming."
Print "Although Maneesh ain't that uncool, either."

;Print blank line
Print ""

;Continue text
Print "Anyway, press Esc as many times as you can in the Next 5 seconds."
Print "At the end of the program, the number of times will be printed."


;Allow the player 5 seconds to hit esc as many times as possible
Delay 5000


;Set numberofhits equal to the amount of times Esc was hit (return value of KeyHit(1) )
numberofhits = KeyHit(1)

;Print the number of times Esc was hit
Print "Esc was hit " + numberofhits + " times."
Print "You gotta love KeyHit(), huh?"

;Hold on a sec so the player can see the final text
Delay 1000

;Make sure the key input memory is cleared by calling FlushKeys
FlushKeys

;Wait for user to press a key before exiting
Print "Press any key to Esc."
WaitKey