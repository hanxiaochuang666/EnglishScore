package com.bykj.service;

import com.bykj.dao.StudentScore;

import java.util.ArrayList;
import java.util.Map;

public interface ScoreService {

    ArrayList<StudentScore> getScoreList(Map<String,Object> map);

    void deleteScore(Map<String,Object> map);
}
