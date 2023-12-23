;demo02-06.bb - Tests if you are old enough to vote
age = Input$("How old are you? ") ;how old is user
If age >= 18 Then
	Print "You are legally allowed to vote!" ;if older or equal to 18

Else Print "Sorry, you need To be a few years older." ;if younger than 18
EndIf
WaitKey