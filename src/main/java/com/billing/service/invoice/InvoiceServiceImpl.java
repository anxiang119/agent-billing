package com.billing.service.invoice;

import com.billing.common.Response;
import com.billing.entity.Invoice;
import com.billing.exception.BusinessException;
import com.billing.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;

    @Override
    @Transactional
    public Invoice applyInvoice(Invoice invoice) {
        if (invoice == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "发票信息不能为空");
        }

        if (invoice.getUserId() == null || invoice.getBillId() == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "用户ID和账单ID不能为空");
        }

        if (invoice.getAmount() == null || invoice.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "发票金额必须大于0");
        }

        return repository.save(invoice);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        if (id == null) {
            throw new BusinessException(Response.ResponseCode.PARAM_ERROR.getCode(), "发票ID不能为空");
        }

        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(Response.ResponseCode.NOT_FOUND.getCode(), "发票不存在"));
    }

    @Override
    @Transactional
    public void cancelInvoice(Long id) {
        Invoice invoice = getInvoiceById(id);
        invoice.setStatus("CANCELLED");
        invoice.setCancelledTime(LocalDateTime.now());
        repository.save(invoice);
    }

    @Override
    @Transactional
    public Invoice reissueInvoice(Long id) {
        Invoice oldInvoice = getInvoiceById(id);
        oldInvoice.setStatus("CANCELLED");
        oldInvoice.setCancelledTime(LocalDateTime.now());
        repository.save(oldInvoice);

        Invoice newInvoice = new Invoice();
        newInvoice.setTenantId(oldInvoice.getTenantId());
        newInvoice.setUserId(oldInvoice.getUserId());
        newInvoice.setBillId(oldInvoice.getBillId());
        newInvoice.setInvoiceType(oldInvoice.getInvoiceType());
        newInvoice.setAmount(oldInvoice.getAmount());
        newInvoice.setCurrency(oldInvoice.getCurrency());
        newInvoice.setStatus("ISSUED");
        newInvoice.setIssuedTime(LocalDateTime.now());

        return repository.save(newInvoice);
    }
}
