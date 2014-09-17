#!/usr/bin/python
#Daniel Loman
#9-16-2014
# Description:
#   This script takes user password and website input, concatenates them and
#   returns a hashed password. This hashed password is then modified to make
#   sure it has at least 1 number, 1 lower case letter and 1 capital letter 

import getpass
import hashlib
import pyperclip

SpecialCharacters = ['!', '@', '#', '$', '%', '^', '&', '*', '(', ')']

ClearTextPassword = getpass.getpass("Enter Password Please: ")
Website = raw_input("Enter Website Please: ")

sha1 = hashlib.sha1()
sha1.update(ClearTextPassword + Website + "\n")

HashedPassword = sha1.hexdigest()[:10]

Digits = [c for c in HashedPassword if c.isdigit()]
Characters = [c for c in HashedPassword if c.isalpha()]

print HashedPassword
if len(Digits) > 1:
  HashedPassword = \
    HashedPassword.replace(Digits[0], SpecialCharacters[int(Digits[0])], 1)
else:
  HashedPassword = '!2' + HashedPassword[2:]

if len(Characters) > 1:
  HashedPassword = \
    HashedPassword.replace(Characters[0], Characters[0].upper(), 1)
else:
  HashedPassword = HashedPassword[:-2] +'dD'
  
pyperclip.copy(HashedPassword)
