
 ;(function(){
 	function animation(){
	  	return {
	  		show:show,
	  		hide:hide,
	  		fadeToggle:fadeToggle,
	  		fadeIn:fadeIn,
	  		fadeOut:fadeOut,
	  		fadeTo:fadeTo,
	  		fadeToggle:fadeToggle,
	  		getStyle:getStyle
	  	}
	  	//获取属性值
		function getStyle(obj, prop) {
			var prevComputedStyle = document.defaultView ? document.defaultView.getComputedStyle( obj, null ) : obj.currentStyle;
			return prevComputedStyle[prop];
		}
		
		/*
			obj:dom对象
			speed:执行速度 fast slow 3000等
			func:回调函数
		*/
		 
		function hide(obj,speed,func){
			if(obj.style.display==='none'){
				return ;
			}
			//获取本来的宽带和高度
			var width = getStyle(obj, 'width');
			var height = getStyle(obj, 'height');
			var new_func = function(){
				//设置回原来的高度和宽度
				obj.style['width'] = width;
				obj.style['height'] = height ;
				func();
			}
			animate(obj,{opacity: 0,display:'none',width:0,height:0},speed,new_func);
		}
 
		function show(obj,speed,func){
			if(obj.style.display!=='none'){
				return ;
			}
			//获取本来的宽带和高度
			var width = getStyle(obj, 'width');
			var height = getStyle(obj, 'height');
			//设置从0开始
			obj.style['width'] = 0;
			obj.style['height'] = 0 ;
			
			animate(obj,{opacity: 1,display:'block',width:width,height:height},speed,func);
		}
		 
		function toggle(obj,speed,func){
			if(obj.style.display==='none'){
				show(obj,speed,func);
			}else{
				hide(obj,speed,func);
			}
					
		 }
		 function fadeOut(obj,speed,func){
			animate(obj,{opacity: 0,display:'none'},speed,func);
		 }
		 
	  	 function fadeIn(obj,speed,func){
			animate(obj,{opacity: 1,display:'block'},speed,func);
		 }
		 
		 function fadeToggle(obj,speed,func){
			if(obj.style.display==='none'){
				fadeIn(obj,speed,func);
			}else{
				fadeOut(obj,speed,func);
			}
		 } 
		 function fadeTo(obj,speed,opacity,func){
			if(opacity>0 && obj.style.display==='none'){
				animate(obj,{opacity: opacity,display:'block'},speed,func);
			}else{
				animate(obj,{opacity: opacity},speed,func);
			}
		 }
		  /*
		obj:dom对象
		prop:动画参数
		speed:执行速度 fast slow 3000等
		func:回调函数
		*/
	   function animate(obj,prop,speed,func){
			//防止重复动画事件
			if(obj.timer) return ;
			//定义定时器执行次数和总执行时间
			var	limit=20,totalTime; 
			if(typeof speed==='number'){//如果传入的是
				totalTime = speed;
			}else if(speed==='slow'){
				totalTime = 600;
			}else if(speed==='fast'){
				totalTime = 200;
			}else{
				totalTime = 400;
			}
			
			var time = totalTime/limit;
		 
			var n=0,cache={},display,primary_cur;//cache用来缓存，省的每次都去dom获取
			obj.timer = setInterval(function(){
				n++;//执行次数每次递增
				for(var p in prop){
					if("display"===p) {
						display = prop["display"];
						if(display!=='none'){
							obj.style['display'] = display;
						}
						delete prop["display"];
						continue;
					}
					//判断是否是可以递增的属性，如果不是则直接让它生效，并从prop中删除，删除后就不用每次任务都执行它
					var reg = /^(\d)+(px$)?/;//数字和像素这样的判定为可以递增的属性
					if(!reg.test(prop[p])){
						obj.style[p] = prop[p];
						delete prop[p];
						continue;
					}
					
					var value,opacityFlag=(p == "opacity")?true:false;
					var cur = 0;
					if(cache[p+"_cur"]){//从缓存中取
						cur = cache[p+"_cur"];
						value = cache[p+"_value"];
					}else{
						value = prop[p];
						if(opacityFlag) {
							//如果本来是隐藏的则cur默认就是0
							if(getStyle(obj, 'display')!=='none'){
								cur = Math.round(parseFloat(getStyle(obj, p)) * 100);
							}
						} else {
							cur = parseInt(getStyle(obj, p));
							//处理100px的格式
							(typeof value==='string') && (value=value.replace(/px$/,""));
						}
						primary_cur=cur;
						cache[p+"_value"] = value;
					}
					
					var incre ;
					if(cache[p+'_increment']){//如果缓存中有则从中取
						incre = cache[p+'_increment'];
					}else{
						if(opacityFlag){
							incre = (value*100-cur)/limit;//计算每次变化值
						}else{
							incre = (value-cur)/limit;//计算每次变化值
						}
						cache[p+'_increment']= incre;
					}
					//缓存起来，这样就不用每次都去dom中获取了。
					cache[p+"_cur"] = cur + incre;
					
					if (opacityFlag) {
						obj.style.filter = "alpha(opacity:"+(cur + incre)+" )";
						obj.style.opacity = (cur + incre)/100 ;
					}else {
						obj.style[p] = cur + incre + "px";
					}
				}
				//如果达到了最大执行次数，要清除定时器，并执行回调函数
				if(n==limit){
					if(display==='none'){
						obj.style['display'] = 'none';
					}
					//清除定时器
					clearInterval(obj.timer);
					obj.timer=undefined;
					func && func();
				}
			},time)
		}
	}
 
	var _ = {
			isFunction : function(o){
				return o!== null &&typeof o ==='function';
			}
		}
 
	function textToast(msg,option){
	  	 var animate = animation();
	  	 var option = option||{},duration=3000,color='#6a6a6a',msgColor='white',type,msgType='normal',openCallback,closeCallback,showFlag
	  	 				,bgFlag,width='300',left,title,faded=true,marginLeft=150;
	  	 				 
     	 
	  	 if(option){
	  	  	option.duration && (function(){
	  	  		duration = option.duration;
	  	  		if(duration==='slow'){
	  	  			duration = '3000';
	  	  		}else if(duration==='fast'){
	  	  			duration = '1000';
	  	  		}else if(duration==='normal'){
	  	  			duration = '2000';
	  	  		}
	  	  	}());
	  	  	option.openCallback && (openCallback = option.openCallback );
	  	  	option.closeCallback && (closeCallback = option.closeCallback );
	  	  	option.color && (color = option.color );
	  	  	option.msgColor && (msgColor = option.msgColor );
	  	  	option.type && (type = option.type);
	  	  	option.msgType && (msgType = option.msgType);
	  	  	option.title && (title = option.title);
	  	  	
	  	  	if(typeof option.isbg==='boolean'){
	  	  		bgFlag = option.isbg;
	  	  	}
	  	  	if(typeof option.faded==='boolean'){
	  	  		faded = option.faded;
	  	  	}
	  	  		
	  	  	(type==='show')?(showFlag=true):(showFlag=false);//是否一直显示
	  	  	if(type==='show'){//如果是一直展示的，必须要有关闭按钮和标题
	  	  		//判断有没有标题信息，没有就默认
	  	  		if(!title){
	  	  			title='消息';
	  	  		}
	  	  	}
	  	  	
	  	  	if(option.width){
	  	  		var t_width = option.width;
	  	  		if(/(\d+)px/.test(t_width) || /(\d+)/.test(t_width)){
	  	  			width = RegExp.$1;
	  	  		}
	  	  	}
	  	  	left = (window.screen.availWidth-30-width)/2+'px';
	  	  	
	  	  	marginLeft=-Math.round(width/2)+'px';
	  	  	width+='px';
	  	 }
	  	 
	  	 // 兼容多层弹出页面
     	 var parent = option.parent||window;
     	 var realDocument =parent.document;
     	 var realEl = realDocument.body;
	  	 
		 var toastDiv = realDocument.getElementById("toast_content_div");
		 if(toastDiv){
		 	 if(showFlag===toastDiv.alwaysShowFlag && bgFlag===toastDiv.bgFlag){//toastDiv div已经创建，并且显示状态没有改变，则不需要重新生成DIV
			 	if(bgFlag){//如果是有遮罩的，相互切换需要另外处理
			 	
			 		/*var toast_content_bg = document.getElementById("toast_content_bg");
			 			toast_content_bg.style.height = getToastHeight()+"px";
			 		var toast_content_child_div = document.getElementById("toast_content_child_div");
			 			toast_content_child_div.style.width=width;
					toast_content_bg.childNodes[0].style.top = (window.scrollY||window.document.documentElement.scrollTop) + window.screen.availHeight * 0.25 +'px';
					*/
					toastDiv.parentElement.removeChild(toastDiv);
			 		clearTimeout(toastDiv.timmer);
			 	 	toastDiv=null;
			 	}else{
			 		var toast_span_msg_id = realDocument.getElementById("toast_span_msg_id");
			 		toast_span_msg_id.innerText=msg;
			 		toast_span_msg_id.style.color=msgColor;
			 		toastDiv.style.backgroundColor=color;
			 		toastDiv.style.top = (window.scrollY||realDocument.documentElement.scrollTop) + window.screen.availHeight * 0.25 +'px';
			 		
			 		//用渐入来显示
					showHide('show');
					
					clearTimeout(toastDiv.timmer);
					//执行显示函数
					openCallback && _.isFunction(openCallback) && openCallback();
					if(!showFlag){
						toastDiv.timmer = setTimeout(function(){
							 showHide('hide',closeCallback);
						},duration);
					}
					return ;
			 	}
			 }else{
			 	 toastDiv.parentElement.removeChild(toastDiv);
			 	 clearTimeout(toastDiv.timmer);
			 	 toastDiv=null;
			 }
		 }
		 
		 var html='';
		 toastDiv = realDocument.createElement("div");
		 toastDiv.alwaysShowFlag=showFlag;
		 toastDiv.bgFlag=bgFlag;
		 toastDiv.id="toast_content_div";	
		 toastDiv.style.position = "absolute";		
		 
		 if(bgFlag){
		 	toastDiv.style.top="0px";
		    toastDiv.style.margin ="0";
		    toastDiv.style.padding ="0";
		    html = '<div id="toast_content_bg" style="DISPLAY: block; Z-INDEX: 1; BACKGROUND: #ccc;LEFT: 0px; POSITION: absolute; TOP: 0px;FILTER: alpha(opacity=100);opacity: 1">';
		    html +='<div id="toast_content_child_div"  style="background:'+color+';width:'+width+';z-index:5;left:'+left+';top:25%;margin-left:'+marginLeft+';position:absolute;border-radius:8px;FILTER: alpha(opacity=0);opacity: 0">';
		 }else{
		 	toastDiv.style.cssText="background:"+color+";width:"+width+";z-index:5;left:"+left+";top:35%;position:absolute;border-radius:8px;FILTER: alpha(opacity=70);opacity: 0.7;display:none;min-height:80px;";
		 }	
		 
		 if(title){
		 	 html +='<div style="border-bottom: 1px dashed rgb(221, 221, 221);">';
		 	 html +='<div><span style="display: block;font-size: 14px;padding: 8px 15px;background-color: lightgoldenrodyellow;border-radius: 8px 8px 0px 0px;border-bottom: 2px solid rgb(48, 153, 220);font-weight: bold;">' + title + '</span>';
			 html +='<span id="msg_ico" style="display: block;position: absolute;right: 10px;top: 9px;border: 1px solid gray;width: 18px;height: 18px;text-align: center;line-height: 15px;cursor: pointer;border-radius: 12px;">x</span></div>';
		 }
		 html +='<div style="margin-left: 10px;margin-right: 10px;">';
		 if(msgType==='suc'||msgType==='success'){
		 	 html +='<span style="font-size: 40px;color: springgreen;">&#9786;</span>';
		 }else if(msgType==='fail'){
		 	 html +='<span style="font-size: 40px;color: orangered;">&#9785;</span>';
		 }else if(msgType==='warn'){
		 	 html +='<span style="font-size: 40px;color: yellow;">&#9888;</span>';
		 }else{//没有图片
		 	
		 }
		 
		 html += "<span id='toast_span_msg_id' style='font-size:16px;padding-left:5px;z-index:6;text-align: center;color:"+msgColor+";word-wrap:break-word;line-height:2;'>"+msg+"</span>";		
		 html +='</div></div>';
		 function closeEvent(){
		 	return closeDiv.bind(this);
		 }
		 			
		 if(showFlag){
		 	 html +="<div style='margin:3px;text-align: center;'>"
				  +"<input id='confirmEventDiv' type='button' value='确定' style='width:85px;height:30px;color:white;border:none;cursor:pointer;background-color:rgb(187, 28, 28);letter-spacing: 6px;'>"
				  +"<input id='closeEventDiv' type='button' value='取消' style='width:85px;height:30px;color:white;border:none;cursor:pointer;background-color:slategray;margin-left: 20px;letter-spacing: 6px;'>"
		 }			
		 
		 if(bgFlag){
		 	html +="</div></div>";
		 }
		 toastDiv.innerHTML	= html ;
		 realEl.appendChild(toastDiv);
		 
		 if(bgFlag){
	    	var toast_content_bg = realDocument.getElementById("toast_content_bg");
				toast_content_bg.style.width =realEl.clientWidth-10+"px";
				toast_content_bg.style.height=getToastHeight()+'px';
				toast_content_bg.childNodes[0].style.top = (parent.scrollY||realDocument.documentElement.scrollTop) + parent.screen.availHeight * 0.25 +'px';
		 }else{
		 	toastDiv.style.top = (parent.scrollY||realDocument.documentElement.scrollTop) + parent.screen.availHeight * 0.25 +'px';
		 }
		
		 //执行显示 
		 showHide('show',openCallback);
		 	
		 if(showFlag){
		 	var confirmEventDiv = realDocument.getElementById("confirmEventDiv");
		 	var closeEventDiv = realDocument.getElementById("closeEventDiv");
		 	var msg_ico = realDocument.getElementById("msg_ico");
		 	if(document.addEventListener){
		 		confirmEventDiv && confirmEventDiv.addEventListener('click',confirmDiv);
		 		closeEventDiv && closeEventDiv.addEventListener('click',closeDiv);
		 		msg_ico && msg_ico.addEventListener('click',closeDiv);
		 	}else{
		 		confirmEventDiv && confirmEventDiv.attachEvent('onclick',confirmDiv);
		 		closeEventDiv && closeEventDiv.attachEvent('onclick',closeDiv);
		 		msg_ico && msg_ico.attachEvent('onclick',closeDiv);
		 	}
		 }
		  
		 
		 
		 if(!showFlag){
			toastDiv.timmer = setTimeout(function(){
				showHide('hide',closeCallback);
			},duration);
		 }
 
		 function getToastHeight(){
		 	return parent.screen.availHeight > realEl.clientHeight ? parent.screen.availHeight : realEl.clientHeight;
		 	//return window.screen.availHeight > document.body.clientHeight ? window.screen.availHeight : document.body.clientHeight;
		 }
		 
		 function confirmDiv(){//关闭是不执行回调函数的
		 	clearTimeout(toastDiv.timmer);
		 	showHide('hide',closeCallback);
		 }
		 
		 function closeDiv(){//关闭是不执行回调函数的
		 	clearTimeout(toastDiv.timmer);
		 	showHide('hide');
		 }
		 
		 function showHide(type,callback){
		 	if(bgFlag){
				var toast_content_bg_div = realDocument.getElementById("toast_content_div");
				var toast_content_child_div = realDocument.getElementById("toast_content_child_div");
					if(faded){//需要执行渐入渐出动画
						if(type==='hide'){
							toast_content_bg_div && (animate.fadeOut(toast_content_bg_div,'slow'));
			 				toast_content_child_div && (animate.fadeOut(toast_content_child_div,'slow'));
					 	}else{
					 		toast_content_bg_div && (animate.fadeIn(toast_content_bg_div,'slow'));
					 		toast_content_child_div && (animate.fadeIn(toast_content_child_div,'slow'));
					 	}
					}else{
						do_showHide(toast_content_bg_div,type,true);
						do_showHide(toast_content_child_div,type,true);
					}
				
			}else{
				if(faded){//需要执行渐入渐出动画
					if(type==='hide'){
			 			toastDiv && (animate.fadeOut(toastDiv,'slow'));
				 	}else{
				 		toastDiv && (animate.fadeIn(toastDiv,'slow'));
				 	}
				 }else{
				 	do_showHide(toastDiv,type);
				 }	
			}
			
			setTimeout(function(){
				callback && _.isFunction(callback) && callback();
			},0)
			
			function do_showHide(el,type,opac){
				if(type==='hide'){
					el && (el.style.display='none');
				}else{
					if(el){
						if(opac){
							el.style.filter='alpha(opacity:100)';
							el.style.opacity=1;
						}
						el.style.display='block';
					}
				}
			}
		 }
    }
    
    
    function showModal(url,option){
     	 var html='',modalDiv,marginLeft,title,confimCall,cancelCall,confimCallback,cancelCallback,width='300px',height='300px';
     	 var option=option||{};
     	 
     	 // 兼容多层弹出页面
     	 var parent = option.parent||window;
     	 var realDocument =parent.document;
     	 var realEl = realDocument.body;
     	 
     	 title=option.title||'窗口';
     	 option.confimCall && (confimCall = option.confimCall );
     	 option.confimCallback && (confimCallback = option.confimCallback );
	  	 option.cancelCall && (cancelCall = option.cancelCall );
	  	 option.cancelCallback && (cancelCallback = option.cancelCallback );
	  	 option.width && (width = option.width );
	  	 option.height && (height = option.height );
	  	 
	  	 marginLeft=-parseInt(width)/2+'px',width=parseInt(width)+'px',height=parseInt(height)+'px';
	  	 
     	 
     	 var id = Math.random().toString().replace('.','');
     	 var modalDiv = realDocument.getElementById("content_div_"+id);	
     	 if(modalDiv){
     	 	realEl.removeChild(modalDiv);
     	 	modalDiv=null;
     	 }
     	 
     	 var frameId = "IFRAME_"+id;
     	 
		 modalDiv = realDocument.createElement("div");
		 modalDiv.id="content_div_"+id;	
		 modalDiv.style.position = "absolute";		
		 
	 	 modalDiv.style.top="0px";
	     modalDiv.style.margin ="0";
	     modalDiv.style.padding ="0";
	     html = '<div id="content_bg_'+id+'" style="Z-INDEX: 1; BACKGROUND: #ccc;LEFT: 0px; POSITION: absolute; TOP: 0px;FILTER: alpha(opacity=100);opacity: 1">';
	     html +='<div id="content_child_div_'+id +'" style="background:#908c8cd9;width:'+width+';height:'+height+';z-index:5;left:50%;margin-left:'+(marginLeft)+';position:absolute;border-radius:8px;">';
		 
		 if(title){
		 	 html +='<div style="border-bottom: 1px dashed rgb(221, 221, 221);height:87%;width: 100%;">';
		 	 html +='<div><span style="display: block;font-size: 14px;padding: 8px 15px;background-color: lightgoldenrodyellow;border-radius: 8px 8px 0px 0px;border-bottom: 2px solid rgb(48, 153, 220);font-weight: bold;">' + title + '</span>';
			 html +='<span id="msg_ico_'+id+'" style="display: block;position: absolute;right: 10px;top: 9px;border: 1px solid gray;width: 18px;height: 18px;text-align: center;line-height: 15px;cursor: pointer;border-radius: 12px;">x</span></div>';
		 }
		 html +='<div style="margin-left: 1px;margin-right: 1px;overflow: hidden;height: 87%;">';
		 html += "<iframe id='"+frameId+"' src='"+url+"' style='width: 100%; height: 100%;'></iframe>";		
		 html +='</div></div>';
		 
	 	 html +="<div style='margin:3px;text-align: center;'>"
			  +"<input id='confirmEventDiv_"+id+"' type='button' value='确定' style='width:85px;height:30px;color:white;border:none;cursor:pointer;background-color:rgb(187, 28, 28);letter-spacing: 6px;'>"
			  +"<input id='closeEventDiv_"+id+"' type='button' value='取消' style='width:85px;height:30px;color:white;border:none;cursor:pointer;background-color:slategray;margin-left: 20px;letter-spacing: 6px;'>"
		      +"</div>";
		 
		 html +='</div></div>';
		 
		 modalDiv.innerHTML	= html ;
		 realEl.appendChild(modalDiv);
		 
    	 var content_bg = realDocument.getElementById("content_bg_"+id);
			 content_bg.style.width =realEl.clientWidth+"px";
			 content_bg.style.height=getToastHeight()+'px';
			 //content_bg.childNodes[0].style.top = (parent.scrollY||realDocument.documentElement.scrollTop) + parent.screen.availHeight * 0.25 +'px';
    
    	
   		var confirmEventDiv = realDocument.getElementById("confirmEventDiv_"+id);
	 	var closeEventDiv = realDocument.getElementById("closeEventDiv_"+id);
	 	var msg_ico = realDocument.getElementById("msg_ico_"+id);
	 	if(document.addEventListener){
	 		confirmEventDiv && confirmEventDiv.addEventListener('click',confirmDiv);
	 		closeEventDiv && closeEventDiv.addEventListener('click',closeDiv);
	 		msg_ico && msg_ico.addEventListener('click',closeDiv);
	 	}else{
	 		confirmEventDiv && confirmEventDiv.attachEvent('onclick',confirmDiv);
	 		closeEventDiv && closeEventDiv.attachEvent('onclick',closeDiv);
	 		msg_ico && msg_ico.attachEvent('onclick',closeDiv);
	 	}
	
		 var childIframe = realDocument.getElementById(frameId);
		 var childWindow = childIframe.contentWindow;
		 function confirmDiv(){
			if(doCallback(childWindow[confimCall],confimCallback)){
				modalDiv.style.display="none";
			}
		 }
		 
		 function closeDiv(){//关闭是不执行回调函数的
		 	if(doCallback(childWindow[cancelCall],cancelCallback)){
		 		modalDiv.style.display="none";
		 	};
		 }
		 
		 function doCallback(fn,callback){
			if(fn && _.isFunction(fn)){
				var res = fn(callback);
				if(!res && String(res)=='false'){
					return false;
				}
			}
			return true;
		 }
    	function getToastHeight(){
		 	return parent.screen.availHeight > realEl.clientHeight ? parent.screen.availHeight : realEl.clientHeight;
		 }
    }
    
    function page(o,callback){
    	o=o||{};
    	var el = o.el;
    	this.pageNum=o.pageNum||0,this.pageSize=o.pageSize||10;
    	this.callback=callback||function(){};
    	var html="<div>";
	    
		   html+=' <table width="90%" border="0"  algin=center style="margin-top:3px;font-size: 13px;">';
				html+='<tr algin=center>';
					html+='<td algin=center><div style="margin-left: 12px;">';
							html+='<button id="firstPage" disabled style="letter-spacing: 3px;">首页</button>&nbsp;';
							html+='<button id="previous" disabled style="letter-spacing: 3px;">上一页</button>&nbsp;';
							html+='<button id="next"  disabled style="letter-spacing: 3px;">下一页</button>&nbsp;';
							html+='<button id="last"  disabled style="letter-spacing: 3px;">尾页</button>&nbsp;';
							html+='<span id=customPageNumSpan>第 '+this.pageNum+'</span> 页/共计 <span id=customTotalPageSpan>0</span> 页,每页显示 '+this.pageSize+' 条,总共 </span><span id=customTotalCountSpan>0</span> 条记录';
						html+='</div>';
					html+='</td>';
				html+='</tr>';
			html+='</table>';
		
		if(el && el.tagName && el.tagName.toLowerCase()=='div'){
			el.innerHTML=html;
			
			this.firstPage = document.getElementById("firstPage");
			this.firstPage.addEventListener('click',firstQuery.bind(this)); 
			
			this.previous = document.getElementById("previous");
			this.previous.addEventListener('click',previousQuery.bind(this))
			
			this.next = document.getElementById("next");
			this.next.addEventListener('click',nextQuery.bind(this))
			
			this.last = document.getElementById("last");
			this.last.addEventListener('click',lastQuery.bind(this));
			
			this.customTotalCountSpan=document.getElementById('customTotalCountSpan');
			this.customPageNumSpan=document.getElementById('customPageNumSpan');
			this.customTotalPageSpan=document.getElementById('customTotalPageSpan');
		}else{
		  throw	new Error("传入的对象必须是div！");
		}
		
		function firstQuery(){
			this.pageNum=1;
			this.query(1);
		}
		function previousQuery(){
			this.pageNum--;
			this.query(1);
		}
		function nextQuery(){
			this.pageNum++;
			this.query(1);
		}
		function lastQuery(){
			this.pageNum=this.totalPage;
			this.query(1);
		}
    }
    
    page.prototype.doUpdate=function(){
    	var total = Math.floor(this.totalCount/this.pageSize);
    	this.totalPage=this.totalCount%this.pageSize==0?total:total+1;
    	if(this.totalPage==1){
    		this.firstPage.disabled=true;
    		this.previous.disabled=true;
    		this.next.disabled=true;
    		this.last.disabled=true;
    	}else{
    		if(this.pageNum==1 && this.totalPage!=1){
	    		this.firstPage.disabled=true;
	    		this.previous.disabled=true;
	    		this.next.disabled=false;
	    		this.last.disabled=false;
	    	}else if(this.pageNum==this.totalPage){
	    		this.firstPage.disabled=false;
	    		this.previous.disabled=false;
	    		this.next.disabled=true;
	    		this.last.disabled=true;
	    	}else{
	    		this.firstPage.disabled=false;
	    		this.previous.disabled=false;
	    		this.next.disabled=false;
	    		this.last.disabled=false;
	    	}
    	}
    
    	
    	this.customTotalCountSpan.innerText=this.totalCount;
    	this.customTotalPageSpan.innerText=this.totalPage;
    	this.customPageNumSpan.innerText=this.pageNum;
    }
    page.prototype.query=function(type){
    	if(!type){
    		this.pageNum=1;
    	}
    	this.callback(this);
    }
    page.prototype.update=function(totalCount){
    	this.totalCount=totalCount;
    	this.doUpdate();
    }
    
    
    function checkValue(val,option){
    	var res;
    	option = option||{};
    	var type = option.type;
    	var len = option.length;
    	
    	res = checkType(type,val);
    	if(res){//不通过
    		return res;
    	}
    	res = checkLen(len,val);
    	if(res){//不通过
    		return res;
    	}
    	
    	function checkType(type,val){
    		var regObj={
    			Number:{reg:/^\d*$/,msg:'请填入数字'}
    			
    		}
    		var typeReg = regObj[type];
    		if(typeReg && typeReg.reg){
    			if(!typeReg.reg.test(val)){//不通过
    				return typeReg.msg;
    			}
    		}
    	}
    	
    	function checkLen(length,val){
    		if(val && val.length>length){
    			return '超过了'+length+'位！';
    		}
    	}
    	
    	return ;
    }
    
    function dateFormat(dataStr,pattern){
		var dt;
		if(dataStr)
			dt = new Date(dataStr);
		else{
			dt = new Date();
		}	
		
		var y = dt.getFullYear();
		var m = getTwoLengthDate(dt.getMonth()+1);
		var d = getTwoLengthDate(dt.getDate());
		
		if(pattern && pattern.toLowerCase()==='yyyy-mm-dd'){
			return y + "-" + m  + "-" + d;
		}else{
			var hh = getTwoLengthDate(dt.getHours());
			var mm = getTwoLengthDate(dt.getMinutes());		
			var ss = getTwoLengthDate(dt.getSeconds());
			if(pattern && pattern.toLowerCase()==='yyyy-mm-dd hh:mi'){
				return y + "-" + m  + "-" + d + " " +hh + ":" + mm
			}else{
				return y + "-" + m  + "-" + d + " " +hh + ":" + mm + ":" + ss;
			}
		}
		
		function getTwoLengthDate(s){
 		   	var ret;
 		   	s = String(s);
 		   	if(s==null){
 		   		ret = "00";
 		   	}
 		   	if(s.length<2){
 		   		ret = "0"+s;
 		   	}else if(s.length>2){
 		   		ret = s.substring(0,2);
 		   	}else{
 		   		ret = s;
 		   	}
 		   	return ret;
 	   }
	}
	
    var commonUtil={
 		textToast:textToast,
 		animation:animation,
 		showModal:showModal,
 		page:page,
 		checkValue:checkValue,
 		dateFormat:dateFormat
 	}
    window.commonUtil=commonUtil;
 })()