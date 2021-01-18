/*
 *
 *  *  Copyright (c) 2020-2021, Lykan (jiashuomeng@gmail.com).
 *  *  <p>
 *  *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *  <p>
 *  * https://www.gnu.org/licenses/lgpl.html
 *  *  <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package cn.kstry.framework.core.config;

import cn.kstry.framework.core.exception.ExceptionEnum;
import cn.kstry.framework.core.exception.KstryException;
import cn.kstry.framework.core.util.AssertUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件的形式，解析出配置文件
 *
 * @author lykan
 */
public class FileEventStoryDefConfig extends BaseEventStoryDefConfig implements EventStoryDefConfig {

    @SuppressWarnings("ConstantConditions")
    public static EventStoryDefConfig parseTaskStoryConfig(String taskRouteConfigName) {
        AssertUtil.notBlank(taskRouteConfigName, ExceptionEnum.CONFIGURATION_PARSE_FAILURE, "config file name blank!");
        try {
            URL resource = FileEventStoryDefConfig.class.getClassLoader().getResource(taskRouteConfigName);
            AssertUtil.notNull(resource, ExceptionEnum.CONFIGURATION_PARSE_FAILURE, "config file not exist! name:%s", taskRouteConfigName);

            Path path = Paths.get(resource.toURI());
            AssertUtil.notNull(path, ExceptionEnum.CONFIGURATION_PARSE_FAILURE, "config file not exist! name:%s", taskRouteConfigName);

            StringBuilder sb = new StringBuilder();
            Files.lines(path).forEach(sb::append);

            return FileEventStoryDefConfig.doParseTaskStoryConfig(sb.toString());
        } catch (Exception e) {
            KstryException.throwException(e);
            return null;
        }
    }

    private static EventStoryDefConfig doParseTaskStoryConfig(String configStr) {
        AssertUtil.notBlank(configStr, ExceptionEnum.CONFIGURATION_PARSE_FAILURE, "config file blank!");
        Map<String, String> configMap = JSON.parseObject(configStr, new TypeReference<HashMap<String, String>>() {
        });

        FileEventStoryDefConfig config = new FileEventStoryDefConfig();
        if (StringUtils.isNotBlank(configMap.get(EVENT_DEF))) {
            config.setEventDef(JSON.parseObject(configMap.get(EVENT_DEF),
                    new TypeReference<EventDef<String, EventDefItem<String, EventDefItemConfig>>>() {
                    })
            );
            checkNodeDef(config);
        }

        if (StringUtils.isNotBlank(configMap.get(REQUEST_MAPPING_DEF))) {
            config.setRequestMappingDef(JSON.parseObject(configMap.get(REQUEST_MAPPING_DEF),
                    new TypeReference<RequestMappingDef<String, RequestMappingDefItem<String, String>>>() {
                    })
            );
            checkRequestMappingDef(config);
        }

        if (StringUtils.isNotBlank(configMap.get(STORY_DEF))) {
            config.setStoryDef(JSON.parseObject(configMap.get(STORY_DEF),
                    new TypeReference<StoryDef<String, StoryDefItem<StoryDefItemConfig>>>() {
                    })
            );
            checkStoryDef(config);
        }

        if (StringUtils.isNotBlank(configMap.get(STRATEGY_DEF))) {
            config.setRouteStrategyDef(JSON.parseObject(configMap.get(STRATEGY_DEF),
                    new TypeReference<RouteStrategyDef<String, StrategyDefItem<StrategyDefItemConfig>>>() {
                    })
            );
            checkRouteStrategyDef(config);
        }
        return config;
    }
}