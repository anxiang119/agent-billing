package com.billing.service.external;

import java.util.Map;

public interface InvoiceApiService {
    String createInvoice(Map<String, Object> params);
    String queryInvoice(String invoiceNo);
    String cancelInvoice(String invoiceNo);
}
