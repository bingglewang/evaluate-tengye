webpackJsonp([6],{"0nQ+":function(e,t){},NHnr:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=n("xd7I"),r=(n("0nQ+"),{watch:{$route:function(e,t){console.log("当前路径："+e.path),console.log("之前路径："+t.path)}}}),a={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"fillcontain",attrs:{id:"app"}},[t("router-view")],1)},staticRenderFns:[]};var u=n("C7Lr")(r,a,!1,function(e){n("fHDX")},null,null).exports,i=n("3XdE"),c=function(e){return n.e(2).then(function(){return e(n("+uyb"))}.bind(null,n)).catch(n.oe)},l=[{path:"/",name:"login",component:function(e){return n.e(0).then(function(){return e(n("Luci"))}.bind(null,n)).catch(n.oe)}},{path:"/home",name:"",component:function(e){return n.e(1).then(function(){return e(n("vkyI"))}.bind(null,n)).catch(n.oe)},meta:{CName:"首页",auth:!0},children:[{path:"",component:c,meta:{auth:!0}},{path:"/userList",component:c,meta:{CName:"用户列表",auth:!0}},{path:"/recordList",component:function(e){return n.e(3).then(function(){return e(n("1F2k"))}.bind(null,n)).catch(n.oe)},meta:{CName:"查询列表",auth:!0}}]},{path:"*",name:"NotFoundView",component:function(e){return n.e(4).then(function(){return e(n("thLP"))}.bind(null,n)).catch(n.oe)}}],s=n("rVsN"),f=n.n(s),d=n("3cXf"),p=n.n(d),h=n("aozt"),m=n.n(h),w=n("Gir3"),g=n.n(w),v="";function N(e){var t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{};return new f.a(function(n,o){m.a.post(e,t).then(function(e){n(e.data)},function(e){o(e)})})}v="/evaluate",m.a.defaults.timeout=5e3,m.a.defaults.baseURL=v,m.a.interceptors.request.use(function(e){return e.data=p()(e.data),e.headers={"Content-Type":"application/json;charset=UTF-8"},e},function(e){return f.a.reject(err)}),m.a.interceptors.response.use(function(e){return 2==e.data.errCode&&router.push({path:"/login",querry:{redirect:router.currentRoute.fullPath}}),e},function(e){return f.a.reject(e)}),o.default.use(i.a);var S=new i.a({routes:l});S.beforeEach(function(e,t,n){if(e.matched.some(function(e){return e.meta.auth}))if(void 0!=window.localStorage.user&&""!=window.localStorage.user&&null!=window.localStorage.user){N("/admin/getAdminById",{token:JSON.parse(window.localStorage.user).token}).then(function(t){if(0==t.status)n();else{if("login"===e.name)return void n();n({path:"/",query:{redirect:e.fullPath}})}})}else{if("login"===e.name)return void n();n({path:"/",query:{redirect:e.fullPath}})}else n()});var b=S,y=n("R4Sj");o.default.use(y.a);var C=new y.a.Store({state:{user:{uName:null==window.localStorage.getItem("user")?"未登录":JSON.parse(window.localStorage.getItem("user")).uName}},mutations:{logout:function(e){window.localStorage.removeItem("user"),e.routes=[]},setUser:function(e,t){window.localStorage.setItem("user",p()(t)),e.user.uName=t.uName}},actions:{}});n("hsAa");o.default.config.productionTip=!1,o.default.use(g.a),o.default.prototype.$post=N,new o.default({el:"#app",router:b,store:C,components:{App:u},template:"<App/>"})},fHDX:function(e,t){},hsAa:function(e,t){}},["NHnr"]);