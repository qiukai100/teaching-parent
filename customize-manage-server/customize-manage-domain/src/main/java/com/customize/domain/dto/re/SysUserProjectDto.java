package com.customize.domain.dto.re;

import com.customize.domain.entity.re.SysUserProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * re_sys_user_project 表
 *
 * @author qiukai
 * @date 2020-04-26
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("关联用户项目数据交互模型")
public class SysUserProjectDto extends SysUserProject {

}
