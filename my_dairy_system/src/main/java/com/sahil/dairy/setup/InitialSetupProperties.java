package com.sahil.dairy.setup;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.initial-setup")
public class InitialSetupProperties {

    private Owner owner = new Owner();

    private Farm farm = new Farm();

    @Getter
    @Setter
    public static class Owner {

        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String password;
    }

    @Getter
    @Setter
    public static class Farm {

        private String farmName;
        private String phone;
        private String email;
        private String address;
        private String totalLandAcres;
    }
}