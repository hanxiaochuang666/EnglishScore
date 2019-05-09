package com.bykj.service;

import com.bykj.dao.StudentScore;
import com.bykj.mapper.StudentScoreMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @desc
 * @Author：hanchuang
 * @Version 1.0
 * @Date：add on 14:21 2019/5/6
 */

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ScoreServiceImplTest {

    @Autowired
    ScoreServiceImpl scoreService;
    @Autowired
    StudentScoreMapper scoreMapper;
    @Test
    public void getScoreList() {
//        List<StudentScore> studentScore = new ArrayList<>();
//        studentScore = scoreService.getScoreList();
//        studentScore.forEach(s-> System.out.println("输入出数据：==============="+s.toString()));
        StudentScore s = new StudentScore();
        s.setBatchId(201409);
        s.setMajor("英语专业");
        s.setName("davv");
        s.setScore(90);
        s.setScoreStatus("合格");
        s.setSex("男");
        s.setStudentId(2222222L);
        scoreMapper.insert(s);
    }
}