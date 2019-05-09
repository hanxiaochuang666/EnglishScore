package com.bykj.controller;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bykj.dao.StudentScore;
import com.bykj.mapper.StudentScoreMapper;
import com.bykj.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @description
  * @Author hanchuang
  * @Version 1.0
  * @Date add on 2019/5/6
  */

@Controller
@RequestMapping("/")
public class ScoreController  {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private StudentScoreMapper scoreMapper;

    /**
      * @Author hanchuang
      * @Version 1.0
      * @Date add on 2019/5/8
      * @Description
      */
    @RequestMapping(value = "getScoreList", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<StudentScore> getScoreList(HttpServletRequest request, HttpServletResponse response) {

        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("studentId",request.getParameter("studentId"));
        paraMap.put("name",request.getParameter("name"));

        ArrayList<StudentScore> list = scoreService.getScoreList(paraMap);
        list.forEach(studentScore -> logger.info("查询结果列表："+studentScore.toString()));
        return list;
    }


    /**
      * @Author hanchuang
      * @Version 1.0
      * @Date add on 2019/5/8
      * @Description   执行excel导入
      */

    @RequestMapping(value = "importExcel", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String importExcel(@RequestParam(value = "efile") MultipartFile file, HttpServletRequest request) throws Exception{

        Map<String,Object> retMap = new HashMap<>();
        retMap.put("success","true");

        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);// 表头
        importParams.setTitleRows(0);// 标题
        // 需要验证
        importParams.setNeedVerfiy(true);

        try {
            // 使用原生的，可以返回成功条数，失败条数
            ExcelImportResult<StudentScore> result = ExcelImportUtil.importExcelMore(file.getInputStream(), StudentScore.class,
                    importParams);
            List<StudentScore> successList = result.getList();
            result.getFailList();
            // 使用工具
//            List<StudentScore> results = ExcelUtiles.importExcel(file,0,1,StudentScore.class);

            for (StudentScore demoExcel : successList) {
                System.out.println(demoExcel.toString());
                scoreMapper.insertSelective(demoExcel);
            }
        } catch (Exception e) {
            retMap.put("success","false");
            retMap.put("errMsg","文件上传出错！"+e.getMessage());
        }
        return  new JSONObject(retMap).toJSONString();
    }



    /**
     * 下载服务器中的excel文件
     *
     * @param request  Request对象
     * @param response Response对象
     * @return
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public String downloadExcelFile(HttpServletRequest request, HttpServletResponse response) {

        String fileName = request.getParameter("filePath");
        // 1: 找到excel文件
        String storePath = request.getSession().getServletContext().getRealPath("/") + "resources\\";
//        String fileName = "english.xls";

        File file = new File(storePath + fileName);
        if (!file.exists()) {
            throw new RuntimeException("file do not exist");
        }
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        // 重置response
        response.reset();
        //设置http头信息的内容
        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/vnd.ms-excel");
        response.setContentType("application/force=download");
        response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        int fileLength = (int) file.length();
        response.setContentLength(fileLength);

        try {
            if (fileLength != 0) {
                inputStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                // 创建输出流
                servletOutputStream = response.getOutputStream();
                int readLength;
                // 读取文件内容并输出到response的输出流中
                while ((readLength = inputStream.read(buf)) != -1) {
                    servletOutputStream.write(buf, 0, readLength);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("download file error");
        } finally {
            try {
                // 关闭ServletOutputStream
                if (servletOutputStream != null) {
                    servletOutputStream.flush();
                    servletOutputStream.close();
                }
                // 关闭InputStream
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @Author hanchuang
     * @Version 1.0
     * @Date add on 2019/5/8
     * @Description
     */
    @RequestMapping(value = "deleteScore",  method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Map<String,Object> deleteScore(HttpServletRequest request, HttpServletResponse response) throws Exception{

        Map<String,Object> retMap = new HashMap<>();
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("studentId",request.getParameter("studentId"));
        paraMap.put("name",request.getParameter("name"));
        paraMap.put("batchId",request.getParameter("batchId"));
        paraMap.put("id",request.getParameter("id"));
        scoreService.deleteScore(paraMap);
        retMap.put("result","success");
        return retMap;
    }

}