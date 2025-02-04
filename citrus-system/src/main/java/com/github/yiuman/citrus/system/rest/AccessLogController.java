package com.github.yiuman.citrus.system.rest;

import cn.hutool.core.date.DateUtil;
import com.github.yiuman.citrus.support.crud.query.annotations.Equals;
import com.github.yiuman.citrus.support.crud.query.annotations.In;
import com.github.yiuman.citrus.support.crud.rest.BaseQueryController;
import com.github.yiuman.citrus.support.crud.view.impl.PageTableView;
import com.github.yiuman.citrus.support.model.Header;
import com.github.yiuman.citrus.system.entity.AccessLog;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 访问日志
 *
 * @author yiuman
 * @date 2020/9/24
 */
@RestController
@RequestMapping("/rest/access/log")
public class AccessLogController extends BaseQueryController<AccessLog, Long> {

    public AccessLogController() {
        addSortBy("created_time", true);
        setParamClass(AccessLogQuery.class);
    }

    @Data
    static class AccessLogQuery {

        @Equals(mapping = "user_id")
        private Long userId;

        @In(mapping = "resource_type")
        private List<Long> resourceType;
    }

    @Override
    protected Object createView(List<AccessLog> records) {
        PageTableView<AccessLog> view = new PageTableView<>(false);
        view.addHeader(Header.builder().text("用户").value("username").align(Header.Align.center).build());
        view.addHeader("IP地址", "ipAddress");
        view.addHeader("请求", "method_url", entity -> String.format("%s  %s", entity.getRequestMethod(), entity.getUrl()));
        view.addHeader("参数", "params");
        view.addHeader("资源名称", "resourceName");
        view.addHeader("时间", "createTime_", entity -> DateUtil.format(entity.getCreatedTime(), "yyyy-MM-dd hh:mm:ss"));

        view.addWidget("用户", "userId");
        return view;
    }

}
