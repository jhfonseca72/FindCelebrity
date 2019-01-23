package com.globant.test.configuration;

import com.globant.test.adapter.db.gateway.DataBaseMemoryAdapter;
import com.globant.test.usescases.FindCelebrityUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    private final AppProperties properties;

    public UseCaseConfiguration(AppProperties properties) {
        this.properties = properties;
    }

    @Bean
    public FindCelebrityUseCase getFindCelebrityUseCase(DataBaseMemoryAdapter adapter) {
        FindCelebrityUseCase useCase = new FindCelebrityUseCase(adapter, this.properties.getApproach());
        return useCase;
    }
}
