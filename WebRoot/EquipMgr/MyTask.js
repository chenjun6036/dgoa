Ext.onReady(function() {
    Ext.QuickTips.init();
    var store = Ext.create('Ext.data.Store', {
        fields: [
            {name: 'title',type: 'string'},  //标题
            {name: 'taskId',type: 'string'},  //任务id
            {name: 'applyTime',type: 'Date'},  //申请时间
            {name: 'applicant',type: 'string'},  //申请人
            {name: 'formId',type: 'Long'},  //
            {name: 'formTemplateName',type: 'string'} //模板名称
        ],
        proxy: {
            type: 'ajax',
            url: 'approvalFlowAction!myTaskList?formTemplateId=5'
        }
    });
    store.load();
    
    var approvedInfos = Ext.create('Ext.data.Store', {
        fields: [
            {name: 'approver.realName',type: 'string'},  //审核人真实姓名
            {name: 'approveTime',type: 'Date'},  //审核时间
            {name: 'approval',type: 'boolean'},  //状是否通过
            {name: 'comment',type: 'string'}//审核内容
        ],
        proxy: {
            type: 'ajax',
            url: 'approvalFlowAction!approvedHistory'
        }
    });
    
//    var type = Ext.create('Ext.data.Store', {
//        fields: ['name'],
//        data: [
//            {"name": "==显示全部=="}, 
//            {"name": "物料采购申请"}, 
//            {"name": "物料领用申请"}
//        ]
//    });
    
    function showApprovalWindow(taskId, formId, formTemplateName) {
        Ext.create('Ext.window.Window', {
            title: "审批"+formTemplateName,
            id: 'edit',
            modal: true,
            resizable: true,
            closable: true,
            layout: "fit",
            items: [{
                    xtype: "form",
                    border: false,
                    bodyPadding: 15,
                    width: 350,
                    url: "roleManagerAction!updateRole?roleId=",
                    method: "post",
                    defaultType: "textfield",
                    fieldDefaults: {
                        labelWidth: 80,
                        labelSeparator: ": ",
                        anchor: "0"
                    },
                    items: [
                        {
                            fieldLabel: "审批意见",
                            name: "comment",
                            emptyText: "请输入审批意见",
                            xtype: 'textarea'
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: "toolbar",
                            items: [
                                {
                                    text: "点击下载申请文档",
                                    icon: "images/download.png",
                                    handler: function() {
                                        window.open('approvalFlowAction!downloadFile?formId=' + formId);
                                    }
                                }
                            ]
                        }
                    ],
                    buttons: [
                        {
                            text: '同意',
                            handler: function() {
                                var form = this.up("form").getForm();
                                var window = Ext.getCmp("edit");
                                form.submit({
                                    method: "post",
                                    submitEmptyText: false,
                                    params: {approval: true,formId: formId,taskId: taskId},
                                    url: 'approvalFlowAction!approve',
                                    //                waitMsg: '正在提交申请...',
                                    success: function(form, action) {
                                        Ext.Msg.alert("提示", "审批成功！");
                                        store.reload();
                                        window.close();
                                    },
                                    failure: function() {
                                        Ext.Msg.alert("错误", "审批出现错误！");
                                        //					                	window.parent.location = "login.jsp"
                                        window.close();
                                    },
                                    scope: form
                                });
                            }
                        }, {
                            text: '不同意',
                            handler: function() {
                                var form = this.up("form").getForm();
                                var window = Ext.getCmp("edit");
                                form.submit({
                                    method: "post",
                                    submitEmptyText: false,
                                    params: {approval: false,formId: formId,taskId: taskId},
                                    url: 'approvalFlowAction!approve',
                                    //                waitMsg: '正在提交申请...',
                                    success: function(form, action) {
                                        Ext.Msg.alert("提示", "审批成功！");
                                        store.reload();
                                        window.close();
                                    },
                                    failure: function() {
                                        Ext.Msg.alert("错误", "审批出现错误！");
                                        //					                	window.parent.location = "login.jsp"
                                        window.close();
                                    },
                                    scope: form
                                });
                            }
                        }
                    ]
                }]
        }).show();
    }
    
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
        title: '审批列表',
        icon: 'images/table.png',
        renderTo: Ext.getBody(),
        collapsible: true,
        id: 'grid',
        store: store,
        //  features:[{ftype:"grouping"}],       
        forceFit: true, //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。       
//        tbar: [
//            {
//                xtype: "combobox",
//                id: 'status',
//                fieldLabel: "类型",
//                labelSeparator: ":",
//                labelWidth: 60,
//                forceSelection: true,
//                editable: false,
//                store: type,
//                triggerAction: 'all',
//                forceSelection: true,
//                displayField: 'name',
//                valueField: 'name',
//                value: type.getAt(0).get("name"),
//                listeners: {change: function(field, newvalue) {
//                        store.clearFilter(true);
//                        if (newvalue != "==显示全部==") {
//                            store.filter("formTemplateName", newvalue);
//                        } else {
//                            store.filterBy(function() {
//                                return true;
//                            });
//                        }
//                    }
//                }
//            }
//        ],
        columns: [{
                text: '标题',
                dataIndex: 'title'
            }, {
                text: '申请人',
                dataIndex: 'applicant'
            }, {
                text: '申请时间',
                dataIndex: 'applyTime',
                renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
            }, /*{
                text: '类型',
                dataIndex: 'formTemplateName'
            },*/ {
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 30,
                text: '审批处理',
                align: 'center',
                items: [{
                        icon: 'images/tableedit.png',
                        tooltip: '审批处理',
                        handler: function(grid, rowIndex, colIndex) {
                            var rec = grid.getStore().getAt(rowIndex);
                            showApprovalWindow(rec.get('taskId'), rec.get('formId'), rec.get('formTemplateName'));
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
                            showApprovedInfosWindow(rec.get('formId'));
                        }
                    }]
            }
        ]
    });
})
