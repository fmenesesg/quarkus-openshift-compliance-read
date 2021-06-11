package com.redhat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.openshift.client.OpenShiftClient;
import io.vertx.core.json.JsonObject;

import java.util.Map;

import javax.inject.Inject;

import org.jboss.logging.Logger;



@Path("/ccr")
public class ComplianceCheckResult {

    private static final Logger LOGGER = Logger.getLogger("ComplianceCheckResult");

    @Inject
    OpenShiftClient openshiftClient;
    
    @GET
    @Path("/{namespace}")
    @Produces(MediaType.APPLICATION_JSON)
    
    public  JsonObject ccrs(@PathParam("namespace") String namespace) {
        
        CustomResourceDefinitionContext ccr = new CustomResourceDefinitionContext
        .Builder()
        .withGroup("compliance.openshift.io")
        .withScope("Namespaced")
        .withVersion("v1alpha1")
        .withPlural("compliancecheckresults")
        .build();
        
        JsonObject jsonObject = new JsonObject(openshiftClient.customResource(ccr).inNamespace(namespace).list());
        LOGGER.info(jsonObject);
        

        return jsonObject;
    }
}
