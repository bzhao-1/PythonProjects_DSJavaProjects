#Ben Zhao CS 111 Final
#An Interactive Hangman Game: This Code allows the user to Play Hangman
##Graphics.Py, Game.py, and The Srabble Dictionary Files are required to run this code! See the ReadMe for detailed description
##Enjoy!!


import random
from graphics import *
from random import randint


class HangMan:
    "An Interactive Hangman Game"

    print("Welcome to Hangman!")
    print("This game was created by Ben Zhao and Daanyal Raja")
    print("It's a pretty self-explanatory game. Once you boot the game up, it'll ask you if you want to guess a word or a letter.")
    print("If you guess a letter, you have to input one letter that you think the hangman word will contain.")
    print("If you guess a word, just input a word! We recommend having an idea of what that word will be, but if you wanna guess, go ahead!")
    print("We hope you enjoy our game and have fun playing!")

    def __init__(self):
        #Variable that is set to True or False, False establishes that Hangman Background has not been drawn
        #Set to true, so a new game board will not be drawn each time a letter or word is guessed
        self.boardDrawn = False
        #Dictionary of random Words to Choose from
        file = open("/Users/benzhao/Desktop/CS111_Final/scrabble_dictionary.txt", "r")
        contents = file.read()
        self.words = contents.split('\n')
        #Get Random Word
        self.word = random.choice(self.words)
        if len(self.word) > 10:
            self.word = random.choice(self.words)
        #User Guess Set to Empty to Start
        self.guessLetter = ""
        #Guesses that have been entered, Max will be 8
        self.guesses = 0
        #Remaining Unguessed Letters in the Word
        self.remainingLetters = len(self.word)
        #Check if user has won game, True/False, if not Win keep playing, if Won, print win message
        self.winner = False
        #User Guess Word Set to Empty for now
        self.guessWord = ""
        #GuessList for letters alaready guessed
        self.guessList = []
        #GuessList for words already guessed
        self.guessListWord = []
        #Draw Game Board
        self.board = self.makeBoard()

    def userLetter(self): #Allows the user to guess a letter
        while 1: #Repeats this until a proper input is given
            self.guessLetter = input("Guess a letter: ") #User inputs letter
            self.guessLetter = self.guessLetter.upper()
            if len(self.guessLetter) > 1 or self.guessLetter not in "ABCEDFGHIJKLMNOPQRSTUVWXYZ":
                print("Invalid Input") #Invalidates inputs that are NOT letters
            else:
                break
        width = 900 // len(self.word) #Spacing out dashes
        if self.guessLetter not in self.guessList: #determines whether letter has been guessed and if it is in the wrod
            if self.guessLetter in self.word:
                print(self.guessLetter, "is in the word!")
                for i in self.word: #determining how many times letter appears in word to account for multiple same letter in word
                    count = 0
                    if i == self.guessLetter:
                        count += 1
                        self.remainingLetters = self.remainingLetters - count #Counts the number of words left in the word
                for i in range(len(self.word)):
                    if self.word[i] == self.guessLetter: #Places the guessed letter in the proper dash that represents where in the word it is located
                        if i == 0:
                            letter = Text(Point((i+width)/2, 100), self.guessLetter)
                            letter.setSize(36)
                            letter.draw(self.win)
                        if i > 0:
                            letter = Text(Point(((i*width+10)+width*(i+1))/2, 100), self.guessLetter)
                            letter.setSize(36)
                            letter.draw(self.win)
                if self.remainingLetters == 0: #determines if you have won if there are no letters left in word
                    self.winner = True
            else: #Accounts for when the word is NOT in the random hangman word, guesses increase by 1 and draws letters in green space
                self.guesses += 1
                letter = Text(Point(self.guesses*100, 800), self.guessLetter)
                letter.setSize(36)
                letter.draw(self.win)
                print(self.guessLetter, "is not in the word, attempts remaining:", 8 - self.guesses)
        else: #Accounts for guessing a word that was already previously guessed
            print("You have already guessed", self.guessLetter, "Try again")
        self.guessList.append(self.guessLetter)

    def drawHangman(self): #Draws the hangman
        if self.winner == False:
            if self.guesses >= 1: #Draws the head with 1 guess
                head = Circle(Point(525, 245), 60)
                head.setFill("Beige")
                head.draw(self.win)
            if self.guesses >= 2: #Draws body with 2 guesses
                body = Line(Point(525, 305), Point(525, 525))
                body.draw(self.win)
            if self.guesses >= 3: #Draws one arm with 3 guesses
                arm1 = Line(Point(525, 350), Point(475, 475))
                arm1.draw(self.win)
            if self.guesses >= 4: #Draws other arm with 4 guesses
                arm2 = Line(Point(525, 350), Point(575, 475))
                arm2.draw(self.win)
            if self.guesses >= 5: #Draws one leg with 5 guesses
                leg1 = Line(Point(525, 525), Point(495, 675))
                leg1.draw(self.win)
            if self.guesses >= 6: #Draws other leg with 6 guesses
                leg2 = Line(Point(525, 525), Point(555, 675))
                leg2.draw(self.win)
            if self.guesses >= 7: #Draws one eye with 7 guesses
                eye1 = Line(Point(485, 230), Point(510, 260))
                eye2 = Line(Point(510, 230), Point(485, 260))
                eye1.draw(self.win)
                eye2.draw(self.win)
            if self.guesses >= 8: #Draws other eye with 8 guesses
                eye3 = Line(Point(565, 230), Point(540, 260))
                eye4 = Line(Point(540, 230), Point(565, 260))
                eye3.draw(self.win)
                eye4.draw(self.win)
                time.sleep(3) #Draws "BETTER LUCK NEXT TIME" on the window :D
                letter = Text(Point(450, 700), "BETTER LUCK NEXT TIME!")
                letter.setSize(36)
                letter.setTextColor("Hot Pink")
                letter.draw(self.win)

    def run_game(self): #Allows you to guess a word or letter
        while self.winner == False:
            answer = input("Would you like to guess a word or letter? Enter W or L: ")
            answer = answer.upper()
            if answer != "W" and answer != "L":
                print("Invalid Input") #Prints invalid input for inputs other than W and L
            if answer == "L": #Calls the letter function and draw hangman
                self.userLetter()
                self.drawHangman()
                if self.guesses == 8: #Calls draw hangman in the event you lose and your last input was a letter
                    self.drawHangman()
                    print("The word was", self.word)
                    print("The max number of attempts has been reached.")
                    print("The hangman died. Try again!")
                    break
            if answer == "W": #Calls the word function and draw hangman
                self.userWord()
                self.drawHangman()
                if self.guesses == 8: #Calls draw hangman in the event you lose and your last input was a word
                    self.drawHangman()
                    print("The word was", self.word)
                    print("The max number of attempts has been reached.")
                    print("The hangman died. Try again!")
                    break
        if self.winner == True: #If you win, prints the winning statement
            time.sleep(3)
            letter = Text(Point(450, 700), "Good Job!")
            letter.setSize(36)
            letter.setTextColor("Hot Pink")
            letter.draw(self.win)
            print("The word was", self.word)
            print("Congrulations, You have won the game!")

    def makeBoard(self): #This creates the background of the board, which includes the dashes, the gallows, sun, grass, and sky.
        if self.boardDrawn == False: #ensures that multiple boards are not drawn each round
            self.win = GraphWin("Board", 900, 900) #makebackground
            border1 = Line(Point(150, 150), Point(150, 750)) #makebackground
            border2 = Line(Point(0, 750), Point(900, 750)) #makebackground
            border3 = Line(Point(150, 150), Point(525, 150)) #makebackground
            border4 = Line(Point(525, 150), Point(525, 185)) #makebackground
            border5 = Line(Point(150, 300), Point(300, 150)) #makebackground
            border6 = Line(Point(105, 750), Point(150, 700)) #makebackground
            border7 = Line(Point(195, 750), Point(150, 700)) #makebackground
            border1.setWidth(5) #makebackground
            border3.setWidth(5) #makebackground
            border4.setWidth(5) #makebackground
            border5.setWidth(5) #makebackground
            border6.setWidth(5) #makebackground
            border7.setWidth(5) #makebackground
            rectangle1 = Rectangle(Point(0, 750), Point(900, 900)) #makebackground
            rectangle1.setFill("Light Green") #makebackground
            rectangle2 = Rectangle(Point(0, 750), Point(900, 0)) #makebackground
            rectangle2.setFill("Light Blue") #makebackground
            sun = Circle(Point(900, 0), 100) #makebackground
            sun.setFill("Yellow") #makebackground
            rectangle1.draw(self.win) #makebackground
            rectangle2.draw(self.win) #makebackground
            sun.draw(self.win) #makebackground
            border1.draw(self.win) #makebackground
            border2.draw(self.win) #makebackground
            border3.draw(self.win) #makebackground
            border4.draw(self.win) #makebackground
            border5.draw(self.win) #makebackground
            border6.draw(self.win) #makebackground
            border7.draw(self.win) #makebackground
            width = 900 // len(self.word) #properly spaces out dashes for the selected random word
            for i in range(len(self.word)):
                if i == 0:
                    dash = Line(Point(i, 125), Point(width, 125))
                    dash.draw(self.win)
                if i > 0:
                    dash = Line(Point(i*width+10, 125), Point(width*(i+1), 125))
                    dash.draw(self.win)

            self.boardDrawn = True

    def userWord(self): #allows the user to guess a word
        self.guesses += 1 #reduces a guess whenever guesses a word
        validInput = False
        while validInput == False: #asks the user for word until valid input is given
            self.guessWord = input("Guess a Word: ")
            self.guessWord = self.guessWord.upper()
            for i in range(len(self.guessWord)):
                if self.guessWord[i] not in "ABCDEFGHIJKLMNOPQRSTUVWXYZ" or len(self.guessWord) == 1:
                    print("Invalid Input")
                    break
                else:
                    validInput = True

        width = 900 // len(self.word)
        if self.guessWord not in self.guessListWord: #looks for words not guessed
            if self.guessWord == self.word: #if the guessed word and chosen word are same, draw letters of those word on dashes
                for i in range(len(self.word)):
                        if i == 0: #draw first letter on first dash
                            letter = Text(Point((i+width)/2, 100), self.word[i])
                            letter.setSize(36)
                            letter.draw(self.win)
                        if i > 0: #draw next letters on proper dashes
                            letter = Text(Point(((i*width+10)+width*(i+1))/2, 100), self.word[i])
                            letter.setSize(36)
                            letter.draw(self.win)
                self.winner = True #declare winner as true
                return self.winner == True
            else:
                letter = Text(Point(self.guesses*100, 800), self.guessWord) #draw incorrect words in green space
                letter.setSize(30)
                letter.draw(self.win)
                print("That is not the right word")
                print(self.guessWord, "is not the word, attempts remaining:", 8 - self.guesses)
        else:
            print("You have already guessed this! Try Again: ")
        self.guessListWord.append(self.guessWord)
