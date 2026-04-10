JAVAC = javac
JAVA = java

SRC = Main.java game/*.java

CLASSES = $(SRC:.java=.class)

all: run

compile:
	$(JAVAC) -d bin $(SRC)

run: compile
	$(JAVA) -cp bin Main

clean:
	rm -rf bin/
