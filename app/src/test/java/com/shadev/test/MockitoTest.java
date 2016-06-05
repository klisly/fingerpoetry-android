/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.shadev.test;

/**
 * Created by devinshine on 15/9/4.
 *
 */
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
public class MockitoTest {


    @Before
    public void setUp(){

    }

    @Test
    public void MocksTest(){
//        TestSubscriber<Mock> testSubscriber = new TestSubscriber<>();
//        getMocks().subscribe(testSubscriber);
//        assertThat(testSubscriber.getOnNextEvents().size(),is(3));
    }

    @Test
    public void ListMocksTest(){
//        TestSubscriber<Mock> testSubscriber = new TestSubscriber<>();
//        getListMocks().flatMap(Observable::from).subscribe(testSubscriber);
//        assertThat(testSubscriber.getOnNextEvents().size(), is(3));
    }


    private Observable<Mock> getMocks(){
        return Observable.just(new Mock(),new Mock(),new Mock());
    }

    private Observable<List<Mock>> getListMocks(){
//        List<Mock> list = new ArrayList<>();
//        list.add(new Mock());
//        list.add(new Mock());
//        list.add(new Mock());
//        return Observable.just(list);
        return null;
    }

    class Mock{
    }
}
