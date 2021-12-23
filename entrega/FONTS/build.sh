# Set the name of the executable JAR
jar_name='recomenador'
# export PATH_TO_FX='lib/javafx-sdk-17.0.1-linux/lib'

# jlink --module-path lib/javafx-jmods-17.0.1/ --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.web,javafx.media,javafx.swing --bind-services --output out

# List all source files
find . -name "*.java" > sources.txt 2> /dev/null

echo "Building project..."
# javac @sources.txt -d out --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml || echo "Compilation failed!"
javac @sources.txt -cp .:lib -d out || echo "Compilation failed!"

# Create an executable JAR file with the application
while true; do
    read -p "Do you want to create an executable JAR? [Y/n]: " Ans
    case $Ans in
        Y*|y*) Ans='YES'; break;;
        N*|n*) Ans='NO'; break;;
        *) echo "Please answer Yes or No.";;
    esac
done
echo "You answered ${Ans}."
if [ $Ans = 'YES' ]; then
    cd out
    find . -name "*.class" > temp.txt 2> /dev/null
    echo "Generating JAR..."
    echo "Manifest-Version: 1.0" > MANIFEST.mf
    echo "Class-Path: .:lib:libs" >> MANIFEST.mf
    echo "Main-Class: Launcher" >> MANIFEST.mf
    jar cmvf MANIFEST.mf ${jar_name}.jar @temp.txt \
        && mv *.jar .. \
        && echo "" \
        && echo "JAR has been created, you can execute it with 'java -jar ${jar_name}.jar'."
fi
