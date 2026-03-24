package com.billing.service.promotion;

import com.billing.common.Response;
import com.billing.entity.Promotion;
import com.billing.exception.BusinessException;
import com.billing.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 优惠Service实现
 */
@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository repository;

    @Override
    @Transactional
    public Promotion createPromotion(Promotion promotion) {
        if (promotion == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "优惠活动信息不能为空");
        }

        if (promotion.getTenantId() == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "租户ID不能为空");
        }

        if (promotion.getName() == null || promotion.getName().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "活动名称不能为空");
        }

        if (promotion.getType() == null || promotion.getType().trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "优惠类型不能为空");
        }

        return repository.save(promotion);
    }

    @Override
    public Promotion getPromotionById(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "优惠活动ID不能为空");
        }

        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "优惠活动不存在"));
    }

    @Override
    public List<Promotion> getPromotionsByTenantId(Long tenantId) {
        if (tenantId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "租户ID不能为空");
        }

        return repository.findByTenantId(tenantId);
    }

    @Override
    public List<Promotion> getActivePromotions(Long tenantId, LocalDateTime time) {
        if (tenantId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "租户ID不能为空");
        }

        if (time == null) {
            time = LocalDateTime.now();
        }

        return repository.findActivePromotions(tenantId, time);
    }

    @Override
    @Transactional
    public Promotion updatePromotion(Long id, Promotion promotion) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "优惠活动ID不能为空");
        }

        if (promotion == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "优惠活动信息不能为空");
        }

        Promotion existing = getPromotionById(id);

        if (promotion.getName() != null) {
            existing.setName(promotion.getName());
        }

        if (promotion.getType() != null) {
            existing.setType(promotion.getType());
        }

        if (promotion.getConditionAmount() != null) {
            existing.setConditionAmount(promotion.getConditionAmount());
        }

        if (promotion.getDiscountAmount() != null) {
            existing.setDiscountAmount(promotion.getDiscountAmount());
        }

        if (promotion.getDiscountRate() != null) {
            existing.setDiscountRate(promotion.getDiscountRate());
        }

        if (promotion.getEffectiveStartTime() != null) {
            existing.setEffectiveStartTime(promotion.getEffectiveStartTime());
        }

        if (promotion.getEffectiveEndTime() != null) {
            existing.setEffectiveEndTime(promotion.getEffectiveEndTime());
        }

        if (promotion.getStatus() != null) {
            existing.setStatus(promotion.getStatus());
        }

        if (promotion.getDescription() != null) {
            existing.setDescription(promotion.getDescription());
        }

        return repository.save(existing);
    }

    @Override
    @Transactional
    public void deletePromotion(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "优惠活动ID不能为空");
        }

        Promotion promotion = getPromotionById(id);
        repository.delete(promotion);
    }
}
