(function(a,b){
	var _jquery = a,
	_self = b;
	/**
	 * 页面函数
	 */
	_self.PageFunc = {
			Del : function(url,code){
				_self.BUI.Message.Confirm('确认要删除吗？',function(){
					_jquery.ajax({
						type: "POST",
						url: url,
						data:{"code":code},
						dataType: "json",
					    success: function(data) {
						    if(data.status == "1"){
						    	_self.BUI.Message.Alert(data.message,function(){
					    			_self.store.load(null);
					    		},'success');
						    }else{
						    	_self.BUI.Message.Alert(data.message,'error');
						    }
					    }
					});
				},'question');
			}
			
	}; 
	/**
	 * 逻辑函数
	 */
	_self.LogicFunc = {
		/**
		 * 修改品牌默认流水
		 * @param defualtchip
		 * @param brandcode
		 */
		defualtship:function(defualtchip,brandcode){
			var submitform = $("#defualtchipform");
			submitform.find("input[name='defualtchip']").val(defualtchip);
			submitform.find("input[name='brandcode']").val(brandcode);
			var form = new _self.BUI.Form.HForm({
	 		      srcNode : '#defualtchipform',
	 	    }).render(); 
			var dialog = new _self.BUI.Overlay.Dialog({
		          title:'默认存款需完成流水',
		          width:280,
		          height:150,
		          closeAction : 'destroy',
		          contentId:'setdefualtchip',
		          success:function () {
		        	  if(form.isValid()){
		        		  $.ajax({
				        		url:getRootPath()+"/EOBrand/UpdateDefualtShip",
				        		type:"post",
				        		data:submitform.serialize(),
				        		dataType:"json",
				        		success:function(data){
				        			if(data&&data.status/1==1){	
				        				_jquery("#btnSearch").trigger("click");
				        				dialog.close();
				        			}else{
				        				_self.BUI.Message.Alert(data.message,'error');
				        			}
				        		}
				        	});
		        	  }
		          }
			 	})
		        dialog.show();
		},
		backLoseMoney:function(moneyinoutcode,_button){
			var trigger_button = _jquery(_button);
			BUI.Message.Show({
		        title : '返回金额',
		        msg : '确定返还该笔上分金额?',
		        icon : 'question',
		        buttons : [
		          {
		            text:'确定',
		            elCls : 'button button-primary',
		            handler : function(){
		            	$.ajax({
		            		url:getRootPath()+"/moneyInAndOut/backMoney",
		            		type:"post",
		            		dataType:"json",
		            		data:{moneyinoutcode:moneyinoutcode},
		            		success:function(data){
		            			if(data.status/1==1){
		            				trigger_button.remove();
		            				BUI.Message.Alert(data.message,"success");
		            			}else{
		            				BUI.Message.Alert(data.message,"error");
		            			}
		            		}
		            	});
		            	this.close();
		            }
		          },
		          {
		            text:'取消',
		            elCls : 'button',
		            handler : function(){
		              this.close();
		            }
		          }
		        ]
		      });
		}
	};
})($,window)
