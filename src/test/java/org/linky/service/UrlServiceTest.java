package org.linky.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linky.Application;
import org.linky.application.config.InternalConfiguration;
import org.linky.exceptions.AbstractBaseException;
import org.linky.model.internal.acl.User;
import org.linky.model.internal.urls.Url;
import org.linky.model.services.UrlRequest;
import org.linky.repository.UrlRepository;
import org.linky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, InternalConfiguration.class})
@ComponentScan("org.linky")
@Transactional
public class UrlServiceTest {

    @Autowired
    private UrlService service;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UrlRepository urlRepository;

    public User GenerateTestBed() {
        User testUser = User.builder()
                .username("username")
                .password("password")
                .build();
        userRepository.save(testUser);
        Url url = Url.builder()
                .code("randomCode")
                .url("aURL")
                .user(testUser)
                .build();
        urlRepository.save(url);
        testUser.setUrls(Set.of(url));
        SecurityContextHolder
                .getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword()));
        return testUser;
    }

    @Test
    public void AddUrlTest() throws AbstractBaseException {
        GenerateTestBed();
        service.newUrl(UrlRequest.builder().url("www.google.com").secure(true).build());
    }

    @Test
    public void GetUrlsTest() throws AbstractBaseException {
        User user = GenerateTestBed();
        Assert.assertEquals(user.getUrls().size(), service.getUserUrls().size());
    }

    @Test
    public void EditUrlsTest() throws AbstractBaseException {
        User user = GenerateTestBed();
        Url url = user.getUrls().toArray(new Url[user.getUrls().size()])[0];
        service.editUrl(url.getCode(), UrlRequest.builder().url("https://www.url.com").secure(true).build());
        List<Url> urls = new ArrayList<>(service.getUserUrls());
        Assert.assertEquals("https://www.url.com", urls.get(0).getUrl());
        Assert.assertTrue(urls.get(0).getSecure());
    }

    @Test
    public void DeleteUrlsTest() throws AbstractBaseException {
        User user = GenerateTestBed();
        Url url = service.getUserUrls().toArray(new Url[user.getUrls().size()])[0];
        service.deleteUrl(url.getCode());
    }

    @Test
    public void GetUrlByCodeTest() throws AbstractBaseException {
        User user = GenerateTestBed();
        Url url = user.getUrls().toArray(new Url[user.getUrls().size()])[0];
        Url url2 = service.getUrlByCode(url.getCode());
        Assert.assertEquals(url.getCode(), url2.getCode());
        Assert.assertEquals(url.getUrl(), url2.getUrl());
        Assert.assertEquals(url.getSecure(), url2.getSecure());
    }

}
