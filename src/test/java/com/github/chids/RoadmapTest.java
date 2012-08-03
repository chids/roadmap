package com.github.chids;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import com.github.chids.roadmap.Project;
import com.google.common.io.NullOutputStream;
import com.google.common.io.Resources;
import com.yammer.dropwizard.config.ConfigurationException;
import com.yammer.dropwizard.config.ConfigurationFactory;
import com.yammer.dropwizard.validation.Validator;

public class RoadmapTest {

    @Test
    public void basicParseAndWrite() throws IOException, URISyntaxException, ConfigurationException {
        final ConfigurationFactory<Project> factory = ConfigurationFactory.forClass(Project.class, new Validator());
        final Project project = factory.build(new File(Resources.getResource("sample.yaml").toURI()));
        project.writeTo(new NullOutputStream());
    }
}
