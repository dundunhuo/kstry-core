/*
 *
 *  * Copyright (c) 2020-2022, Lykan (jiashuomeng@gmail.com).
 *  * <p>
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * <p>
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  * <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package cn.kstry.framework.core.component.bpmn.link;

import cn.kstry.framework.core.bpmn.EndEvent;
import cn.kstry.framework.core.bpmn.FlowElement;
import cn.kstry.framework.core.bpmn.StartEvent;
import cn.kstry.framework.core.bpmn.impl.EndEventImpl;
import cn.kstry.framework.core.bpmn.impl.InclusiveGatewayImpl;
import cn.kstry.framework.core.bpmn.impl.ParallelGatewayImpl;
import cn.kstry.framework.core.bpmn.impl.StartEventImpl;
import cn.kstry.framework.core.component.bpmn.builder.InclusiveJoinPointBuilder;
import cn.kstry.framework.core.component.bpmn.builder.ParallelJoinPointBuilder;
import cn.kstry.framework.core.exception.ExceptionEnum;
import cn.kstry.framework.core.util.AssertUtil;
import cn.kstry.framework.core.util.GlobalUtil;

/**
 * BPMN 元素代码方式连接起点
 *
 * @author lykan
 */
public class StartDiagramProcessLink extends BpmnDiagramLink implements StartProcessLink {

    /**
     * 开始事件
     */
    private final StartEvent startEvent;

    /**
     * 结束事件
     */
    private final EndEvent endEvent;

    public StartDiagramProcessLink(String id, String name) {
        AssertUtil.notBlank(id, ExceptionEnum.PARAMS_ERROR, "Id is not allowed to be empty!");
        StartEventImpl se = new StartEventImpl();
        se.setId(id);
        se.setName(name);
        this.startEvent = se;

        EndEventImpl endEvent = new EndEventImpl();
        endEvent.setId(GlobalUtil.uuid());
        this.endEvent = endEvent;
    }

    @Override
    public ProcessLink getProcessLink() {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends FlowElement> T getElement() {
        return (T) startEvent;
    }

    @Override
    public InclusiveJoinPointBuilder inclusive() {
        InclusiveGatewayImpl gateway = new InclusiveGatewayImpl();
        gateway.setId(GlobalUtil.uuid());
        return new InclusiveJoinPointBuilder(gateway, this);
    }

    @Override
    public ParallelJoinPointBuilder parallel() {
        ParallelGatewayImpl gateway = new ParallelGatewayImpl();
        gateway.setId(GlobalUtil.uuid());
        return new ParallelJoinPointBuilder(gateway, this);
    }

    public EndEvent getEndEvent() {
        return endEvent;
    }
}