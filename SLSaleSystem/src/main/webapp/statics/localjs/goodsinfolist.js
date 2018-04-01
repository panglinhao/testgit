$('#a_goodsFormat').cleditor();
$('#a_note').cleditor();
//增加商品信息验证
function addGoodsInfoFunction(){
	$("#add_formtip").html("");
	var result = true;
	
	if($.trim($("#a_goodsName").val()) == "" || $("#a_goodsName").val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，商品名称不能为空。</li>");
		result = false;
	}
	
	if($.trim($("#a_goodsSN").val()) == "" || $("#a_goodsSN").val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，商品编号不能为空。</li>");
		result = false;
	}else{
		if($("#add_formtip").attr("key") == "1"){
			$("#add_formtip").append("<li>对不起，该编号已存在。</li>");
			result = false;
		}
	}
	var a = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/;//匹配正浮点数
	var b = /^[1-9]\d*$/;//匹配正整数
	if($("#a_marketPrice").val() == ""){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，市场价不能为空。</li>");
		result = false;
	}else{
		if(!a.test($("#a_marketPrice").val()) && !b.test($("#a_marketPrice").val())){
			$("#add_formtip").css("color","red");
			$("#add_formtip").append("<li>对不起，市场价必须是大于零的数。</li>");
			result = false;
		}
	}
	
	if($("#a_realPrice").val() == ""){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，优惠价不能为空。</li>");
		result = false;
	}else{
		if(!a.test($("#a_realPrice").val()) && !b.test($("#a_realPrice").val())){
			$("#add_formtip").css("color","red");
			$("#add_formtip").append("<li>对不起，优惠价必须是大于零的数。</li>");
			result = false;
		}
	}
	
	if($("#a_num").val() == ""){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，库存量不能为空。</li>");
		result = false;
	}else{
		if(!b.test($("#a_num").val())){
			$("#add_formtip").css("color","red");
			$("#add_formtip").append("<li>对不起，库存量必须大于0。</li>");
			result = false;
		}
	}
	
	if(result == true) alert("添加成功 ^_^");
	return result;
}

function modifyGoodsInfoFunction(){
	$("#modify_formtip").html("");
	var result = true;
	
	if($.trim($("#m_goodsName").val()) == "" || $("#m_goodsName").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，商品名称不能为空。</li>");
		result = false;
	}
	
	if($.trim($("#m_goodsSN").val()) == "" || $("#m_goodsSN").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，商品编号不能为空。</li>");
		result = false;
	}else{
		if($("#modify_formtip").attr("key") == "1"){
			$("#modify_formtip").append("<li>对不起，该编号已存在。</li>");
			result = false;
		}
	}
	var a = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/;//匹配正浮点数
	var b = /^[1-9]\d*$/;//匹配正整数
	if($("#m_marketPrice").val() == ""){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，市场价不能为空。</li>");
		result = false;
	}else{
		if(!a.test($("#m_marketPrice").val()) && !b.test($("#a_marketPrice").val())){
			$("#modify_formtip").css("color","red");
			$("#modify_formtip").append("<li>对不起，市场价必须是大于零的数。</li>");
			result = false;
		}
	}
	
	if($("#m_realPrice").val() == ""){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，优惠价不能为空。</li>");
		result = false;
	}else{
		if(!a.test($("#m_realPrice").val()) && !b.test($("#a_realPrice").val())){
			$("#modify_formtip").css("color","red");
			$("#modify_formtip").append("<li>对不起，优惠价必须是大于零的数。</li>");
			result = false;
		}
	}
	
	if($("#m_num").val() == ""){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，库存量不能为空。</li>");
		result = false;
	}else{
		if(!b.test($("#m_num").val())){
			$("#modify_formtip").css("color","red");
			$("#modify_formtip").append("<li>对不起，库存量必须大于0。</li>");
			result = false;
		}
	}
	
	if(result == true) alert("修改成功 ^_^");
	return result;
}


//点击复选框直接进行上架，下架操作
$(".modifyState").click(function(){
	modify = $(this);
	id= modify.attr("id");
	state= modify.attr("state");
	goodsState = new Object();
	goodsState.id = id;
	if(state == "1"){
		goodsState.state = 2;
	}
	else{
		goodsState.state = 1;
	}
	
	$.ajax({
		url: '/SLSaleSystem/backend/modifygoodsinfo.html',
		type: 'POST',
		data:{goodsInfo:JSON.stringify(goodsState)},
		dataType: 'html',
		timeout: 5000,
		error: function(){
			alert("上架或下架商品操作时失败！请重试。");
		},
		success: function(result){
			if(result != "" && "success" == result){
				if(isstart == "1")
					modify.attr("state",2);
				else
					modify.attr("state",1);
			}else if("failed" == result){
				alert("上架或下架商品操作时失败！请重试。");
			}else if("nodata" == result){
				alert("对不起，没有任何数据需要处理！请重试。");
			}
		}
	});
});

$(".addgoodsinfocancel").click(function(){
	$("#add_formtip").html('');
	$("#a_goodsName").val('');
	$("#a_goodsSN").val('');
	$("#a_marketPrice").val('');
	$("#a_realPrice").val('');
	$("#a_num").val('');
	$("#a_unit").val('');
	$("#a_state").val('1');
	$("#a_goodsFormat").html('');
	$("#a_note").html('');
});

$(".viewgoodsinfocancel").click(function(){
	$("#v_state").html('');
	$("#v_goodsFormat").html('');
	$("#v_note").html('');
});

$(".modifygoodsinfocancel").click(function(){
	$("#modify_formtip").html('');
	$("#m_state").html('');
	$("#m_goodsFormat").html('');
	$("#m_note").html('');
});

$('.addgoodsinfo').click(function(e){
	$("#add_formtip").html('');
	e.preventDefault();
	$('#addGoodsInfoDiv').modal('show');
});

$(".viewgoodsInfo").click(function(e){
	var v_id = $(this).attr('id');
	$.ajax({
		url: '/SLSaleSystem/backend/getgoodsinfo.html',
		data: {id:v_id},
		dataType: 'html',
		timeout: 1000,
		error: function(){
			alert("error");
		},
		success: function(result){
			if("nodata" == result){
				alert("没有数据！");
			}else if("failed" == result){
				alert("操作超时！");
			}else{
				v = eval('(' + result + ')');
				$("#v_id").val(v.id);
				$("#v_goodsName").val(v.goodsName);
				$("#v_goodsSN").val(v.goodsSN);
				$("#v_marketPrice").val(v.marketPrice);
				$("#v_realPrice").val(v.realPrice);
				$("#v_num").val(v.num);
				$("#v_unit").val(v.unit);
				var state = v.state;
				if(state == '1'){
					$("#v_state").html('上架');
				}else{
					$("#v_state").html('下架');
				}
				$("#v_goodsFormat").html(v.goodsFormat);
				$("#v_note").html(v.note);
				
				e.preventDefault();
				$('#viewGoodsInfoDiv').modal('show');
			}
		}
	});
});

$(".modifygoodsInfo").click(function(e){
	var m_id = $(this).attr('id');
	$.ajax({
		url: '/SLSaleSystem/backend/getgoodsinfo.html',
		data:{id:m_id},
		dataType:'html',
		timeout:1000,
		error:function(){
			alert("error");
		},
		success:function(result){
			if("nodata" == result){
				alert("没有数据！");
			}else if("failed" == result){
				alert("操作超时！");
			}else{
				m = eval('('+result+')');
				$("#m_id").val(m.id);
				$("#m_goodsName").val(m.goodsName);
				$("#m_goodsSN").val(m.goodsSN);
				$("#m_marketPrice").val(m.marketPrice);
				$("#m_realPrice").val(m.realPrice);
				$("#m_num").val(m.num);
				$("#m_unit").val(m.unit);
				$("#m_state").html('');
				var state = m.state;
				if(state == 1){
					$("#m_state").append("<label>状态：</label><input type=\"radio\" name=\"state\" checked=\"checked\" value=\"1\"/>上架<input type=\"radio\" name=\"state\" value=\"2\"/>下架");
//					$("#m_stateup").attr("checked","checked");
//					$("#m_statedown").removeAttr("checked");
					
				}else if(state == 2){
				    $("#m_state").append("<label>状态：</label><input type=\"radio\" name=\"state\" value=\"1\"/>上架<input type=\"radio\" name=\"state\" checked=\"checked\" value=\"2\"/>下架");
				}
				$("#m_goodsFormatli").html("");
				$("#m_goodsFormatli").append("<span>商品规格：</span> <br/><textarea id=\"m_goodsFormat\" name=\"goodsFormat\" rows=\"3\">"+m.goodsFormat+"</textarea>");
				$('#m_goodsFormat').cleditor();
				
				$("#m_noteli").html("");
				$("#m_noteli").append("<span>商品说明：</span> <br/><textarea id=\"m_note\" name=\"note\" rows=\"3\">"+m.note+"</textarea>");
				$('#m_note').cleditor();
				
			}
			e.preventDefault();
			$('#modifyGoodsInfoDiv').modal('show');
		}
	});
});

$(".delgoodsInfo").click(function(){
	var delId = $(this).attr('id');
	var goodsSN = $(this).attr('goodsSN');
	if(confirm("您确定要删除编号为【"+goodsSN+"】的商品吗？")){
		$.post("/SLSaleSystem/backend/delgoodsinfo.html",{'delId':delId},function(result){
			if("success" == result){
				alert("删除成功！");
				window.location.href="/SLSaleSystem/backend/goodsinfolist.html";
			}else if("noallow" == result){
				alert("该商品在套餐中使用，不能删除！");
			}else{
				alert("删除失败！");
			}
		},'html');
	}
});

$("#a_goodsSN").blur(function(){
	var aSN = $("#a_goodsSN").val();
	if(aSN != ""){
		//id传-1表示增加
		$.post("/SLSaleSystem/backend/goodsSNisexit.html",{'goodsSN':aSN,'id':'-1'},function(result){
			if(result == "repeat"){
				$("#add_formtip").css("color","red");
				$("#add_formtip").html("<li>对不起，该商品编号已存在。</li>");
				$("#add_formtip").attr("key","1");
				result = false;
			}else if(result == "failed"){
				alert("操作超时！");
			}else if(result == "only"){
				$("#add_formtip").css("color","green");
				$("#add_formtip").html("<li>该商品编号可以正常使用。</li>");
				$("#add_formtip").attr("key","0");
			}
		},'html');
	}
	
});

$("#m_goodsSN").blur(function(){
	var mSN = $("#m_goodsSN").val();
	if(mSN != ""){
		$.post("/SLSaleSystem/backend/goodsSNisexit.html",{'goodsSN':mSN,'id':$("#m_id").val()},function(result){
			if(result == "repeat"){
				$("#modify_formtip").css("color","red");
				$("#modify_formtip").html("<li>对不起，该商品编号已存在。</li>");
				$("#modify_formtip").attr("key","1");
				result = false;
			}else if(result == "failed"){
				alert("操作超时！");
			}else if(result == "only"){
				$("#modify_formtip").css("color","green");
				$("#modify_formtip").html("<li>该商品编号可以正常使用。</li>");
				$("#modify_formtip").attr("key","0");
			}
		},'html');
	}
});
