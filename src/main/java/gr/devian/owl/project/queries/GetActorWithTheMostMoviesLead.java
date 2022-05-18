package gr.devian.owl.project.queries;

import gr.devian.owl.project.util.QueryUtils;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class GetActorWithTheMostMoviesLead implements BaseQuery {
    @Override
    public ResultSet executeQuery(final Object[] arguments, final Model model) {
        System.out.println("""
                                
                Actor with the most movies as a lead actor.
                """);

        final String queryString = """
                PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                PREFIX ont: <http://devian.gr/movieproductionontology#>
                            
                SELECT DISTINCT
                    ?actor
                    (COUNT(?movie) as ?movies)
                WHERE {
                  ?actor ont:wasLeadInMovie ?movie.
                }
                GROUP BY ?actor
                ORDER BY DESC(?movies)
                LIMIT 1
                """;

        return QueryUtils.query(model, queryString);
    }

    @Override
    public Object[][] executionCases() {
        return new Object[][]{};
    }
}
