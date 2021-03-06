package com.customize.web.controller.re;

import com.customize.web.core.BaseController;
import com.customize.web.core.Result;
import com.customize.domain.vo.re.SysUserPostVo;
import com.customize.domain.dto.re.SysUserPostDto;
import com.customize.service.service.re.ISysUserPostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.customize.web.core.Result.success;

/**
 * @author qiukai
 * @date 2020-04-26
 */
@Slf4j
@RestController
@RequestMapping("re/sysUserPost")
@Api(value = "re/sysUserPost", description  = "关联用户岗位管理接口")
public class SysUserPostController extends BaseController {

    private final ISysUserPostService sysUserPostService;

    @Autowired
    public SysUserPostController(ISysUserPostService sysUserPostService) throws Exception {
        this.sysUserPostService = sysUserPostService;
    }

    @ApiOperation("分页条件查询关联用户岗位")
    @RequestMapping(method = RequestMethod.GET, value = "querySysUserPostPage")
    public Result querySysUserPostPage(SysUserPostVo sysUserPostVo) throws Exception {
        return success(sysUserPostService.querySysUserPostPage(sysUserPostVo));
    }

    @ApiOperation("条件查询关联用户岗位")
    @RequestMapping(method = RequestMethod.GET, value = "querySysUserPostList")
    public Result querySysUserPostList(SysUserPostVo sysUserPostVo) throws Exception {
        return success(sysUserPostService.querySysUserPostList(sysUserPostVo));
    }

    @ApiOperation("查询所有关联用户岗位")
    @RequestMapping(method = RequestMethod.GET, value = "selectSysUserPostList")
    public Result selectSysUserPostList() throws Exception {
        return success(sysUserPostService.selectSysUserPostList());
    }

    @ApiOperation("根据ID查询关联用户岗位")
    @RequestMapping(method = RequestMethod.GET, value = "findSysUserPostById/{pkReUserPostId}")
    public Result findById(@PathVariable("pkReUserPostId") String pkReUserPostId) {
        return success(sysUserPostService.findById(pkReUserPostId));
    }

    @ApiOperation("新增关联用户岗位")
    @RequestMapping(method = RequestMethod.POST, value = "insertSysUserPost")
    public Result insertSysUserPost(SysUserPostDto sysUserPostDto) throws Exception {
        return success(sysUserPostService.insertSelective(sysUserPostDto));
    }

    @ApiOperation("修改关联用户岗位")
    @RequestMapping(method = RequestMethod.PUT, value = "updateSysUserPost")
    public Result updateSysUserPost(SysUserPostDto sysUserPostDto) throws Exception {
        return success(sysUserPostService.updateByPrimaryKeySelective(sysUserPostDto));
    }

    @ApiOperation("根据ID移除关联用户岗位（逻辑删除）")
    @RequestMapping(method = RequestMethod.DELETE, value = "removeSysUserPostById/{pkReUserPostId}")
    public Result removeSysUserPostById(@PathVariable("pkReUserPostId") String pkReUserPostId) throws Exception {
        return success(sysUserPostService.removeByPrimaryKey(pkReUserPostId));
    }

    @ApiOperation("根据ID移除关联用户岗位（物理删除）")
    @RequestMapping(method = RequestMethod.DELETE, value = "deleteSysUserPostById/{pkReUserPostId}")
    public Result deleteSysUserPostById(@PathVariable("pkReUserPostId") String pkReUserPostId) throws Exception {
        return success(sysUserPostService.deleteByPrimaryKey(pkReUserPostId));
    }

    @ApiOperation("批量删除关联用户岗位（逻辑删除）")
    @RequestMapping(method = RequestMethod.DELETE, value = "removeSysUserPostBatch")
    public Result removeSysUserPostBatch(@RequestParam String[] pkReUserPostIds) throws Exception {
        return success(sysUserPostService.removeSysUserPostBatch(pkReUserPostIds));
    }

    @ApiOperation("批量删除关联用户岗位（物理删除）")
    @RequestMapping(method = RequestMethod.DELETE, value = "deleteSysUserPostBatch")
    public Result deleteSysUserPostBatch(@RequestParam String[] pkReUserPostIds) throws Exception {
        return success(sysUserPostService.deleteSysUserPostBatch(pkReUserPostIds));
    }

}
