package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Epicoitem;
import com.mycompany.myapp.repository.EntityManager;
import com.mycompany.myapp.repository.EpicoitemRepository;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link EpicoitemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class EpicoitemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Float DEFAULT_COST_PRICE = 1F;
    private static final Float UPDATED_COST_PRICE = 2F;

    private static final Float DEFAULT_UNIT_PRICE = 1F;
    private static final Float UPDATED_UNIT_PRICE = 2F;

    private static final String DEFAULT_PIC_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_PIC_FILENAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/epicoitems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EpicoitemRepository epicoitemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Epicoitem epicoitem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Epicoitem createEntity(EntityManager em) {
        Epicoitem epicoitem = new Epicoitem()
            .name(DEFAULT_NAME)
            .category(DEFAULT_CATEGORY)
            .costPrice(DEFAULT_COST_PRICE)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .picFilename(DEFAULT_PIC_FILENAME);
        return epicoitem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Epicoitem createUpdatedEntity(EntityManager em) {
        Epicoitem epicoitem = new Epicoitem()
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .costPrice(UPDATED_COST_PRICE)
            .unitPrice(UPDATED_UNIT_PRICE)
            .picFilename(UPDATED_PIC_FILENAME);
        return epicoitem;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Epicoitem.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        epicoitem = createEntity(em);
    }

    @Test
    void createEpicoitem() throws Exception {
        int databaseSizeBeforeCreate = epicoitemRepository.findAll().collectList().block().size();
        // Create the Epicoitem
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(epicoitem))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeCreate + 1);
        Epicoitem testEpicoitem = epicoitemList.get(epicoitemList.size() - 1);
        assertThat(testEpicoitem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEpicoitem.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testEpicoitem.getCostPrice()).isEqualTo(DEFAULT_COST_PRICE);
        assertThat(testEpicoitem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testEpicoitem.getPicFilename()).isEqualTo(DEFAULT_PIC_FILENAME);
    }

    @Test
    void createEpicoitemWithExistingId() throws Exception {
        // Create the Epicoitem with an existing ID
        epicoitem.setId(1L);

        int databaseSizeBeforeCreate = epicoitemRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(epicoitem))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEpicoitemsAsStream() {
        // Initialize the database
        epicoitemRepository.save(epicoitem).block();

        List<Epicoitem> epicoitemList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Epicoitem.class)
            .getResponseBody()
            .filter(epicoitem::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(epicoitemList).isNotNull();
        assertThat(epicoitemList).hasSize(1);
        Epicoitem testEpicoitem = epicoitemList.get(0);
        assertThat(testEpicoitem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEpicoitem.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testEpicoitem.getCostPrice()).isEqualTo(DEFAULT_COST_PRICE);
        assertThat(testEpicoitem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testEpicoitem.getPicFilename()).isEqualTo(DEFAULT_PIC_FILENAME);
    }

    @Test
    void getAllEpicoitems() {
        // Initialize the database
        epicoitemRepository.save(epicoitem).block();

        // Get all the epicoitemList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(epicoitem.getId().intValue()))
            .jsonPath("$.[*].name")
            .value(hasItem(DEFAULT_NAME))
            .jsonPath("$.[*].category")
            .value(hasItem(DEFAULT_CATEGORY))
            .jsonPath("$.[*].costPrice")
            .value(hasItem(DEFAULT_COST_PRICE.doubleValue()))
            .jsonPath("$.[*].unitPrice")
            .value(hasItem(DEFAULT_UNIT_PRICE.doubleValue()))
            .jsonPath("$.[*].picFilename")
            .value(hasItem(DEFAULT_PIC_FILENAME));
    }

    @Test
    void getEpicoitem() {
        // Initialize the database
        epicoitemRepository.save(epicoitem).block();

        // Get the epicoitem
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, epicoitem.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(epicoitem.getId().intValue()))
            .jsonPath("$.name")
            .value(is(DEFAULT_NAME))
            .jsonPath("$.category")
            .value(is(DEFAULT_CATEGORY))
            .jsonPath("$.costPrice")
            .value(is(DEFAULT_COST_PRICE.doubleValue()))
            .jsonPath("$.unitPrice")
            .value(is(DEFAULT_UNIT_PRICE.doubleValue()))
            .jsonPath("$.picFilename")
            .value(is(DEFAULT_PIC_FILENAME));
    }

    @Test
    void getNonExistingEpicoitem() {
        // Get the epicoitem
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingEpicoitem() throws Exception {
        // Initialize the database
        epicoitemRepository.save(epicoitem).block();

        int databaseSizeBeforeUpdate = epicoitemRepository.findAll().collectList().block().size();

        // Update the epicoitem
        Epicoitem updatedEpicoitem = epicoitemRepository.findById(epicoitem.getId()).block();
        updatedEpicoitem
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .costPrice(UPDATED_COST_PRICE)
            .unitPrice(UPDATED_UNIT_PRICE)
            .picFilename(UPDATED_PIC_FILENAME);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedEpicoitem.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedEpicoitem))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeUpdate);
        Epicoitem testEpicoitem = epicoitemList.get(epicoitemList.size() - 1);
        assertThat(testEpicoitem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEpicoitem.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testEpicoitem.getCostPrice()).isEqualTo(UPDATED_COST_PRICE);
        assertThat(testEpicoitem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testEpicoitem.getPicFilename()).isEqualTo(UPDATED_PIC_FILENAME);
    }

    @Test
    void putNonExistingEpicoitem() throws Exception {
        int databaseSizeBeforeUpdate = epicoitemRepository.findAll().collectList().block().size();
        epicoitem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, epicoitem.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(epicoitem))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEpicoitem() throws Exception {
        int databaseSizeBeforeUpdate = epicoitemRepository.findAll().collectList().block().size();
        epicoitem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(epicoitem))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEpicoitem() throws Exception {
        int databaseSizeBeforeUpdate = epicoitemRepository.findAll().collectList().block().size();
        epicoitem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(epicoitem))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEpicoitemWithPatch() throws Exception {
        // Initialize the database
        epicoitemRepository.save(epicoitem).block();

        int databaseSizeBeforeUpdate = epicoitemRepository.findAll().collectList().block().size();

        // Update the epicoitem using partial update
        Epicoitem partialUpdatedEpicoitem = new Epicoitem();
        partialUpdatedEpicoitem.setId(epicoitem.getId());

        partialUpdatedEpicoitem.name(UPDATED_NAME).category(UPDATED_CATEGORY).picFilename(UPDATED_PIC_FILENAME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEpicoitem.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEpicoitem))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeUpdate);
        Epicoitem testEpicoitem = epicoitemList.get(epicoitemList.size() - 1);
        assertThat(testEpicoitem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEpicoitem.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testEpicoitem.getCostPrice()).isEqualTo(DEFAULT_COST_PRICE);
        assertThat(testEpicoitem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testEpicoitem.getPicFilename()).isEqualTo(UPDATED_PIC_FILENAME);
    }

    @Test
    void fullUpdateEpicoitemWithPatch() throws Exception {
        // Initialize the database
        epicoitemRepository.save(epicoitem).block();

        int databaseSizeBeforeUpdate = epicoitemRepository.findAll().collectList().block().size();

        // Update the epicoitem using partial update
        Epicoitem partialUpdatedEpicoitem = new Epicoitem();
        partialUpdatedEpicoitem.setId(epicoitem.getId());

        partialUpdatedEpicoitem
            .name(UPDATED_NAME)
            .category(UPDATED_CATEGORY)
            .costPrice(UPDATED_COST_PRICE)
            .unitPrice(UPDATED_UNIT_PRICE)
            .picFilename(UPDATED_PIC_FILENAME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEpicoitem.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedEpicoitem))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeUpdate);
        Epicoitem testEpicoitem = epicoitemList.get(epicoitemList.size() - 1);
        assertThat(testEpicoitem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEpicoitem.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testEpicoitem.getCostPrice()).isEqualTo(UPDATED_COST_PRICE);
        assertThat(testEpicoitem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testEpicoitem.getPicFilename()).isEqualTo(UPDATED_PIC_FILENAME);
    }

    @Test
    void patchNonExistingEpicoitem() throws Exception {
        int databaseSizeBeforeUpdate = epicoitemRepository.findAll().collectList().block().size();
        epicoitem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, epicoitem.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(epicoitem))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEpicoitem() throws Exception {
        int databaseSizeBeforeUpdate = epicoitemRepository.findAll().collectList().block().size();
        epicoitem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(epicoitem))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEpicoitem() throws Exception {
        int databaseSizeBeforeUpdate = epicoitemRepository.findAll().collectList().block().size();
        epicoitem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(epicoitem))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Epicoitem in the database
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEpicoitem() {
        // Initialize the database
        epicoitemRepository.save(epicoitem).block();

        int databaseSizeBeforeDelete = epicoitemRepository.findAll().collectList().block().size();

        // Delete the epicoitem
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, epicoitem.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Epicoitem> epicoitemList = epicoitemRepository.findAll().collectList().block();
        assertThat(epicoitemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
