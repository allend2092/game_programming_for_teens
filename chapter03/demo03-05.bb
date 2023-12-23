;demo03-05.bb  - Closes program after player presses ESC.
Print "Why did you open this progam?"
Repeat 
	Print "Press Esc to exit."
	;wait a sec
	Delay 1000 
;repeat until user hits esc
Until KeyHit(1) 
Print "Program is ending."