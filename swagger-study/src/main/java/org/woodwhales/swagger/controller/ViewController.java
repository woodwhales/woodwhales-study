package org.woodwhales.swagger.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.woodwhales.swagger.base.ResponseResult;
import org.woodwhales.swagger.param.TeacherRequestBody;
import org.woodwhales.swagger.response.Teacher;

import java.util.Date;

/**
 * @author woodwhales on 2020-08-07
 * @description
 */
@RestController
@RequestMapping("/view")
public class ViewController {

    @ApiOperation(value = "根据教师编号查询教师", notes = "根据教师编号查询教师，不存在则返回空")
    @GetMapping("getTeacher")
    public ResponseResult<Teacher> getStudent(@RequestParam String teacherId) {
        Teacher teacher = new Teacher();
        teacher.setId("2");
        teacher.setTeacherName("张三");
        teacher.setTeacherAge(18);
        teacher.setTeacherAddress("北京市");
        teacher.setCreateTime(new Date());
        teacher.setUpdateTime(new Date());
        return ResponseResult.success(teacher);
    }

    @ApiOperation(value = "创建教师")
    @PostMapping("createTeacher")
    public ResponseResult<Void> createTeacher(@Validated @RequestBody TeacherRequestBody requestBody) {
        return ResponseResult.success();
    }

}
