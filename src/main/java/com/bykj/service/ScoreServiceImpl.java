package com.bykj.service;

import com.bykj.dao.StudentScore;
import com.bykj.dao.StudentScoreExample;
import com.bykj.mapper.StudentScoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    StudentScoreMapper studentScoreMapper;

    @Override
    public ArrayList<StudentScore> getScoreList(Map<String,Object> map) {

        StudentScoreExample scoreExample = new StudentScoreExample();
        scoreExample.createCriteria().andStudentIdEqualTo(Long.parseLong(String.valueOf(map.get("studentId"))))
                .andNameEqualTo(String.valueOf(map.get("name"))).andStatusEqualTo(1);
        return (ArrayList<StudentScore>) studentScoreMapper.selectByExample(scoreExample);
    }

    @Override
    public void deleteScore(Map<String, Object> map) {
        StudentScore studentScore = new StudentScore();
        studentScore.setStatus(0);
        studentScore.setId(Integer.parseInt(String.valueOf(map.get("id"))));
        studentScoreMapper.updateByPrimaryKeySelective(studentScore);
    }
}
