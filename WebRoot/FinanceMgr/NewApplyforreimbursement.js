Ext.onReady(function(){
	var audits = Ext.create('Ext.data.Store', {
        fields: [
                 {name: 'realName',type: 'string'},  //用户名
                 {name: 'userId',type: 'int'}//用户id
             ],
             proxy: {
                 type: 'ajax',
                 url: 'userManagerAction!getAllAudits'
             }
         });
	audits.load();
	
	var executors = Ext.create('Ext.data.Store', {
        fields: [
                 {name: 'realName',type: 'string'},  //用户名
                 {name: 'userId',type: 'int'}//用户id
             ],
             proxy: {
                 type: 'ajax',
                 url: 'userManagerAction!getAllExecutor'
             }
         });
	executors.load();
	
//	var admins = Ext.create('Ext.data.Store', {
//		fields: [
//		         {name: 'realName',type: 'string'},  //用户名
//		         {name: 'userId',type: 'int'}//用户id
//		         ],
//		         proxy: {
//		        	 type: 'ajax',
//		        	 url: 'userManagerAction!getAllAdmin'
//		         }
//	});
//	admins.load();
//	
//	var superAdmins = Ext.create('Ext.data.Store', {
//		fields: [
//		         {name: 'realName',type: 'string'},  //用户名
//		         {name: 'userId',type: 'int'}//用户id
//		         ],
//		         proxy: {
//		        	 type: 'ajax',
//		        	 url: 'userManagerAction!getAllSuperAdmin'
//		         }
//	});
//	superAdmins.load();
	
	
	Ext.create('Ext.form.Panel', {
        renderTo: document.body,
        width: 500,
        frame: true,
        title: '财务报销申请',
        bodyPadding: '10 10 0',
        id:'form',
        defaults: {
            anchor: '100%',
            allowBlank: false,
            msgTarget: 'side',
            labelWidth: 100
        },

        items: [
	        {
	        	xtype: 'combo',
	        	fieldLabel: '审核人',
	        	emptyText:'请选择审核人',
	        	blankText:"审核人不可为空",
	        	queryMode: 'local',
	        	store:audits,
	        	displayField: 'realName',
	            valueField: 'userId',
	            name:'audit',
	            editable:false
	        },
//	        {
//	        	xtype: 'combo',
//	        	fieldLabel: '超级管理员',
//	        	emptyText:'请选择超级管理员（可选）',
//	        	queryMode: 'local',
//	        	store:superAdmins,
//	        	allowBlank:true,
//	        	displayField: 'realName',
//	        	valueField: 'userId',
//	        	id: 'superAdmin',
//	            editable:false
//	        },
	        {
	        	xtype: 'combo',
	        	fieldLabel: '执行人',
	        	emptyText:'请选择执行人',
	        	blankText:"执行人不可为空",
	        	queryMode: 'local',
	        	store:executors,
	        	displayField: 'realName',
	        	valueField: 'userId',
	        	name: 'executor',
	            editable:false
	        },
//	        {
//	        	xtype: 'combo',
//	        	fieldLabel: '管理员',
//	        	emptyText:'请选择管理员',
//	        	blankText:"管理员不可为空",
//	        	queryMode: 'local',
//	        	store:admins,
//	        	displayField: 'realName',
//	        	valueField: 'userId',
//	        	name:'admin',
//	            editable:false
//	        },	        
	        {
	            xtype: 'filefield',
	            id: 'form-file',
	            emptyText: '请选择财务报销申请表',
	            blankText:"必须上传财务报销申请表",
	            fieldLabel: '财务报销申请表',
	            name: 'file',
	            buttonText: '浏览',
	            buttonConfig: {
	                iconCls: 'upload-icon'
	            }
	        },{
	            xtype: 'textarea',
	            fieldLabel: '备注',
	            name:'remark',
	            allowBlank:true
	        },{
	        	xtype:"hidden",
	        	name:'formTemplateId',
	        	value:4
	        }],       
        buttons: [{
            text: '提交',
            handler: function(){
                var form = this.up('form').getForm();
                var file = Ext.getCmp("form-file");
                var fileName = file.getRawValue();
                if(form.isValid()){
                    form.submit({
                    	method:"post",
                    	submitEmptyText:false,
                    	params:{fileName:fileName},
                        url: 'approvalFlowAction!submit',
//                        waitMsg: '正在提交申请...',
                        success: function(form,action){
                        	Ext.Msg.alert("提示","申请成功，请耐心等待相关人员审批！");
                        	form.reset();
                        },
                        failure:function(){
                        	Ext.Msg.alert("提示","申请失败！");
//                        	window.parent.location = "login.jsp"
                        },
                        scope: form
                    });
                }
            }
        },{
            text: '重置',
            handler: function() {
                this.up('form').getForm().reset();
            }
        }]
    });

})