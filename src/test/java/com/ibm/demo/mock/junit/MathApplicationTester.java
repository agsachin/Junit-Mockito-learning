package com.ibm.demo.mock.junit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

// @RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class MathApplicationTester {

    //@InjectMocks annotation is used to create and inject the mock object
    @InjectMocks
    MathApplication mathApplication = new MathApplication();

    //@Mock annotation is used to create the mock object to be injected
    @Mock
    CalculatorService calcService;

    @Test
    public void testAdd(){
        //add the behavior of calc service to add two numbers
        when(calcService.add(10.0,20.0)).thenReturn(30.00);
        when(calcService.subtract(20.0,10.0)).thenReturn(10.00);

        // 1
        // test the add functionality
        Assert.assertEquals(mathApplication.add(10.0, 20.0),30.0,0);

        // 2
        // verify call to calcService is made or not with same arguments.
        //verify the behavior
        verify(calcService).add(10.0, 20.0);

        // 3
        // -ve verification of the behavior
        //verify(calcService).add(20.0, 30.0);

        // 4
        // test the add functionality
        Assert.assertEquals(mathApplication.add(10.0, 20.0),30.0,0);
        Assert.assertEquals(mathApplication.add(10.0, 20.0),30.0,0);

        //check if add function is called three times
        verify(calcService, times(3)).add(10.0, 20.0);


        // 5
        //test the subtract functionality
        Assert.assertEquals(mathApplication.subtract(20.0, 10.0),10.0,0.0);

        //check a minimum 1 call count
        verify(calcService, atLeastOnce()).subtract(20.0, 10.0);

        //check if add function is called minimum 2 times
        verify(calcService, atLeast(2)).add(10.0, 20.0);

        //check if add function is called maximum 3 times
        verify(calcService, atMost(3)).add(10.0,20.0);

        //verify call to add method to be completed within 100 ms
        verify(calcService, timeout(0).times(3)).add(10.0,20.0);

    }


    // 6

    @Test(expected = IllegalAccessError.class)
    public void testDivide(){
        //add the behavior to throw exception
        when(calcService.divide(anyDouble(),eq(0.0))).thenThrow(new ArithmeticException("denominator == 0"));

        //test the add functionality
        mathApplication.divide(10.0, 0.0);
    }


    // 7

    @Test
    public void testAddAndSubtract(){

        //add the behavior to add numbers
        when(calcService.add(20.0,10.0)).thenReturn(30.0);

        //subtract the behavior to subtract numbers
        when(calcService.subtract(20.0,10.0)).thenReturn(10.0);

        //test the add functionality
        Assert.assertEquals(mathApplication.add(20.0, 10.0),30.0,0);

        //test the subtract functionality
        Assert.assertEquals(mathApplication.subtract(20.0, 10.0),10.0,0);

        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(calcService);

        //following will make sure that add is first called then subtract is called.
        inOrder.verify(calcService).add(20.0,10.0);
        inOrder.verify(calcService).subtract(20.0,10.0);

        //-ve test case
        //following will make sure that add is first called then subtract is called.
        inOrder.verify(calcService).subtract(20.0,10.0);
        inOrder.verify(calcService).add(20.0,10.0);
    }



    @Test
    public void testBehaviourDrivenDevelopment(){

        //Given
        given(calcService.add(20.0,10.0)).willReturn(30.0);

        //when
        double result = calcService.add(20.0,10.0);

        //then
        Assert.assertEquals(result,30.0,0);
    }

    @Test
    public void testTimeout(){

        //test the add functionality
        Assert.assertEquals(mathApplication.add(20.0, 10.0),30.0,0);

        //verify call to add method to be completed within 100 ms
        verify(calcService, timeout(100)).add(20.0,10.0);

        //invocation count can be added to ensure multiplication invocations
        //can be checked within given timeframe
        verify(calcService, timeout(100).times(1)).subtract(20.0,10.0);
    }
}