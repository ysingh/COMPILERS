SOURCEPATH= --source-path src
JAVAC=javac $(SOURCEPATH) -d bin

sources = $(wildcard **/*.java)
$(info source is $(sources))

classes = $(sources:.java=.class)
$(info classes is $(classes))

all: program

run:
	java -cp bin Main

program: $(classes)

clean:
	rm -f *.class

%.class: %.java
	$(JAVAC) $<

jar: $(classes)
	jar cvf program.jar $(classes)

.PHONY: all program clean jar
