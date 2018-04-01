<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well" data-orginal-title>
			<h2>
				<i class="icon-th"></i> 公告栏
			</h2>
			<div class="box-icon">
				<a
					style="display: block; width: 60px; padding-right: 1px; font-size: 14px;"
					href="/SLSaleSystem/informanage/portalafficheList.html">more >></a>
			</div>
		</div>
		<div class="box-content">
			<table style="width: 100%;">
				<c:if test="${afficheList != null}">
					<c:forEach items="${afficheList}" var="affiche">
						<tr>
							<td style="width: 20px; padding: 3px;"><span
								class="icon icon-color icon-info" /></td>
							<td style=""><a
								href="/SLSaleSystem/informanage/portalAfficheDetail.html?id=${affiche.id}">${affiche.title}</a></td>
							<td style="width: 80px;"><fmt:formatDate
									value="${affiche.publishTime}" pattern="yyyy-MM-dd" /></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
	</div>
	<!--/span-->
</div>

<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well" data-orginal-title>
			<h2>
				<i class="icon-file"></i> 资讯栏
			</h2>
			<div class="box-icon">
				<a
					style="display: block; width: 60px; padding-right: 1px; font-size: 14px;"
					href="/SLSaleSystem/informanage/portalinfoList.html">more >></a>
			</div>
		</div>
		<div class="box-content">
			<table style="width: 100%;">
				<c:if test="${infoList != null}">
					<c:forEach items="${infoList}" var="info">
						<tr>
							<td style="width: 20px; padding: 3px;"><span
								class="icon icon-color icon-info" /></td>
							<td style=""><a
								href="/SLSaleSystem/informanage/portalInfoDetail.html?id=${info.id}">${info.title}</a></td>
							<td style="width: 80px;"><fmt:formatDate
									value="${info.publishTime}" pattern="yyyy-MM-dd" /></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
	</div>
	<!--/span-->
</div>

<!-- content ends -->
</div>
<!--/#content.span10-->
</div>
<!--/fluid-row-->

<hr>

<div class="modal hide fade" id="myModal">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>Settings</h3>
	</div>
	<div class="modal-body">
		<p>Here settings can be configured...</p>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn" data-dismiss="modal">Close</a> <a href="#"
			class="btn btn-primary">Save changes</a>
	</div>
</div>

<%@include file="/WEB-INF/pages/common/foot.jsp"%>
