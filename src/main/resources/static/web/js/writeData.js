(function(){
	var _this = new Vue({
	  el: '#app',
	  data: {
		innerHeight: "",
		tableHeight: "",
		dialogFormVisible:false,		
		columns: [],
		fileList:[],
		imageUrl:"",
		nowId:"",
		flag:true,
		totalData:0,		
		title:'新增产品',
		query:{
			name:'',
			stuff:'',
			date:'',
			source:'',
			pageNo:1, //初始页
			pageSize:10,    //    每页的数据
			partner:''
		},
		val:{
			id:'',
			name:'',
			stuff:'',
			describes:'',
			prime:'',
			type:'',
			primerate:'2',
			disrate:'0.85',			
			profitrate:'1',
			source:'',
			remark:'', 
			partner:'',
			date:'',
			status:'',
			imgurl:''
		},
		tableData: [],
		tableChecked:[]
	  },
	  methods: {
		init: function() {
			_this.queryData();
		},
		 // 初始页currentPage、初始每页数据数pagesize和数据data
        handleSizeChange: function (size) {
            _this.query.pageSize = size;
			_this.queryData();
                
        },
		clearQuery:function(){
			_this.query = {
				name:'',
				stuff:'',
				date:'',
				source:'',
				pageNo:1, //初始页
				pageSize:10,    //    每页的数据
				partner:''
			},
			_this.queryData();
		},
        handleCurrentChange: function(pageNo){
            _this.query.pageNo = pageNo;
            _this.queryData();  
        },
		 openMsg: function(title,msg,type) {
			  if(type==null){
				  type='success';
			  }
			  this.$notify[type]({
		          title: title,
		          message: msg,
		          position:"top-center",
		          customClass: "tip_center",
		          duration: 1500,
		          offset: 0
		  })
		  },
			handleSuccess:function(response, file, fileList){
				if(response!=""&&(response+"").indexOf("成功")<0){
					_this.val.id = response;
				}				
				_this.submitData();
			},
			handlePictureCardPreview (file) {
	      _this.imageUrl = file.url;
     		 },
			handleError:function(err, file, fileList){
				_this.openMsg('错误','上传失败！','error');
			},
			uploadUrl(key){
					url ="/jade/upload?id="+key;
				return url;
			},
			handleChange(file, fileList) {
				_this.flag = false;
	      _this.$refs.upload.clearFiles();
	      _this.$refs.upload.uploadFiles.push(file);
	      _this.imageUrl = URL.createObjectURL(file.raw);
	    },
			beforeAvatarUpload:function(file) {
				var testmsg=file.name.substring(file.name.lastIndexOf('.')+1)
				const isJPG = file.type === 'image/jpeg';
				const isJPG2 = file.type === 'image/png';
				const isJPG3 = file.type === 'image/jpg';
				const isJPG4 = file.type === 'image/gif';
				const isJPG5 = file.type === 'image/bmp';
				if(!isJPG&&!isJPG2&&!isJPG3&&!isJPG4&&!isJPG5) {
					_this.openMsg('警告','请上传头像图片格式：jpeg,png,jpg,gif,bmp','warning');
				}
				return isJPG||isJPG2||isJPG3||isJPG4||isJPG5;
			},
		saveData:function(){
			if(_this.flag){
				_this.submitData();
			}else{
				 _this.$refs.upload.submit();
			}

		},
		submitData:function(){
			var param = _this.val;
			_this.flag = true;
			Vue.$http.post('/jade/addJade',param,function(result) {
					_this.closeDilog();
					_this.openMsg('提示','保存成功！');
					_this.queryData();
		 });
	 },
		addData:function(){
			_this.dialogFormVisible = true;
			_this.title = "新增产品";
		},
		handleClick:function(row){
			_this.val = row;
			_this.nowId = row.id;
			_this.imageUrl = row.imgurl;
			_this.title = "编辑产品";
			_this.dialogFormVisible = true;
		},
		closeDilog:function(){
			_this.flag = true;
		    this.dialogFormVisible = false;
				_this.imageUrl = '';
				var val = {
					id:'',
					name:'',
					stuff:'',
					describes:'',
					prime:'',
					type:'',
					primerate:'2',
					disrate:'0.85',			
					profitrate:'1',
					source:'',
					remark:'', 
					partner:'',
					date:'',
					status:'',
					imgurl:''
				};
				_this.val = val;
		},
		exportExcel:function(){
			var param = _this.query;
			window.location.href = "/jade/export?name="+param.name+"&stuff="+param.stuff+"&date="+param.date+"&source="+param.source+"&partner="+param.partner;
			// Vue.$http.post("/jade/export", param, function(result) {
			// });
		},
		deleteData:function(){
			if(_this.tableChecked==null||_this.tableChecked.length==0){
				_this.openMsg('提示','请选择需要剔除的数据','warning');
				return;
			}
			var ids = '';
			for(var i=0;i<_this.tableChecked.length;i++){
				   ids += _this.tableChecked[i].id +',';
			 }
		      this.$confirm('确认删除操作?', '提示', {
		          confirmButtonText: '确定',
		          cancelButtonText: '取消',
		          type: 'warning'
		        }).then(() => {
		          Vue.$http.get('/jade/delJade?id='+ids,function(result) {
		        	  _this.openMsg('提示','删除成功！');
		        	  _this.queryData();
				  });
		        }).catch(() => {
		        });

		},
		queryData: function() {
			var param = _this.query;
			Vue.$http.post("/jade/getJade", param, function(result) {
				var data = result.data.list;
				_this.totalData = result.data.total;
				for(var i=0;i<data.length;i++){					
					if(data[i].id!=null&&data[i].id==_this.nowId){
					    data[i].imgurl = "/jade/getImg?id="+data[i].id+"&time"+(new Date().getTime());
					 }else{
						data[i].imgurl = "/jade/getImg?id="+data[i].id;
					 }
					 data[i].preview =[];
					 data[i].preview[0]=data[i].imgurl;					 
				 }
				_this.tableData = data;
			});
		},
		handleSelectionChange:function(val){
			_this.tableChecked = val;
		},	  
		checkedNumber:function(type){
			var val = _this.val[type];
			val = val.trim();
			if (isNaN(val)){
				_this.openMsg('警告','请输入数字','warning');
				_this.val[type] = '';
				return false;
			}
			_this.val[type] = val;
		}
		}
	});

	_this.init();
})();
