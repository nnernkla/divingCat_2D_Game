Graphics 1000,600,32,2
SetBuffer BackBuffer()

;set up initial 
;setup initial settings
speed = 2 
frame = 0
wallGen = 1
catX =200
catY =150
fishGen = 350
iniJellyX = 0
iniEel = 0
tankGen = 1000
tankCD = 500
tankTC = 0
jellyFishGen = 500
eelGen = 200
dead=False
iniEner = 10000
iniOxy = 20000
dest = 10000
energy = 100
oxygen = 100
goal = 100


Type tank
	Field x
	Field y
End Type

Type fish
	Field x
	Field y
End Type

Type jellyFish
	Field x
	Field y
End Type

Type eel
	Field x
	Field y
End Type

Type wall
	Field x
	Field y
End Type

fntArial=LoadFont("Arial",24,False,False,False)
SetFont fntArial
;load image
background = LoadImage("images/underwater3.jpg")
grass = LoadImage("images/grass1.png") 
cat = LoadImage("images/ScubaCat1.png") 
fishPic = LoadImage("images/fish1_1.png") 
jellyPic = LoadImage("images/jellyfish1.png") 
eelPic1 = LoadImage("images/eel4.png") 
eelPic2 = LoadImage("images/eel4_re.png")
catdie = LoadImage("images/catDead.png")
wallPic = LoadImage("images/wall2.png")


tankPic = LoadImage("images/tank1.png")

While Not KeyDown(1)
DrawImage background,0,0,0
DrawImage cat,catX,catY,0

shadowText 100,30,"Destination : " + goal +"%"  
shadowText 500,30,"Energy : " + energy + "%"
shadowText 900,30,"Oxygen : " + oxygen + "%"

frame = frame + speed
If energy > 100 Then iniOxy = 20000
If oxygen > 100 Then iniEner = 10000
goal = dest/100 
oxygen = iniOxy / 200
iniOxy = iniOxy -1
energy = iniEner/100
; draw ground 
If  dead = False 
	If KeyDown(200)
			iniEner = iniEner - 2 
			catY = catY-2
			DrawImage cat,catX,catY,0
			For x = 1 To 12 ;
				DrawImage grass,-600+(x*100),430
			Next
	Else If  KeyDown(208)
			iniEner = iniEner - 2 
		 	catY = catY+2
			DrawImage cat,catX,catY,0
			For x = 1 To 12 ;
				DrawImage grass,-600+(x*100),430
			Next
	Else If KeyDown(205)
			iniEner = iniEner - 2 
			dest = dest - 1
		For x = 1 To 12 
			DrawImage grass,-600+(x*100)-(frame Mod 100),430 
		Next
	Else If KeyDown(203)
			iniEner = iniEner - 2 
			dest = dest+1
		For x = 1 To 12 
			DrawImage grass,-600+(x*100)+(frame Mod 100),430
		Next
	Else 
			iniEner = iniEner - 1
		For x = 1 To 12 
			DrawImage grass,-600+(x*100),430
		Next

	End If
End If 

;draw fish and tank
	tankGen = tankGen - 1
	tankCD = tankCD - tankTC
	If tankGen = 0
		tankTC = 1
		tankGen = Rand(1800,2200)
		oxyTank.tank = New tank
		oxyTank\x = Rand(300,1000) 
		oxyTank\y = Rand(50,550)
	End If

	For oxyTank.tank = Each tank 
		If KeyDown(205) Then oxyTank\x = oxyTank\x-speed 
		; if not keyDown
		DrawImage tankPic,oxyTank\x,oxyTank\y
		If ImagesCollide(tankPic,oxyTank\x,oxyTank\y,0,cat,catX+10,catY,0)
			Delete oxyTank
			iniOxy = iniOxy + 3000
	    Else If tankCD < 0  
			Delete oxyTank
			tankTC = 0
			tankCD = 500
		End If 
	Next
; draw wall
	wallGen = wallGen - 1
	If wallGen = 0
		wallGen = Rand(2500,3000)
		wally.wall = New wall
		wally\x = 1000 
		wally\y = Rand(-30,-50)
	End If

	For wally.wall = Each wall 
		If KeyDown(205) Then wally\x = wally\x-speed 
		; if not keyDown
		DrawImage wallPic,wally\x,wally\y
		If ImagesCollide(wallPic,wally\x,wally\y,0,cat,catX+10,catY,0)
			catY =catY + 0
	    Else If wally\x < -600  
			Delete wally
		End If 
	Next
;draw fish 	
	fishGen = fishGen - 1
	If fishGen = 0 Then
		fishGen = Rand(200,400)
		fishy.fish = New fish
		fishy\x = 1000 
		fishy\y = Rand(100,500)
	End If

	For fishy.fish = Each fish 
		fishy\x = fishy\x-speed 
		DrawImage fishPic,fishy\x,fishy\y
		If ImagesCollide(fishPic,fishy\x,fishy\y,0,cat,catX+10,catY,0)
			iniEner = iniEner + 500
			Delete fishy
	    Else If fishy\x < -50  
			Delete fishy
		End If 
	Next
;draw jelly fish	
		jellyFishGen = jellyFishGen - 1
	If jellyFishGen = 0 Then
		jellyFishGen = Rand(500,800)
		jelly.jellyFish = New jellyFish
		iniJellyX = Rand(100,1000)
		jelly\x = iniJellyX 
		jelly\y = 0
	End If

	For jelly.jellyFish = Each jellyFish
		If iniJellyX > 200 
			jelly\x = jelly\x-speed
		Else 
			jelly\x = jelly\x+speed
		End If 
		jelly\y = jelly\y+1
		DrawImage jellyPic,jelly\x,jelly\y		
		If ImagesCollide(jellyPic,jelly\x,jelly\y,0,cat,catX,catY,0)
			dead = True 
	    Else If jelly\y > 650  
			Delete jelly
		End If  
	Next
;draw eel	
		eelGen = eelGen - 1
	If eelGen = 0 Then
		eelGen = Rand(500,900)
		eels.eel = New eel
		iniEel = Rand(1,2)		
		If iniEel = 1 
			eels\x = 0
		Else If iniEel = 2 
			eels\x = 1000
		End If   
		eels\y = Rand(50,550)
	End If

	For eels.eel = Each eel
		If iniEel < 10 
			eels\x = eels\x+speed
			eelPic = eelPic1
		Else 
			eels\x = eels\x-speed
			eelPic = eelPic2
		End If 

		DrawImage eelPic,eels\x,eels\y		
		If ImagesCollide(eelPic,eels\x,eels\y,0,cat,catX,catY,0)
			dead = True 	
	    Else If eels\x > 1200 Or eels\x < -200 
			Delete eels
		End If 
	Next
; if cat die	
	If dead = True 
		catY = catY+2
		DrawImage catdie,catX,catY,0
		shadowText GraphicsWidth()/2,200,"Game Over" 
		For x = 1 To 12 ;
				DrawImage grass,-600+(x*100),430
		Next 

	End If 



	Flip
Wend ;end while
End
Function shadowText(textX,textY,textString$)
	Color 0,0,0
	Text textX+2,textY+2,textString,1,1
	Color 255,255,255
	Text textX,textY,textString,1,1
End Function