var Lazy = {
	eCatch: {},
	eHandle: 0,
	isFunction: function(obj) {
		return Object.prototype.toString.call(obj) === "[object Function]";
	},
	addEvent: function(o, e, func) {
		if (o.addEventListener) {
			o.addEventListener(e, func, false);
		} else {
			o.attachEvent("on" + e, func);
		}
		this.eCatch[++this.eHandle] = {
			"handler": func
		};
		return this.eHandle;
	},
	removeEvent: function(o, e, func) {
		if (o.addEventListener) {
			o.removeEventListener(e, this.eCatch[func].handler, false);
		} else {
			o.detachEvent("on" + e, this.eCatch[func].handler);
		}
	},
	converNodeToArray: function(nodes) {
		var array = [];
		try {
			array = Array.prototype.slice.call(nodes, 0);
		} catch (e) {
			for (var i = 0, len = nodes.length; i < len; i++) {
				array.push(nodes[i]);
			}
		}
		return array;
	},
	each: function(o, fn) {
		for (var i = 0, len = o.length; i < len; i++) {
			fn.call(o[i], i, o[i]);
		}
	},
	create: function(o) {
		o.loading = false;
		o.timmer = undefined;
		o.time_act = 0;
		o.imgList = [];
		this.imgLoad = o.imgLoad;
		var lazyId = o.lazyId,
			that = this,
			imgList = [];
		lazyId = (typeof lazyId) == "string" ? [].concat(lazyId) : lazyId;
		that.each(lazyId, function(i, v) {
			var lid = document.getElementById(v);
			if (!lid) return;
			var imgs;
			if (document.querySelectorAll) {
				imgs = document.querySelectorAll('#' + v + ' img');
			} else {
				imgs = lid.getElementsByTagName("img");
			}
			imgList = imgList.concat(imgs && that.converNodeToArray(imgs));
		});
		that.each(imgList, function(i, v) {
			if (v.getAttribute(o.trueSrc)) {
				o.imgList.push(v);
			}
		});
		o.imgCount = o.imgList.length;
		if (o.jsList) {
			o.jsCount = o.jsList.length;
			for (var i = 0; i < o.jsCount; i++) {
				o.jsList[i].oDom = (typeof(o.jsList[i].id) == 'object') ? o.jsList[i].id : document.getElementById(o.jsList[i].id);
			}
		} else {
			o.jsList = [];
			o.jsCount = 0;
		}
		return o;
	},
	checkPhone: function(ua) {
		if (ua.indexOf("android") > -1 || ua.indexOf("iphone") > -1 || ua.indexOf("ipod") > -1 || ua.indexOf("ipad") > -1) {
			this.isPhone = true;
		} else {
			this.isPhone = false;
		}
	},
	checkLazyLoad: function(ua) {
		if (ua.indexOf("opera mini") > -1) {
			return false;
		} else {
			return true;
		}
	},
	init: function(o) {
		if (o.imgCount < 1 && o.jsCount < 1) return;
		var ua = navigator.userAgent.toLowerCase();
		if (this.checkLazyLoad(ua)) {
			this.checkPhone(ua);
			o.e1 = this.addEvent(window, "scroll", this.load(o));
			o.e2 = this.addEvent(window, "touchmove", this.load(o));
			o.e3 = this.addEvent(window, "touchend", this.load(o));
			this.loadTime(o);
		} else {
			this.loadOnce(o);
		}
	},
	getImgTop: function(o) {
		var imgTop = 0;
		if (!o) return;
		while (o.offsetParent) {
			imgTop += o.offsetTop;
			o = o.offsetParent
		}
		return imgTop;
	},
	load: function(o) {
		return function() {
			if (o.loading == true) return;
			o.loading = true;
			if (o.time_act && ((1 * new Date() - o.time_act) > o.delay_tot)) {
				o.timmer && clearTimeout(o.timmer);
				Lazy.loadTime(o);
			} else {
				o.timmer && clearTimeout(o.timmer);
				o.timmer = setTimeout(function() {
					Lazy.loadTime(o);
				}, o.delay);
			}
			o.loading = false;
		}
	},
	setSrc: function(o, l) {
		var self = this;
		var src2 = o.getAttribute(l),
			_img = new Image();
		_img.onload = function() {
			o.setAttribute("src", src2);
			o.removeAttribute(l);
			if (self.imgLoad) {
				self.imgLoad.call(o, o, _img.width, _img.height);
			}
		}
		_img.src = src2;
	},
	setJs: function(js) {
		Lazy.isFunction(js) ? js.call(this, this) : eval(js);
	},
	loadTime: function(o) {
		o.time_act = 1 * new Date();
		var winH, winTop, winTot;
		if (this.isPhone) {
			winH = document.documentElement.clientHeight;
			winTop = window.scrollY;
			winTot = winTop + winH;
		} else {
			winH = document.documentElement.clientHeight || document.body.clientHeight;
			winTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
			winTot = winH + winTop;
		}
		if (!o.offset) {
			o.offset = winH / 2;
		}
		var wTop_o = winTop - o.offset,
			wTot_o = winTot + o.offset;
		var imgCache = [];
		for (var i = 0; i < o.imgCount; i++) {
			var img = o.imgList[i],
				imgH = img.clientHeight,
				imgTop, imgB;
			if (img.getBoundingClientRect) {
				imgTop = img.getBoundingClientRect().top + winTop;
			} else {
				imgTop = this.getImgTop(img);
			}
			imgB = imgTop + imgH;
			if ((imgTop > wTop_o && imgTop < wTot_o) || (imgB > wTop_o && imgB < wTot_o)) {
				if (imgTop > winTop && imgTop < winTot) {
					this.setSrc(img, o.trueSrc);
				} else {
					imgCache.push(img);
				}
				o.imgList.splice(i, 1);
				i--;
				o.imgCount--;
			}
		}
		var imgCacheL = imgCache.length;
		if (imgCacheL) {
			for (var i = 0; i < imgCacheL; i++) {
				var img = imgCache[i]
				this.setSrc(img, o.trueSrc);
			}
		}
		if (o.jsList) {
			for (var i = 0; i < o.jsCount; i++) {
				var oJs = o.jsList[i];
				var jsTop = this.getImgTop(oJs.oDom, winTop);
				if ((jsTop > wTop_o && jsTop < wTot_o)) {
					this.setJs.call(oJs.oDom, oJs.js);
					o.jsList.splice(i, 1);
					i--;
					o.jsCount--;
				}
			}
		}
		if (o.imgCount == 0 && o.jsCount == 0) {
			this.removeEvent(window, "scroll", o.e1);
			this.removeEvent(window, "touchmove", o.e2);
			this.removeEvent(window, "touchend", o.e3);
		}
	},
	loadOnce: function(o) {
		for (var i = 0; i < o.imgCount; i++) {
			var img = o.imgList[i];
			this.setSrc(img, o.trueSrc);
		}
		if (o.jsList) {
			for (var i = 0; i < o.jsCount; i++) {
				var oJs = o.jsList[i];
				this.setJs.call(oJs.oDom, oJs.js);
			}
		}
	}
};