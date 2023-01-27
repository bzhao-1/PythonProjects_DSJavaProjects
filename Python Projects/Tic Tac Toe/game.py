# Ben Zhao, CS 111 Fall 2021 
#The purpose of this program is to play a game of Tic Tac Toe in either Text or Graphical Mode where user input can be entered or clicked on in Graphics and the AI will play
#O in a random box against you.
#This program does a good job of implementing most of its instance variables in all of the methods, thus it is generally cohesive. However, it is long and attempts to do way too many things. 


from random import randint
from graphics import *
import random

class Game:
    """An interactive Tic-Tac-Toe game."""

    def __init__(self, graphical):
        """Initialize a Game object."""
        # The game board is representing as a 3x3 2D list.
        self.board = self.makeBoard()
        # This attribute stores the winner, if any. If it's set to
        # the empty string (''), the game is ongoing. If it's set to
        # 'X', the user has won. If it's set to 'O', the AI won. If
        # it's set to 'Draw', the game has ended in a draw.
        self.winner = ''
        # This attribute should be set to True or False. If it's set
        # to True, then the user will play a graphical version of the
        # game, made possible by Zelle's graphics.py library.
        self.graphical = graphical  # True or False
        #This attribute stores the instance of the Graphics Window, so that the graphics can be drawn in any funciton
        self.win = None
        #This attributes should be True/False, If it is False, a window for Graphics will be drawn, If it is True, a new window for graphics will not be drawn each turn.
        self.boardDrawn = False

    def makeBoard(self):
        """Return a fresh game board in the form of a 2D list."""
        self.board = [['-', '-', '-'], ['-', '-', '-'], ['-', '-', '-'], "\n"]
        return self.board

    def play(self):
        """Carry out the game loop until the game ends."""
        # Draw the fresh board before the game starts
        self.drawBoard()
        # Keep taking turns until someone wins or there's a draw
        while self.winner == '':
            # Solicit the user's turn first
            self.userTurn()
            # Check if the user has won
            self.checkForWinner()
            # Draw the updated game board
            self.drawBoard()
            # If user doesn't win, produce the AI's next turn
            if self.winner == '':
                self.aiTurn()
                # Check if the AI has won
                self.checkForWinner()
                # Draw the updated game board
                self.drawBoard()
        # Once the game is over, print the winner
        if self.winner == 'X':
            print("Congratulations, you win!")
        elif self.winner == 'O':
            print("The computer wins! Better luck next time.")
        else:
            print("It's a draw!")

    def userTurn(self, row = 0, column = 0):
        """Solicit and execute the user's next move."""
        #Setting userturn for if game mode is graphics,
        if self.graphical == True:
            #Run a while loop so that it will play until x is drawn for that turn
            while 1:
                #Getting coordinates for where the user clicks in one of the boxes for their turn, getting the row and column by taking the coordinate and subtracting 100 and dividing by 100
                userpoint = self.win.getMouse()
                x = userpoint.x
                y = userpoint.y
                row=int((y-100)/100)
                column=int((x-100)/100)
                #Whereever box the user decides to click on, An X will be drawn in that Box if there is not already an X or O in that Box.
                #If there is not an X or O in the box, the X is stored so that the user and AI can not put their next turn in that box
                if self.board[row][column] != "O" and self.board[row][column] != "X":
                    self.board[row][column] = "X"
                    #X in Box 1
                    if (100 < x < 200) and (100 < y < 200):
                        Line5 = Line(Point(100, 100), Point(200, 200))
                        Line6 = Line(Point(200, 100), Point(100, 200))
                        Line5.draw(self.win)
                        Line6.draw(self.win)
                        break
                    #X in Box 2
                    if (200 < x < 300) and (100 < y < 200):
                        Line5 = Line(Point(200, 100), Point(300, 200))
                        Line6 = Line(Point(300, 100), Point(200, 200))
                        Line5.draw(self.win)
                        Line6.draw(self.win)
                        break
                    #X in Box 3
                    if (300 < x < 400) and (100 < y < 200):
                        Line5 = Line(Point(300, 100), Point(400, 200))
                        Line6 = Line(Point(400, 100), Point(300, 200))
                        Line5.draw(self.win)
                        Line6.draw(self.win)
                        break
                    #X in Box 4
                    if (100 < x < 200) and (200 < y < 300):
                        Line5 = Line(Point(100, 200), Point(200, 300))
                        Line6 = Line(Point(200, 200), Point(100, 300))
                        Line5.draw(self.win)
                        Line6.draw(self.win)
                        break
                    #X in Box 5
                    if (200 < x < 300) and (200 < y < 300):
                        Line5 = Line(Point(200, 200), Point(300, 300))
                        Line6 = Line(Point(300, 200), Point(200, 300))
                        Line5.draw(self.win)
                        Line6.draw(self.win)
                        break
                    #X in Box 6
                    if (300 < x < 400) and (200 < y < 300):
                        Line5 = Line(Point(300, 200), Point(400, 300))
                        Line6 = Line(Point(400, 200), Point(300, 300))
                        Line5.draw(self.win)
                        Line6.draw(self.win)
                        break
                    #X in Box 7
                    if (100 < x < 200) and (300 < y < 400):
                        Line5 = Line(Point(100, 300), Point(200, 400))
                        Line6 = Line(Point(200, 300), Point(100, 400))
                        Line5.draw(self.win)
                        Line6.draw(self.win)
                        break
                    #X in Box 8
                    if (200 < x < 300) and (300 < y < 400):
                        Line5 = Line(Point(200, 300), Point(300, 400))
                        Line6 = Line(Point(300, 300), Point(200, 400))
                        Line5.draw(self.win)
                        Line6.draw(self.win)
                        break
                    #X in Box 9
                    if (300 < x < 400) and (300 < y < 400):
                        Line5 = Line(Point(300, 300), Point(400, 400))
                        Line6 = Line(Point(400, 300), Point(300, 400))
                        Line5.draw(self.win)
                        Line6.draw(self.win)
                        break
                    self.boardDrawn = True


        #Running Userturn for Text Mode
        else:
            #Run a while loop so that it will play until x is drawn for that turn
            while 1:
                if self.graphical == False:
                    #Asking for user row and column they want to play their X in
                    row = int(input("Enter a row 0-2 you wish to put your X : "))
                    column = int(input("Enter a column 0-2 you wish to put your X: "))
                    #If user input not on grid, ask for valid user input
                    if row < 0 or row > 2 or column > 2 or column < 0:
                        print("That is not a box on the grid. Please enter a valid input!")
                    else:
                        #Once again if their input has not already been played, an X will be played and stored so that next turns can not play in that box
                        if self.board[row][column] != "O" and self.board[row][column] != "X":
                            self.board[row][column] = "X"
                            break

                        #If user input in a spot already played, tell them to enter a unmarked spot
                        if self.board[row][column] == "O" or self.board[row][column] == "X":
                            print("That spot already has been marked please choose a different one!")



    def aiTurn(self):
        """Execute the AI's next move."""
        #Setting AI Turn in Graphics
        if self.graphical == True:
            #Giving each square in Tic Tac Toe board a name/variable name and setting them to be strings "rowcolumn" for each box
            box1 = "00"
            box2 = "01"
            box3 = "02"
            box4 = "10"
            box5 = "11"
            box6 = "12"
            box7 = "20"
            box8 = "21"
            box9 = "22"
            #Run a while loop so that it will play until O is drawn for that turn
            while 1:
                #Creating a list of the boxes and pulling out a random box for AI Turn
                available = [box1, box2, box3, box4, box5, box6, box7, box8, box9]
                index = random.randint(0, len(available)-1)
                choice = available[index]
                #For the row and column of the random box, if there is not an O or X in that box, play an O there for the turn
                if self.board[int(choice[0])][int(choice[1])] != "O" and self.board[int(choice[0])][int(choice[1])] != "X" :
                    aiRow = int(choice[0])
                    aiColumn = int(choice[1])
                    #Set AI row to the row in the box and AI column to the column in the box and update that box in the board to be an O so that future turns cant play in that box
                    self.board[aiRow][aiColumn] = "O"
                    #Draw the O centered in the box that has been chosen
                    aCircle = Circle(Point(150 + (aiColumn * 100),(aiRow * 100) + 150), 50)
                    aCircle.draw(self.win)
                    break
                self.boardDrawn = True
        #Running the text version for AI turn, pull random int between 0-2 for row and column if that box has not been played, put the O there
        else:
            #Run a while loop so that the turn will play until O is drawn for that turn
            while 1:
                aiRow = randint(0, 2)
                aiColumn = randint(0, 2)
                if self.board[aiRow][aiColumn] != "O" and self.board[aiRow][aiColumn] != "X":
                    self.board[aiRow][aiColumn] = "O"
                    break



    def checkForWinner(self):
        #Set Game Winner if Any
        for i in range(3):
            #Checking if there is a winner through the rows
            if self.board[i][0] == self.board[i][1] and self.board[i][0] == self.board[i][2] and self.board[i][0] != "-":
                self.winner = self.board[i][0]
            #If not, Checking if there is a winner through the columns
            elif self.board[0][i] == self.board[1][i] and self.board[0][i] == self.board[2][i] and self.board[0][i] != "-":
                self.winner = self.board[0][i]
        #If not, checking if there is a winner through the diagonals
        if self.winner == '' and self.board[0][0] == self.board[1][1] and self.board[0][0] == self.board[2][2] and self.board[0][0] != "-":
            self.winner = self.board[0][0]
        elif self.winner == '' and self.board[0][2] == self.board[1][1] and self.board[0][2] == self.board[2][0] and self.board[1][1] != "-":
            self.winner = self.board[0][2]
        #Only check for draw if all spaces on board have been filled, or else draw would intialize each turn if there was no winner on that turn
        board_filled = True
        for i in range(3):
            for j in range(3):
                if self.board[i][j] == "-":
                    board_filled = False
        if board_filled == True and self.winner == '':
            self.winner = "Draw"



    def drawBoard(self):
        """Display the current game board."""
        #Draw our gameboard in a New Window only if drawing a fresh game
        if self.graphical == True:
            if self.boardDrawn == False:
                self.win = GraphWin("Tic Tac Toe", 500, 500)
                #Our game board consists of spaces all 100x100 in size
                Line1 = Line(Point(200, 100), Point(200,400))
                Line2 = Line(Point(300, 100), Point(300,400))
                Line3 = Line(Point(100, 200), Point(400,200))
                Line4 = Line(Point(100, 300), Point(400, 300))
                Line1.setWidth(3)
                Line2.setWidth(3)
                Line3.setWidth(3)
                Line4.setWidth(3)
                Line1.draw(self.win)
                Line2.draw(self.win)
                Line3.draw(self.win)
                Line4.draw(self.win)
                self.boardDrawn = True
        #Drawing game board in textical version
        if self.graphical == False:
            for i in self.board:
                for j in i:
                    print(j, end = "  ")
                print()
