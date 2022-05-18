package gr.devian.owl.project.util;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;

public final class QueryUtils {
    public static ResultSet query(final Model model, final String queryString) {
        final Query query = QueryFactory.create(queryString);
        try (final QueryExecution qe = QueryExecutionFactory.create(query, model)) {
            var resultSet = qe.execSelect();
            return resultSet.materialise();
        }
    }
}
