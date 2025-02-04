package com.github.yiuman.citrus.workflow.rest;

import com.github.yiuman.citrus.support.crud.view.impl.PageTableView;
import com.github.yiuman.citrus.workflow.vo.TaskInfoVo;
import com.github.yiuman.citrus.workflow.vo.TaskQueryParams;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

/**
 * 任务查询控制器
 *
 * @author yiuman
 * @date 2021/3/8
 */
@RestController
@RequestMapping("/rest/tasks")
public class TaskInfoController extends BaseWorkflowQueryController<TaskInfoVo, String> {

    public TaskInfoController() {
        setParamClass(TaskQueryParams.class);
    }

    @Override
    protected Object createView(List<TaskInfoVo> records) {
        PageTableView<Task> view = new PageTableView<>();
        view.addWidget("处理人或候选人", "taskCandidateOrAssigned");
        view.addHeader("任务名称", "name");
        view.addHeader("处理人", "assignee");
        view.addHeader("创建时间", "createTime");
        view.addHeader("处理时间", "dueDate");
        view.addHeader("删除原因", "deleteReason");
        return view;
    }

    @Override
    public String getKeyQueryField() {
        return "taskId";
    }

    @Override
    protected Function<? super Object, ? extends TaskInfoVo> getTransformFunc() {
        return task -> {
            TaskInfoVo taskInfo = new TaskInfoVo();
            BeanUtils.copyProperties(task, taskInfo);
            return taskInfo;
        };
    }
}
