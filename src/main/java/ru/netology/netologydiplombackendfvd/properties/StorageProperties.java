package ru.netology.netologydiplombackendfvd.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file")
@Component
//надо ещё почитать https://www.baeldung.com/configuration-properties-in-spring-boot
// без @Component не работает, хотя вроде как уже можно без этой аннотации
public class StorageProperties {
    private String rootLocation;

    public String getRootLocation() {
        return rootLocation;
    }

    public void setRootLocation(String rootLocation) {
        this.rootLocation = rootLocation;
    }
}
