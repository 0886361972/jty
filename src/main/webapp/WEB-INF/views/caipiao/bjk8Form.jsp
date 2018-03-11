<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>北京快8详情</title>
    <%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
    <form id="mainform" action="${ctx}/caipiao/bjk8/drawNum" method="post">
        <table  class="formTable">

            <input name="id" type="hidden"   missingMessage="" class="easyui-numberbox" value="${bjk8.id}"/>

            <tr>
                <td>期数：</td>
                <td>
                    <input name="qs" type="text" min="100"  missingMessage="" class="easyui-numberbox" value="<c:if test="${next!=null}">${next}</c:if><c:if test="${bjk8.qs!=null}">${bjk8.qs}</c:if>" required="required"/>
                </td>
            </tr>
            <tr>
                <td>第一球：</td>
                <td>
                    <input name="qiu1" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu1}" class="easyui-numberbox" required="required"/>
                </td>
            </tr>
            <tr>
                <td>第二球：</td>
                <td>
                    <input name="qiu2" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu2}" class="easyui-numberbox" required="required"/>
                </td>
            </tr>
            <tr>
                <td>第三球：</td>
                <td>
                    <input name="qiu3" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu3}" class="easyui-numberbox" required="required"/>
                </td>
            </tr>
            <tr>
                <td>第四球：</td>
                <td>
                    <input name="qiu4" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu4}" class="easyui-numberbox" required="required"/>
                </td>
            </tr>
            <tr>
                <td>第五球：</td>
                <td>
                    <input name="qiu5" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu5}" class="easyui-numberbox" required="required"/>
                </td>
            </tr>
            <tr>
                <td>第六球：</td>
                <td>
                    <input name="qiu6" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu6}" class="easyui-numberbox" required="required"/>
                </td>
            </tr>
            <tr>
                <td>第七球：</td>
                <td>
                    <input name="qiu7" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu7}" class="easyui-numberbox" required="required"/>
                </td>
            </tr>
            <tr>
                <td>第八球：</td>
                <td>
                    <input name="qiu8" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu8}" class="easyui-numberbox" required="required"/>
                </td>
            </tr>
            <tr>
                <td>第九球：</td>
                <td>
                    <input name="qiu9" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu9}" class="easyui-numberbox" required="required"/>
                </td>
            </tr><tr>
                <td>第十球：</td>
                <td>
                    <input name="qiu10" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu10}" class="easyui-numberbox" required="required"/>
                </td>
            </tr><tr>
                <td>第十一球：</td>
                <td>
                    <input name="qiu11" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu11}" class="easyui-numberbox" required="required"/>
                </td>
            </tr><tr>
                <td>第十二球：</td>
                <td>
                    <input name="qiu12" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu12}" class="easyui-numberbox" required="required"/>
                </td>
            </tr><tr>
                <td>第十三球：</td>
                <td>
                    <input name="qiu13" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu13}" class="easyui-numberbox" required="required"/>
                </td>
            </tr><tr>
                <td>第十四球：</td>
                <td>
                    <input name="qiu14" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu14}" class="easyui-numberbox" required="required"/>
                </td>
            </tr><tr>
                <td>第十五球：</td>
                <td>
                    <input name="qiu15" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu15}" class="easyui-numberbox" required="required"/>
                </td>
            </tr><tr>
                <td>第十六球：</td>
                <td>
                    <input name="qiu16" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu16}" class="easyui-numberbox" required="required"/>
                </td>
            </tr><tr>
                <td>第十七球：</td>
                <td>
                    <input name="qiu17" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu17}" class="easyui-numberbox" required="required"/>
                </td>
            </tr><tr>
                <td>第十八球：</td>
                <td>
                    <input name="qiu18" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu18}" class="easyui-numberbox" required="required"/>
                </td>
            </tr><tr>
                <td>第十九球：</td>
                <td>
                    <input name="qiu19" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu19}" class="easyui-numberbox" required="required"/>
                </td>
            </tr>
            <tr>
                <td>第二十球：</td>
                <td>
                    <input name="qiu20" type="text" min="1" max="80" missingMessage="北京快8开奖号码必须是1到80之间" value="${bjk8.qiu20}" class="easyui-numberbox" required="required"/>
                </td>
            </tr>
            <tr>
                <td>状态</td>
                <td>
                    <c:if test="${!bjk8.flag}">
                      <input name="flag" type="radio" checked="checked" value="0"/><label>开奖中</label>
                      <input name="flag" type="radio" value="1" /><label>已开奖</label>
                    </c:if>
                    <c:if test="${bjk8.flag}">
                        <input name="flag" type="radio"  value="0"/><label>开奖中</label>
                        <input name="flag" type="radio" checked="checked" value="1" /><label>已开奖</label>
                    </c:if>
                </td>

            </tr>

        </table>
    </form>
</div>
<script type="text/javascript">
    $(function(){
        $('#mainform').form({
            onSubmit: function(){
                var isValid = $(this).form('validate');
                return isValid;	// 返回false终止表单提交
            },
            success:function(data){
                successTip(data,dg,d);
            }
        });
    });

</script>
</body>
</html>