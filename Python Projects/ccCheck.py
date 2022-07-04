## Program to check validity of Credit Card Number
## Given first 15 digits of Credit Card, Returns 16th digit 

number = int (input("Enter the first 15 digits of your card number: "))
number = str(number)

def checkNum(number):
    sum = 0
    d = 1
    cloneSum = 0
    num = 0
    for i in range(0,15):
        if i % 2 == 0:
            d = 2 * int(number[i])
        else:
            d = int(number[i])
        sum = sum + (d % 10) + d//10
    cloneSum = sum
    for x in range (0, 10):
        sum = cloneSum + (x % 10) + x//10
        if sum % 10 == 0:
            num = x
            break
    return print(num)

checkNum(number)
