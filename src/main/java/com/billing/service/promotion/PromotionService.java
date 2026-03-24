package com.billing.service.promotion;

import com.billing.entity.Promotion;
import com.billing.repository.PromotionRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 优惠Service接口
 * 提供优惠管理业务逻辑
 */
public interface PromotionService {

    /**
     * 创建优惠活动
     *
     * @param promotion 优惠活动信息
     * @return 创建的优惠活动
     */
    Promotion createPromotion(Promotion promotion);

    /**
     * 根据ID获取优惠活动
     *
     * @param id 优惠活动ID
     * @return 优惠活动
     */
    Promotion getPromotionById(Long id);

    /**
     * 查询优惠活动列表
     *
     * @param tenantId 租户ID
     * @return 优惠活动列表
     */
    List<Promotion> getPromotionsByTenantId(Long tenantId);

    /**
     * 获取有效的优惠活动列表
     *
     * @param tenantId 租户ID
     * @param time 时间点
     * @return 有效的优惠活动列表
     */
    List<Promotion> getActivePromotions(Long tenantId, LocalDateTime time);

    /**
     * 更新优惠活动
     *
     * @param id 优惠活动ID
     * @param promotion 更新的优惠活动信息
     * @return 更新后的优惠活动
     */
    Promotion updatePromotion(Long id, Promotion promotion);

    /**
     * 删除优惠活动
     *
     * @param id 优惠活动ID
     */
    void deletePromotion(Long id);
}
