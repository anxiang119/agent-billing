package com.billing.service.invoice;

import com.billing.entity.Invoice;

public interface InvoiceService {
    Invoice applyInvoice(Invoice invoice);
    Invoice getInvoiceById(Long id);
    void cancelInvoice(Long id);
    Invoice reissueInvoice(Long id);
}
