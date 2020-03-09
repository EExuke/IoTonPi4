############################################################################# ##
# Copyright (C) 2019-2020 Cameo Communications, Inc.
############################################################################# ##
#
# --------------------------------------------------------------------------
#     AUTHOR                   : EExuke
#     FILE NAME                : HC-SR04.py
#     FILE DESCRIPTION         : Python file
#     FIRST CREATION DATE      : 2020/03/09
# --------------------------------------------------------------------------
#     Version                  : 1.0
#     Last Change              : 2020/03/09
## ************************************************************************** ##

#-----------------------------------------------------------
#                  MAIN PROCESS
#-----------------------------------------------------------
import RPi.GPIO as GPIO
import time

def checkdist():
    #发出触发信号
    GPIO.output(22,GPIO.HIGH)
    #保持 10us 以上（我选择 15us）
    time.sleep(0.000015)
    GPIO.output(22,GPIO.LOW)
    while not GPIO.input(23):
        pass
    #发现高电平时开时计时
    t1 = time.time()
    while GPIO.input(23):
        pass
    #高电平结束停止计时
    t2 = time.time()
    #返回距离， 单位为米
    return (t2-t1)*340/2

GPIO.setmode(GPIO.BCM)
#第 15 号针， GPIO22
GPIO.setup(22, GPIO.OUT, initial=GPIO.LOW)
#第 16 号针， GPIO23
GPIO.setup(23,GPIO.IN)
time.sleep(2)
try:
    while True:
        print("Distance: %0.2f m" %checkdist())
        time.sleep(3)
except KeyboardInterrupt:
    GPIO.cleanup()

