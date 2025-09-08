package com.mindnova.factories;

import com.mindnova.adapter.SocialLoginAdapter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SocialLoginAdapterFactory {

    private final Map<String, SocialLoginAdapter> adapters;

    public SocialLoginAdapterFactory(List<SocialLoginAdapter> adapters) {
        // map key dựa trên provider name, in hoa để đồng bộ với frontend
        this.adapters = adapters.stream()
                .collect(Collectors.toMap(
                        a -> a.getProviderName().toUpperCase(),
                        Function.identity()
                ));
    }

    public SocialLoginAdapter getAdapter(String provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Provider cannot be null");
        }
        SocialLoginAdapter adapter = adapters.get(provider.toUpperCase());
        if (adapter == null) {
            throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
        return adapter;
    }
}
