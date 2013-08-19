Ext.onReady(function() {	
	Ext.QuickTips.init();	
	 //人员列表
    var store = Ext.create('Ext.data.Store', {
        fields: [
            {name: 'userName',type: 'string'},  //用户名
            {name: 'realName',type: 'string'},  //真实姓名
            {name: 'email',type: 'string'},  //联系方式
            {name: 'userId',type: 'int'},  //用户id
            {name: "status", type: 'int'}//用户状态
        ],
        proxy: {
            type: 'ajax',
            url: 'userManagerAction!getAllUsers'
        }
    });
    store.load();
    
    var roleStore = Ext.create('Ext.data.Store', {
        fields: [
            {name: 'roleId',type: 'int'},  //角色id
            {name: 'roleName',type: 'string'}  //角色名
        ],
        proxy: {
            type: 'ajax',
            url: 'roleManagerAction!getAllRoles'
        }
    });
    roleStore.load();
    
    var userRolesStore = Ext.create('Ext.data.Store', {
        fields: [
                 {name: 'roleId',type: 'int'},  //角色id
                 {name: 'roleName',type: 'string'}  //角色名
             ],
             proxy: {
                 type: 'ajax',
                 url: 'roleManagerAction!getRolesByUser'
             }
   });
    
    //顶部toolbar
    Ext.create('Ext.toolbar.Toolbar', {
        renderTo: document.body,
        items: [{
                text: '新建用户',
                icon: 'images/user/add_user.png',
                handler: function() {
                    showAddWindow();
                }
            }/*,{
             	text : "导出到excel",
             	icon:'images/export.png',
                handler:function(){
                	window.open('userCrud!exportUserInfo');
                }            
            }*/,'->', {
            	text:"刷新",
            	icon:"images/refresh.gif",
            	handler:function(){store.reload();}
            }]
    });
    
  //人员列表
    Ext.create('Ext.grid.Panel', {
        title: '用户列表',
        icon:'images/table.png',
        renderTo: Ext.getBody(),
        collapsible: true,
        id:'grid',
        store: store,
        //  features:[{ftype:"grouping"}],       
        forceFit: true, //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。                     
        columns: [{
                text: '用户名',
                dataIndex: 'userName'
            }, {
                text: '真实姓名',
                dataIndex: 'realName'
            },  {
                text: '电子邮箱',
                dataIndex: 'email'
            },{
            	text: '审核状态',
            	dataIndex: 'status',
            	renderer:function(value){
            		if(value == 0){
            			return '<span>已通过审核</span>';
            		}else if(value == 1){
            			return '<span>正在等待审核</span>';
            		}else{
            			return '<span>未通过审核</span>';
            		}
            	}
            }, {
            	menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 30,
                text: '配置角色',
                align: 'center',
                items: [{
                        icon: 'images/setting.png',
                        tooltip: '配置用户角色',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            showSettingWindow(rec.get('userId'), rec.get('realName'));
                        }
                    }]
            },{
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 30,
                text: '编辑',
                align: 'center',
                items: [{
                        icon: 'images/user/edit_user.png',
                        tooltip: '编辑用户',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            showEditWindow(rec.get('userId'), rec.get('userName'), rec.get('realName'), rec.get('email'));
                        }
                    }]
            }, {
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 30,
                text: '删除',
                align: 'center',
                items: [{
                        icon: 'images/user/delete_user.png',
                        tooltip: '删除用户',
                        handler: function(grid, rowIndex, colIndex) {
                        	var rec = grid.getStore().getAt(rowIndex);
                            	Ext.Msg.confirm("提示信息", "你确认删除此用户？删除后将无法恢复！", function(btn) {
                                    if (btn == "yes") {
                                        Ext.Ajax.request({
                                            url: "userManagerAction!deleteUser?user.userId="+rec.get("userId"),
                                            method: "get",
                                            success: function(response) {
                                                var res = Ext.JSON.decode(response.responseText);
                                                if (res.success == true) {
                                                    Ext.Msg.alert("提示信息", res.status,function(){
                                                    	var grid = Ext.getCmp("grid");
                                                    	grid.getStore().remove(rec);
                                                    });
                                                } 
                                                else {
                                                    Ext.Msg.alert("删除失败", res.status);
                                                }
                                            }
                                        });
                                    }
                                });                                                      
                        }
                    }]
            }
        ]
    });
    
    //显示配置角色窗口
    function showSettingWindow(userId,realName){
    	var params = userRolesStore.getProxy().extraParams;
    	params.userId = userId;
    	userRolesStore.load();
    	Ext.create("Ext.window.Window",{
    		title: "配置"+realName+"角色",
            id: 'setting',
            modal: true,
            width:500,
            resizable: false,
            closable: true,
            items:[{
            	xtype:"form",
            	header: false,
                baseCls: 'ex-panel',
                url:'userManagerAction!addUserRole?user.userId='+userId,
                title: "添加用户角色",
                bodyPadding: 10,
                width: 200,
                defaultType: 'textfield',
                items:[{
                	xtype: "combobox",
                    fieldLabel: "角色",
                    queryMode: 'local',
                    store: roleStore,
                    displayField: "roleName",
                    valueField: "roleId",
                    value: roleStore.getAt(0).get("roleId"),
                    editable: false,
                    name: 'roleId'
                }],
                dockedItems: [{xtype: "toolbar",
                    dock: 'bottom',
                    ui: "footer",
                    style: "margin-left:60px",
                    items: [{
                            text: '确认添加',
                            icon: 'images/save1.png',
                            formBind: true,
                            disabled: true,
                            handler:function(){
                            	var form=this.up('form').getForm();
                            	form.submit({
                            		success:function(form,action){
                            			Ext.Msg.alert("提示信息",action.result.status);
                            			userRolesStore.reload();
                            			form.reset();
                            		},
                            		failure:function(form,action){
                            			Ext.Msg.alert("添加失败",action.result.status);
                            		}
                            	});
                            }
                        }]
                }]
            },{
                xtype: 'grid',
                store: userRolesStore,
                collapsible: true,
                icon: 'images/table.png',
                title: realName+'角色列表',
                forceFit: true, //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。  
                columns: [{
                        text: '角色名',
                        dataIndex: 'roleName',
                        flex: 5
                    },{
                        menuDisabled: true,
                        sortable: false,
                        xtype: 'actioncolumn',
                        flex: 3,
                        text: '删除',
                        align: 'center',
                        items: [{
                                icon: 'images/tabledelete.png',
                                tooltip: '删除'+realName+'该角色',
                                handler: function(grid, rowIndex, colIndex) {
                                    var rec = grid.getStore().getAt(rowIndex);
                                    Ext.Msg.confirm("提示信息", "你确认删除"+realName+"该角色？删除后将无法恢复！", function(btn) {
                                        if (btn == "yes") {
                                            Ext.Ajax.request({
                                                url: "userManagerAction!deleteUserRole?user.userId="+userId,
                                                method: "get",
                                                params: {
                                                    roleId: rec.get("roleId")
                                                },
                                                success: function(response) {
                                                    var res = Ext.JSON.decode(response.responseText);
                                                    if (res.success == true) {
                                                        Ext.Msg.alert("提示信息", res.status,function(){
                                                        	grid.getStore().remove(rec);
                                                        });
                                                    } 
                                                    else {
                                                        Ext.Msg.alert("删除失败", res.status);
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            }]
                    }]
            }]
    	}).show();
    };
    
  //显示编辑窗口
    function showEditWindow(id, userName, realName, email) {
        Ext.create('Ext.window.Window', {
            title: "编辑用户",
            id: 'edit',
            modal: true,
            resizable: true,
            closable: true,
            layout:"fit",
            items: [{
                    xtype: "form",
                    border: false,
                    bodyPadding: 15,
                    width:350,
                    url: "userManagerAction!updateUser?user.userId=" + id,
                    method: "post",
                    defaultType: "textfield",
                    fieldDefaults: {
                        labelWidth: 80,
                        labelSeparator: ": ",
                        anchor: "0"
                    },
                    items: [
                        {
                            fieldLabel: "用&nbsp;户&nbsp;名",
                            name: "user.userName",
                            emptyText: "请输入用户名",
                            allowBlank: false,
                            msgTarget : 'side',
                            value: userName,
                            blankText: "用户名不能为空"
                        }, 
                        {
                            fieldLabel: "真实姓名",
                            name: "user.realName",
                            emptyText: "请输入真实姓名",
                            allowBlank: false,
                            msgTarget : 'side',
                            value: realName,
                            blankText: "真实姓名不能为空"
                        },{
                            fieldLabel: "电子邮箱",
                            name: "user.email",
                            emptyText: "请输入电子邮箱",
                            allowBlank: true,
                            value: email
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: "toolbar",
                            items: [
                                {
                                    text: "保存",
                                    icon: "images/save1.png",
                                    formBind: true,
                                    disabled: true,
                                    handler: function() {
                                        var form = this.up('form').getForm();
                                        form.submit({
                                        	submitEmptyText: false,
                                            success: function(form, action) {
                                                Ext.Msg.alert("提示信息", "修改成功！");
                                                store.reload();
                                                var win = Ext.getCmp("edit");
                                                win.close();
                                            },
                                            failure: function(form, action) {
                                                Ext.Msg.alert('修改失败', action.result.status,function(){
                                                	if(action.result.status == "对不起，该用户名已存在，请重新输入！"){
                                                		form.findField("user.userName").focus(true,100);
                                                	}
                                                });
                                            },
                                            scope: form
                                        });
                                    }
                                }
                            ]
                        }
                    ]
                }]
        }).show();
    };
    
  //显示添加窗口
    function showAddWindow() {
        Ext.create('Ext.window.Window', {
            title: "添加用户",
            id: 'add',
            modal: true,
            resizable: false,
            closable: true,
            layout:"fit",
            items: [{
                    xtype: "form",
                    border: false,
                    bodyPadding: 15,
                    width:350,
                    url: "userManagerAction!addUser",
                    method: "post",
                    defaultType: "textfield",
                    fieldDefaults: {
                        labelWidth: 80,
                        labelSeparator: ": ",
                        anchor: "0"
                    },
                    items: [
                        {
                            fieldLabel: "用&nbsp;户&nbsp;名",
                            name: "user.userName",
                            emptyText: "请输入用户名",
                            allowBlank: false,
                            msgTarget : 'side',
                            blankText: "用户名不能为空"
                        }, 
                        {
                            fieldLabel: "真实姓名",
                            name: "user.realName",
                            emptyText: "请输入真实姓名",
                            allowBlank: false,
                            msgTarget : 'side',
                            blankText: "真实姓名不能为空"
                        },{
                            fieldLabel: "电子邮箱",
                            name: "user.email",
                            emptyText: "请输入电子邮箱",
                            allowBlank: true
                        },{
                        	xtype:"label",
                        	html:"<p>说明：以此方式添加的用户的审核状态将直接为：“已通过审核”状态！</p>"
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: "toolbar",
                            items: [
                                {
                                    text: "保存",
                                    icon: "images/save1.png",
                                    formBind: true,
                                    disabled: true,
                                    handler: function() {
                                        var form = this.up('form').getForm();
                                        form.submit({
                                        	submitEmptyText: false,
                                            success: function(form, action) {
                                                Ext.Msg.alert("提示信息", "添加成功！");
                                                store.reload();
                                                var win = Ext.getCmp("add");
                                                win.close();
                                            },
                                            failure: function(form, action) {
                                                Ext.Msg.alert('添加失败', action.result.status,function(){
                                                	if(action.result.status == "对不起，该用户名已存在，请重新输入！"){
                                                		form.findField("user.userName").focus(true,100);
                                                	}
                                                });
                                            },
                                            scope: form
                                        });
                                    }
                                }
                            ]
                        }
                    ]
                }]
        }).show();
    };


});