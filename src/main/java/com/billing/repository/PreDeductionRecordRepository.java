package com.billing.repository;

import com.billing.entity.PreDeductionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 预扣记录Repository接口
 * 提供预扣记录数据访问方法
 */
@Repository
public interface PreDeductionRecordRepository extends JpaRepository<PreDeductionRecord, Long> {

    /**
     * 根据用户ID查询预扣记录
     *
     * @param userId 用户ID
     * @return 预扣记录列表
     */
    List<PreDeductionRecord> findByUserId(Long userId);

    /**
     * 根据资源ID查询预扣记录
     *
     * @param resourceId 资源ID
     * @return 预扣记录列表
     */
    List<PreDeductionRecord> findByResourceId(String resourceId);

    /**
     * 根据状态查询预扣记录
     *
     * @param status 状态
     * @return 预扣记录列表
     */
    List<PreDeductionRecord> findByStatus(String status);

    /**
     * 根据资源ID查询待结算的预扣记录
     *
     * @param resourceId 资源ID
     * @return 预扣记录
     */
    PreDeductionRecord findByResourceIdAndStatus(String resourceId, String status);
}
