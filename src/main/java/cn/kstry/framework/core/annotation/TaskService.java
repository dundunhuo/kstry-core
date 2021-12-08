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
package cn.kstry.framework.core.annotation;

import cn.kstry.framework.core.enums.ScopeTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注在方法上，标识为任务的执行节点
 *
 * @author lykan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface TaskService {

    /**
     * 服务名称
     *
     * @return name
     */
    String name();

    /**
     * 子能力
     *
     * @return ability
     */
    String ability() default StringUtils.EMPTY;

    /**
     * 指定 cn.kstry.framework.core.kv.KvAbility 从哪个域获取变量
     *
     * @return ksScope
     */
    String kvScope() default StringUtils.EMPTY;

    /**
     *  指定结果到哪些 Scope。该参数不为空时会使 Response Class 上的 @Notice... 类注解失效
     *
     * @return noticeScope
     */
    ScopeTypeEnum[] noticeScope() default {};

    /**
     * 指定节点返回值类型，使用 Mono 且有返回值时需要指定。当指定类型与实际类型不符是会报错
     *
     * @return returnClassType
     */
    Class<?> returnClassType() default Object.class;
}