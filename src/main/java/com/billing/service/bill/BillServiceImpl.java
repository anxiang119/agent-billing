package com.billing.service.bill;

import com.billing.common.Response;
import com.billing.entity.Bill;
import com.billing.exception.BusinessException;
import com.billing.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillGenerator billGenerator;
    private final BillFileGenerator fileGenerator;

    @Override
    @Transactional
    public Bill generateMonthlyBill(Long tenantId, Long userId) {
        LocalDateTime periodStart = LocalDateTime.now().minusDays(30);
        LocalDateTime periodEnd = LocalDateTime.now();

        return billGenerator.generateMonthlyBill(tenantId, userId, periodStart, periodEnd);
    }

    @Override
    public Bill getBillById(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "账单ID不能为空");
        }

        return billRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "账单不存在"));
    }

    @Override
    public Bill getBillByBillNo(String billNo) {
        if (billNo == null || billNo.trim().isEmpty()) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "账单编号不能为空");
        }

        Bill bill = billRepository.findByBillNo(billNo);

        if (bill == null) {
            throw new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "账单不存在");
        }

        return bill;
    }

    @Override
    @Transactional
    public void deleteBill(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "账单ID不能为空");
        }

        Bill bill = getBillById(id);
        billRepository.delete(bill);
    }

    @Override
    public File generatePdfFile(Long billId) {
        if (billId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "账单ID不能为空");
        }

        return fileGenerator.generatePdfFile(billId);
    }

    @Override
    public File generateExcelFile(Long billId) {
        if (billId == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "账单ID不能为空");
        }

        return fileGenerator.generateExcelFile(billId);
    }
}
