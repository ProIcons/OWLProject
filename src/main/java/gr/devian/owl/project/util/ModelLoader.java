package gr.devian.owl.project.util;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public final class ModelLoader {
    public static OntModel load(String fileName) throws IOException {
        final File file = Path.of(fileName).toFile();
        if (!file.exists()) {
            throw new FileNotFoundException(fileName);
        }

        final OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        try (FileReader reader = new FileReader(file)) {
            model.read(reader, null);
        } catch (IOException e) {
            throw new IOException("Failed to read model file");
        }

        return model;
    }
}

