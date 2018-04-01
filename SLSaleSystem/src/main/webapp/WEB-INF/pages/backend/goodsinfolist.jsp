<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/head.jsp"%>
<div>
	<ul class="breadcrumb">
		<li><a href="#">后台管理</a> <span class="divider">/</span></li>
		<li><a href="/SLSaleSystem/backend/goodsinfolist.html">商品管理</a></li>
	</ul>
</div>
<div class="row-fluid sortable">
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2>
				<i class="icon-user"></i> 商品列表
			</h2>
			<div class="box-icon">
				<span class="icon32 icon-color icon-add custom-setting addgoodsinfo" />
			</div>
		</div>

		<div class="box-content">
			<form action="/SLSaleSystem/backend/goodsinfolist.html" method="post">
				<div class="searcharea">
					商品名称: <input type="text" name="s_goodsName" value="${s_goodsName}" />
					状态： <select name="s_state" style="width: 100px;">
						<option value="" selected="selected">--请选择--</option>
						<c:if test="${s_state == 1}">
							<option value="1" selected="selected">上架</option>
							<option value="2">下架</option>
						</c:if>
						<c:if test="${s_state == 2}">
							<option value="2" selected="selected">下架</option>
							<option value="1">上架</option>
						</c:if>
						<c:if test="${s_state == null || s_state == ''}">
							<option value="2">下架</option>
							<option value="1">上架</option>
						</c:if>
					</select>
					<button type="submit" class="btn btn-primary">
						<i class="icon-search icon-white"></i> 查询
					</button>
				</div>
			</form>
			<table
				class="table table-striped table-bordered bootstrap-datatable datatable">
				<thead>
					<tr>
						<th>商品名称</th>
						<th>市场价(元)</th>
						<th>优惠价(元)</th>
						<th>库存量</th>
						<th>状态(上架/下架)</th>
						<th>最后更新时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${page.items != null }">
						<c:forEach items="${page.items }" var="goodsInfo">
							<tr>
								<td class="center">${goodsInfo.goodsName }</td>
								<td class="center">${goodsInfo.marketPrice }</td>
								<td class="center">${goodsInfo.realPrice }</td>
								<td class="center">${goodsInfo.num }</td>
								<td class="center"><input type="checkbox"
									title="直接勾选修改状态，立即生效" data-rel="tooltip" class="modifyState"
									state="${goodsInfo.state}" id="${goodsInfo.id}"
									<c:if test="${goodsInfo.state == 1}">checked="true"</c:if> />
								</td>
								<td class="center"><fmt:formatDate
										value="${goodsInfo.lastUpdateTime }" pattern="yyyy-MM-dd" /></td>
								<td class="center"><a class="btn btn-success viewgoodsInfo"
									href="#" id="${goodsInfo.id }"> <i
										class="icon-zoom-in icon-white"></i> 查看
								</a> <a class="btn btn-info modifygoodsInfo" href="#"
									id="${goodsInfo.id }"> <i class="icon-edit icon-white"></i>
										修改
								</a> <a class="btn btn-danger delgoodsInfo" href="#"
									id="${goodsInfo.id }" goodsSN="${goodsInfo.goodsSN}"> <i
										class="icon-trash icon-white"></i> 删除
								</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>

			<div class="pagination pagination-centered">
				<ul>
					<c:choose>
						<c:when test="${page.page == 1 }">
							<li class="active"><a href="javascript:void();" title="首页">首页</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="/SLSaleSystem/backend/goodsinfolist.html?currentpage=1&s_loginCode=${s_loginCode }&s_referCode=${s_referCode}&s_roleId=${s_roleId}&s_isStart=${s_isStart}"
								title="首页">首页</a></li>
						</c:otherwise>
					</c:choose>
					<c:if test="${page.prevPages != null }">
						<c:forEach items="${page.prevPages}" var="num">
							<li><a
								href="/SLSaleSystem/backend/goodsinfolist.html?currentpage=${num }&s_loginCode=${s_loginCode }&s_referCode=${s_referCode}&s_roleId=${s_roleId}&s_isStart=${s_isStart}"
								title="${num }"> ${num }</a></li>
						</c:forEach>
					</c:if>
					<li class="active"><a href="#" title="${page.page }">${page.page }</a>
					</li>
					<c:if test="${page.nextPages != null }">
						<c:forEach items="${page.nextPages}" var="num">
							<li><a
								href="/SLSaleSystem/backend/goodsinfolist.html?currentpage=${num }&s_goodsName=${s_goodsName }&s_state=${s_state}"
								title="${num }"> ${num }</a></li>
						</c:forEach>
					</c:if>
					<c:if test="${page.pageCount != null }">
						<c:choose>
							<c:when test="${page.page == page.pageCount }">
								<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
							</c:when>
							<c:otherwise>
								<li><a
									href="/SLSaleSystem/backend/goodsinfolist.html?currentpage=${page.pageCount }&s_goodsName=${s_goodsName }&s_state=${s_state}"
									title="尾页">尾页</a></li>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${page.pageCount == null }">
						<li class="active"><a href="javascript:void();" title="尾页">尾页</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</div>

<!-- 增加商品的模式窗口 -->
<div class="modal hide fade" id="addGoodsInfoDiv">
	<form action="/SLSaleSystem/backend/addgoodsinfo.html" enctype="multipart/form-data" method="post"
		onsubmit="return addGoodsInfoFunction();">
		<div class="modal-header">
			<button type="button" class="close addgoodsinfocancel"
				data-dismiss="modal">×</button>
			<h3>添加商品信息</h3>
		</div>
		<div class="modal-body">
			<ul id="add_formtip"></ul>
			<ul class="topul">
				<li><label>商品名称：</label><input type="text" id="a_goodsName"
					name="goodsName" /> <span style="color: red; font-weight: bold;">*</span>
				</li>
				<li><label>商品编号：</label><input type="text" id="a_goodsSN"
					name="goodsSN" /> <span style="color: red; font-weight: bold;">*</span>
				</li>
				<li><label>市场价：</label> <input type="text" id="a_marketPrice"
					name="marketPrice" /> <span style="color: red; font-weight: bold;">*</span></li>
				<li><label>优惠价：</label> <input type="text" id="a_realPrice"
					name="realPrice" /> <span style="color: red; font-weight: bold;">*</span></li>
				<li><label>库存量：</label><input type="text" id="a_num" name="num" />
					<span style="color: red; font-weight: bold;">*</span></li>
				<li><label>单位：</label> <input type="text" id="a_unit"
					name="unit" /><span style="color: red; font-weight: bold;">*</span>
				</li>
				<li><label>状态：</label> <input type="radio" 
					name="state" checked="checked" value="1" />上架 <input type="radio"
					 name="state" value="2" />下架</li>
			</ul>
			<div class="clear"></div>

			<ul class="downul">
				<li><span>商品规格：</span> <br /> <textarea class="cleditor"
						id="a_goodsFormat" name="goodsFormat" rows="3"></textarea></li>
			</ul>
			<ul class="downul">
				<li><span>商品说明：</span> <br /> <textarea class="cleditor"
						id="a_note" name="note" rows="3"></textarea></li>
			</ul>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn addgoodsinfocancel" data-dismiss="modal">取消</a>
			<input type="submit" class="btn btn-primary" value="保存" />
		</div>
	</form>
</div>


<!-- 用户详情信息的模式窗口 -->
<div class="modal hide fade" id="viewGoodsInfoDiv">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h3>查看商品信息</h3>
	</div>
	<div class="modal-body">
		<input id="v_id" type="hidden" value="" />
		<ul class="topul">
			<li><label>商品名称：</label> <input type="text" id="v_goodsName"
				readonly="readonly" /></li>
			<li><label>商品编号：</label> <input type="text" id="v_goodsSN"
				readonly="readonly" /></li>
			<li><label>市场价：</label> <input type="text" id="v_marketPrice"
				readonly="readonly" /></li>
			<li><label>优惠价：</label> <input type="text" id="v_realPrice"
				readonly="readonly" /></li>
			<li><label>库存量：</label> <input type="text" id="v_num" readonly="readonly" />
			</li>
			<li><label>单位：</label> <input type="text" id="v_unit" readonly="readonly" />
			</li>
			<li><label>状态:</label> <span id="v_state"></span></li>
		</ul>
		<div class="clear"></div>
		<ul class="downul">
			<li>
				<span>商品规格：</span> <br/><div id="v_goodsFormat" readonly="readonly" rows="3"></div>
			</li>
	    </ul>
		<ul class="downul">
			<li>
				<span>商品说明：</span> <br/><div id="v_note" readonly="readonly" rows="3"></div>
			</li>
	    </ul>
	</div>
	<div class="modal-footer">
		<a href="#" class="btn viewgoodsinfocancel" data-dismiss="modal">关闭</a>
	</div>

</div>

<!-- 修改商品的模式窗口 -->
<div class="modal hide fade" id="modifyGoodsInfoDiv">
	<form action="/SLSaleSystem/backend/modifygoodsinfo.html" method="post"
		onsubmit="return modifyGoodsInfoFunction();">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>修改商品信息</h3>
		</div>
		<div class="modal-body">
			<ul id="modify_formtip"></ul>
			<input id="m_id" type="hidden" name="id" />
			<ul class="topul">
				<li><label>商品名称：</label> <input type="text" id="m_goodsName"
					name="goodsName" /> <span style="color: red; font-weight: bold;">*</span>
				</li>
				<li><label>商品编号：</label> <input type="text" id="m_goodsSN"
					name="goodsSN" /> <span style="color: red; font-weight: bold;">*</span>
				</li>
				<li><label>市场价：</label> <input type="text" id="m_marketPrice"
					name="marketPrice" /> <span style="color: red; font-weight: bold;">*</span>
				</li>
				<li><label>优惠价：</label> <input type="text" id="m_realPrice"
					name="realPrice" /> <span style="color: red; font-weight: bold;">*</span>
				</li>
				<li><label>库存量：</label> <input type="text" id="m_num"
					name="num" /> <span style="color: red; font-weight: bold;">*</span>
				</li>
				<li><label>单位：</label> <input type="text" id="m_unit"
					name="unit" /> <span style="color: red; font-weight: bold;">*</span>
				</li>
			</ul>
			<div class="clear"></div>
			<ul class="downul">
				<li id="m_state"></li>
				<li id="m_goodsFormatli"></li>
			</ul>
			<ul class="downul">
				<li id="m_noteli"></li>
			</ul>
		</div>

		<div class="modal-footer">
			<a href="#" class="btn modifygoodsinfocancel" data-dismiss="modal">取消</a>
			<input type="submit" class="btn btn-primary" value="保存" />
		</div>
	</form>
</div>


<%@include file="/WEB-INF/pages/common/foot.jsp"%>
<script type="text/javascript"
	src="<%=path%>/statics/localjs/goodsinfolist.js"></script>