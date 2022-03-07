package com.banking.assignment.controller;

import com.banking.assignment.constants.GlobalConstants;
import com.banking.assignment.payload.request.ViewStatementRequest;
import com.banking.assignment.payload.response.ViewStatementResponse;
import com.banking.assignment.security.services.StatementService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
public class StatementControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private StatementController controller;

    @Mock
    private StatementService statementService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    public void testFetchStatementsValidation() throws Exception {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        List<ViewStatementResponse> response = Mockito.mock(List.class);
        ViewStatementRequest request = Mockito.mock(ViewStatementRequest.class);

        Mockito.when(statementService.fetchStatements(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/statement/statements")
                        .content(gson.toJson(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());




    }
    @Test
    public void testFetchStatements() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(GlobalConstants.DATE_FORMAT);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        List<ViewStatementResponse> response = Mockito.mock(List.class);
        ViewStatementRequest request = Mockito.mock(ViewStatementRequest.class);

        Mockito.when(statementService.fetchStatements(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/statement/statements")
                        .content("{\"accountId\": \"0000210229876\",\"fromDate\": \"2022-03-07\",\"toDate\": \"2022-03-07\",\"fromAmount\": 10,\"toAmount\": 40000}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testFetchStatement() throws Exception {
        List<ViewStatementResponse> response = Mockito.mock(List.class);
        ViewStatementRequest request = Mockito.mock(ViewStatementRequest.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/statement/")
                .param("accountNumber", "0012250016010").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testFetchStatementValidation() throws Exception {
        List<ViewStatementResponse> response = Mockito.mock(List.class);
        ViewStatementRequest request = Mockito.mock(ViewStatementRequest.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/statement/")
                        .param("accountId", "00122500160").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
