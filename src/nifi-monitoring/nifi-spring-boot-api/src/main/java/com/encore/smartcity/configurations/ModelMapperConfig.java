package com.encore.smartcity.configurations;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();

      /*  PropertyMap<Rule, RuleDtoSave> propertyMap = new PropertyMap<Rule, RuleDtoSave> (){
            protected void configure() {
                map(source.getRuleId()).setDesc(null);
            }
        }

        mm.addMappings(propertyMap);
        */

        /*
         * TODO: Tricky -> these two lines below is to prevent model mapper error, because RuleDtoSave class does not have ruleID only Rule class does
         * */
        mm.getConfiguration().setAmbiguityIgnored(true);
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return mm;
    }

    public static  <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        ModelMapperConfig config = new ModelMapperConfig();
        return entityList.stream()
                .map(entity -> config.modelMapper().map(entity, outCLass))
                .collect(Collectors.toList());
    }
}
