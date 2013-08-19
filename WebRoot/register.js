Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.apply(Ext.form.field.VTypes,{
		password:function(val,field){ 
                if(field.confirmTo){ 
                    var pwd=Ext.getCmp(field.confirmTo);                   
                    if(val== pwd.getValue()){ 
                        return true; 
                    } 
                    else 
                    { 
                        return false; 
                    }                    
                    } 
                    return false;
                } 
              });
	Ext.create("Ext.form.Panel",{
		renderTo: 'center',
		title:"用户注册",
		width: 350,
		height: 300,
        bodyPadding: 40,
        id: "form",
        url: "loginAction!register",
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
                fieldLabel: "真实姓名&nbsp;",
                name: "realName",
                allowBlank: false,
                tabIndex: 2,
                blankText: "用户名不能为空",
                msgTarget : 'side',
                margin:"15 0 0 0"
            },
            {
                fieldLabel: "密 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码",
                name: "password",
                allowBlank: false,
                inputType: "password",
                id:'password',
                tabIndex: 3,
                blankText: "密码不能为空",                        
                msgTarget : 'side',
                margin:"15 0 0 0"
            },
            {
                fieldLabel: "确认密码&nbsp;",
                allowBlank: false,
                inputType: "password",
                vtype:'password',
                vtypeText:'两次密码不一致',
                confirmTo:'password',
                tabIndex: 4,
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
                        text: "注&nbsp;&nbsp;&nbsp;&nbsp;册",
                        scale:'medium',
                        id:'login',
                        style:"margin-left:80px",
                        width:80,
                        tabIndex:5,
                        formBind: true,
                        disabled: true,
                        handler: function() {
                            var form = this.up('form').getForm();
                            form.submit({
                                success: function(form, action) {
                                    document.location = "login.jsp";
                                },
                                failure: function(form, action) {
                                    Ext.Msg.alert('注册失败', action.result.status,function(){
                                    	if(action.result.status=="对不起，该用户名已存在，请重新输入！"){
                                    		form.findField("username").focus(true,100);
                                    	}
                                    });                                            
                                },
                                scope: form
                            });
                        }
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