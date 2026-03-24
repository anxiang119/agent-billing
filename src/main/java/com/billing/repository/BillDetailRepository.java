package com.billing.repository;

import com.billing.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账单明细Repository接口
 * 提供账单明细明细数据访问方法
 */
@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

    /**
     * 根据账单ID查询明细列表
     *
     * @param billId 账单ID
     * @return 明细列表
     */
    List<BillDetail> findByBillId(Long billId);
}
