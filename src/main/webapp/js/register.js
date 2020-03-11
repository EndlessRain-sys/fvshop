$(function () {
    $('#submit').click(function () {
        var personInfo = {};
        personInfo.name = $('#name').val();
        personInfo.gender = $('#gender').val();
        var userName = $('#username').val();
        var password = $('#password').val();
        var formData = new FormData();
        formData.append('personInfoStr',JSON.stringify(personInfo));
        formData.append('userName', userName);
        formData.append('password', password);
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }
        formData.append('verifyCodeActual', verifyCodeActual);
        $.ajax({
            url : '/local/register',
            type : 'post',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    $.toast('注册成功！');
                } else {
                    $.toast('注册失败！' + data.errMsg);
                }
                // 点击验证码图片的时候，注册码会改变
                $('#captcha_img').click();
            }
        });
    });
})