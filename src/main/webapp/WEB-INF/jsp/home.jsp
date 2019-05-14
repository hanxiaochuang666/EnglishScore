<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>英语成绩查询</title>
    <jsp:include page="public-home.jsp"></jsp:include>
</head>
<body>
<center>
    <table class="easyui-datagrid" id="tt" style="width:800px"
           data-options="rownumbers:true,singleSelect:true,toolbar:'#tb'">
    </table>
    <%--查询条件--%>
    <div id="tb" style="padding:2px 5px;">
        <div style="margin-bottom:5px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="downFile()">模板下载</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="uploadExcel();">Excel导入学生成绩</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
               onclick="deleteScore()">删除</a>
        </div>
        <div>
            <span>学号：</span>
            <input id="studentId" class="easyui-validatebox" data-options="required:true" required
                   style="line-height:26px;border:1px solid #ccc">
            <span>姓名:</span>
            <input id="name" class="easyui-validatebox" data-options="required:true" required
                   style="line-height:26px;border:1px solid #ccc">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">查询</a>
        </div>

    </div>

    <div id="uploadonlineinfo" class="easyui-window" style="width:380px;padding:10px 40px" closed="true">
        <form id="fam" method="post" enctype="multipart/form-data">
            <table border="0" style="margin-top:4px;" width="100%" align="center">
                <tr>
                    <td>
                        <input class="easyui-filebox" name="efile" id="efile" data-options="prompt:'文件上传'"
                               style="width:100%"/>
                    </td>

                </tr>
            </table>
        </form>

        <div data-options="region:'south',border:false" style="text-align:center;padding:5px 0 0;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
               onclick="uploadonline();" style="width:80px">上传</a>
        </div>
    </div>
</center>
<script type="text/javascript">

    function uploadExcel() {
        $('#uploadonlineinfo').window('open').dialog('setTitle', '文件上传');
    }

    function uploadonline() {
        var add = "importExcel";
        $('#fam').form('submit', {
            url: add,
            onSubmit: function () {

            },
            success: function (result) {
                var result = eval('(' + result + ')');

                if (result.success) {
                    if($("#fam")){
                        $("#fam").resetForm();
                    }
                    //document.getElementById('fam')&&document.getElementById('fam').reset();
                    $('#uploadonlineinfo').window('close');
                    $.messager.alert({
                        title: 'Success',
                        msg: '上传成功'
                    });
                    $('#tt').datagrid('reload');
                } else {
                    $.messager.error()({
                        title: 'Error',
                        msg: '文件内容出错，请检查文件是否符合标准模板！'
                    });
                }
            }
        });
    }

    function doSearch() {

        // 校验一下必填项
        var studentId = $('#studentId').val();
        var name = $('#name').val();
        if (jQuery.trim(studentId) == "") {
            $.messager.alert({
                title: 'warn',
                msg: '学号不能为空！'
            });
            return false;
        }
        if (jQuery.trim(name) == "") {
            $.messager.alert({
                title: 'warn',
                msg: '姓名不能为空！'
            });
            return false;
        }
        $('#tt').datagrid({
            title: '北京语言大学网络教育学院学位英语成绩导入',
            url: 'getScoreList',
            emptyMsg: '列表为空',
            method:'get',
            loadMsg: '正在加载数据',
            columns: [[
                {field: 'id', title: 'ID', align: 'center'},
                {field: 'batchId', title: '考试批次', align: 'center'},
                {field: 'studentId', title: '学号', align: 'center'},
                {field: 'name', title: '姓名', align: 'center'},
                {field: 'sex', title: '性别', align: 'center'},
                {field: 'major', title: '所属专业', align: 'center'},
                {field: 'score', title: '分数', align: 'center'},
                {field: 'scoreStatus', title: '成绩状态', align: 'center'},
                {field: 'violationFlag', title: '违规标识', align: 'center'}
            ]]

        });
        $('#tt').datagrid('load', {
            title: '北京语言大学网络教育学院学位英语成绩导入',
            studentId: $('#studentId').val(),
            name: $('#name').val(),
            url: 'getScoreList',
            emptyMsg: '列表为空',
            method:'get',
            columns: [[
                {field: 'id', align: 'center'},
                {field: 'batchId', align: 'center'},
                {field: 'studentId', align: 'center'},
                {field: 'name', align: 'center'},
                {field: 'sex', align: 'center'},
                {field: 'major', align: 'center'},
                {field: 'score', align: 'center'},
                {field: 'scoreStatus', align: 'center'},
                {field: 'violationFlag', align: 'center'}
            ]]
        });
    }

    function downFile() {
        var form = $("<form>");//定义一个form表单
        form.attr("style", "display:none");
        form.attr("target", "");
        form.attr("method", "get");
        form.attr("action", "download");
        var input1 = $("<input>");
        input1.attr("type", "hidden");
        input1.attr("name", "filePath");
        input1.attr("value", "English.xls");
        $("body").append(form);//将表单放置在web中
        form.append(input1);
        form.submit();//表单提交
        form.remove();
    }

    /*删除行*/
    function deleteScore() {
        var row = $('#tt').datagrid('getSelected');
        if (row) {
            $.messager.confirm('确认', '确定删除？', function(r){
                if (r){
                    $.ajax({
                        url: "deleteScore",
                        type: "POST",
                        async: true,
                        dataType: "json",
                        data: {
                            id: row.id,
                            name: row.name,
                            batchId: row.batchId,
                            studentId: row.studentId
                        },
                        success: function (result) {
                            // alert("success!");
                            $('#tt').datagrid('reload');
                        },
                        error: function (xhr) {
                            alert("错误提示： " + xhr.status + " " + xhr.statusText);
                        }
                    });
                }
            });
            // $.messager.alert('Info', row.id + ":" + row.batchId + ":" + row.studentId + ":" + row.name);
        } else {
            $.messager.alert('Info', '您还未选择要删除的数据！');
        }
    }

</script>
</body>
</html>