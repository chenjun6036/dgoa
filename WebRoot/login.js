Ext.onReady(function(){
	Ext.create("Ext.form.Panel",{
		renderTo: 'center',
		title:"用户登录",
		width: 350,
		height: 200,
        bodyPadding: 40,
        id: "form",
        url: "loginAction!login",
        method: "post",
        currentTabIndex: 1,
        defaultType: "textfield",
        fieldDefaults: {
            labelWidth: 80,
            anchor: "0"
        },
        items: [
            {
                fieldLabel: "用&nbsp;&nbsp;户&nbsp;&nbsp;名",
                name: "username",
                allowBlank: false,
                tabIndex: 1,
                blankText: "用户名不能为空",
                msgTarget : 'side'
            }, 
            {
                fieldLabel: "密 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码",
                name: "password",
                allowBlank: false,
                inputType: "password",
                tabIndex: 2,
                blankText: "密码不能为空",                        
                msgTarget : 'side',
                margin:"15 0 0 0"
            },
            {
                xtype: "toolbar",
                dock: "bottom",
                ui: "footer",
                style:"margin-top:25px",
                items: [
                    {
                        text: "登&nbsp;&nbsp;&nbsp;&nbsp;录",
                        scale:'medium',
                        id:'login',
                        style:"margin-left:75px",
                        width:80,
                        tabIndex:3,
                        formBind: true,
                        disabled: true,
                        handler: function() {
                            var form = this.up('form').getForm();
                            form.submit({
                                success: function(form, action) {
                                    document.location = "index.jsp";
                                },
                                failure: function(form, action) {
                                    Ext.Msg.alert('登陆失败', action.result.status,function(){
                                    	if(action.result.status=="密码错误！"){                                         
                                    	form.findField("password").focus(true,100);
                                    	}else if(action.result.status=="该用户名不存在！"){
                                    	form.findField("username").focus(true,100);
                                    	}
                                    });                                            
                                },
                                scope: form
                            });
                        }
                    }, 
                    {
                    	xtype:"label",
                        style:"margin-left:10px;font-size:13px",
                        width:80,
                        html:'<a href="register.jsp">注册</a>'
                    }
                ]
            }
        ],
        listeners:{
        	boxready:function(){
        		var form = Ext.getCmp('form').getForm();
        		form.findField("username").focus(true,100);
        	}
        }
	});
});