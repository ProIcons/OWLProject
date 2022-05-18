package gr.devian.owl.project.queries;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public interface BaseQuery {
    ResultSet executeQuery(Object[] arguments, Model model);

    Object[][] executionCases();
}
