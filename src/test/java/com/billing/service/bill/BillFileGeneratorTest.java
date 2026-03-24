package com.billing.service.bill;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 账单文件生成器测试
 */
class BillFileGeneratorTest {

    @Test
    void testGeneratePdfFile_Success() {
        BillFileGenerator generator = new BillFileGenerator();

        File file = generator.generatePdfFile(1L);

        assertNotNull(file);
        assertTrue(file.getName().endsWith(".pdf"));
    }

    @Test
    void testGenerateExcelFile_Success() {
        BillFileGenerator generator = new BillFileGenerator();

        File file = generator.generateExcelFile(1L);

        assertNotNull(file);
        assertTrue(file.getName().endsWith(".xlsx"));
    }
}
