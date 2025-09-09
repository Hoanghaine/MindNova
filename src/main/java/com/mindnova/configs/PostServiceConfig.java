package com.mindnova.configs;

import com.mindnova.repositories.PostRepository;
import com.mindnova.repositories.UserRepository;
import com.mindnova.services.PostService;
import com.mindnova.services.decorators.DuplicateCheckDecorator;
import com.mindnova.services.decorators.ProfanityFilterDecorator;
import com.mindnova.services.impl.PostServiceImpl;
import com.mindnova.services.proxy.LoggingProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostServiceConfig {

    @Bean
    public PostService postService(PostRepository postRepository, UserRepository userRepository,@Value("${badwords.path}") String filePath) {
        PostService core = new PostServiceImpl(postRepository,userRepository );

        PostService decorated = new DuplicateCheckDecorator(
                new ProfanityFilterDecorator(core,filePath), postRepository
        );

        return new LoggingProxy(decorated);
    }
}
