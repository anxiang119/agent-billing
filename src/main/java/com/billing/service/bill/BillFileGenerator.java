package com.billing.service.bill;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 账单文件生成器
 * 提供PDF和Excel文件生成功能
 */
@Component
@RequiredArgsConstructor
public class BillFileGenerator {

    /**
     * 生成PDF文件
     *
     * @param billId 账单ID
     * @return PDF文件
     */
    public File generatePdfFile(Long billId) {
        String fileName = "bill_" + billId + ".pdf";

        try {
            File file = new File(fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                // 这里简化处理，实际应该使用PDF生成库如iText
                String content = "Bill " + billId + "\nTotal Amount: $100.00";
                fos.write(content.getBytes());
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException("生成PDF文件失败", e);
        }
    }

    /**
     * 生成Excel文件
     *
     * @param billId 账单ID
     * @return Excel文件
     */
    public File generateExcelFile(Long billId) {
        String fileName = "bill_" + billId + ".xlsx";

        try {
            File file = new File(fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                // 这里简化处理，实际应该使用Apache POI
                String content = "Bill," + billId + "\nTotal Amount,$100.00";
                fos.write(content.getBytes());
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException("生成Excel文件失败", e);
        }
    }
}
