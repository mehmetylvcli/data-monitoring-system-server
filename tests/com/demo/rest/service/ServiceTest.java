package com.dataMonitoringSystemServer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ServiceTest {

    @Mock
    TestService testService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCustomer() {
        when(testService.getFileNumber(12L)).thenReturn(1L);
        Assert.assertEquals(1,testService.getFileNumber(12L).longValue());
        verify(testService).getFileNumber(12L);
    }
}
