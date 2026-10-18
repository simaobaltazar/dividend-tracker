package pt.simaobaltazar.dividendtracker.config;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiring -> wiring
                .scalar(ExtendedScalars.GraphQLBigDecimal)
                .scalar(ExtendedScalars.Date);
    }
}
