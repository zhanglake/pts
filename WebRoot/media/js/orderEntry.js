var OrderEntry = function () {
	
	var selectedOrderType = "";
	var selectedDeptCode = "";
	
	var handleWizard = function (){
		if (!jQuery().bootstrapWizard) {
            return;
        }
        
        function format(state) {
            if (!state.id) return state.text; // optgroup
            return "<img class='flag' src='assets/img/flags/" + state.id.toLowerCase() + ".png'/>&nbsp;&nbsp;" + state.text;
        }

        $("#country_list").select2({
            placeholder: "Select",
            allowClear: true,
            formatResult: format,
            formatSelection: format,
            escapeMarkup: function (m) {
                return m;
            }
        });

        var form = $('#submit_form');
        var error = $('.alert-error', form);
        var success = $('.alert-success', form);

        form.validate({
            doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
            errorElement: 'span', //default input error message container
            errorClass: 'validate-inline', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                //account
                username: {
                    minlength: 5,
                    required: true
                },
                password: {
                    minlength: 5,
                    required: true
                },
                rpassword: {
                    minlength: 5,
                    required: true,
                    equalTo: "#submit_form_password"
                },
                //profile
                fullname: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                phone: {
                    required: true
                },
                gender: {
                    required: true
                },
                address: {
                    required: true
                },
                city: {
                    required: true
                },
                country: {
                    required: true
                },
                //payment
                card_name: {
                    required: true
                },
                card_number: {
                    minlength: 16,
                    maxlength: 16,
                    required: true
                },
                card_cvc: {
                    digits: true,
                    required: true,
                    minlength: 3,
                    maxlength: 4
                },
                card_expiry_mm: {
                    digits: true,
                    required: true
                },
                card_expiry_yyyy: {
                    digits: true,
                    required: true
                },
                'payment[]': {
                    required: true,
                    minlength: 1
                }
            },

            messages: { // custom messages for radio buttons and checkboxes
                'payment[]': {
                    required: "Please select at least one option",
                    minlength: jQuery.format("Please select at least one option")
                }
            },

            errorPlacement: function (error, element) { // render error placement for each input type
                if (element.attr("name") == "gender") { // for uniform radio buttons, insert the after the given container
                    error.addClass("no-left-padding").insertAfter("#form_gender_error");
                } else if (element.attr("name") == "payment[]") { // for uniform radio buttons, insert the after the given container
                    error.addClass("no-left-padding").insertAfter("#form_payment_error");
                } else {
                    error.insertAfter(element); // for other inputs, just perform default behavoir
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit   
                success.hide();
                error.show();
                App.scrollTo(error, -200);
            },

            highlight: function (element) { // hightlight error inputs
                $(element).closest('.help-inline').removeClass('ok'); // display OK icon
                $(element).closest('.control-group').removeClass('success').addClass('error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change dony by hightlight
                $(element).closest('.control-group').removeClass('error'); // set error class to the control group
            },

            success: function (label) {
                if (label.attr("for") == "gender" || label.attr("for") == "payment[]") { // for checkboxes and radip buttons, no need to show OK icon
                    label.closest('.control-group').removeClass('error').addClass('success');
                    label.remove(); // remove error label here
                } else { // display success icon for other inputs
                    label.addClass('valid ok') // mark the current input as valid and display OK icon
                    .closest('.control-group').removeClass('error').addClass('success'); // set success class to the control group
                }
            },

            submitHandler: function (form) {
                success.show();
                error.hide();
                //add here some ajax code to submit your form or just call form.submit() if you want to submit the form without ajax
            }

        });

        var displayConfirm = function() {
            $('.display-value', form).each(function(){
                var input = $('[name="'+$(this).attr("data-display")+'"]', form);
                if (input.is(":text") || input.is("textarea")) {
                    $(this).html(input.val());
                } else if (input.is("select")) {
                    $(this).html(input.find('option:selected').text());
                } else if (input.is(":radio") && input.is(":checked")) {
                    $(this).html(input.attr("data-title"));
                } else if ($(this).attr("data-display") == 'card_expiry') {
                    $(this).html($('[name="card_expiry_mm"]', form).val() + '/' + $('[name="card_expiry_yyyy"]', form).val());
                } else if ($(this).attr("data-display") == 'payment') {
                    var payment = [];
                    $('[name="payment[]"]').each(function(){
                        payment.push($(this).attr('data-title'));
                    });
                    $(this).html(payment.join("<br>"));
                }
            });
        }

        // default form wizard
        $('#form_wizard_1').bootstrapWizard(
        {
            'nextSelector': '.button-next',
            'previousSelector': '.button-previous',
            onTabClick: function (tab, navigation, index) 
            {
                return false;
            },
            onNext: function (tab, navigation, index) 
            {
                success.hide();
                error.hide();

                if (form.valid() == false) {
                    return false;
                }

                var total = navigation.find('li').length;
                var current = index + 1;
                // set wizard title
                $('.step-title', $('#form_wizard_1')).text('第 ' + (index + 1) + '步（共' + total + '步） ');
                // set done steps
                jQuery('li', $('#form_wizard_1')).removeClass("done");
                var li_list = navigation.find('li');
                for (var i = 0; i < index; i++) {
                    jQuery(li_list[i]).addClass("done");
                }
                
                if (current == 3)
                {
                	handleLoadDepartmentOrderStatus();
                	
                	handleLoadMaterial();
                }
                if (current == 4)
                {
                	handleOrderPerview();
                }
                
                if (current == 1) 
                {
                    $('#form_wizard_1').find('.button-previous').hide();
                } 
                else 
                {
                    $('#form_wizard_1').find('.button-previous').show();
                }

                if (current >= total) 
                {
                    $('#form_wizard_1').find('.button-next').hide();
                    $('#form_wizard_1').find('.button-submit').show();
                    displayConfirm();
                } 
                else 
                {
                    $('#form_wizard_1').find('.button-next').show();
                    $('#form_wizard_1').find('.button-submit').hide();
                }
                App.scrollTo($('.page-title'));
            },
            onPrevious: function (tab, navigation, index) 
            {
                success.hide();
                error.hide();

                var total = navigation.find('li').length;
                var current = index + 1;
                // set wizard title
                $('.step-title', $('#form_wizard_1')).text('第 ' + (index + 1) + '步（共' + total + '步） ');
                // set done steps
                jQuery('li', $('#form_wizard_1')).removeClass("done");
                var li_list = navigation.find('li');
                for (var i = 0; i < index; i++) {
                    jQuery(li_list[i]).addClass("done");
                }

                if (current == 1) 
                {
                    $('#form_wizard_1').find('.button-previous').hide();
                } 
                else 
                {
                    $('#form_wizard_1').find('.button-previous').show();
                }

                if (current >= total) 
                {
                    $('#form_wizard_1').find('.button-next').hide();
                    $('#form_wizard_1').find('.button-submit').show();
                } 
                else 
                {
                    $('#form_wizard_1').find('.button-next').show();
                    $('#form_wizard_1').find('.button-submit').hide();
                }

                App.scrollTo($('.page-title'));
            },
            onTabShow: function (tab, navigation, index) 
            {
                var total = navigation.find('li').length;
                var current = index + 1;
                var $percent = (current / total) * 100;
                $('#form_wizard_1').find('.bar').css({
                    width: $percent + '%'
                });
            }
        });

        $('#form_wizard_1').find('.button-previous').hide();
        $('#form_wizard_1 .button-submit').click(function () {
            alert('处理完成! ');
        }).hide();
	}
	
	var handlePulsate = function () 
	{
        if (!jQuery().pulsate) {
            return;
        }

        if (App.isIE8() == true) {
            return; // pulsate plugin does not support IE8 and below
        }
        
        if (jQuery().pulsate) 
        {
        	//default selected ordertype
        	jQuery('.ordertype').each(function()
	    	{
        		$(this).pulsate({color: "#bf1c56"});
				return false;
	    	});
            
            jQuery('.ordertype').click(function () 
            {
            	jQuery('.ordertype').each(function()
            	{
            		$(this).pulsate("destroy");
            	});
            	
                $(this).pulsate({
                    color: "#bf1c56",
                });
            });
        }
    }
	
	var handleSelectOrderType = function ()
	{
		//default selected ordertype
		jQuery('.ordertype').each(function()
    	{
			selectedOrderType = $(this).find(".number").attr("data");
			$(".selectedOrdertype").text("("+$(this).find(".number").text()+")");
			return false;
    	});
		
		jQuery('.ordertype').click(function () 
        {
			selectedOrderType = $(this).find(".number").attr("data");
			$(".selectedOrdertype").text("("+$(this).find(".number").text()+")");
        });
	}
	
	var handleDepartmentOrder = function()
	{
		$(".reloadDepartment").click(function () 
		{
			handleLoadDepartmentOrderStatus();
		});
	}
	
	var handleLoadDepartmentOrderStatus = function()
	{
		var url = "order/departmentOrderStatus.do?formType=" + selectedOrderType;
		$.get(url, function (data, status) 
		{
			var $table = $("#tbDeptOrderStatus").bootstrapTable({data:data});
			$table.bootstrapTable("load", data);
			$table.bootstrapTable("hideLoading");
    	});
	}
	
	var handleLoadDepartmentOrderItems = function(deptCode, deptName)
	{
		selectedDeptCode = deptCode;
		App.blockUI($(".page-header-fixed"));
        
        //title
        $("#deptOrderView>.modal-header>.page-title").text(deptName);
        
        //get data
        var url = "order/deptOrderDetails.do?formType=" + selectedOrderType + "&deptCode=" + selectedDeptCode;
        
		//get data
		$.get(url, function (data, status) 
		{
			var $table = $("#tbView").bootstrapTable({data:data});
			$table.bootstrapTable("load", data);
			$table.bootstrapTable("hideLoading");
			
			$("#orderMaterials").val("");
			
			App.unblockUI($(".page-header-fixed"));
			
			handleEditMaterial();
    	});
		
      	//popup
		$("#deptOrderView").modal({keyboard: true});
	}
	
	var handleEditDepartmentOrder = function()
	{
		$(".addOrderItem").bind("click", function ()
		{
			var selMaterial = $("#orderMaterials").val();
			if (selMaterial == "") return;
			
			handleEditOrderItem(selMaterial);
		});
	}
	
	var handleEditOrderItem = function (selMaterial)
	{
		if ($("#tbView").find("tr").length == 2)
		{
			$("#tbView").find(".no-records-found").remove();
		}
		var material = materialMap.get(selMaterial);
		var exists = false;
		$(".mtrlCd").each(function()
		{
			if ($(this).text() == material.MTRLCD)
			{
				exists = true;
				return false;
			}
		});
		if (exists == true)
		{
			App.alert("已存在该项！");
			return;
		}
		var tr = ['<tr>', 
		          	'<td><span class="mtrlCd">' + material.MTRLCD + '</span></td>',
					'<td>' + material.MTRLNM + '</td>',
					'<td>' + material.SPC + '</td>',
					'<td><input class="ordCnt m-wrap" style="width:50px;"></td>',
					'<td>' + material.ORDUNT + '</td>',
					'<td><span class="chkScl">' + material.CHKSCL + '</span></td>',
					'<td><span class="chkCnt"></span></td>',
					'<td>' + material.CHKUNT + '</td>',
					'<td><input class="cmnts m-wrap small"></td>',
					'<td><a class="deleteOrderItem" href="javascript:void(0)">删除</a></td>',
		          '</tr>'].join("");
		//alert(tr);
		$("#tbView").append(tr);
		
		handleEditMaterial();
	}
	
	var handleEditMaterial = function()
	{
		$(".ordCnt").inputmask({alias: "decimal"});
		$(".ordCnt").bind("input propertychange change", function(e)
		{
			var chkScl = $(this).closest("tr").find(".chkScl").text();
			var ordCnt = $(this).val();
			var tmp = Number(chkScl) * Number(ordCnt);
			//alert("chkScl = " + chkScl + "    ordCnt = " + ordCnt);
			$(this).closest("tr").find(".chkCnt").text(tmp);
		});
		
		$(".deleteOrderItem").bind("click", function(e) 
		{
			e.preventDefault();
			$(this).closest("tr").remove();
		});
	}
	
	var materialMap = null;
	var handleLoadMaterial = function()
	{
		App.blockUI($(".page-header-fixed"));
		//get data
        var url = "order/orderMaterials.do?formType=" + selectedOrderType;
		//get data
		$.get(url, function (data, status) 
		{
			materialMap = new HashMap();
			var trHtml = '<option value=""></option>';
			$.each(data, function(i, material) 
			{
				trHtml += '<option value="' + material.MTRLCD + '">' + material.MTRLCD + " " + material.MTRLNM + " " + material.MNMNCCD + '</option>';
				materialMap.put(material.MTRLCD, material);
			});
			$("#orderMaterials").html(trHtml);
			
			var $chosen = $("#orderMaterials").next();
			if ($chosen.hasClass("chzn-container"))
			{
				$("#orderMaterials").removeClass("chzn-done");
				$chosen.remove();
			}
			
			$("#orderMaterials").chosen();
			
			App.unblockUI($(".page-header-fixed"));
    	});
	}
	
	var handleMaterialChose = function ()
	{
		$("#orderMaterials").change(function () 
		{
			handleEditOrderItem($(this).val());
        });
	}
	
	var handleSaveOrderItem = function ()
	{
		$(".saveOrderItem").bind("click", function()
		{
			var dt = "";
			var mtrlCd = "";
			var ordCnt = "";
			var chkCnt = "";
			var cmnts  = "";
			var doSave = true;
			$("#tbView").find("tr").each(function()
			{
				mtrlCd = $(this).find(".mtrlCd").text();
				ordCnt = $(this).find(".ordCnt").val();
				chkCnt = $(this).find(".chkCnt").text();
				cmnts  = $(this).find(".cmnts").val();
				if (mtrlCd != "")
				{
					if (ordCnt == "")
					{
						doSave = false;
					}
					//alert("mtrlCd=" + mtrlCd + "         ordCnt=" + ordCnt);
					dt += mtrlCd + "_" + ordCnt + "_" + chkCnt + "_" + cmnts + ";";
				}
			});
			if (dt == "")
			{
				dt = 
				{
					deptCode:selectedDeptCode,
					formType:selectedOrderType
				};
				App.blockUI($(".page-header-fixed"));
				$.post("order/clearDeptOrderItems.do", dt, function(data)
				{
					App.alert(data);
					
					handleLoadDepartmentOrderStatus();
					$("#deptOrderView").modal("hide");
					App.unblockUI($(".page-header-fixed"));
				});
			}
			else
			{
				dt = 
				{
					data:dt, 
					deptCode:selectedDeptCode,
					formType:selectedOrderType
				};
				if (doSave)
				{
					App.blockUI($(".page-header-fixed"));
					$.post("order/saveDeptOrderItems.do", dt, function(data)
					{
						App.alert(data);
						
						handleLoadDepartmentOrderStatus();
						$("#deptOrderView").modal("hide");
						App.unblockUI($(".page-header-fixed"));
					});
				}
				else
				{
					App.alert("有未完成的输入项，请检查！");
				}
			}
		})
	}
	
	var handleOrderPerview = function()
	{
		var url = "order/orderPerview.do?formType=" + selectedOrderType;
		$.get(url, function (data, status) 
		{
			var $table = $("#tbOrderView").bootstrapTable({data:data});
			$table.bootstrapTable("load", data);
			$table.bootstrapTable("hideLoading");
    	});
	}
	
	var handleCreateOrder = function()
	{
		$(".createOrder").bind("click", function()
		{
			var lth = $("#tbOrderView").find("tr:last").children('td').eq(1);
			if (lth.html() == undefined)
			{
				App.alert("没有订货内容，请检查！");
				return;
			}
			if (confirm("确定将现有部门订货数据生成订单 ?确定后将无法修改。") == false) 
            {
                return;
            }
			
			var url = "order/createOrder.do?formType=" + selectedOrderType;
			$.get(url, function (data, status) 
			{
				App.alert(data);
	    	});
		});
	}
	
    return {
        //main function to initiate the module
        init: function () 
        {
        	handlePulsate();
        	
        	handleWizard();
            
        	handleSelectOrderType();
        	
        	handleDepartmentOrder();
        	
        	handleEditDepartmentOrder();
        	
        	handleSaveOrderItem();
        	
        	handleCreateOrder();
        	
        	handleMaterialChose();
        },
        
        handleLoadDepartmentOrderItems:function(deptCode, deptName)
        {
        	handleLoadDepartmentOrderItems(deptCode, deptName);
        }
    };

}();