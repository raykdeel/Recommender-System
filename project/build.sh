# remove previously compiled files
rm -rf $(find . -name "*.class")

# List all source files
find . -name "*.java" > sources.txt 2> /dev/null

javac @sources.txt -cp lib -d bin || echo "Compilation failed!" && exit
