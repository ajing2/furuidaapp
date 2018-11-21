// function tree() {
//     var zTreeObj;
//     // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
//     var setting = {};
//     // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
//     var zNodes = [
//         {name:"test1", open:true, children:[
//                 {name:"test1_1"}, {name:"test1_2"}]},
//         {name:"test2", open:true, children:[
//                 {name:"test2_1"}, {name:"test2_2"}]}
//     ];
//     $(document).ready(function(){
//         zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
//     });
// }

function tree() {
    $("#tree").show();
    $("#order").hide();
    $("#table").hide();

    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        check: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        // callback: {
        //     onNodeCreated: alert("333222222")
        // }
    };
    // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
    url = "/user/tree";
    $.ajax({
        type: "GET",
        timeout: 10000, // 超时时间 10 秒
        // headers: JSON.parse(sessionStorage.getItem('Access-Control-Allow-Origin')),
        url: url,
        // dataType: 'json',
        // data: JSON.stringify(formParams),
        contentType: 'application/json',
        async: false,
        success: function (callback) {
            if (callback.code == 0) {
                data = callback.data;
                console.log(data);
                var zNodes = data;
                zTreeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(err);
        },
        complete: function(XMLHttpRequest, status) { //请求完成后最终执行参数　
            console.log("done.");
        }
    });
}
$(document).ready(function(){
    tree();
});