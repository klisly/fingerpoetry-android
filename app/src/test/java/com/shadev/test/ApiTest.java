/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.shadev.test;

import android.content.Context;
import android.test.mock.MockContext;

import com.klisly.bookbox.BuildConfig;
import com.klisly.bookbox.api.BookRetrofit;
import com.klisly.bookbox.domain.ApiResult;
import com.klisly.bookbox.domain.LoginData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ApiTest {
    BookRetrofit bookRetrofit = null;
    Observer<ApiResult<LoginData>> observer = new Observer<ApiResult<LoginData>>() {
        @Override
        public void onCompleted() {
            System.out.println("onCompleted");

        }

        @Override
        public void onError(Throwable e) {
            System.out.println("onError："+e.getMessage());

        }

        @Override
        public void onNext(ApiResult<LoginData> data) {
            System.out.println("datas:" + data);
        }
    };

    @Before
    public void setUp() {
        bookRetrofit = BookRetrofit.getInstance();
    }

    @Test
    public void login() throws IOException {
        Context context = new MockContext();
        Observable<ApiResult<LoginData>> observable= bookRetrofit.getAccountApi().login("18301441595", "123456");
        observable.observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .subscribe(observer);
    }

    @Test
    public void register() throws IOException {
//        TestSubscriber
//                ApiResult<LoginData> call = bookRetrofit.getAccountApi().registerN("18301441595", "123456",
//                        "了了此生");
//                System.out.println("data:"+call);
        //        Response<ApiResult<LoginData>> data = call.execute();
        //                TestSubscriber<ApiResult<LoginData>> subscriber = new TestSubscriber<>();
        //                bookRetrofit.getAccountApi()
        //                        .login("18301441595", "123456")
        //                        .subscribeOn(Schedulers.io())
        //                        .observeOn(AndroidSchedulers.mainThread())
        //                        .subscribe(subscriber);
        //                List<ApiResult<LoginData>> data = subscriber.getOnNextEvents();
        //        System.out.println("data:" + data.body());
        //        res.subscribe(subscriber);

        //        String result = EntityUtils.toString(httpResponse.getEntity());
    }
    //Test2 这个是走真实网络返回的数据
    @Test
    public void reposTest2() {
        //        List<Repo> list = mGithub.listRepos("devinshine");
        //        assertThat(list.size(), is(not(0)));
        //        System.out.print(list.size());
    }

    //Test3 这个是走真实网络返回的数据
    @Test
    public void reposTestByObservable() {
        //        int size = mGithub.listRepos2Observable("devinshine")
        //            .flatMap(Observable::from)
        //            .count()
        //            .toBlocking()
        //            .single();
        //        assertThat(size, is(not(0)));
        //        System.out.print(size);

        //下面代码是会报错的
        //TestSubscriber<Repo> testSubscriber = new TestSubscriber<>();
        //mGithub.listRepos2Observable("devinshine")
        //    .flatMap(Observable::from)
        //    .subscribe(testSubscriber);
        //assertThat(testSubscriber.getOnNextEvents().size(),is(not(0)));
    }

    //Test4 这是走模拟数据
    @Test
    public void reposTestByMockClient() throws IOException {
        //        String mockJsonResult =
        //            "[{\"id\":19669199,\"name\":\"AnimationDemo\",\"full_name\":\"DevinShine/AnimationDemo\",
        // \"owner\":{\"login\":\"DevinShine\",\"id\":7385819,\"avatar_url\":\"https://avatars.githubusercontent
        // .com/u/7385819?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/DevinShine\",
        // \"html_url\":\"https://github.com/DevinShine\",\"followers_url\":\"https://api.github
        // .com/users/DevinShine/followers\",\"following_url\":\"https://api.github
        // .com/users/DevinShine/following{/other_user}\",\"gists_url\":\"https://api.github
        // .com/users/DevinShine/gists{/gist_id}\",\"starred_url\":\"https://api.github
        // .com/users/DevinShine/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github
        // .com/users/DevinShine/subscriptions\",\"organizations_url\":\"https://api.github
        // .com/users/DevinShine/orgs\",\"repos_url\":\"https://api.github.com/users/DevinShine/repos\",
        // \"events_url\":\"https://api.github.com/users/DevinShine/events{/privacy}\",
        // \"received_events_url\":\"https://api.github.com/users/DevinShine/received_events\",\"type\":\"User\",
        // \"site_admin\":false},\"private\":false,\"html_url\":\"https://github.com/DevinShine/AnimationDemo\",
        // \"description\":\"\",\"fork\":false,\"url\":\"https://api.github.com/repos/DevinShine/AnimationDemo\",
        // \"forks_url\":\"https://api.github.com/repos/DevinShine/AnimationDemo/forks\",\"keys_url\":\"https://api
        // .github.com/repos/DevinShine/AnimationDemo/keys{/key_id}\",\"collaborators_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/collaborators{/collaborator}\",\"teams_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/teams\",\"hooks_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/hooks\",\"issue_events_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/issues/events{/number}\",\"events_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/events\",\"assignees_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/assignees{/user}\",\"branches_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/branches{/branch}\",\"tags_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/tags\",\"blobs_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/git/tags{/sha}\",\"git_refs_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/git/refs{/sha}\",\"trees_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/git/trees{/sha}\",\"statuses_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/statuses/{sha}\",\"languages_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/languages\",\"stargazers_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/stargazers\",\"contributors_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/contributors\",\"subscribers_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/subscribers\",\"subscription_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/subscription\",\"commits_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/commits{/sha}\",\"git_commits_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/git/commits{/sha}\",\"comments_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/comments{/number}\",\"issue_comment_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/issues/comments{/number}\",\"contents_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/contents/{+path}\",\"compare_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/compare/{base}...{head}\",\"merges_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/merges\",\"archive_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/downloads\",\"issues_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/issues{/number}\",\"pulls_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/pulls{/number}\",\"milestones_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/milestones{/number}\",\"notifications_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/notifications{?since,all,participating}\",\"labels_url\":\"https://api
        // .github.com/repos/DevinShine/AnimationDemo/labels{/name}\",\"releases_url\":\"https://api.github
        // .com/repos/DevinShine/AnimationDemo/releases{/id}\",\"created_at\":\"2014-05-11T15:05:26Z\",
        // \"updated_at\":\"2014-05-11T15:16:59Z\",\"pushed_at\":\"2014-05-11T15:16:58Z\",\"git_url\":\"git://github
        // .com/DevinShine/AnimationDemo.git\",\"ssh_url\":\"git@github.com:DevinShine/AnimationDemo.git\",
        // \"clone_url\":\"https://github.com/DevinShine/AnimationDemo.git\",\"svn_url\":\"https://github
        // .com/DevinShine/AnimationDemo\",\"homepage\":null,\"size\":1556,\"stargazers_count\":0,
        // \"watchers_count\":0,\"language\":\"Java\",\"has_issues\":true,\"has_downloads\":true,\"has_wiki\":true,
        // \"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"open_issues_count\":0,\"forks\":0,
        // \"open_issues\":0,\"watchers\":0,\"default_branch\":\"master\"}]";
        //        Response response =
        //            new Response("http://www.baidu.com", 200, "nothing", Collections.EMPTY_LIST,
        //                new TypedByteArray("application/json", mockJsonResult.getBytes()));
        //        when(mMockClient.execute(Matchers.anyObject())).thenReturn(response);
        //
        //        int size = mMockGithub.listRepos2Observable("devinshine")
        //            .flatMap(Observable::from)
        //            .count()
        //            .toBlocking()
        //            .single();
        //        assertThat(size, is(1));
    }

    private class ApiResultFunc<T> implements Func1<ApiResult<T>, T> {

        @Override
        public T call(ApiResult<T> httpResult) {
            if (httpResult.getStatus() != 200) {
                System.out.println("faile");
            }
            return httpResult.getData();
        }
    }
}
