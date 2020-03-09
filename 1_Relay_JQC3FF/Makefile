CFILES = src
TARGET = Test

.PHONY:
all: ${TARGET}

Test:Test.java
	javac -classpath .:classes:/opt/pi4j/lib/'*' -d . Test.java


run:
	sudo java -classpath .:classes:/opt/pi4j/lib/'*' Test
