package AVLTree.src;

import java.util.ArrayList;
import java.util.Collections;

public class Student implements Comparable<Student> {
    public int age;
    public String name = null;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public int compareTo(Student o) {
        if(this.age-o.age != 0)
             return this.age-o.age > 0 ?1:-1;
        return 0;
    }


}
