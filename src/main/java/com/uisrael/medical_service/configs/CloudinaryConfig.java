package com.uisrael.medical_service.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxtfj9kvg",
                "api_key", "424141944812271",
                "api_secret", "VGjKpsT4HLcIIyEQTTkTLYjweMI"
        ));
    }
}
