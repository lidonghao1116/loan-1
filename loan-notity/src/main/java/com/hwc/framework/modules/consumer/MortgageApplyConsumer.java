package com.hwc.framework.modules.consumer;

import cn.freesoft.utils.FsUtils;
import com.alibaba.fastjson.JSONObject;
import com.hwc.common.aliyun.ons.HwcOnsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by  on 2017/12/4.
 */
@Component
public class MortgageApplyConsumer extends OnsConsumerBase {

    private static final Logger logger = LoggerFactory.getLogger(MortgageApplyConsumer.class);
    @Value("${ons.borrow.consumer.mortgageApplyId}")
    private String id;
    @Value("${ons.borrow.consumer.mortgageApplyTag}")
    private String tag;
    @Value("${ons.topic}")
    private String topic;
    @Override
    protected String getConsumerId() {
        return id;
    }

    @Override
    protected String getTags() {
        return tag;
    }

    @Override
    protected boolean doConsume(HwcOnsContext context) {
        JSONObject jsonObject = new JSONObject();
        if (context.getData() instanceof JSONObject) {
            jsonObject=(JSONObject)context.getData();
            jsonObject.put("loan", jsonObject.getDouble("amount"));
            jsonObject.put("date", FsUtils.formatDate(new Date()));
            mortgageGenXinNotifyService.mortgageApplyNotify(jsonObject);
        }

        return false;
    }
}
