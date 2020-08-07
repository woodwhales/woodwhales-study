package org.woodwhales.swagger.controller.bg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.woodwhales.swagger.base.ResponseResult;
import org.woodwhales.swagger.response.Student;

import java.util.Date;

/**
 * @author woodwhales on 2020-08-07
 * @description
 */
@RequestMapping("/bg")
@RestController
public class BgAdminController {

    @GetMapping("getStudent")
    public ResponseResult<Student> getStudent(@RequestParam String studentId) {
        Student student = new Student();
        student.setId("1");
        student.setStuName("张三");
        student.setStuAge(18);
        student.setStuAddress("北京市");
        student.setCreateTime(new Date());
        student.setUpdateTime(new Date());
        return ResponseResult.success(student);
    }

}
