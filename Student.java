package com.certiflow;

public class Student {  
    int rollno;  
    String name;  
    static String college = "XYZ";  

    // Static method to change the value of static variable
    public static void changeCollege(String newCollege) {  
        college = newCollege;  
    }  

    // Constructor to initialize the variables
    public Student(int r, String n) {  
        rollno = r;  
        name = n;  
    }  

    // Method to display values returns it as string so UI can display it
    public String display() {
        String output = "Roll Number: " + rollno + ", Name: " + name + ", College: " + college;
        System.out.println(output);
        return output;
    }  
}
