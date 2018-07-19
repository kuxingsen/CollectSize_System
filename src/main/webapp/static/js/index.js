
$(document).ready(function(){
    /*
    *	页面加载完成后发一个空请求，请求用户已输入的数据
    */
    $.ajax({
        url: '/collect/studentInfo',
        type: 'GET',
        dataType:'json',
        success: function(resp){
            if(resp.code == 0) {
                //正常返回，已经提交过了
                $('#sName').val(resp.rows[0].name);
                $('#sNo').val(resp.rows[0].student_id);
                $('#sDepartment').val(resp.rows[0].department);
                $('#sClass').val(resp.rows[0].class_name);
                $('sSize').val(resp.rows[0].size);
            } else {
                //第一次登系统，没提交过不处理
            }
        }
    })
});

$('#submit').click(function(){
	var sName = $('#sName').val();
	var sNo = $('#sNo').val();
	var sDepartment = $('#sDepartment').val();
	var sClass = $('#sClass').val();
	var sSize = $('#sSize').val();
	//验证表单不空
	if(!(sName&&sNo&&sClass) || sDepartment==0 || sSize==0) {
		$.alert("表单内容不能为空");	
	} else {
		$.showPreloader('正在上传表单');
		$.ajax({
			url: '/collect/submit',
			type: 'POST',
			data: {
				name: sName,
				student_id: sNo,
                department: sDepartment,
				class_name: sClass,
				size: sSize
			},
			dataType: "json",
			success: function(resp) {
				$.hidePreloader();
				if(resp.code == 0) {
					$.alert('表单提交成功');
				} else {
					$.alert('表单提交失败！' + resp.msg);
				}
			},
			error: function(xhr, type){
				$.hidePreloader();
			    $.alert('提交表单失败!请检查网络设置')
			}
		})
	}
	
});