package com.example.controller;

import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.Example;
import com.example.model.ApiResponse;
import com.example.model.InicisMember;
import com.example.repository.InicisMemberRepository;
import com.example.service.InicisApiService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Example.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private InicisApiService inicisApiService;


    @Autowired
    private InicisApiController inicisApiController;

    //@MockBean
    //private InicisMemberRepository repository;

    @Test
    public void testController() {

        ApiResponse apiResponse = inicisApiController.getJoinInfo();

        System.out.println(apiResponse.getMsg());

    }


    @Test
    public void testService() {
        List<InicisMember> inicisMembers = inicisApiService.findAll();
        for(InicisMember member : inicisMembers){
            System.out.println(member.getCorpname());
        }


    }

    @Test
    public void testFindFromDB() {
        List<InicisMember> expected = Arrays.asList(
                new InicisMember(),
                new InicisMember());

        //given(repository.findAll()).willReturn(expected);
    }

}
