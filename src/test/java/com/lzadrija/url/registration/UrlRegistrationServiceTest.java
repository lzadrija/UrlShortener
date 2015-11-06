package com.lzadrija.url.registration;

import com.lzadrija.MainConfiguration;
import com.lzadrija.url.UrlRepository;
import com.lzadrija.url.registration.account.AccountUrlRepository;
import com.lzadrija.url.shortening.UrlShorteningService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfiguration.class)
@WebAppConfiguration
public class UrlRegistrationServiceTest {
    @Mock
    private  Environment env;
    @Mock
    private  UrlShorteningService service;
    @Mock
    private  UrlRepository urlRepo;
    @Mock
    private  AccountUrlRepository accUrlRepo;
    @InjectMocks
    private  UrlRegistrationService urlRegService;

    @WithUserDetails
    @Test
    public void preAuthorizeRegister() {
    }

}
