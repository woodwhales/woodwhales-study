package org.woodwhales.guava.compare;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author woodwhales on 2020-08-10
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;
    private Integer age;
    private String address;

    public int compareToNullsLast(Student student) {
        return ComparisonChain.start()
                                .compare(this.age, student.getAge())
                                .compare(this.address, student.getAddress(), Ordering.natural().nullsLast())
                                .result();
    }

    public int compareToNullsFirst(Student student) {
        return ComparisonChain.start()
                .compare(this.age, student.getAge())
                .compare(this.address, student.getAddress(), Ordering.natural().nullsFirst())
                .result();
    }

}
