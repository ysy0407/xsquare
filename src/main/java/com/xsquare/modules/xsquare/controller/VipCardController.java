package com.xsquare.modules.xsquare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xsquare.common.annotation.SysLog;
import com.xsquare.common.exception.RRException;
import com.xsquare.common.utils.*;
import com.xsquare.modules.xsquare.common.annotation.MoneyLog;
import com.xsquare.modules.xsquare.common.annotation.VipHandleLog;
import com.xsquare.modules.xsquare.entity.VipUserEntity;
import com.xsquare.modules.xsquare.service.VipUserService;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xsquare.modules.xsquare.entity.VipCardEntity;
import com.xsquare.modules.xsquare.service.VipCardService;


/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
@RestController
@RequestMapping("/xsquare/vipcard")
public class VipCardController {
	@Autowired
	private VipCardService vipCardService;
	@Autowired
	private VipUserService vipUserService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("xsquare:vipcard:list")
	public R list(@RequestParam Map<String, Object> params){
		System.out.println("params:"+params);
		if (!params.containsKey("vipCardStatus")) {
			params.put("vipCardStatus", "1");
		}

		/*params.put("vipCardStatus", params.get("vipCardStatus").toString());
		params.put("vipCardType", params.get("vipCardType").toString());
		params.put("userNameOrCardId", params.get("userNameOrCardId").toString());*/

		//查询列表数据
        Query query = new Query(params);
		System.out.println("query:"+query);

		List<VipCardEntity> vipCardList = vipCardService.queryList(query);
		int total = vipCardService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(vipCardList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("xsquare:vipcard:info")
	public R info(@PathVariable("id") String id){
		VipCardEntity vipCard = vipCardService.queryObject(id);
		
		return R.ok().put("vipCard", vipCard);
	}
	
	/**
	 * 保存
	 */
	@SysLog("新增会员卡")
	@MoneyLog
	@VipHandleLog()
	@PostMapping("/save")
	@RequiresPermissions("xsquare:vipcard:save")
	public R save(@RequestBody JSONObject jsonObject) throws RRException{
		JSONObject vipCardJson = jsonObject.getJSONObject("vipCard");
		JSONObject vipUserJson = jsonObject.getJSONObject("vipUser");
		String uuid = UUIDUtils.getUUID();
		//将vipUser的json对象转为实体类对象
		VipUserEntity vipUser = (VipUserEntity)JSONObject.toBean(vipUserJson, VipUserEntity.class);
		//将vipCard的json对象转为实体类对象
		VipCardEntity vipCard = (VipCardEntity)JSONObject.toBean(vipCardJson, VipCardEntity.class);

		vipCard.setVipUser(vipUser);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vipCardNum", vipCard.getVipCardNum());
		List<VipCardEntity> vipCardEntityList = vipCardService.queryList(param);
		if (vipCardEntityList.size() != 0) {
			throw new RRException("该卡号已经被注册过了");
		}
		//当传入的vipID为空时，则save
		String vipUserId = vipUser.getId();
		if (vipUserId == null || "".equals(vipUserId)) {
			vipUser.setId(uuid);
			vipCard.setVipUserId(uuid);
			vipUserService.save(vipUser);
		} else {
			vipCard.setVipUserId(vipUserId);
			vipUserService.update(vipUser);
		}
		vipCard.setOpenDate(DateUtils.getDate());
		vipCardService.save(vipCard);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("xsquare:vipcard:update")
	public R update(@RequestBody VipCardEntity vipCard){
		vipCardService.update(vipCard);
		
		return R.ok();
	}

	/**
	 * 操作
	 */
	@SysLog("操作会员卡")
	@MoneyLog
	@VipHandleLog()
	@PostMapping("/handle")
	public R handle(@RequestBody JSONObject jsonObject){
		JSONObject handleJson = jsonObject.getJSONObject("handle");
		JSONObject vipCardJson = jsonObject.getJSONObject("vipCard");
		JSONObject vipUserJson = jsonObject.getJSONObject("vipUser");
		JSONObject newVipCardJson = jsonObject.getJSONObject("newVipCard");
		VipCardEntity handleVipCard = new VipCardEntity();
		//ID
		handleVipCard.setId(Integer.valueOf(vipCardJson.get("id").toString()));
		//余额
		handleVipCard.setBalanceMoney(Double.valueOf(vipCardJson.get("balanceMoney").toString()));
		//次数
		handleVipCard.setBalanceNumber(Integer.valueOf(vipCardJson.get("balanceNumber").toString()));
		//积分
		handleVipCard.setIntegral(Integer.valueOf(vipCardJson.get("integral").toString()));
		//操作类型
		int handleType = Integer.valueOf(handleJson.get("type").toString());
		switch (handleType) {
			//开卡
			case 6:
			//删卡
			case 7:
				break;
			//签到扣费
			case 8:
				break;
			//手动签到扣费
			case 9:
				break;
			//手动扣费
			case 10:
				System.out.println("手动扣费");
				//余额
				handleVipCard.setBalanceMoney(handleVipCard.getBalanceMoney() - Double.valueOf(handleJson.get("deductMoney").toString()));
				//次数
				handleVipCard.setBalanceNumber(handleVipCard.getBalanceNumber() - Integer.valueOf(handleJson.get("deductNumber").toString()));
//				//积分
//				handleVipCard.setIntegral(handleVipCard.getIntegral() + Integer.valueOf(handleJson.get("deductIntegral").toString()));
				//有效期
				handleVipCard.setEffectiveDate(handleJson.get("deductDate").toString());
				break;
			//充值
			case 11:
				//余额
				handleVipCard.setBalanceMoney(handleVipCard.getBalanceMoney() + Double.valueOf(handleJson.get("rechargeMoney").toString()));
				//次数
				handleVipCard.setBalanceNumber(handleVipCard.getBalanceNumber()+ Integer.valueOf(handleJson.get("rechargeNumber").toString()));
				//积分
				handleVipCard.setIntegral(handleVipCard.getIntegral() + Integer.valueOf(handleJson.get("rechargeIntegral").toString()));
				//有效期
				handleVipCard.setEffectiveDate(handleJson.get("rechargeDate").toString());
				break;
			//冻结
			case 12:
				handleVipCard.setStatus("2");
				break;
			//解冻
			case 13:
				handleVipCard.setStatus("1");
				break;
			//换卡
			case 14:
				handleVipCard.setStatus("4");
				break;
			//修改会员信息
			case 15:

				break;
			//积分操作
			case 16:
				//积分
				handleVipCard.setIntegral(handleVipCard.getIntegral() + Integer.valueOf(handleJson.get("integral").toString()));
				break;
			//退款
			case 17:
				//余额
				handleVipCard.setBalanceMoney(handleVipCard.getBalanceMoney() - Double.valueOf(handleJson.get("deductMoney").toString()));
				//次数
				handleVipCard.setBalanceNumber(handleVipCard.getBalanceNumber() - Integer.valueOf(handleJson.get("deductNumber").toString()));
				//有效期
				handleVipCard.setEffectiveDate(handleJson.get("deductDate").toString());
				break;
		}

		vipCardService.update(handleVipCard);
		//换卡要进行的
		if (handleType == 14) {
			newVipCardJson.remove("show");
			VipCardEntity handleNewVipCard = (VipCardEntity)JSONObject.toBean(newVipCardJson, VipCardEntity.class);
			VipUserEntity vipUser = (VipUserEntity)JSONObject.toBean(vipUserJson, VipUserEntity.class);
			handleNewVipCard.setVipCardNum(vipCardJson.get("vipCardNum").toString());
			handleNewVipCard.setVipUserId(vipUser.getId());
			handleNewVipCard.setOpenDate(DateUtils.getDate());
			System.out.println(handleNewVipCard);
			handleNewVipCard.setVipUser(vipUser);
			vipCardService.save(handleNewVipCard);
		}
		return R.ok();
	}

	
	/**
	 * 删除
	 */
	@SysLog("删除会员卡")
	@RequestMapping("/delete")
	@RequiresPermissions("xsquare:vipcard:delete")
	public R delete(@RequestBody String[] ids){
		vipCardService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
