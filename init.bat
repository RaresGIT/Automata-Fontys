PATH=%PATH%;"C:\Program Files\Java\jdk-16\bin"
SET CLASSPATH=%CLASSPATH%;obj;antlr-4.9.2-complete.jar
doskey a4=java org.antlr.v4.Tool Example2.g4 -no-listener -visitor -o gen
doskey jc=javac gen\Example2*.java Main.java -d obj
doskey grun=java org.antlr.v4.gui.TestRig Example2 start2 -gui input.txt
doskey run=java Main $L input.txt
doskey clean=del /Q gen\* obj\*