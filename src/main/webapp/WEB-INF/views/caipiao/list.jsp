<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>北京快8列表</title>
    <%@ include file="/WEB-INF/views/include/easyui.jsp" %>
    <script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
    <div>
        <form id="searchFrom" action="">
            <input type="text" name="filter_LIKES_operator" class="easyui-validatebox"
                   data-options="width:150,prompt: '操作人'"/>
            <input type="text" name="filter_EQL_qs" class="easyui-validatebox" data-options="width:150,prompt: '期数'"/>
            <input type="text" name="filter_GED_addTime" class="easyui-my97" datefmt="yyyy-MM-dd"
                   data-options="width:150,prompt: '开始日期'"/>
            - <input type="text" name="filter_LED_addTime" class="easyui-my97" datefmt="yyyy-MM-dd"
                     data-options="width:150,prompt: '结束日期'"/>
            <span class="toolbar-item dialog-tool-separator"></span>
            <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
        </form>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-suppliers" plain="true"
           onclick="openNum()">添加开奖</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-suppliers" plain="true"
           onclick="detailNum()">开奖详情</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-suppliers" plain="true"
           onclick="delNum()">删除开奖</a>
    </div>
</div>
<table id="dg"></table>
<div id="dlg"></div>
<div id="del_bjk8"></div>
<script type="text/javascript">
    var dg;
    var d;
    $(function () {
        dg = $('#dg').datagrid({
            method: "get",
            url: '${ctx}/${requestUrl}',
            fit: true,
            fitColumns: true,
            border: false,
            idField: 'id',
            striped: true,
            pagination: true,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50],
            singleSelect: true,
            columns: [[
                {field: 'id', title: 'id', hidden: true},
                {field: 'qs', title: '期数', sortable: true, width: 100},
                {field: 'kj', title: '开奖结果', sortable: true, width: 450},
                {
                    field: 'addTime', title: '开奖时间', sortable: true, width: 100,
                    formatter: function (value, row, index) {
                        return jQuery.format.date(value, "yyyy-MM-dd HH:mm:ss");
                    }
                },
                {field: 'operator', title: '操作人', sortable: true, width: 100},
                {field: 'operatorIp', title: '操作人ip', sortable: true, width: 100},
                {
                    field: 'flag', title: '状态', sortable: true,
                    formatter: function (value, row, index) {
                        return value == 1 ? '已开奖' : '开奖中';
                    }
                }
            ]],
            headerContextMenu: [
                {
                    text: "冻结该列", disabled: function (e, field) {
                        return dg.datagrid("getColumnFields", true).contains(field);
                    },
                    handler: function (e, field) {
                        dg.datagrid("freezeColumn", field);
                    }
                },
                {
                    text: "取消冻结该列", disabled: function (e, field) {
                        return dg.datagrid("getColumnFields", false).contains(field);
                    },
                    handler: function (e, field) {
                        dg.datagrid("unfreezeColumn", field);
                    }
                }
            ],
            enableHeaderClickMenu: true,
            enableHeaderContextMenu: true,
            enableRowContextMenu: false,
            toolbar: '#tb'
        });
    });

    //弹窗增加
    function openNum() {
        d = $("#dlg").dialog({
            title: '添加开奖',
            width: 380,
            height: 450,
            href: '${ctx}/${openUrl}',
            maximizable: true,
            modal: true,
            buttons: [{
                text: '确认',
                handler: function () {
                    $("#mainform").submit();
                }
            }, {
                text: '取消',
                handler: function () {
                    d.panel('close');
                }
            }]
        });
    }

    function detailNum() {

        var row = $("#dg").datagrid('getSelected');
        if (row == null) {
            alert('请选中一行数据')
        }
        ;
        var id = row['id'];

        d = $("#dlg").dialog({
            title: '开奖详情',
            width: 380,
            height: 450,
            href: '${ctx}/${detailUrl}?id=' + id,
            maximizable: true,
            modal: true,
            buttons: [{
                text: '确认',
                handler: function () {
                    $("#mainform").submit();
                }
            }, {
                text: '取消',
                handler: function () {
                    d.panel('close');
                }
            }]
        });
    }


    function delNum() {

        var row = $("#dg").datagrid('getSelected');
        if (row == null) {
            alert('请选中一行数据');
            return;
        }
        var id = row['id'];
        var qs = row['qs'];

        d = $("#del_bjk8").dialog({
            title: '删除开奖',
            width: 230,
            height: 100,
            maximizable: true,
            content: '期数:' + qs + ' 是否删除这一期？',
            modal: true,
            buttons: [{
                text: '确认',
                handler: function () {
                    $.ajax(
                        {
                            url: '${ctx}/caipiao/bjk8/delOne',
                            type: "POST",
                            //dataType: "json",
                            // async: false,
                            data: {id: id},
                            success: function () {
                                d.panel('close');
                                cx();
                            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                                // // 状态码
                                // console.log(XMLHttpRequest.status);
                                // // 状态
                                // console.log(XMLHttpRequest.readyState);
                                // // 错误信息
                                // console.log(textStatus);
                                // console.log(errorThrown);

                            }
                        })
                }
            }, {
                text: '取消',
                handler: function () {
                    d.panel('close');
                }
            }]
        });
    }

    //创建查询对象并查询
    function cx() {
        var obj = $("#searchFrom").serializeObject();
        dg.datagrid('load', obj);
    }

</script>
</body>
</html>