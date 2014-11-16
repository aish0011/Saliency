# Instructions for Lab9

In this lab, we will build construct a simple saliency stack.  You will need to
reuse code for the (1) pyramid filter and (2) center-surround differences.
These is the culmination of all the previous Java-based labs on building pieces of the saliency processing logic.

1. Develop the Java program and run them. The saliency code will do this
> 	ant 
> 	java -cp ./build/classes sg.edu.ntu.sce.Saliency donald_duck_in.bmp donald_duck_out
> 	java -cp ./build/classes sg.edu.ntu.sce.Saliency test_red.bmp test_out test_saliency

2. Run the JUnit that will be used to grade your output. If your results are
bit-exact matches you will get a full grade. <font color="red">CAUTION: I will not be releasing the JUnit reference image until 1 day before the deadline. This is your final lab assignment, and I expect this to be a test of the skills you have picked up along the semester. Pay close attention to the instructions</font>
> 	java -cp ./build/classes:/usr/share/java/junit.jar junit.textui.TestRunner sg.edu.ntu.sce.Lab9JUnit

3. Submit your code
> 	git add -A
> 	git commit -a -m "this lab is so easy, sandra bullock can do it while spinning in orbit"
> 	git push origin master

