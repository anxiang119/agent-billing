package com.billing.controller;

import com.billing.common.Response;
import com.billing.entity.Promotion;
import com.billing.service.promotion.PromotionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 优惠Controller测试
 */
@WebMvcTest(PromotionController.class)
class PromotionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PromotionService promotionService;

    @Autowired
    private ObjectMapper objectMapper;

    private Promotion testPromotion;

    @BeforeEach
    void setUp() {
        testPromotion = new Promotion();
        testPromotion.setId(1L);
        testPromotion.setTenantId(10L);
        testPromotion.setName("双11优惠");
        testPromotion.setType("FULL_REDUCTION");
        testPromotion.setConditionAmount(new BigDecimal("100.00"));
        testPromotion.setDiscountAmount(new BigDecimal("10.00"));
        testPromotion.setEffectiveStartTime(LocalDateTime.now());
        testPromotion.setStatus("ACTIVE");
    }

    @Test
    void testGetPromotionById_Success() throws Exception {
        when(promotionService.getPromotionById(1L)).thenReturn(testPromotion);

        mockMvc.perform(get("/api/promotions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("双11优惠"));
    }

    @Test
    void testCreatePromotion_Success() throws Exception {
        when(promotionService.createPromotion(any())).thenAnswer(invocation -> {
            Promotion p = invocation.getArgument(0);
            p.setId(2L);
            return p;
        });

        mockMvc.perform(post("/api/promotions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPromotion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.id").value(2));
    }
}
