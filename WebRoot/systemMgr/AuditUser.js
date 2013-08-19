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
            url: 'userManagerAction!getAllNeedAuditUsers'
        }
    });
    store.load();
    
 
    
    
  //人员列表
    Ext.create('Ext.grid.Panel', {
        title: '待审核用户列表',
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
            },{
            	menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 30,
                text: '通过审核',
                align: 'center',
                items: [{
                        icon: 'images/pass.png',
                        tooltip: '通过审核',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            Ext.Ajax.request({
                                url: "userManagerAction!auditUser?user.userId="+rec.get("userId"),
                                method: "get",
                                params:{isApproval:true},
                                success: function(response) {
                                    var res = Ext.JSON.decode(response.responseText);
                                    if (res.success == true) {
                                        Ext.Msg.alert("提示信息", res.status,function(){
                                        	var grid = Ext.getCmp("grid");
                                        	grid.getStore().remove(rec);
                                        });
                                    } 
                                    else {
                                        Ext.Msg.alert("审核失败", res.status);
                                    }
                                }
                            });
                        }
                    }]
            },{
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 30,
                text: '拒绝通过审核',
                align: 'center',
                items: [{
                        icon: 'images/user/edit_user.png',
                        tooltip: '拒绝通过审核',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            Ext.Ajax.request({
                                url: "userManagerAction!auditUser?user.userId="+rec.get("userId"),
                                method: "get",
                                params:{isApproval:false},
                                success: function(response) {
                                    var res = Ext.JSON.decode(response.responseText);
                                    if (res.success == true) {
                                        Ext.Msg.alert("提示信息", res.status,function(){
                                        	var grid = Ext.getCmp("grid");
                                        	grid.getStore().remove(rec);
                                        });
                                    } 
                                    else {
                                        Ext.Msg.alert("审核失败", res.status);
                                    }
                                }
                            });
                        }
                    }]
            }
        ]
    });
    
   
});