package cl.goldenoddjobs.users.config;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ApiSecretFilterTest {

    private MockHttpServletRequest request(String method, String path, String secret) {
        MockHttpServletRequest request = new MockHttpServletRequest(method, path);
        if (secret != null) request.addHeader(ApiSecretFilter.HEADER, secret);
        return request;
    }

    @Test
    void requestWithoutSecretReturns401() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);
        new ApiSecretFilter("s3cr3t").doFilter(request("GET", "/api/usuarios/perfil", null), response, chain);
        assertEquals(401, response.getStatus());
        verifyNoInteractions(chain);
    }

    @Test
    void requestWithWrongSecretReturns401() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);
        new ApiSecretFilter("s3cr3t").doFilter(request("GET", "/api/usuarios/perfil", "other"), response, chain);
        assertEquals(401, response.getStatus());
        verifyNoInteractions(chain);
    }

    @Test
    void requestWithCorrectSecretPasses() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);
        MockHttpServletRequest request = request("GET", "/api/usuarios/perfil", "s3cr3t");
        new ApiSecretFilter("s3cr3t").doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void anEmptyConfiguredSecretRejectsEverything() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        new ApiSecretFilter("").doFilter(request("GET", "/api/usuarios/perfil", ""), response, mock(FilterChain.class));
        assertEquals(401, response.getStatus());
    }

    @Test
    void preflightPasses() throws Exception {
        FilterChain chain = mock(FilterChain.class);
        new ApiSecretFilter("s3cr3t").doFilter(request("OPTIONS", "/api/usuarios/perfil", null), new MockHttpServletResponse(), chain);
        verify(chain).doFilter(any(), any());
    }
}
