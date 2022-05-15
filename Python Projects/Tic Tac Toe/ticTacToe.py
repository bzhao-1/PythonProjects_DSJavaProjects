#Ben Zhao && Ayden Vinyard
##The purpose of this program is to play a game of Tic Tac Toe in either Text or Graphical Mode where user input can be entered or clicked on in Graphics and the AI will play
#O in a random box against you.

from game import Game
#Welcome Message
print("Welcome to Tic Tac Toe! Dont lose!")
#Asking user for Graphical or Text Version of Game and intializing
mode = input("Do you want to play in Graphical or Text mode? Enter G or T: ")
mode = mode.upper()
if mode == "T":
    game = Game(graphical=False)
    game.play()
if mode == "G":
    game = Game(graphical=True)
    game.play()
