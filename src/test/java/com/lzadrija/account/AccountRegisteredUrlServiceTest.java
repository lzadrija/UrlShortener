package com.lzadrija.account;

import com.lzadrija.BaseTest;
import com.lzadrija.exception.ResourceNotFoundException;
import com.lzadrija.url.statistics.UrlHitsService;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

public class AccountRegisteredUrlServiceTest extends BaseTest {

    @Mock
    private AccountRepository accountRepo;
    @Mock
    private UrlHitsService hitsService;
    @InjectMocks
    private AccountRegisteredUrlService service;

    @Test
    public void givenExistingIdGetStatistic() {

        Account a = new Account("Pippin-1956", null);
        when(accountRepo.exists(a.getId())).thenReturn(true);
        when(accountRepo.getOne(a.getId())).thenReturn(a);
        when(hitsService.getHitsByLongUrl(a)).thenReturn(new HashMap<>());

        Map<String, Long> statistic = service.getStatisticForAccount(a.getId());

        assertThat(statistic).isNotNull();
        assertThat(statistic).isEmpty();
    }

    @Test(expected = ResourceNotFoundException.class)
    public void givenInvalidIdShouldFailToGetStatistic() {

        Account a = new Account("Grimma_1921", null);
        when(accountRepo.exists(a.getId())).thenReturn(false);

        service.getStatisticForAccount(a.getId());
    }

}
