package com.xsquare.modules.xsquare.controller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xsquare.modules.xsquare.entity.VipUserEntity;
import com.xsquare.modules.xsquare.service.VipUserService;
import com.xsquare.common.utils.PageUtils;
import com.xsquare.common.utils.Query;
import com.xsquare.common.utils.R;




/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
@RestController
@RequestMapping("/xsquare/vipuser")
public class VipUserController {
	@Autowired
	private VipUserService vipUserService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
//	@RequiresPermissions("xsquare:vipuser:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<VipUserEntity> vipUserList = vipUserService.queryList(query);
		int total = vipUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(vipUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:vipuser:info")
	public R info(@PathVariable("id") Integer id){
		VipUserEntity vipUser = vipUserService.queryObject(id);
		
		return R.ok().put("vipUser", vipUser);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("xsquare:vipuser:save")
		public R save(@RequestBody VipUserEntity vipUser){
		vipUserService.save(vipUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
//	@RequiresPermissions("xsquare:vipuser:update")
	public R update(@RequestBody JSONObject jsonObject){
		JSONObject vipUserJson = jsonObject.getJSONObject("vipUser");
		VipUserEntity vipUser = (VipUserEntity)JSONObject.toBean(vipUserJson, VipUserEntity.class);
		vipUserService.update(vipUser);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:vipuser:delete")
	public R delete(@RequestBody Integer[] ids){
		vipUserService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
