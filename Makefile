SRCDIR = pw/koj/jetstreem/

all: $(SRCDIR)parser/Parser.class

$(SRCDIR)parser/Parser.class: $(SRCDIR)/parser/Parser.java
	javac $(SRCDIR)parser/Parser.java

