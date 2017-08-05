package kz.izzi.bus.web.rest;

import kz.izzi.bus.IzzibusApp;

import kz.izzi.bus.domain.LotteryRound;
import kz.izzi.bus.repository.LotteryRoundRepository;
import kz.izzi.bus.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static kz.izzi.bus.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LotteryRoundResource REST controller.
 *
 * @see LotteryRoundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IzzibusApp.class)
public class LotteryRoundResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private LotteryRoundRepository lotteryRoundRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLotteryRoundMockMvc;

    private LotteryRound lotteryRound;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LotteryRoundResource lotteryRoundResource = new LotteryRoundResource(lotteryRoundRepository);
        this.restLotteryRoundMockMvc = MockMvcBuilders.standaloneSetup(lotteryRoundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LotteryRound createEntity(EntityManager em) {
        LotteryRound lotteryRound = new LotteryRound()
            .datetime(DEFAULT_DATETIME);
        return lotteryRound;
    }

    @Before
    public void initTest() {
        lotteryRound = createEntity(em);
    }

    @Test
    @Transactional
    public void createLotteryRound() throws Exception {
        int databaseSizeBeforeCreate = lotteryRoundRepository.findAll().size();

        // Create the LotteryRound
        restLotteryRoundMockMvc.perform(post("/api/lottery-rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lotteryRound)))
            .andExpect(status().isCreated());

        // Validate the LotteryRound in the database
        List<LotteryRound> lotteryRoundList = lotteryRoundRepository.findAll();
        assertThat(lotteryRoundList).hasSize(databaseSizeBeforeCreate + 1);
        LotteryRound testLotteryRound = lotteryRoundList.get(lotteryRoundList.size() - 1);
        assertThat(testLotteryRound.getDatetime()).isEqualTo(DEFAULT_DATETIME);
    }

    @Test
    @Transactional
    public void createLotteryRoundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lotteryRoundRepository.findAll().size();

        // Create the LotteryRound with an existing ID
        lotteryRound.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLotteryRoundMockMvc.perform(post("/api/lottery-rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lotteryRound)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LotteryRound> lotteryRoundList = lotteryRoundRepository.findAll();
        assertThat(lotteryRoundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = lotteryRoundRepository.findAll().size();
        // set the field null
        lotteryRound.setDatetime(null);

        // Create the LotteryRound, which fails.

        restLotteryRoundMockMvc.perform(post("/api/lottery-rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lotteryRound)))
            .andExpect(status().isBadRequest());

        List<LotteryRound> lotteryRoundList = lotteryRoundRepository.findAll();
        assertThat(lotteryRoundList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLotteryRounds() throws Exception {
        // Initialize the database
        lotteryRoundRepository.saveAndFlush(lotteryRound);

        // Get all the lotteryRoundList
        restLotteryRoundMockMvc.perform(get("/api/lottery-rounds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lotteryRound.getId().intValue())))
            .andExpect(jsonPath("$.[*].datetime").value(hasItem(sameInstant(DEFAULT_DATETIME))));
    }

    @Test
    @Transactional
    public void getLotteryRound() throws Exception {
        // Initialize the database
        lotteryRoundRepository.saveAndFlush(lotteryRound);

        // Get the lotteryRound
        restLotteryRoundMockMvc.perform(get("/api/lottery-rounds/{id}", lotteryRound.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lotteryRound.getId().intValue()))
            .andExpect(jsonPath("$.datetime").value(sameInstant(DEFAULT_DATETIME)));
    }

    @Test
    @Transactional
    public void getNonExistingLotteryRound() throws Exception {
        // Get the lotteryRound
        restLotteryRoundMockMvc.perform(get("/api/lottery-rounds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLotteryRound() throws Exception {
        // Initialize the database
        lotteryRoundRepository.saveAndFlush(lotteryRound);
        int databaseSizeBeforeUpdate = lotteryRoundRepository.findAll().size();

        // Update the lotteryRound
        LotteryRound updatedLotteryRound = lotteryRoundRepository.findOne(lotteryRound.getId());
        updatedLotteryRound
            .datetime(UPDATED_DATETIME);

        restLotteryRoundMockMvc.perform(put("/api/lottery-rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLotteryRound)))
            .andExpect(status().isOk());

        // Validate the LotteryRound in the database
        List<LotteryRound> lotteryRoundList = lotteryRoundRepository.findAll();
        assertThat(lotteryRoundList).hasSize(databaseSizeBeforeUpdate);
        LotteryRound testLotteryRound = lotteryRoundList.get(lotteryRoundList.size() - 1);
        assertThat(testLotteryRound.getDatetime()).isEqualTo(UPDATED_DATETIME);
    }

    @Test
    @Transactional
    public void updateNonExistingLotteryRound() throws Exception {
        int databaseSizeBeforeUpdate = lotteryRoundRepository.findAll().size();

        // Create the LotteryRound

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLotteryRoundMockMvc.perform(put("/api/lottery-rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lotteryRound)))
            .andExpect(status().isCreated());

        // Validate the LotteryRound in the database
        List<LotteryRound> lotteryRoundList = lotteryRoundRepository.findAll();
        assertThat(lotteryRoundList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLotteryRound() throws Exception {
        // Initialize the database
        lotteryRoundRepository.saveAndFlush(lotteryRound);
        int databaseSizeBeforeDelete = lotteryRoundRepository.findAll().size();

        // Get the lotteryRound
        restLotteryRoundMockMvc.perform(delete("/api/lottery-rounds/{id}", lotteryRound.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LotteryRound> lotteryRoundList = lotteryRoundRepository.findAll();
        assertThat(lotteryRoundList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LotteryRound.class);
        LotteryRound lotteryRound1 = new LotteryRound();
        lotteryRound1.setId(1L);
        LotteryRound lotteryRound2 = new LotteryRound();
        lotteryRound2.setId(lotteryRound1.getId());
        assertThat(lotteryRound1).isEqualTo(lotteryRound2);
        lotteryRound2.setId(2L);
        assertThat(lotteryRound1).isNotEqualTo(lotteryRound2);
        lotteryRound1.setId(null);
        assertThat(lotteryRound1).isNotEqualTo(lotteryRound2);
    }
}
