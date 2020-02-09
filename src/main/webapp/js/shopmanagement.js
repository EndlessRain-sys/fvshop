$(function() {
	var shopId = getQueryString('shopId');
	var shopInfoUrl = '/shopadmin/getshopmanagementinfo?shopId=' + shopId;
    //调用shop/getshopmanagementinfo，并将modelMap（@ResponseBody将modelMap转化成Json类型）传入function(data)
	//$.getJSON( url [, data ] [, success(data, textStatus, jqXHR) ] )
    // url是必选参数，表示json数据的地址；
    // data是可选参数，用于请求数据时发送数据参数；
    // success是可参数，这是一个回调函数，用于处理请求到的数据。
	//等价于：
    // $.ajax({
    //   url: url,
    //   data: data,
    //   success: callback,
    //   dataType: json
    // });
	$.getJSON(shopInfoUrl, function(data) {
		if (data.redirect) {
			window.location.href = data.url;
		} else {
			if (data.shopId != undefined && data.shopId != null) {
				shopId = data.shopId;
			}
			$('#shopInfo').attr('href', '/shopadmin/shopoperation?shopId=' + shopId);
		}
	});
});