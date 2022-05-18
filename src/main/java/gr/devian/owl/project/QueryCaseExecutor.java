package gr.devian.owl.project;

import gr.devian.owl.project.queries.BaseQuery;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;

import java.util.LinkedHashSet;
import java.util.Set;

public class QueryCaseExecutor {
    private final Set<BaseQuery> queryCases = new LinkedHashSet<>();

    public void register(final BaseQuery query) {
        this.queryCases.add(query);
    }

    public void execute(final InfModel model) {
        for (final BaseQuery query : this.queryCases) {

            if (query.executionCases().length == 0) {
                final ResultSet result = query.executeQuery(null, model);
                ResultSetFormatter.out(System.out, result);
                continue;
            }

            for (final Object[] args : query.executionCases()) {
                final ResultSet result = query.executeQuery(args, model);
                ResultSetFormatter.out(System.out, result);
            }
        }
    }
}
