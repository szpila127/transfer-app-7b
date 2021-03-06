package com.transfer.app7f.service;

import com.transfer.app7f.config.AppConfig;
import com.transfer.app7f.config.JsonBuilder;
import com.transfer.app7f.domain.dto.AccountDto;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private RestTemplate restTemplate = new RestTemplate();
    private JsonBuilder<AccountDto> jsonBuilder = new JsonBuilder<>();
    private List<AccountDto> accountDtos;

    public Set<AccountDto> getAccountDtos() {
        return new HashSet<>(accountDtos);
    }

    public void fetchAll() {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "account")
                .encode()
                .build()
                .toUri();
        Optional<AccountDto[]> accounts = Optional.ofNullable(restTemplate.getForObject(url, AccountDto[].class));
        accountDtos = new ArrayList<>(accounts
                .map(Arrays::asList)
                .orElse(new ArrayList<>()));
    }

    public List<AccountDto> filterByCurrency(String string) {
        string = string.toUpperCase();
        String finalString = string;
        return accountDtos.stream()
                .filter(accountDto -> accountDto.getCurrency().contains(finalString))
                .collect(Collectors.toList());
    }

    public List<AccountDto> filterByUserId(String id) {
        return accountDtos.stream()
                .filter(accountDto -> accountDto.getUserId().contains(id))
                .collect(Collectors.toList());
    }

    public void save(AccountDto accountDto) {
        String url = AppConfig.backendEndpoint + "account";
        try {
            restTemplate.postForObject(url, (accountDto), Void.class);
        } catch (RestClientException e) {
            LOGGER.error("" + e);
        }
    }

    public void update(AccountDto accountDto) {
        String url = AppConfig.backendEndpoint + "account";
        restTemplate.put(url, jsonBuilder.prepareJson(accountDto));
    }

    public void delete(long id) {
        URI url = UriComponentsBuilder.fromHttpUrl(AppConfig.backendEndpoint + "account/" + id)
                .encode()
                .build()
                .toUri();
        restTemplate.delete(url);
    }
}
