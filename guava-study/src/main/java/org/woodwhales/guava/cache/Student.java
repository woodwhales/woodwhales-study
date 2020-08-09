package org.woodwhales.guava.cache;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.5 15:00
 * @description:
 */
public class Student {

    private final String name;
    private final String address;
    private final String className;
    private byte[] data = new byte[1024 * 1024];

    public Student(String name, String address, String className) {
        this.name = name;
        this.address = address;
        this.className = className;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", className='" + className + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getClassName() {
        return className;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(String.format("this name = %s will be GC", this.name));
    }
}
