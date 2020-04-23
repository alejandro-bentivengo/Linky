package org.linky.internal.url.implementations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linky.Application;
import org.linky.application.config.InternalConfiguration;
import org.linky.exceptions.AbstractBaseException;
import org.linky.internal.url.IUrlHandler;
import org.linky.model.services.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, InternalConfiguration.class})
@ComponentScan("org.linky")
public class UrlHandlerTest {

    @Autowired
    private IUrlHandler urlHandler;

    private static final Set<String> URL_FOR_TEST = Set.of(
            "www.google.com",
            "www.facebook.com",
            "stackoverflow.com",
            "www.youtube.com",
            "https://www.google.com/search?hl=en&sugexp=les;&gs_nf=1&gs_mss=how%20do%20I%20iron%20a%20s&tok=POkeFnEdGVTAw_InGMW-Og&cp=21&gs_id=2j&xhr=t&q=how%20do%20I%20iron%20a%20shirt&pf=p&sclient=psy-ab&oq=how+do+I+iron+a+shirt&gs_l=&pbx=1&bav=on.2,or.r_gc.r_pw.r_cp.r_qf.&biw=1600&bih=775&cad=h"
    );
    private static final Set<String> MALFORMED_URL_FOR_TEST = Set.of(
            "",
            "wwwfacebookcom",
            "somerandomstring2345",
            "12341324.44432",
            "https:1432//sdfasd.com"
    );


    @Test
    public void UrlValidationTestCorrect() throws AbstractBaseException {
        for (String url : URL_FOR_TEST) {
            urlHandler.handle(UrlRequest.builder().url(url).secure(true).build());
        }
    }

    @Test
    public void UrlValidationTestInvalid() {
        for (String url : MALFORMED_URL_FOR_TEST) {
            try {
                urlHandler.handle(UrlRequest.builder().url(url).build());
            } catch (AbstractBaseException exception) {
                continue;
            }
            Assert.fail("A URL passed as valid when it shouldn't have");
        }
    }

}
