package com.restapp.web.rest;

import com.restapp.Application;
import com.restapp.domain.Accounting;
import com.restapp.repository.AccountingRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AccountingResource REST controller.
 *
 * @see AccountingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AccountingResourceTest {

    private static final String DEFAULT_DAILYSALESTOTAL = "AAAAA";
    private static final String UPDATED_DAILYSALESTOTAL = "BBBBB";

    private static final Double DEFAULT_DAILYTIPSTOTAL = 1D;
    private static final Double UPDATED_DAILYTIPSTOTAL = 2D;

    private static final Double DEFAULT_ADDCOUPONVALUE = 1D;
    private static final Double UPDATED_ADDCOUPONVALUE = 2D;

    @Inject
    private AccountingRepository accountingRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAccountingMockMvc;

    private Accounting accounting;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccountingResource accountingResource = new AccountingResource();
        ReflectionTestUtils.setField(accountingResource, "accountingRepository", accountingRepository);
        this.restAccountingMockMvc = MockMvcBuilders.standaloneSetup(accountingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        accounting = new Accounting();
        accounting.setDailysalestotal(DEFAULT_DAILYSALESTOTAL);
        accounting.setDailytipstotal(DEFAULT_DAILYTIPSTOTAL);
        accounting.setAddcouponvalue(DEFAULT_ADDCOUPONVALUE);
    }

    @Test
    @Transactional
    public void createAccounting() throws Exception {
        int databaseSizeBeforeCreate = accountingRepository.findAll().size();

        // Create the Accounting

        restAccountingMockMvc.perform(post("/api/accountings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accounting)))
                .andExpect(status().isCreated());

        // Validate the Accounting in the database
        List<Accounting> accountings = accountingRepository.findAll();
        assertThat(accountings).hasSize(databaseSizeBeforeCreate + 1);
        Accounting testAccounting = accountings.get(accountings.size() - 1);
        assertThat(testAccounting.getDailysalestotal()).isEqualTo(DEFAULT_DAILYSALESTOTAL);
        assertThat(testAccounting.getDailytipstotal()).isEqualTo(DEFAULT_DAILYTIPSTOTAL);
        assertThat(testAccounting.getAddcouponvalue()).isEqualTo(DEFAULT_ADDCOUPONVALUE);
    }

    @Test
    @Transactional
    public void getAllAccountings() throws Exception {
        // Initialize the database
        accountingRepository.saveAndFlush(accounting);

        // Get all the accountings
        restAccountingMockMvc.perform(get("/api/accountings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(accounting.getId().intValue())))
                .andExpect(jsonPath("$.[*].dailysalestotal").value(hasItem(DEFAULT_DAILYSALESTOTAL.toString())))
                .andExpect(jsonPath("$.[*].dailytipstotal").value(hasItem(DEFAULT_DAILYTIPSTOTAL.doubleValue())))
                .andExpect(jsonPath("$.[*].addcouponvalue").value(hasItem(DEFAULT_ADDCOUPONVALUE.doubleValue())));
    }

    @Test
    @Transactional
    public void getAccounting() throws Exception {
        // Initialize the database
        accountingRepository.saveAndFlush(accounting);

        // Get the accounting
        restAccountingMockMvc.perform(get("/api/accountings/{id}", accounting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(accounting.getId().intValue()))
            .andExpect(jsonPath("$.dailysalestotal").value(DEFAULT_DAILYSALESTOTAL.toString()))
            .andExpect(jsonPath("$.dailytipstotal").value(DEFAULT_DAILYTIPSTOTAL.doubleValue()))
            .andExpect(jsonPath("$.addcouponvalue").value(DEFAULT_ADDCOUPONVALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccounting() throws Exception {
        // Get the accounting
        restAccountingMockMvc.perform(get("/api/accountings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccounting() throws Exception {
        // Initialize the database
        accountingRepository.saveAndFlush(accounting);

		int databaseSizeBeforeUpdate = accountingRepository.findAll().size();

        // Update the accounting
        accounting.setDailysalestotal(UPDATED_DAILYSALESTOTAL);
        accounting.setDailytipstotal(UPDATED_DAILYTIPSTOTAL);
        accounting.setAddcouponvalue(UPDATED_ADDCOUPONVALUE);

        restAccountingMockMvc.perform(put("/api/accountings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accounting)))
                .andExpect(status().isOk());

        // Validate the Accounting in the database
        List<Accounting> accountings = accountingRepository.findAll();
        assertThat(accountings).hasSize(databaseSizeBeforeUpdate);
        Accounting testAccounting = accountings.get(accountings.size() - 1);
        assertThat(testAccounting.getDailysalestotal()).isEqualTo(UPDATED_DAILYSALESTOTAL);
        assertThat(testAccounting.getDailytipstotal()).isEqualTo(UPDATED_DAILYTIPSTOTAL);
        assertThat(testAccounting.getAddcouponvalue()).isEqualTo(UPDATED_ADDCOUPONVALUE);
    }

    @Test
    @Transactional
    public void deleteAccounting() throws Exception {
        // Initialize the database
        accountingRepository.saveAndFlush(accounting);

		int databaseSizeBeforeDelete = accountingRepository.findAll().size();

        // Get the accounting
        restAccountingMockMvc.perform(delete("/api/accountings/{id}", accounting.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Accounting> accountings = accountingRepository.findAll();
        assertThat(accountings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
