Ext.onReady(function() {
    Ext.QuickTips.init();
    var store = Ext.create('Ext.data.Store', {
        fields: [
            {name: 'title',type: 'string'},  //标题
            {name: 'path',type: 'string'},  //申请表下载路径
            {name: 'applyTime',type: 'Date'},  //申请时间
            {name: 'applicant.realName',type: 'string'},  //申请人
            {name: 'status',type: 'string'},  //状态
            {name: 'id',type: 'Long'},  //id
            {name: 'formTemplate.name',type: 'string'} //模板名称
        ],
        proxy: {
            type: 'ajax',
            url: 'approvalFlowAction!formManageList?formTemplateId=3'
        }
    });
    var params = store.getProxy().extraParams;
    params.status = "==显示全部==";
    store.load();
    
    var status = Ext.create('Ext.data.Store', {
        fields: ['name'],
        data: [
            {"name": "==显示全部=="}, 
            {"name": "审批中"}, 
            {"name": "已通过"}, 
            {"name": "未通过"}
        ]
    });
    
    var type = Ext.create('Ext.data.Store', {
        fields: ['name'],
        data: [
            {"name": "==显示全部=="}, 
            {"name": "现金预支申请"}, 
            {"name": "财务报销申请"}
        ]
    });
    
    var approvedInfos = Ext.create('Ext.data.Store', {
        fields: [
            {name: 'approver.realName',type: 'string'},  //标题
            {name: 'approveTime',type: 'Date'},  //状态
            {name: 'approval',type: 'boolean'},  //状态
            {name: 'comment',type: 'string'}
        ],
        proxy: {
            type: 'ajax',
            url: 'approvalFlowAction!approvedHistory'
        }
    });
    
    function showApprovedInfosWindow(formId) {
        var params = approvedInfos.getProxy().extraParams;
        params.formId = formId;
        approvedInfos.load();
        Ext.create('Ext.window.Window', {
            title: "审批记录",
            id: 'edit',
            modal: true,
            resizable: true,
            closable: true,
            layout: "fit",
            items: [{
                    xtype: "grid",
                    title: '审批列表',
                    icon: 'images/table.png',
                    collapsible: true,
                    width: 500,
                    store: approvedInfos,
                    //  features:[{ftype:"grouping"}],       
                    forceFit: true, //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。                     
                    columns: [{
                            text: '审批人',
                            dataIndex: 'approver.realName',
                            flex:5
                        }, {
                            text: '审批时间',
                            dataIndex: 'approveTime',
                            flex:12,
                            renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
                        }, {
                            text: '是否通过',
                            dataIndex: 'approval',
                            flex:5,
                            renderer: function(value, meta, record) {
                                if (value == true)
                                    return '<span>是</span>';
                                else
                                    return '<span>否</span>'
                            }
                        },{
                        	text: '审批意见',
                            dataIndex: 'comment',
                            flex:20
                        }
                    ]
                }]
        }).show();
    }
    
    
    Ext.create('Ext.grid.Panel', {
        title: '申请记录列表',
        icon: 'images/table.png',
        renderTo: Ext.getBody(),
        collapsible: true,
        id: 'grid',
        store: store,
        //  features:[{ftype:"grouping"}],       
        forceFit: true, //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。            
        tbar: [
            {
                xtype: "combobox",
                id: 'typeCom',
                fieldLabel: "类型",
                labelSeparator: ":",
                labelWidth: 60,
                forceSelection: true,
                editable: false,
                store: type,
                triggerAction: 'all',
                forceSelection: true,
                displayField: 'name',
                valueField: 'name',
                value: type.getAt(0).get("name"),
                listeners: {change: function(field, newvalue) {
                        store.clearFilter(true);
                        if (newvalue != "==显示全部==") {
                            store.filter("formTemplate.name", newvalue);
                            var statusCom = Ext.getCmp('statusCom').getValue();
                            if (statusCom != "==显示全部==") {
                                store.filter("status", statusCom);
                            }
                        } else {
                            var statusCom = Ext.getCmp('statusCom').getValue();
                            if (statusCom != "==显示全部==") {
                                store.filter("status", statusCom);
                            } else {
                                store.filterBy(function() {
                                    return true;
                                });
                            }
                        }
                    }
                }
            }, {
                xtype: "combobox",
                id: 'statusCom',
                fieldLabel: "当前状态",
                labelSeparator: ":",
                labelWidth: 60,
                forceSelection: true,
                editable: false,
                store: status,
                triggerAction: 'all',
                forceSelection: true,
                displayField: 'name',
                valueField: 'name',
                value: status.getAt(0).get("name"),
                listeners: {change: function(field, newvalue) {
                        store.clearFilter(true);
                        if (newvalue != "==显示全部==") {
                            store.filter("status", newvalue);
                            var typeCom = Ext.getCmp('typeCom').getValue();
                            if (typeCom != "==显示全部==") {
                                store.filter("formTemplate.name", typeCom);
                            }
                        } else {
                            var typeCom = Ext.getCmp('typeCom').getValue();
                            if (typeCom != "==显示全部==") {
                                store.filter("formTemplate.name", typeCom);
                            } else {
                                store.filterBy(function() {
                                    return true;
                                });
                            }
                        }
                    }
                }
            }
        ],
        columns: [{
                text: '标题',
                dataIndex: 'title'
            }, {
                text: '申请人',
                dataIndex: 'applicant.realName',
                width:30
            }, {
                text: '申请时间',
                dataIndex: 'applyTime',
                renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
                width:40
            }, {
                text: '类型',
                dataIndex: 'formTemplate.name',
                width:30
            }, {
                text: '当前状态',
                dataIndex: 'status',
                width:30
            }, {
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 30,
                text: '下载申请文件',
                align: 'center',
                items: [{
                        icon: 'images/download.png',
                        tooltip: '下载申请文件',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            window.open('approvalFlowAction!downloadFile?formId=' + rec.get('id'));
                        }
                    }]
            }, {
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 30,
                text: '查看审批记录',
                align: 'center',
                items: [{
                        icon: 'images/detail.png',
                        tooltip: '查看审批记录',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            showApprovedInfosWindow(rec.get('id'));
                        }
                    }]
            }/*,{
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 30,
                text: '删除',
                align: 'center',
                items: [{
                    icon: 'images/tabledelete.png',
                    tooltip: '删除该申请',
                    handler: function(grid, rowIndex, colIndex) {
                        var rec = grid.getStore().getAt(rowIndex);
                        Ext.Msg.confirm("提示信息", "你确认删除该申请？删除后将无法恢复！", function(btn) {
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
            }*/
        ]
    });
})
