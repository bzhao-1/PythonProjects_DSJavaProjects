from hangman import HangMan

exit = False
while exit == False:
    hangman = HangMan()
    hangman.run_game()
    again = input("Would you like to play again? Enter Yes or No: " )
    again = again.capitalize()
    if again == "No":
        exit = True
        print("Thanks for playing!")
