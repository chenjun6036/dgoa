Ext.onReady(function() {	
	Ext.QuickTips.init();
	//角色列表
    var store = Ext.create('Ext.data.Store', {
        fields: [
            {name: 'roleName',type: 'string'},  //权限名
            {name: 'roleId',type: 'int'}//权限id
        ],
        proxy: {
            type: 'ajax',
            url: 'roleManagerAction!getAllRoles'
        }
    });
    store.load();
    
  //顶部toolbar
    Ext.create('Ext.toolbar.Toolbar', {
        renderTo: document.body,
        items: [{
                text: '新增权限',
                icon: 'images/add.png',
                handler: function() {
                    showAddWindow();
                }
            },'->', {
            	text:"刷新",
            	icon:"images/refresh.gif",
            	handler:function(){store.reload();}
            }]
    });
    
  //权限列表
    Ext.create('Ext.grid.Panel', {
        title: '权限列表',
        icon:'images/table.png',
        renderTo: Ext.getBody(),
        collapsible: true,
        id:'grid',
        store: store,
        //  features:[{ftype:"grouping"}],       
        forceFit: true, //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。                     
        columns: [{
                text: '权限名',
                dataIndex: 'roleName'
            }, {
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 30,
                text: '编辑',
                align: 'center',
                items: [{
                        icon: 'images/tableedit.png',
                        tooltip: '编辑权限',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            showEditWindow(rec.get('roleId'), rec.get('roleName'));
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
                        icon: 'images/tabledelete.png',
                        tooltip: '删除权限',
                        handler: function(grid, rowIndex, colIndex) {
                        	var rec = grid.getStore().getAt(rowIndex);
                            	Ext.Msg.confirm("提示信息", "你确认删除此权限？删除后将无法恢复！", function(btn) {
                                    if (btn == "yes") {
                                        Ext.Ajax.request({
                                            url: "roleManagerAction!deleteRole?roleId="+rec.get("roleId"),
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
    
  //显示编辑窗口
    function showEditWindow(id, roleName) {
        Ext.create('Ext.window.Window', {
            title: "编辑角色",
            id: 'edit',
            modal: true,
            resizable: false,
            closable: true,
            layout:"fit",
            items: [{
                    xtype: "form",
                    border: false,
                    bodyPadding: 15,
                    width:350,
                    url: "roleManagerAction!updateRole?roleId=" + id,
                    method: "post",
                    defaultType: "textfield",
                    fieldDefaults: {
                        labelWidth: 80,
                        labelSeparator: ": ",
                        anchor: "0"
                    },
                    items: [
                        {
                            fieldLabel: "角色名",
                            name: "roleName",
                            emptyText: "请输入角色名",
                            allowBlank: false,
                            msgTarget : 'side',
                            value: roleName,
                            blankText: "角色名不能为空"
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
                                                Ext.Msg.alert("提示信息", action.result.status);
                                                store.reload();
                                                var win = Ext.getCmp("edit");
                                                win.close();
                                            },
                                            failure: function(form, action) {
                                                Ext.Msg.alert('修改失败', action.result.status,function(){
                                                	if(action.result.status == "对不起，该角色名已存在，请重新输入！"){
                                                		form.findField("roleName").focus(true,100);
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
            title: "添加角色",
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
                    url: "roleManagerAction!addRole",
                    method: "post",
                    defaultType: "textfield",
                    fieldDefaults: {
                        labelWidth: 80,
                        labelSeparator: ": ",
                        anchor: "0"
                    },
                    items: [
                        {
                            fieldLabel: "角色名",
                            name: "roleName",
                            emptyText: "请输入角色名",
                            allowBlank: false,
                            msgTarget : 'side',
                            blankText: "角色名不能为空"
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
                                                Ext.Msg.alert("提示信息", action.result.status);
                                                store.reload();
                                                var win = Ext.getCmp("add");
                                                win.close();
                                            },
                                            failure: function(form, action) {
                                                Ext.Msg.alert('修改失败', action.result.status,function(){
                                                	if(action.result.status == "对不起，该角色名已存在，请重新输入！"){
                                                		form.findField("roleName").focus(true,100);
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