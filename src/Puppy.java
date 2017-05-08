/**
 * Created by tongs on 12/29/2016.
 */
public class Puppy {
    int puppyAge;
    public Puppy(String name){
        System.out.println("狗的名字是:" + name);
    }

    public void setAge(int age){
        puppyAge = age;
    }

    public String getAge(){
        System.out.println("the age of the dog:" + puppyAge);
        String xxx;
        xxx = "s";
        return xxx;
    }

    public static void main(String[] args){
        Puppy myPuppy = new Puppy("tommy");
        String d;
        myPuppy.setAge(2);
        d = myPuppy.getAge();
        System.out.println(d);
        System.out.println("dog:" + myPuppy.puppyAge);
    }
}
