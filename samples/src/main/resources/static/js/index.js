;(function ($) {
    "use strict"; // Start of use strict
    var Index = function(){
        this.init = _init.bind(this);
    };

    function _init(){
        var _this = this;
        _defConstSet.call(_this);
        _defCache.call(_this);
        _defLoadDom.call(_this);
        _defBindEvents.call(_this);
    };

    /**
     * 定义页面常量
     */
    function _defConstSet(){
        this.CONST = {};
        this.CONST.DEF_ZERO_VAL = 0; // 公共参数 0
        this.CONST.DEF_ONE_VAL = 1; // 公共参数 1
        this.CONST.DEF_TWO_VAL = 2; // 公共参数 2
        Object.freeze(this.CONST);
    };

    function _defCache(){
        var _this = this;

        _this.urlMap = {
            imgRoot: urlMaps.imgRoot,
            defImg: _this.CONST.DEF_IMG_URL,
            upload: urlMaps.uploadUrl+"/file/upload",
        };

    };

    function _defLoadDom(){
        var _this = this;
        // 表单控件
        _loadFormControl.call(_this);
        _loadGrid.call(_this);

    };





    /**
     * 统一事件绑定
     * @private
     */
    function _defBindEvents(){
        var _this = this;

        // 表格 搜索
        // $("body #mainContainer").off('click.banner.btn.s.search','.recommend-row .btn-s-search')
        //     .on('click.banner.btn.s.search','.recommend-row  .btn-s-search',function(){
        //         var sBannerTopic = $('.recommend-row input[name="s-bannerTopic"]').val();
        //         var sBannerType = $('#sBannerTypeSele2').val();
        //         var sBannerStatus = $('#sBannerStatusSele2').val();
        //         var sEffectiveBegintime = $('.recommend-row input[name="s-effectiveBegintime"]').val();
        //         var sEffectiveEndtime = $('.recommend-row input[name="s-effectiveEndtime"]').val();
        //         if(!$.izNotEmpty(sBannerType)  || sBannerType=="请选择"){
        //             sBannerType = '';
        //         }
        //         if(!$.izNotEmpty(sBannerStatus)  || sBannerStatus=="请选择"){
        //             sBannerStatus = '';
        //         }
        //         _this.table.reload('recommendGrid',{
        //             where: {
        //                 bannerTopic: sBannerTopic,
        //                 bannerType: sBannerType,
        //                 bannerStatus: sBannerStatus,
        //                 effectiveBegintime: sEffectiveBegintime,
        //                 effectiveEndtime: sEffectiveEndtime
        //             },
        //             page: {curr: 1}
        //         });
        //
        //     });
        // 表格 搜索 重置
        // $("body #mainContainer").off('click.banner.btn.s.reset','.recommend-row  .btn-s-reset')
        //     .on('click.banner.btn.s.reset','.recommend-row  .btn-s-reset',function(){
        //         $('.recommend-row input[name="s-bannerTopic"]').val('');
        //         $('#sBannerTypeSele2').val("").trigger("change");
        //         $('#sBannerStatusSele2').val("").trigger("change");
        //         $('.recommend-row input[name="s-effectiveBegintime"]').val('');
        //         $('.recommend-row input[name="s-effectiveEndtime"]').val('');
        //     });


    };


    /**
     * form表单控件
     * @private
     */
    function _loadFormControl(){
        var _this = this;
        // 类型
        // 系列选择
        $('#typeSele2').select2({
            // tags: [{id:"-1",text:"选择/输入"}],
            placeholder: "选择/输入",
            width: '95%',
            language : "zh-CN",
            // minimumInputLength: 4,
            allowClear: true,
            // dropdownParent: $('.goods-modify-modal'),
            ajax: {
                url : _this.urlMap.series,
                dataType : 'json',
                type : "POST",
                async: true,
                cache:false,
                delay : 250,
                data : function(inputParams) {
                    return {
                        type : inputParams.term || '',
                    };
                },
                processResults : function(data) {
                    if (!data || data.length == 0){
                        $('#seriesCodeSele').data('val',JSON.stringify("[]"));
                        return [];
                    }
                    $('#seriesCodeSele').data('val',JSON.stringify(data));
                    return {
                        results : $.map(data, function(item) {
                            return {
                                text : item.seriesCode,
                                id : item.seriesCode,
                                seriesId : item.seriesId,
                                seriesTitle : item.seriesTitle,
                                pictureUrl : item.pictureUrl,
                                cardUrl : item.cardUrl,
                                sidePicUrl : item.sidePicUrl,
                            }
                        })
                    };
                }
            }, // ajax end
        }).on('change',function(e){  //select2:select
            $('#seriesId').val('');
            var data = $(this).select2("data")[0];
            if(data){

            }
        });
        $('#typeSele2').select2({
            width: '60%',
            language : "zh-CN",
            minimumResultsForSearch: -1,
            placeholder: "请选择",
            data: (function(){
                return [
                    {
                        id:"",
                        text:""
                    },
                    {}];
            })(),
        });




        _createIntervalJeDate('#tBeginDate', '#tEndDate');

        /****select BEGIN****************************************/

        //  banner 状态
        $('#sBannerStatusSele2').select2({
            width: '60%',
            language : "zh-CN",
            minimumResultsForSearch: -1,
            placeholder: "请选择",
            data: (function(){
                return $.extend(true,[],_this.bannerStatusSelect2Data)
            })(),
        });
        /** select END*******************************************/
    };// _loadFormControl

    /**
     * 加载表格
     * @private
     */
    function _loadGrid() {
        var _this = this;
        layui.use('table', function(){
            _this.table = $.extend(true,_this.table,layui.table);

            var tableCols = [[
                {type:'checkbox'}
                ,{field:'bannerOrder', align: 'center', title: '序号'}
                ,{field:'bannerTopic', align: 'center', title: '主题'}
                ,{field:'pictureUrl', align: 'center', title: '图片', height:'100px',style:'height:100px;', templet: _coverUrlTemplate}
                ,{field:'bannerType', align: 'center', title: '类型',templet: _bannerTypeTemplate}
                ,{field:'bannerLinks', align: 'center', title: '链接<br>(点击复制)', templet:'<div><span  class="click-copy" style="cursor: pointer" title="{{d.bannerLinks}}">{{d.bannerLinks}}</span></div>'}
                ,{field:'bannerStatus', align: 'center', title: '状态',templet: _bannerStatusTemplate}

                ,{field:'id',title: '', hide: true}
                ,{fixed: 'right', width:'10%', align:'center', title: '操作', toolbar: '#recommend-toolBar'}
            ]];

            var gridParam = {
                id: 'recommendGrid'
                ,elem: '#recommendGrid'
                ,url: _this.urlMap.getGrid
                // 向后台传的自定义默认参数
                ,cols: tableCols // 表头
                ,parseData: function(res){ // 格式化回显数据
                    if(res.code === 0){
                        var list = res.rows;
                        if($.isArray(list) && list.length>0){
                            list = $.map(list,function(param){
                                var tempParam =  $.extend(true,tempParam,param);
                                tempParam.pictureUrl = !$.izNotEmpty(param.pictureUrl)?_this.urlMap.defImg:param.pictureUrl;
                                return tempParam;
                            });
                        }
                        res.rows = $.extend(true,res.rows,list);
                    }
                    return res;
                }
                ,done: function(res, curr, count){
                    // 设置工具高度和行高自适应
                    $(".layui-table-main  tr").each(function (index ,val) {
                        $($(".layui-table-fixed .layui-table-body tbody tr")[index]).height($(val).height());
                    });
                    // 设置工具固定后浮动层
                    $('.layui-table-box .layui-table-fixed').css('z-index',0);
                    // 设置单元格不显示弹层
                    $.addCSSRule(document.styleSheets[ document.styleSheets.length-1],
                        '.layui-table-grid-down','display:none');  //
                    // 关闭工具框弹层
                    $('.layui-table-box .layui-table-fixed')[0].style.display = 'none';
                    // 设置toolbar竖向排列
                    $('.layui-table-body table>tbody>tr').find('td.layui-table-col-special:last-child .layui-table-cell')
                        .addClass('order-toolBar');
                }
            };
            var __gridParam = $.extend(true,__gridParam,defaultGridParam);
            gridParam = $.extend(true,__gridParam, gridParam);
            _this.table.render(gridParam);

            //监听工具条
            //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            _this.table.on('tool(recommendGrid)', function(obj){
                var data = obj.data; //获得当前行数据
                var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                var tr = obj.tr; //获得当前行 tr 的DOM对象

                if(layEvent === 'detail'){ //查看
                    _viewInit.call(_this,data);
                }else if(layEvent === 'delete'){ //删除
                    layer.confirm('真的删除行么', function(index){
                        layer.close(index);
                        _setRowsStatus.call(_this,data,obj,0);
                    });
                }else if(layEvent === 'edit'){ //编辑
                    _modifyInit.call(_this,data);
                }else if(layEvent === 'disable'){ //下架
                    _setRowsStatus.call(_this,data,obj,2);
                }else if(layEvent === 'enable'){ //上架
                    _setRowsStatus.call(_this,data,obj,1);
                }

            }); // 监听工具条
        }); // layui.use

        /**************************
         * grid 内部方法 begin
         ***************************/
        function _coverUrlTemplate(d){
            if(d){
                if(d.pictureUrl){
                    return '<div class="to-center"><img src="'+_this.urlMap.imgRoot+d.pictureUrl+oSSImgZoom.LFIT_W_300_H_300+'" ' +
                        'onerror="javascript:this.src=\''+urlMaps.backUrl+_this.urlMap.defImg+'\';this.onerror=null"/></div>';
                }
            }
            return d.pictureUrl;
        }
        function _bannerTypeTemplate(d){
            if(d){
                if(d.bannerType && d.bannerType=='1'){
                    return '<div><span>主Banner</span></div>';
                }
                if(d.bannerType && d.bannerType=='0'){
                    return '<div><span>子Banner</span></div>';
                }
            }
            return d.bannerType;
        }
        function _bannerStatusTemplate(d){
            if(d){
                if($.izNotEmpty(d.bannerStatus) && d.bannerStatus=='0'){
                    return '<div><span>草稿</span></div>';
                }
                if($.izNotEmpty(d.bannerStatus) && d.bannerStatus=='1'){
                    return '<div><span>已上架</span></div>';
                }
                if($.izNotEmpty(d.bannerStatus) && d.bannerStatus=='2'){
                    return '<div><span>已下架</span></div>';
                }
            }
            return d.bannerStatus;
        }

        /**************************
         * grid 内部方法 end
         ***************************/
    };// loadGrid


    /**
     * jeDate 开始日期结束日期区间
     * $begin '#begin'
     * $end '#end'
     */
    function _createIntervalJeDate ($begin, $end){
        var uid = uuid.createUUID();
        jeDate($begin,LinkageStartDate);
        jeDate($end,LinkageEndDate);
        window[uid] = {},
            window[uid].endDate = {},
            window[uid].beginDate = {};
        window[uid].endDate.minDate = '2000-01-01 00:00:00';
        window[uid].beginDate.maxDate = '2099-06-16 23:59:59';

        function LinkageStartDate(istg) {
            return {
                trigger : istg || "click",
                format: 'YYYY-MM-DD hh:mm:ss',
                minDate: '2000-01-01 00:00:00', //设定最小日期为当前日期
                maxDate: function (that) {
                    // that 指向实例对象
                    var nowMaxDate = jeDate.valText($end) == "" && jeDate.valText(that.valCell) == "";
                    return nowMaxDate? '2099-06-16 23:59:59' : window[uid].beginDate.maxDate;
                }, //设定最小日期为当前日期
                donefun: function(obj){
                    window[uid].endDate.minDate = obj.val; //将结束日的初始值设定为开始日的最大日期
                    jeDate($end,LinkageEndDate(false));
                    $($begin).trigger('blur');
                },
                clearfun: function(){
                    $($begin).trigger('blur');
                }
            };
        }

        function LinkageEndDate(istg) {
            return {
                trigger : istg || "click",
                format: 'YYYY-MM-DD hh:mm:ss',
                minDate: function (that) {
                    // that 指向实例对象
                    var nowMinDate = jeDate.valText($begin) == "" && jeDate.valText(that.valCell) == "";
                    // return nowMinDate ? jeDate.nowDate({DD:0}) : contractEdit.endDate.minDate ;
                    return nowMinDate ? '2000-01-01 00:00:00' : window[uid].endDate.minDate;
                }, // 设定最小日期为当前日期
                maxDate: '2099-06-16 23:59:59', //设定最大日期为当前日期
                donefun: function(obj){
                    window[uid].beginDate.maxDate = obj.val; //将结束日的初始值设定为开始日的最大日期
                    jeDate($begin,LinkageStartDate(false));
                    $($end).trigger('blur');
                },
                clearfun: function(){
                    $($end).trigger('blur');
                },
            };
        }
    };
    /**
     * 公共方法
     * 重置表单
     * @returns {{resetSeries: resetSeries, resetGoods: resetGoods, seriesCodeSele: resetSeries, goodsCodeSele: resetGoods}}
     */
    function _formReset(){
        var _this = this;
        $('#bannerId').val('');
        $('#bannerTypeSele2').val("").trigger("change");
        $('#bannerModifyModal input[name="bannerOrder"]').val('');
        $('#bannerModifyModal input[name="bannerTopic"]').val('');
        $('#bannerModifyModal input[name="bannerLinks"]').val('');
        $('#bannerModifyModal input[name="effectiveBegintime"]').val('');
        $('#bannerModifyModal input[name="effectiveEndtime"]').val('');

        $('#bannerFile').find('i').show();
        $('#bannerFile').find('img').prop('src','').hide();
        $('#pictureUrl').val('');
        $('#seriesCardUrl').val('');
        // 重置校验
        $.fn.cValidation.resetValidation();
    };


    function _getCurrPage(){
        var currPage =  $(".layui-laypage-em").next().html();
        if($.izNotEmpty(currPage)){
            currPage = parseInt(currPage);
        }else{
            currPage = 1;
        }
        if(isNaN(currPage)){currPage=1;}
        return currPage;
    }

    new Index().init();
})(jQuery);
