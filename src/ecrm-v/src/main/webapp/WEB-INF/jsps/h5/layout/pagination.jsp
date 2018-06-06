<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
            <div class="am-cf">
              <span id="count">共${listCount}条记录</span>
              <div class="am-fr">
                <ul data-am-widget="pagination" class="am-pagination am-pagination-default">

                  <c:if test="${pageIndex > 1}">
                    <li class="am-pagination-prev "><a href="javascript:prevPage();" class="">上一页</a></li>
                  </c:if>

                  <li class="am-pagination-select">
                    <c:choose>
                      <c:when test="${pageNum > 0}">
                        <select id="pageIndex">
                          <c:forEach var="i" begin="1" end="${pageNum}">
                            <option value="${i}" ${pageIndex == i ? 'selected="selected"' : ''} >${i}/${pageNum}</option>
                          </c:forEach>
                        </select>
                       </c:when>
                       <c:otherwise>
                         <li class="">0 / 0</li>
                       </c:otherwise>
                    </c:choose>
                  </li>

                  <c:if test="${pageIndex < pageNum}">
                    <li class="am-pagination-next "><a href="javascript:nextPage();" class="">下一页</a></li>
                  </c:if>

                  <input type="hidden" name="pageIndex" value="${pageIndex}"/>
                  <input type="hidden" name="limit" value="${limit}"/>
                  <input type="hidden" name="field" value="${field}"/>
                  <input type="hidden" name="direction" value="${direction}"/>
                </ul>
              </div>
            </div>

	<hr />
	<p>H5版后台显示数据有限，若想查看详细数据，请登录PC后台查看 !</p>
<script type="text/javascript">
$("button[type='submit']").click(function(){
	$("input[name='pageIndex']").val('1');
	$("form").submit();
});
$("#pageIndex").change(function(){
	var pageIndex = $(this).val();
	console.log(pageIndex);
	$("input[name='pageIndex']").val(pageIndex);
	$("form").submit();
});
function prevPage() {
	var pageIndex = $("input[name='pageIndex']").val();
	console.log(pageIndex--);
	$("input[name='pageIndex']").val(pageIndex);
	$("form").submit();
}
function nextPage() {
	var pageIndex = $("input[name='pageIndex']").val();
	console.log(pageIndex++);
	$("input[name='pageIndex']").val(pageIndex);
	$("form").submit();
}
</script>