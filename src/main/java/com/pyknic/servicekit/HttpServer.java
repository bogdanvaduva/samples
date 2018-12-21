/*
 * Copyright 2015 Emil Forslund.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pyknic.servicekit;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract super class for HTTP servers. To use ServiceKit, create a subclass to
 * {@code HttpServer} and attach a {@link Service} annotation to all methods that
 * should be accessible over standard http.
 * <p>
 * This project uses the NanoHTTPD library by {@url https://github.com/elonen elonen}.
 *
 * @author  Emil Forslund
 * @see     {@url https://github.com/NanoHttpd/nanohttpd}
 */
public abstract class HttpServer {

    private final int port;
    private final NanoHTTPD server;
    private final Map<String, ServiceHook<HttpServer>> hooks;

    /**
     * Creates a new HTTP server, parsing the subclass of this for methods annoted
     * using the {@link Service} annotation. To launch the server you must still call
     * {@link ::start()}.
     *
     * @param port  the port to open this server on
     */
    protected HttpServer(int port) {
        this.port = port;
        this.server = new NanoHTTPD(port) {

            @Override
            public Response serve(IHTTPSession session) {
                final URI uri;
                final Map<String, String> params;
                final String path;
                final String service;

                try {
                    uri     = new URI(session.getUri());
                    params  = session.getParms();
                    path = parseURIForPath(uri);
                    service = parseURIForService(uri);
                } catch (URISyntaxException | ServiceException ex) {
                    return newFixedLengthResponse(Status.BAD_REQUEST, "text/plain", ex.getMessage());
                }

                final ServiceHook<HttpServer> hook;
                try {
                    hook = findCorrectHook(path, service);
                } catch (ServiceException ex) {
                    return newFixedLengthResponse(Status.NOT_FOUND, "text/plain", ex.getMessage());
                }

                final String result;
                
                try {
                    result = hook.getCache().get(session.getQueryParameterString(), 
                        u -> hook.call(params)
                    );
                } catch (HttpResponseException ex) {
                    return ex.createResponse();
                } catch (ServiceException ex) {
                    System.err.println(ex.getMessage());
                    return newFixedLengthResponse(
                        Status.INTERNAL_ERROR, "text/plain", ex.getMessage());
                }

                return newFixedLengthResponse(Status.OK, hook.getEncoder().getMimeType(), result);
            }
        };
        
        hooks = createServiceHooks();
    }

    /**
     * Starts the server. The operation could fail for an example if the port specified
     * in the constructor is already used by some other application.
     *
     * @return              a reference to this
     * @throws IOException  if the instantiation failed
     * @see                 NanoHTTPD::start()
     */
    public final HttpServer start() throws IOException {
        server.start();
        onStarted();
        return this;
    }
    
    public void onStarted() {}

    /**
     * Stops the server.
     *
     * @return  a reference to this
     * @see     NanoHTTPD::stop()
     */
    public final HttpServer stop() {
        server.stop();
        onStopped();
        return this;
    }
    
    public void onStopped() {}
    
    private Map<String, ServiceHook<HttpServer>> createServiceHooks() {
        return Stream.of(getClass().getMethods())
            .filter(m -> m.getAnnotation(Service.class) != null)
            .map(m -> {
                return ServiceHook.create(this, m);
            })
            .collect(Collectors.toMap(e -> e.getPath() + e.getName(), e -> e));
    }
    
    private String parseURIForPath(URI uri) throws ServiceException {
        List<String> retVal = parseURI(uri);
        if (retVal.size() == 1) {
            return "";
        } else {
            return retVal.subList(0, retVal.size()-1).stream().map((s) -> s + "/").reduce("", String::concat);
        }
    }

    private String parseURIForService(URI uri) throws ServiceException {
        List<String> retVal = parseURI(uri);
        if (retVal.size() >= 1) { 
            return retVal.get(retVal.size()-1);
        } else {
            throw new ServiceException(
                "No service specified in uri: '" + uri.toString() + "'."
            );            
        }
    }

    private List<String> parseURI(URI uri) {
        List<String> elements = new ArrayList();
        Stream.of(uri.getPath())
            .filter(p -> { 
                return p != null; 
            })
            .flatMap(p -> {
                return Stream.of(p.split("/"));
            })
            .filter(p -> !p.isEmpty())
            .forEach(p -> {
                elements.add(p); 
            });        
        return elements;
    }
    private ServiceHook<HttpServer> findCorrectHook(String path, String service) throws ServiceException {
        return Optional.ofNullable(hooks.get(path + service))
            .orElseThrow(
                () -> new ServiceException(
                    "The specified service '" + service + "' could not be found."
                )
            );
    }
    
    public static <T extends HttpServer> void run(Class<T> serverClass) throws ServiceException {
        final HttpServer server;
        
        try {
            server = serverClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new ServiceException(
                "Server class '" + serverClass.getSimpleName() + 
                "' could not be instantiated with default constructor.",
                ex
            );
        }
        
        try {
            server.start();
        } catch (IOException ex) {
            throw new ServiceException(
                "Server '" + serverClass.getSimpleName() + 
                "' could not be started on port '" + server.port + "'.", 
                ex
            );
        }

        System.out.println("Server started, Hit Enter to stop.");

        try {System.in.read();} 
        catch (Throwable ignored) {}
        finally {
            server.stop();
        }
        
        System.out.println("Server stopped.");
    }
}