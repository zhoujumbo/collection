package com.jibug.cetty.sample.entity.query;

import com.basic.support.commons.business.crud.pojo.Grid;
import com.basic.support.commons.business.mybatis.query.ConditionQuery;
import com.basic.support.commons.business.mybatis.query.condition.BetweenValueCondition;
import com.basic.support.commons.business.mybatis.query.condition.SingleValueCondition;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @ClassName BannerQuery
 * @Description TODO
 * @Author jb.zhou
 * @Date 2019/7/2
 * @Version 1.0
 */
@Data
public class GoodsMxQuery extends Grid {


    private String goodsId;

    private String series;

    private String title;

    private Date beginTime;

    private Date endTime;

    @Override
    public ConditionQuery buildConditionQuery() {
        ConditionQuery conditionQuery = super.buildConditionQuery();

        // 查询条件组装 、 条件校验
        if(StringUtils.isNotBlank(this.getGoodsId())){
            conditionQuery.add(new SingleValueCondition("goods_id", "like", "%"+this.getGoodsId()+"%"));
        }

        if(StringUtils.isNotBlank(this.getSeries())){
            conditionQuery.add(new SingleValueCondition("series", "like", "%"+this.getSeries()+"%"));
        }

        if(StringUtils.isNotBlank(this.getTitle())){
            conditionQuery.add(new SingleValueCondition("title", "like", "%"+this.getTitle()+"%"));
        }

        if(Objects.nonNull(beginTime) && Objects.nonNull(endTime)){
            conditionQuery.add(new BetweenValueCondition("creat_time", this.beginTime, this.endTime));
        }

        return conditionQuery;
    }


}
