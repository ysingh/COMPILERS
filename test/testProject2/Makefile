ANTLR := /usr/local/lib/antlr-4.11.1-complete.jar
BUILD_DIR := build
COMPILER_JAR := tigerc.jar
GRAMMAR := Tiger.g4
JAR_DIR := cs8803_bin
MAIN_CLASS_NAME := Main
SUBMISSION := submission.zip

ANTLR_JAVA_FILES := \
	src/TigerBaseListener.java \
	src/TigerBaseVisitor.java \
	src/TigerLexer.java \
	src/TigerListener.java \
	src/TigerParser.java \
	src/TigerVisitor.java

ANTLR_FILES := \
	src/Tiger.interp \
	src/Tiger.tokens \
	src/TigerLexer.interp \
	src/TigerLexer.tokens \
	$(ANTLR_JAVA_FILES)

ANTLR_LIBS := \
	$(BUILD_DIR)/javax \
	$(BUILD_DIR)/org

SOURCES := \
	src/*.java

.PHONY:
all : $(COMPILER_JAR)

$(COMPILER_JAR): $(SOURCES) $(ANTLR_JAVA_FILES) $(ANTLR_LIBS)
	mkdir -p $(BUILD_DIR) $(JAR_DIR)
	javac -d $(BUILD_DIR) -cp "src:$(ANTLR)" $(SOURCES) $(ANTLR_JAVA_FILES)
	cd $(BUILD_DIR) && jar cfe ../$(JAR_DIR)/$(COMPILER_JAR) $(MAIN_CLASS_NAME) *.class org javax && cd ..

$(ANTLR_JAVA_FILES): $(GRAMMAR)
	java -jar $(ANTLR) -o src -visitor $(GRAMMAR)

$(ANTLR_LIBS):
	mkdir -p $(BUILD_DIR)
	cd $(BUILD_DIR) && jar xf $(ANTLR) && cd ..
	rm -rf $(BUILD_DIR)/META-INF

.PHONY:
submision: clean
	zip -r $(SUBMISSION) $(SOURCES) $(GRAMMAR) Makefile

.PHONY:
clean:
	rm -rf $(ANTLR_LIBS)
	rm -f $(SUBMISSION) \
	$(JAR_DIR)/$(COMPILER_JAR) \
	$(ANTLR_FILES) \
	$(BUILD_DIR)/*.class \