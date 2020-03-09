############################################################################# ##
# Copyright (C) 2019-2020 Cameo Communications, Inc.
############################################################################# ##
#
# --------------------------------------------------------------------------
#     AUTHOR                   : EExuke
#     FILE NAME                : HC-SR501.py
#     FILE DESCRIPTION         : Python file
#     FIRST CREATION DATE      : 2020/03/09
# --------------------------------------------------------------------------
#     Version                  : 1.0
#     Last Change              : 2020/03/09
## ************************************************************************** ##

#-----------------------------------------------------------
#                  MAIN PROCESS
#-----------------------------------------------------------
# 基于Python GPIO库
import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(12, GPIO.IN)
GPIO.setup(16, GPIO.OUT)

try:
    while True:
        if GPIO.input(12) == 1:
            print("Some people here!")
            GPIO.output(16, GPIO.HIGH)
        else:
            print("Nobody!")
            GPIO.output(16, GPIO.LOW)
        time.sleep(.1)
except KeyboardInterrupt:
    GPIO.cleanup()
    print("All Cleanup!")

