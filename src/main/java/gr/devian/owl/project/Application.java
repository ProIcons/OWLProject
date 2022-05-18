package gr.devian.owl.project;

import gr.devian.owl.project.queries.*;
import gr.devian.owl.project.util.ModelLoader;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;

import java.io.IOException;

public class Application {
    private final InfModel infModel;

    public Application(final String rdfModel) throws IOException {
        final OntModel ontModel = ModelLoader.load(rdfModel);
        final Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        this.infModel = ModelFactory.createInfModel(reasoner, ontModel);
    }

    public static void main(final String[] args) throws IOException {
        final Application application = new Application("mpo.xml");
        application.runAllQueries();
    }

    public void runAllQueries() {
        final QueryCaseExecutor queryCaseExecutor = new QueryCaseExecutor();
        queryCaseExecutor.register(new CoStarsOfActor());
        queryCaseExecutor.register(new ActorsThatAreYoungerThan());
        queryCaseExecutor.register(new PeopleThatHaveMoreThanOneRolesInMovie());
        queryCaseExecutor.register(new EquipmentUsedInMovie());
        queryCaseExecutor.register(new EquipmentManufacturersUsedByMovie());
        queryCaseExecutor.register(new GetMovieCreditsWithoutExtras());
        queryCaseExecutor.register(new GetActorWithTheMostMoviesLead());
        queryCaseExecutor.register(new MostUsedGenreByActor());

        queryCaseExecutor.execute(this.infModel);
    }
}
