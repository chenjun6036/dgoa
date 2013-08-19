Ext.onReady(function(){
	Ext.QuickTips.init();
	var store = Ext.create('Ext.data.TreeStore', {
        proxy: {
            type: 'ajax',
            url: 'loginAction!showTree'
        }    	
    });
    store.load();
	var viewport = Ext.create('Ext.Viewport', {
        id: 'test',
        layout: 'border',
        items: [
        	{
        		xtype:'panel',
        		height:94,        		
        		region:'north',
        		html:'<iframe name="top" width="100%" height="100%" src="top.jsp"  frameborder="0"></iframe>'
        	},
            Ext.create('Ext.Component', {
                region: 'south',
                height: 30,
                autoEl: {
                    tag: 'div',
                    html: "<p style='text-align:center;margin-top:5px'><font style='font-size:15px'>Copyright© 2013 njust,all rights reserved</font></p>"
                }
            }), 
            Ext.create('Ext.panel.Panel', {
                region: 'center',
                id: 'centerPanel',
                title: '功能菜单',
                icon:'images/application.png',
                html:'<iframe id="center-iframe" scrolling="auto" frameborder="0"    width="100%"   height="100%" src="welcome.jsp"></iframe>'
            }), 
            {
                xtype: "treepanel",
                region: 'west',
                id: 'tree',
                width: 200,
                store: store,
                split: true,
                useArrows: true,
                collapsible: true,
                dockedItems: [{
                        xtype: 'toolbar',
                        items: [{
                                text: '全部展开',
                                icon:"images/expand-all.gif",
                                handler: function() {
                                    var tree = Ext.getCmp("tree");
                                    tree.getEl().mask('Expanding tree...');
                                    var toolbar = this.up('toolbar');
                                    toolbar.disable();
                                    tree.expandAll(function() {
                                        tree.getEl().unmask();
                                        toolbar.enable();
                                    });
                                }
                            }, {
                                text: '全部折叠',
                                icon:"images/collapse-all.gif",
                                handler: function() {
                                    var toolbar = this.up('toolbar');
                                    toolbar.disable();
                                    var tree = Ext.getCmp("tree");
                                    tree.collapseAll(function() {
                                        toolbar.enable();
                                    });
                                }
                            }]
                    }],
                rootVisible: false,
                title: '功能目录',
                icon:"images/tree.png",      
                listeners: {
                    itemclick: function(tree, record, items, index) {  
                    	centerPanel = Ext.getCmp("centerPanel");
                        if (record.data.leaf) {
                        	Ext.get("center-iframe").dom.src=record.data.hrefTarget;
                        	centerPanel.setTitle(record.data.text);
                        } else {
                            var exp = record.isExpanded();
                            if (exp) {
                                record.collapse();
                            } 
                            else {
                                record.expand();
                            }
                        }
                    }
                }
            }
        ]
    });
});